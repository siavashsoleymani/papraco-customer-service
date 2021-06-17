package com.papraco.customerservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.ContractKind;
import com.papraco.customerservice.repository.ContractKindRepository;
import com.papraco.customerservice.service.dto.ContractKindDTO;
import com.papraco.customerservice.service.mapper.ContractKindMapper;
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
 * Integration tests for the {@link ContractKindResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractKindResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contract-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContractKindRepository contractKindRepository;

    @Autowired
    private ContractKindMapper contractKindMapper;

    @Autowired
    private MockMvc restContractKindMockMvc;

    private ContractKind contractKind;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractKind createEntity() {
        ContractKind contractKind = new ContractKind().name(DEFAULT_NAME);
        return contractKind;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractKind createUpdatedEntity() {
        ContractKind contractKind = new ContractKind().name(UPDATED_NAME);
        return contractKind;
    }

    @BeforeEach
    public void initTest() {
        contractKindRepository.deleteAll();
        contractKind = createEntity();
    }

    @Test
    void createContractKind() throws Exception {
        int databaseSizeBeforeCreate = contractKindRepository.findAll().size();
        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);
        restContractKindMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeCreate + 1);
        ContractKind testContractKind = contractKindList.get(contractKindList.size() - 1);
        assertThat(testContractKind.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createContractKindWithExistingId() throws Exception {
        // Create the ContractKind with an existing ID
        contractKind.setId("existing_id");
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        int databaseSizeBeforeCreate = contractKindRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractKindMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllContractKinds() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        // Get all the contractKindList
        restContractKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractKind.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getContractKind() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        // Get the contractKind
        restContractKindMockMvc
            .perform(get(ENTITY_API_URL_ID, contractKind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contractKind.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingContractKind() throws Exception {
        // Get the contractKind
        restContractKindMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewContractKind() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();

        // Update the contractKind
        ContractKind updatedContractKind = contractKindRepository.findById(contractKind.getId()).get();
        updatedContractKind.name(UPDATED_NAME);
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(updatedContractKind);

        restContractKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contractKindDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
        ContractKind testContractKind = contractKindList.get(contractKindList.size() - 1);
        assertThat(testContractKind.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contractKindDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContractKindWithPatch() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();

        // Update the contractKind using partial update
        ContractKind partialUpdatedContractKind = new ContractKind();
        partialUpdatedContractKind.setId(contractKind.getId());

        partialUpdatedContractKind.name(UPDATED_NAME);

        restContractKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractKind))
            )
            .andExpect(status().isOk());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
        ContractKind testContractKind = contractKindList.get(contractKindList.size() - 1);
        assertThat(testContractKind.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateContractKindWithPatch() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();

        // Update the contractKind using partial update
        ContractKind partialUpdatedContractKind = new ContractKind();
        partialUpdatedContractKind.setId(contractKind.getId());

        partialUpdatedContractKind.name(UPDATED_NAME);

        restContractKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractKind))
            )
            .andExpect(status().isOk());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
        ContractKind testContractKind = contractKindList.get(contractKindList.size() - 1);
        assertThat(testContractKind.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contractKindDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContractKind() throws Exception {
        int databaseSizeBeforeUpdate = contractKindRepository.findAll().size();
        contractKind.setId(UUID.randomUUID().toString());

        // Create the ContractKind
        ContractKindDTO contractKindDTO = contractKindMapper.toDto(contractKind);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractKindMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractKindDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractKind in the database
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContractKind() throws Exception {
        // Initialize the database
        contractKindRepository.save(contractKind);

        int databaseSizeBeforeDelete = contractKindRepository.findAll().size();

        // Delete the contractKind
        restContractKindMockMvc
            .perform(delete(ENTITY_API_URL_ID, contractKind.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractKind> contractKindList = contractKindRepository.findAll();
        assertThat(contractKindList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
