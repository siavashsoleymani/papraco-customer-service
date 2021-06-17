package com.papraco.customerservice.service;

import com.papraco.customerservice.service.dto.UserCommentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papraco.customerservice.domain.UserComment}.
 */
public interface UserCommentService {
    /**
     * Save a userComment.
     *
     * @param userCommentDTO the entity to save.
     * @return the persisted entity.
     */
    UserCommentDTO save(UserCommentDTO userCommentDTO);

    /**
     * Partially updates a userComment.
     *
     * @param userCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserCommentDTO> partialUpdate(UserCommentDTO userCommentDTO);

    /**
     * Get all the userComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserCommentDTO> findOne(String id);

    /**
     * Delete the "id" userComment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
