package com.papraco.customerservice.service;

import com.papraco.customerservice.service.dto.StaffDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papraco.customerservice.domain.Staff}.
 */
public interface StaffService {
    /**
     * Save a staff.
     *
     * @param staffDTO the entity to save.
     * @return the persisted entity.
     */
    StaffDTO save(StaffDTO staffDTO);

    /**
     * Partially updates a staff.
     *
     * @param staffDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StaffDTO> partialUpdate(StaffDTO staffDTO);

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffDTO> findAll(Pageable pageable);

    /**
     * Get the "id" staff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StaffDTO> findOne(String id);

    /**
     * Delete the "id" staff.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
