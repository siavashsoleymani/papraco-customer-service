package com.papraco.customerservice.web.rest;

import static com.papraco.customerservice.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.Product;
import com.papraco.customerservice.domain.enumeration.ProductKind;
import com.papraco.customerservice.repository.ProductRepository;
import com.papraco.customerservice.service.dto.ProductDTO;
import com.papraco.customerservice.service.mapper.ProductMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ProductKind DEFAULT_PRODUCT_KIND = ProductKind.SERVICE;
    private static final ProductKind UPDATED_PRODUCT_KIND = ProductKind.PRODUCT;

    private static final Long DEFAULT_REMAIN_COUNT = 1L;
    private static final Long UPDATED_REMAIN_COUNT = 2L;

    private static final Long DEFAULT_RESERVED_COUNT = 1L;
    private static final Long UPDATED_RESERVED_COUNT = 2L;

    private static final BigDecimal DEFAULT_BOUGHT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUGHT_COST = new BigDecimal(2);

    private static final Instant DEFAULT_BOUGHT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOUGHT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FACTURE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FACTURE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ORIGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity() {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .productKind(DEFAULT_PRODUCT_KIND)
            .remainCount(DEFAULT_REMAIN_COUNT)
            .reservedCount(DEFAULT_RESERVED_COUNT)
            .boughtCost(DEFAULT_BOUGHT_COST)
            .boughtAt(DEFAULT_BOUGHT_AT)
            .factureNumber(DEFAULT_FACTURE_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .productOrigin(DEFAULT_PRODUCT_ORIGIN);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity() {
        Product product = new Product()
            .name(UPDATED_NAME)
            .productKind(UPDATED_PRODUCT_KIND)
            .remainCount(UPDATED_REMAIN_COUNT)
            .reservedCount(UPDATED_RESERVED_COUNT)
            .boughtCost(UPDATED_BOUGHT_COST)
            .boughtAt(UPDATED_BOUGHT_AT)
            .factureNumber(UPDATED_FACTURE_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .productOrigin(UPDATED_PRODUCT_ORIGIN);
        return product;
    }

    @BeforeEach
    public void initTest() {
        productRepository.deleteAll();
        product = createEntity();
    }

    @Test
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getProductKind()).isEqualTo(DEFAULT_PRODUCT_KIND);
        assertThat(testProduct.getRemainCount()).isEqualTo(DEFAULT_REMAIN_COUNT);
        assertThat(testProduct.getReservedCount()).isEqualTo(DEFAULT_RESERVED_COUNT);
        assertThat(testProduct.getBoughtCost()).isEqualByComparingTo(DEFAULT_BOUGHT_COST);
        assertThat(testProduct.getBoughtAt()).isEqualTo(DEFAULT_BOUGHT_AT);
        assertThat(testProduct.getFactureNumber()).isEqualTo(DEFAULT_FACTURE_NUMBER);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getProductOrigin()).isEqualTo(DEFAULT_PRODUCT_ORIGIN);
    }

    @Test
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId("existing_id");
        ProductDTO productDTO = productMapper.toDto(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].productKind").value(hasItem(DEFAULT_PRODUCT_KIND.toString())))
            .andExpect(jsonPath("$.[*].remainCount").value(hasItem(DEFAULT_REMAIN_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].reservedCount").value(hasItem(DEFAULT_RESERVED_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].boughtCost").value(hasItem(sameNumber(DEFAULT_BOUGHT_COST))))
            .andExpect(jsonPath("$.[*].boughtAt").value(hasItem(DEFAULT_BOUGHT_AT.toString())))
            .andExpect(jsonPath("$.[*].factureNumber").value(hasItem(DEFAULT_FACTURE_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].productOrigin").value(hasItem(DEFAULT_PRODUCT_ORIGIN)));
    }

    @Test
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.productKind").value(DEFAULT_PRODUCT_KIND.toString()))
            .andExpect(jsonPath("$.remainCount").value(DEFAULT_REMAIN_COUNT.intValue()))
            .andExpect(jsonPath("$.reservedCount").value(DEFAULT_RESERVED_COUNT.intValue()))
            .andExpect(jsonPath("$.boughtCost").value(sameNumber(DEFAULT_BOUGHT_COST)))
            .andExpect(jsonPath("$.boughtAt").value(DEFAULT_BOUGHT_AT.toString()))
            .andExpect(jsonPath("$.factureNumber").value(DEFAULT_FACTURE_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.productOrigin").value(DEFAULT_PRODUCT_ORIGIN));
    }

    @Test
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        updatedProduct
            .name(UPDATED_NAME)
            .productKind(UPDATED_PRODUCT_KIND)
            .remainCount(UPDATED_REMAIN_COUNT)
            .reservedCount(UPDATED_RESERVED_COUNT)
            .boughtCost(UPDATED_BOUGHT_COST)
            .boughtAt(UPDATED_BOUGHT_AT)
            .factureNumber(UPDATED_FACTURE_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .productOrigin(UPDATED_PRODUCT_ORIGIN);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getProductKind()).isEqualTo(UPDATED_PRODUCT_KIND);
        assertThat(testProduct.getRemainCount()).isEqualTo(UPDATED_REMAIN_COUNT);
        assertThat(testProduct.getReservedCount()).isEqualTo(UPDATED_RESERVED_COUNT);
        assertThat(testProduct.getBoughtCost()).isEqualTo(UPDATED_BOUGHT_COST);
        assertThat(testProduct.getBoughtAt()).isEqualTo(UPDATED_BOUGHT_AT);
        assertThat(testProduct.getFactureNumber()).isEqualTo(UPDATED_FACTURE_NUMBER);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductOrigin()).isEqualTo(UPDATED_PRODUCT_ORIGIN);
    }

    @Test
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .remainCount(UPDATED_REMAIN_COUNT)
            .boughtCost(UPDATED_BOUGHT_COST)
            .boughtAt(UPDATED_BOUGHT_AT)
            .description(UPDATED_DESCRIPTION)
            .productOrigin(UPDATED_PRODUCT_ORIGIN);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getProductKind()).isEqualTo(DEFAULT_PRODUCT_KIND);
        assertThat(testProduct.getRemainCount()).isEqualTo(UPDATED_REMAIN_COUNT);
        assertThat(testProduct.getReservedCount()).isEqualTo(DEFAULT_RESERVED_COUNT);
        assertThat(testProduct.getBoughtCost()).isEqualByComparingTo(UPDATED_BOUGHT_COST);
        assertThat(testProduct.getBoughtAt()).isEqualTo(UPDATED_BOUGHT_AT);
        assertThat(testProduct.getFactureNumber()).isEqualTo(DEFAULT_FACTURE_NUMBER);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductOrigin()).isEqualTo(UPDATED_PRODUCT_ORIGIN);
    }

    @Test
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .name(UPDATED_NAME)
            .productKind(UPDATED_PRODUCT_KIND)
            .remainCount(UPDATED_REMAIN_COUNT)
            .reservedCount(UPDATED_RESERVED_COUNT)
            .boughtCost(UPDATED_BOUGHT_COST)
            .boughtAt(UPDATED_BOUGHT_AT)
            .factureNumber(UPDATED_FACTURE_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .productOrigin(UPDATED_PRODUCT_ORIGIN);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getProductKind()).isEqualTo(UPDATED_PRODUCT_KIND);
        assertThat(testProduct.getRemainCount()).isEqualTo(UPDATED_REMAIN_COUNT);
        assertThat(testProduct.getReservedCount()).isEqualTo(UPDATED_RESERVED_COUNT);
        assertThat(testProduct.getBoughtCost()).isEqualByComparingTo(UPDATED_BOUGHT_COST);
        assertThat(testProduct.getBoughtAt()).isEqualTo(UPDATED_BOUGHT_AT);
        assertThat(testProduct.getFactureNumber()).isEqualTo(UPDATED_FACTURE_NUMBER);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductOrigin()).isEqualTo(UPDATED_PRODUCT_ORIGIN);
    }

    @Test
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID().toString());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
