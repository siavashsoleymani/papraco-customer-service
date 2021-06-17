package com.papraco.customerservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.Staff;
import com.papraco.customerservice.repository.StaffRepository;
import com.papraco.customerservice.service.dto.StaffDTO;
import com.papraco.customerservice.service.mapper.StaffMapper;
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
 * Integration tests for the {@link StaffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StaffResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/staff";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private MockMvc restStaffMockMvc;

    private Staff staff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createEntity() {
        Staff staff = new Staff()
            .name(DEFAULT_NAME)
            .family(DEFAULT_FAMILY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .desciption(DEFAULT_DESCIPTION);
        return staff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createUpdatedEntity() {
        Staff staff = new Staff()
            .name(UPDATED_NAME)
            .family(UPDATED_FAMILY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .desciption(UPDATED_DESCIPTION);
        return staff;
    }

    @BeforeEach
    public void initTest() {
        staffRepository.deleteAll();
        staff = createEntity();
    }

    @Test
    void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();
        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStaff.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testStaff.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testStaff.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStaff.getDesciption()).isEqualTo(DEFAULT_DESCIPTION);
    }

    @Test
    void createStaffWithExistingId() throws Exception {
        // Create the Staff with an existing ID
        staff.setId("existing_id");
        StaffDTO staffDTO = staffMapper.toDto(staff);

        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        // Get all the staffList
        restStaffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].desciption").value(hasItem(DEFAULT_DESCIPTION)));
    }

    @Test
    void getStaff() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        // Get the staff
        restStaffMockMvc
            .perform(get(ENTITY_API_URL_ID, staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.desciption").value(DEFAULT_DESCIPTION));
    }

    @Test
    void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewStaff() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        updatedStaff
            .name(UPDATED_NAME)
            .family(UPDATED_FAMILY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .desciption(UPDATED_DESCIPTION);
        StaffDTO staffDTO = staffMapper.toDto(updatedStaff);

        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffDTO))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaff.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testStaff.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testStaff.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStaff.getDesciption()).isEqualTo(UPDATED_DESCIPTION);
    }

    @Test
    void putNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff.name(UPDATED_NAME).phoneNumber(UPDATED_PHONE_NUMBER);

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaff.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testStaff.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testStaff.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStaff.getDesciption()).isEqualTo(DEFAULT_DESCIPTION);
    }

    @Test
    void fullUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff
            .name(UPDATED_NAME)
            .family(UPDATED_FAMILY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .desciption(UPDATED_DESCIPTION);

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaff.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testStaff.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testStaff.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStaff.getDesciption()).isEqualTo(UPDATED_DESCIPTION);
    }

    @Test
    void patchNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, staffDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(UUID.randomUUID().toString());

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.save(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Delete the staff
        restStaffMockMvc
            .perform(delete(ENTITY_API_URL_ID, staff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
