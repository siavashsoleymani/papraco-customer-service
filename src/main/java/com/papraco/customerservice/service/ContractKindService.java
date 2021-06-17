package com.papraco.customerservice.service;

import com.papraco.customerservice.service.dto.ContractKindDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papraco.customerservice.domain.ContractKind}.
 */
public interface ContractKindService {
    /**
     * Save a contractKind.
     *
     * @param contractKindDTO the entity to save.
     * @return the persisted entity.
     */
    ContractKindDTO save(ContractKindDTO contractKindDTO);

    /**
     * Partially updates a contractKind.
     *
     * @param contractKindDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContractKindDTO> partialUpdate(ContractKindDTO contractKindDTO);

    /**
     * Get all the contractKinds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContractKindDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contractKind.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContractKindDTO> findOne(String id);

    /**
     * Delete the "id" contractKind.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
