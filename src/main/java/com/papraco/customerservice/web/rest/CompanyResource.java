package com.papraco.customerservice.web.rest;

import com.papraco.customerservice.repository.CompanyRepository;
import com.papraco.customerservice.service.CompanyService;
import com.papraco.customerservice.service.dto.CompanyDTO;
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
 * REST controller for managing {@link com.papraco.customerservice.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;

    public CompanyResource(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    /**
     * {@code POST  /companies} : Create a new company.
     *
     * @param companyDTO the companyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyDTO, or with status {@code 400 (Bad Request)} if the company has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyDTO result = companyService.save(companyDTO);
        return ResponseEntity
            .created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing company.
     *
     * @param id the id of the companyDTO to save.
     * @param companyDTO the companyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyDTO,
     * or with status {@code 400 (Bad Request)} if the companyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CompanyDTO companyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Company : {}, {}", id, companyDTO);
        if (companyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyDTO result = companyService.save(companyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing company, field will ignore if it is null
     *
     * @param id the id of the companyDTO to save.
     * @param companyDTO the companyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyDTO,
     * or with status {@code 400 (Bad Request)} if the companyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CompanyDTO> partialUpdateCompany(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CompanyDTO companyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Company partially : {}, {}", id, companyDTO);
        if (companyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyDTO> result = companyService.partialUpdate(companyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyDTO.getId())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Companies");
        Page<CompanyDTO> page;
        if (eagerload) {
            page = companyService.findAllWithEagerRelationships(pageable);
        } else {
            page = companyService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/:id} : get the "id" company.
     *
     * @param id the id of the companyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable String id) {
        log.debug("REST request to get Company : {}", id);
        Optional<CompanyDTO> companyDTO = companyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyDTO);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" company.
     *
     * @param id the id of the companyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
