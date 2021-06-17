package com.papraco.customerservice.web.rest;

import static com.papraco.customerservice.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.FactureItem;
import com.papraco.customerservice.domain.enumeration.MeasureType;
import com.papraco.customerservice.repository.FactureItemRepository;
import com.papraco.customerservice.service.dto.FactureItemDTO;
import com.papraco.customerservice.service.mapper.FactureItemMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link FactureItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureItemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final MeasureType DEFAULT_MEASURE_TYPE = MeasureType.NUMBER;
    private static final MeasureType UPDATED_MEASURE_TYPE = MeasureType.KILO;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/facture-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FactureItemRepository factureItemRepository;

    @Autowired
    private FactureItemMapper factureItemMapper;

    @Autowired
    private MockMvc restFactureItemMockMvc;

    private FactureItem factureItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureItem createEntity() {
        FactureItem factureItem = new FactureItem()
            .description(DEFAULT_DESCRIPTION)
            .count(DEFAULT_COUNT)
            .measureType(DEFAULT_MEASURE_TYPE)
            .amount(DEFAULT_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .discount(DEFAULT_DISCOUNT)
            .tax(DEFAULT_TAX);
        return factureItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactureItem createUpdatedEntity() {
        FactureItem factureItem = new FactureItem()
            .description(UPDATED_DESCRIPTION)
            .count(UPDATED_COUNT)
            .measureType(UPDATED_MEASURE_TYPE)
            .amount(UPDATED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX);
        return factureItem;
    }

    @BeforeEach
    public void initTest() {
        factureItemRepository.deleteAll();
        factureItem = createEntity();
    }

    @Test
    void createFactureItem() throws Exception {
        int databaseSizeBeforeCreate = factureItemRepository.findAll().size();
        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);
        restFactureItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeCreate + 1);
        FactureItem testFactureItem = factureItemList.get(factureItemList.size() - 1);
        assertThat(testFactureItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactureItem.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testFactureItem.getMeasureType()).isEqualTo(DEFAULT_MEASURE_TYPE);
        assertThat(testFactureItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFactureItem.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testFactureItem.getDiscount()).isEqualByComparingTo(DEFAULT_DISCOUNT);
        assertThat(testFactureItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
    }

    @Test
    void createFactureItemWithExistingId() throws Exception {
        // Create the FactureItem with an existing ID
        factureItem.setId("existing_id");
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        int databaseSizeBeforeCreate = factureItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFactureItems() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        // Get all the factureItemList
        restFactureItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureItem.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].measureType").value(hasItem(DEFAULT_MEASURE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(sameNumber(DEFAULT_DISCOUNT))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))));
    }

    @Test
    void getFactureItem() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        // Get the factureItem
        restFactureItemMockMvc
            .perform(get(ENTITY_API_URL_ID, factureItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factureItem.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.measureType").value(DEFAULT_MEASURE_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.discount").value(sameNumber(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)));
    }

    @Test
    void getNonExistingFactureItem() throws Exception {
        // Get the factureItem
        restFactureItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewFactureItem() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();

        // Update the factureItem
        FactureItem updatedFactureItem = factureItemRepository.findById(factureItem.getId()).get();
        updatedFactureItem
            .description(UPDATED_DESCRIPTION)
            .count(UPDATED_COUNT)
            .measureType(UPDATED_MEASURE_TYPE)
            .amount(UPDATED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX);
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(updatedFactureItem);

        restFactureItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
        FactureItem testFactureItem = factureItemList.get(factureItemList.size() - 1);
        assertThat(testFactureItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactureItem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFactureItem.getMeasureType()).isEqualTo(UPDATED_MEASURE_TYPE);
        assertThat(testFactureItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFactureItem.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testFactureItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testFactureItem.getTax()).isEqualTo(UPDATED_TAX);
    }

    @Test
    void putNonExistingFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFactureItemWithPatch() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();

        // Update the factureItem using partial update
        FactureItem partialUpdatedFactureItem = new FactureItem();
        partialUpdatedFactureItem.setId(factureItem.getId());

        partialUpdatedFactureItem
            .count(UPDATED_COUNT)
            .measureType(UPDATED_MEASURE_TYPE)
            .amount(UPDATED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .discount(UPDATED_DISCOUNT);

        restFactureItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureItem))
            )
            .andExpect(status().isOk());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
        FactureItem testFactureItem = factureItemList.get(factureItemList.size() - 1);
        assertThat(testFactureItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactureItem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFactureItem.getMeasureType()).isEqualTo(UPDATED_MEASURE_TYPE);
        assertThat(testFactureItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFactureItem.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testFactureItem.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testFactureItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
    }

    @Test
    void fullUpdateFactureItemWithPatch() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();

        // Update the factureItem using partial update
        FactureItem partialUpdatedFactureItem = new FactureItem();
        partialUpdatedFactureItem.setId(factureItem.getId());

        partialUpdatedFactureItem
            .description(UPDATED_DESCRIPTION)
            .count(UPDATED_COUNT)
            .measureType(UPDATED_MEASURE_TYPE)
            .amount(UPDATED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX);

        restFactureItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactureItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactureItem))
            )
            .andExpect(status().isOk());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
        FactureItem testFactureItem = factureItemList.get(factureItemList.size() - 1);
        assertThat(testFactureItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactureItem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFactureItem.getMeasureType()).isEqualTo(UPDATED_MEASURE_TYPE);
        assertThat(testFactureItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFactureItem.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testFactureItem.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testFactureItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
    }

    @Test
    void patchNonExistingFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFactureItem() throws Exception {
        int databaseSizeBeforeUpdate = factureItemRepository.findAll().size();
        factureItem.setId(UUID.randomUUID().toString());

        // Create the FactureItem
        FactureItemDTO factureItemDTO = factureItemMapper.toDto(factureItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(factureItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactureItem in the database
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFactureItem() throws Exception {
        // Initialize the database
        factureItemRepository.save(factureItem);

        int databaseSizeBeforeDelete = factureItemRepository.findAll().size();

        // Delete the factureItem
        restFactureItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, factureItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FactureItem> factureItemList = factureItemRepository.findAll();
        assertThat(factureItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
