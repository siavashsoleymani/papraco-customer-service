package com.papraco.customerservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.Facture;
import com.papraco.customerservice.domain.enumeration.FactureType;
import com.papraco.customerservice.repository.FactureRepository;
import com.papraco.customerservice.service.dto.FactureDTO;
import com.papraco.customerservice.service.mapper.FactureMapper;
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
 * Integration tests for the {@link FactureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactureResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final FactureType DEFAULT_FACTURE_TYPE = FactureType.OFFICIAL;
    private static final FactureType UPDATED_FACTURE_TYPE = FactureType.UNOFFICIAL;

    private static final Boolean DEFAULT_AGREED = false;
    private static final Boolean UPDATED_AGREED = true;

    private static final String DEFAULT_NOTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECKEDOUT = false;
    private static final Boolean UPDATED_CHECKEDOUT = true;

    private static final String ENTITY_API_URL = "/api/factures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureMapper factureMapper;

    @Autowired
    private MockMvc restFactureMockMvc;

    private Facture facture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createEntity() {
        Facture facture = new Facture()
            .title(DEFAULT_TITLE)
            .factureType(DEFAULT_FACTURE_TYPE)
            .agreed(DEFAULT_AGREED)
            .notification(DEFAULT_NOTIFICATION)
            .checkedout(DEFAULT_CHECKEDOUT);
        return facture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createUpdatedEntity() {
        Facture facture = new Facture()
            .title(UPDATED_TITLE)
            .factureType(UPDATED_FACTURE_TYPE)
            .agreed(UPDATED_AGREED)
            .notification(UPDATED_NOTIFICATION)
            .checkedout(UPDATED_CHECKEDOUT);
        return facture;
    }

    @BeforeEach
    public void initTest() {
        factureRepository.deleteAll();
        facture = createEntity();
    }

    @Test
    void createFacture() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();
        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);
        restFactureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isCreated());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate + 1);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFacture.getFactureType()).isEqualTo(DEFAULT_FACTURE_TYPE);
        assertThat(testFacture.getAgreed()).isEqualTo(DEFAULT_AGREED);
        assertThat(testFacture.getNotification()).isEqualTo(DEFAULT_NOTIFICATION);
        assertThat(testFacture.getCheckedout()).isEqualTo(DEFAULT_CHECKEDOUT);
    }

    @Test
    void createFactureWithExistingId() throws Exception {
        // Create the Facture with an existing ID
        facture.setId("existing_id");
        FactureDTO factureDTO = factureMapper.toDto(facture);

        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFactures() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        // Get all the factureList
        restFactureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].factureType").value(hasItem(DEFAULT_FACTURE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].agreed").value(hasItem(DEFAULT_AGREED.booleanValue())))
            .andExpect(jsonPath("$.[*].notification").value(hasItem(DEFAULT_NOTIFICATION)))
            .andExpect(jsonPath("$.[*].checkedout").value(hasItem(DEFAULT_CHECKEDOUT.booleanValue())));
    }

    @Test
    void getFacture() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        // Get the facture
        restFactureMockMvc
            .perform(get(ENTITY_API_URL_ID, facture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facture.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.factureType").value(DEFAULT_FACTURE_TYPE.toString()))
            .andExpect(jsonPath("$.agreed").value(DEFAULT_AGREED.booleanValue()))
            .andExpect(jsonPath("$.notification").value(DEFAULT_NOTIFICATION))
            .andExpect(jsonPath("$.checkedout").value(DEFAULT_CHECKEDOUT.booleanValue()));
    }

    @Test
    void getNonExistingFacture() throws Exception {
        // Get the facture
        restFactureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewFacture() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture
        Facture updatedFacture = factureRepository.findById(facture.getId()).get();
        updatedFacture
            .title(UPDATED_TITLE)
            .factureType(UPDATED_FACTURE_TYPE)
            .agreed(UPDATED_AGREED)
            .notification(UPDATED_NOTIFICATION)
            .checkedout(UPDATED_CHECKEDOUT);
        FactureDTO factureDTO = factureMapper.toDto(updatedFacture);

        restFactureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFacture.getFactureType()).isEqualTo(UPDATED_FACTURE_TYPE);
        assertThat(testFacture.getAgreed()).isEqualTo(UPDATED_AGREED);
        assertThat(testFacture.getNotification()).isEqualTo(UPDATED_NOTIFICATION);
        assertThat(testFacture.getCheckedout()).isEqualTo(UPDATED_CHECKEDOUT);
    }

    @Test
    void putNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFactureWithPatch() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture using partial update
        Facture partialUpdatedFacture = new Facture();
        partialUpdatedFacture.setId(facture.getId());

        partialUpdatedFacture.title(UPDATED_TITLE);

        restFactureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacture))
            )
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFacture.getFactureType()).isEqualTo(DEFAULT_FACTURE_TYPE);
        assertThat(testFacture.getAgreed()).isEqualTo(DEFAULT_AGREED);
        assertThat(testFacture.getNotification()).isEqualTo(DEFAULT_NOTIFICATION);
        assertThat(testFacture.getCheckedout()).isEqualTo(DEFAULT_CHECKEDOUT);
    }

    @Test
    void fullUpdateFactureWithPatch() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture using partial update
        Facture partialUpdatedFacture = new Facture();
        partialUpdatedFacture.setId(facture.getId());

        partialUpdatedFacture
            .title(UPDATED_TITLE)
            .factureType(UPDATED_FACTURE_TYPE)
            .agreed(UPDATED_AGREED)
            .notification(UPDATED_NOTIFICATION)
            .checkedout(UPDATED_CHECKEDOUT);

        restFactureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacture))
            )
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFacture.getFactureType()).isEqualTo(UPDATED_FACTURE_TYPE);
        assertThat(testFacture.getAgreed()).isEqualTo(UPDATED_AGREED);
        assertThat(testFacture.getNotification()).isEqualTo(UPDATED_NOTIFICATION);
        assertThat(testFacture.getCheckedout()).isEqualTo(UPDATED_CHECKEDOUT);
    }

    @Test
    void patchNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();
        facture.setId(UUID.randomUUID().toString());

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(factureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFacture() throws Exception {
        // Initialize the database
        factureRepository.save(facture);

        int databaseSizeBeforeDelete = factureRepository.findAll().size();

        // Delete the facture
        restFactureMockMvc
            .perform(delete(ENTITY_API_URL_ID, facture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
