package com.papraco.customerservice.web.rest;

import com.papraco.customerservice.repository.UserCommentRepository;
import com.papraco.customerservice.service.UserCommentService;
import com.papraco.customerservice.service.dto.UserCommentDTO;
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
 * REST controller for managing {@link com.papraco.customerservice.domain.UserComment}.
 */
@RestController
@RequestMapping("/api")
public class UserCommentResource {

    private final Logger log = LoggerFactory.getLogger(UserCommentResource.class);

    private static final String ENTITY_NAME = "userComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCommentService userCommentService;

    private final UserCommentRepository userCommentRepository;

    public UserCommentResource(UserCommentService userCommentService, UserCommentRepository userCommentRepository) {
        this.userCommentService = userCommentService;
        this.userCommentRepository = userCommentRepository;
    }

    /**
     * {@code POST  /user-comments} : Create a new userComment.
     *
     * @param userCommentDTO the userCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCommentDTO, or with status {@code 400 (Bad Request)} if the userComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-comments")
    public ResponseEntity<UserCommentDTO> createUserComment(@RequestBody UserCommentDTO userCommentDTO) throws URISyntaxException {
        log.debug("REST request to save UserComment : {}", userCommentDTO);
        if (userCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new userComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCommentDTO result = userCommentService.save(userCommentDTO);
        return ResponseEntity
            .created(new URI("/api/user-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-comments/:id} : Updates an existing userComment.
     *
     * @param id the id of the userCommentDTO to save.
     * @param userCommentDTO the userCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCommentDTO,
     * or with status {@code 400 (Bad Request)} if the userCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-comments/{id}")
    public ResponseEntity<UserCommentDTO> updateUserComment(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserCommentDTO userCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserComment : {}, {}", id, userCommentDTO);
        if (userCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserCommentDTO result = userCommentService.save(userCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userCommentDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-comments/:id} : Partial updates given fields of an existing userComment, field will ignore if it is null
     *
     * @param id the id of the userCommentDTO to save.
     * @param userCommentDTO the userCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCommentDTO,
     * or with status {@code 400 (Bad Request)} if the userCommentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userCommentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-comments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserCommentDTO> partialUpdateUserComment(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserCommentDTO userCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserComment partially : {}, {}", id, userCommentDTO);
        if (userCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserCommentDTO> result = userCommentService.partialUpdate(userCommentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userCommentDTO.getId())
        );
    }

    /**
     * {@code GET  /user-comments} : get all the userComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userComments in body.
     */
    @GetMapping("/user-comments")
    public ResponseEntity<List<UserCommentDTO>> getAllUserComments(Pageable pageable) {
        log.debug("REST request to get a page of UserComments");
        Page<UserCommentDTO> page = userCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-comments/:id} : get the "id" userComment.
     *
     * @param id the id of the userCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-comments/{id}")
    public ResponseEntity<UserCommentDTO> getUserComment(@PathVariable String id) {
        log.debug("REST request to get UserComment : {}", id);
        Optional<UserCommentDTO> userCommentDTO = userCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCommentDTO);
    }

    /**
     * {@code DELETE  /user-comments/:id} : delete the "id" userComment.
     *
     * @param id the id of the userCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-comments/{id}")
    public ResponseEntity<Void> deleteUserComment(@PathVariable String id) {
        log.debug("REST request to delete UserComment : {}", id);
        userCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
