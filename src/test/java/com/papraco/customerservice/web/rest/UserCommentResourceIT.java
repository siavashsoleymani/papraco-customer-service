package com.papraco.customerservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papraco.customerservice.IntegrationTest;
import com.papraco.customerservice.domain.UserComment;
import com.papraco.customerservice.repository.UserCommentRepository;
import com.papraco.customerservice.service.dto.UserCommentDTO;
import com.papraco.customerservice.service.mapper.UserCommentMapper;
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
 * Integration tests for the {@link UserCommentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final String ENTITY_API_URL = "/api/user-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserCommentRepository userCommentRepository;

    @Autowired
    private UserCommentMapper userCommentMapper;

    @Autowired
    private MockMvc restUserCommentMockMvc;

    private UserComment userComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserComment createEntity() {
        UserComment userComment = new UserComment().comment(DEFAULT_COMMENT).score(DEFAULT_SCORE);
        return userComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserComment createUpdatedEntity() {
        UserComment userComment = new UserComment().comment(UPDATED_COMMENT).score(UPDATED_SCORE);
        return userComment;
    }

    @BeforeEach
    public void initTest() {
        userCommentRepository.deleteAll();
        userComment = createEntity();
    }

    @Test
    void createUserComment() throws Exception {
        int databaseSizeBeforeCreate = userCommentRepository.findAll().size();
        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);
        restUserCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeCreate + 1);
        UserComment testUserComment = userCommentList.get(userCommentList.size() - 1);
        assertThat(testUserComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserComment.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    void createUserCommentWithExistingId() throws Exception {
        // Create the UserComment with an existing ID
        userComment.setId("existing_id");
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        int databaseSizeBeforeCreate = userCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUserComments() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        // Get all the userCommentList
        restUserCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userComment.getId())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    void getUserComment() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        // Get the userComment
        restUserCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, userComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userComment.getId()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    void getNonExistingUserComment() throws Exception {
        // Get the userComment
        restUserCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewUserComment() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();

        // Update the userComment
        UserComment updatedUserComment = userCommentRepository.findById(userComment.getId()).get();
        updatedUserComment.comment(UPDATED_COMMENT).score(UPDATED_SCORE);
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(updatedUserComment);

        restUserCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
        UserComment testUserComment = userCommentList.get(userCommentList.size() - 1);
        assertThat(testUserComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserComment.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    void putNonExistingUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCommentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserCommentWithPatch() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();

        // Update the userComment using partial update
        UserComment partialUpdatedUserComment = new UserComment();
        partialUpdatedUserComment.setId(userComment.getId());

        partialUpdatedUserComment.comment(UPDATED_COMMENT).score(UPDATED_SCORE);

        restUserCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserComment))
            )
            .andExpect(status().isOk());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
        UserComment testUserComment = userCommentList.get(userCommentList.size() - 1);
        assertThat(testUserComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserComment.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    void fullUpdateUserCommentWithPatch() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();

        // Update the userComment using partial update
        UserComment partialUpdatedUserComment = new UserComment();
        partialUpdatedUserComment.setId(userComment.getId());

        partialUpdatedUserComment.comment(UPDATED_COMMENT).score(UPDATED_SCORE);

        restUserCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserComment))
            )
            .andExpect(status().isOk());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
        UserComment testUserComment = userCommentList.get(userCommentList.size() - 1);
        assertThat(testUserComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserComment.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    void patchNonExistingUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userCommentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserComment() throws Exception {
        int databaseSizeBeforeUpdate = userCommentRepository.findAll().size();
        userComment.setId(UUID.randomUUID().toString());

        // Create the UserComment
        UserCommentDTO userCommentDTO = userCommentMapper.toDto(userComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCommentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserComment in the database
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserComment() throws Exception {
        // Initialize the database
        userCommentRepository.save(userComment);

        int databaseSizeBeforeDelete = userCommentRepository.findAll().size();

        // Delete the userComment
        restUserCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, userComment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserComment> userCommentList = userCommentRepository.findAll();
        assertThat(userCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
