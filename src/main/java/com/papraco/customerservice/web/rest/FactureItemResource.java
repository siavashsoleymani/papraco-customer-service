package com.papraco.customerservice.web.rest;

import com.papraco.customerservice.repository.FactureItemRepository;
import com.papraco.customerservice.service.FactureItemService;
import com.papraco.customerservice.service.dto.FactureItemDTO;
import com.papraco.customerservice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.papraco.customerservice.domain.FactureItem}.
 */
@RestController
@RequestMapping("/api")
public class FactureItemResource {

    private final Logger log = LoggerFactory.getLogger(FactureItemResource.class);

    private static final String ENTITY_NAME = "factureItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactureItemService factureItemService;

    private final FactureItemRepository factureItemRepository;

    public FactureItemResource(FactureItemService factureItemService, FactureItemRepository factureItemRepository) {
        this.factureItemService = factureItemService;
        this.factureItemRepository = factureItemRepository;
    }

    /**
     * {@code POST  /facture-items} : Create a new factureItem.
     *
     * @param factureItemDTO the factureItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factureItemDTO, or with status {@code 400 (Bad Request)} if the factureItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facture-items")
    public ResponseEntity<FactureItemDTO> createFactureItem(@RequestBody FactureItemDTO factureItemDTO) throws URISyntaxException {
        log.debug("REST request to save FactureItem : {}", factureItemDTO);
        if (factureItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new factureItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactureItemDTO result = factureItemService.save(factureItemDTO);
        return ResponseEntity
            .created(new URI("/api/facture-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /facture-items/:id} : Updates an existing factureItem.
     *
     * @param id the id of the factureItemDTO to save.
     * @param factureItemDTO the factureItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureItemDTO,
     * or with status {@code 400 (Bad Request)} if the factureItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factureItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facture-items/{id}")
    public ResponseEntity<FactureItemDTO> updateFactureItem(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FactureItemDTO factureItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FactureItem : {}, {}", id, factureItemDTO);
        if (factureItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactureItemDTO result = factureItemService.save(factureItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureItemDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /facture-items/:id} : Partial updates given fields of an existing factureItem, field will ignore if it is null
     *
     * @param id the id of the factureItemDTO to save.
     * @param factureItemDTO the factureItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factureItemDTO,
     * or with status {@code 400 (Bad Request)} if the factureItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the factureItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factureItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facture-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FactureItemDTO> partialUpdateFactureItem(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FactureItemDTO factureItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FactureItem partially : {}, {}", id, factureItemDTO);
        if (factureItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factureItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factureItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactureItemDTO> result = factureItemService.partialUpdate(factureItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factureItemDTO.getId())
        );
    }

    /**
     * {@code GET  /facture-items} : get all the factureItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factureItems in body.
     */
    @GetMapping("/facture-items")
    public ResponseEntity<List<FactureItemDTO>> getAllFactureItems(Pageable pageable) {
        log.debug("REST request to get a page of FactureItems");
        Page<FactureItemDTO> page = factureItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facture-items/:id} : get the "id" factureItem.
     *
     * @param id the id of the factureItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factureItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facture-items/{id}")
    public ResponseEntity<FactureItemDTO> getFactureItem(@PathVariable String id) {
        log.debug("REST request to get FactureItem : {}", id);
        Optional<FactureItemDTO> factureItemDTO = factureItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factureItemDTO);
    }

    /**
     * {@code DELETE  /facture-items/:id} : delete the "id" factureItem.
     *
     * @param id the id of the factureItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facture-items/{id}")
    public ResponseEntity<Void> deleteFactureItem(@PathVariable String id) {
        log.debug("REST request to delete FactureItem : {}", id);
        factureItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
