package com.papraco.customerservice.service;

import com.papraco.customerservice.service.dto.FactureItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papraco.customerservice.domain.FactureItem}.
 */
public interface FactureItemService {
    /**
     * Save a factureItem.
     *
     * @param factureItemDTO the entity to save.
     * @return the persisted entity.
     */
    FactureItemDTO save(FactureItemDTO factureItemDTO);

    /**
     * Partially updates a factureItem.
     *
     * @param factureItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactureItemDTO> partialUpdate(FactureItemDTO factureItemDTO);

    /**
     * Get all the factureItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactureItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" factureItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactureItemDTO> findOne(String id);

    /**
     * Delete the "id" factureItem.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
