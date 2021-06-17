package com.papraco.customerservice.web.rest;

import com.papraco.customerservice.repository.ContractKindRepository;
import com.papraco.customerservice.service.ContractKindService;
import com.papraco.customerservice.service.dto.ContractKindDTO;
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
 * REST controller for managing {@link com.papraco.customerservice.domain.ContractKind}.
 */
@RestController
@RequestMapping("/api")
public class ContractKindResource {

    private final Logger log = LoggerFactory.getLogger(ContractKindResource.class);

    private static final String ENTITY_NAME = "contractKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractKindService contractKindService;

    private final ContractKindRepository contractKindRepository;

    public ContractKindResource(ContractKindService contractKindService, ContractKindRepository contractKindRepository) {
        this.contractKindService = contractKindService;
        this.contractKindRepository = contractKindRepository;
    }

    /**
     * {@code POST  /contract-kinds} : Create a new contractKind.
     *
     * @param contractKindDTO the contractKindDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractKindDTO, or with status {@code 400 (Bad Request)} if the contractKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-kinds")
    public ResponseEntity<ContractKindDTO> createContractKind(@RequestBody ContractKindDTO contractKindDTO) throws URISyntaxException {
        log.debug("REST request to save ContractKind : {}", contractKindDTO);
        if (contractKindDTO.getId() != null) {
            throw new BadRequestAlertException("A new contractKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractKindDTO result = contractKindService.save(contractKindDTO);
        return ResponseEntity
            .created(new URI("/api/contract-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-kinds/:id} : Updates an existing contractKind.
     *
     * @param id the id of the contractKindDTO to save.
     * @param contractKindDTO the contractKindDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractKindDTO,
     * or with status {@code 400 (Bad Request)} if the contractKindDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractKindDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-kinds/{id}")
    public ResponseEntity<ContractKindDTO> updateContractKind(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ContractKindDTO contractKindDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContractKind : {}, {}", id, contractKindDTO);
        if (contractKindDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractKindDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContractKindDTO result = contractKindService.save(contractKindDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractKindDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /contract-kinds/:id} : Partial updates given fields of an existing contractKind, field will ignore if it is null
     *
     * @param id the id of the contractKindDTO to save.
     * @param contractKindDTO the contractKindDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractKindDTO,
     * or with status {@code 400 (Bad Request)} if the contractKindDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contractKindDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contractKindDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contract-kinds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ContractKindDTO> partialUpdateContractKind(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ContractKindDTO contractKindDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContractKind partially : {}, {}", id, contractKindDTO);
        if (contractKindDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractKindDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContractKindDTO> result = contractKindService.partialUpdate(contractKindDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractKindDTO.getId())
        );
    }

    /**
     * {@code GET  /contract-kinds} : get all the contractKinds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractKinds in body.
     */
    @GetMapping("/contract-kinds")
    public ResponseEntity<List<ContractKindDTO>> getAllContractKinds(Pageable pageable) {
        log.debug("REST request to get a page of ContractKinds");
        Page<ContractKindDTO> page = contractKindService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contract-kinds/:id} : get the "id" contractKind.
     *
     * @param id the id of the contractKindDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractKindDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-kinds/{id}")
    public ResponseEntity<ContractKindDTO> getContractKind(@PathVariable String id) {
        log.debug("REST request to get ContractKind : {}", id);
        Optional<ContractKindDTO> contractKindDTO = contractKindService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractKindDTO);
    }

    /**
     * {@code DELETE  /contract-kinds/:id} : delete the "id" contractKind.
     *
     * @param id the id of the contractKindDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-kinds/{id}")
    public ResponseEntity<Void> deleteContractKind(@PathVariable String id) {
        log.debug("REST request to delete ContractKind : {}", id);
        contractKindService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
