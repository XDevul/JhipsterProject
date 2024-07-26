package com.jhipster.test.web.rest;

import com.jhipster.test.repository.AlloyHttpRepository;
import com.jhipster.test.service.AlloyHttpQueryService;
import com.jhipster.test.service.AlloyHttpService;
import com.jhipster.test.service.criteria.AlloyHttpCriteria;
import com.jhipster.test.service.dto.AlloyHttpDTO;
import com.jhipster.test.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.test.domain.AlloyHttp}.
 */
@RestController
@RequestMapping("/api/alloy-https")
public class AlloyHttpResource {

    private static final Logger log = LoggerFactory.getLogger(AlloyHttpResource.class);

    private static final String ENTITY_NAME = "alloyHttp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlloyHttpService alloyHttpService;

    private final AlloyHttpRepository alloyHttpRepository;

    private final AlloyHttpQueryService alloyHttpQueryService;

    public AlloyHttpResource(
        AlloyHttpService alloyHttpService,
        AlloyHttpRepository alloyHttpRepository,
        AlloyHttpQueryService alloyHttpQueryService
    ) {
        this.alloyHttpService = alloyHttpService;
        this.alloyHttpRepository = alloyHttpRepository;
        this.alloyHttpQueryService = alloyHttpQueryService;
    }

    /**
     * {@code POST  /alloy-https} : Create a new alloyHttp.
     *
     * @param alloyHttpDTO the alloyHttpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alloyHttpDTO, or with status {@code 400 (Bad Request)} if the alloyHttp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AlloyHttpDTO> createAlloyHttp(@Valid @RequestBody AlloyHttpDTO alloyHttpDTO) throws URISyntaxException {
        log.debug("REST request to save AlloyHttp : {}", alloyHttpDTO);
        if (alloyHttpDTO.getId() != null) {
            throw new BadRequestAlertException("A new alloyHttp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        alloyHttpDTO = alloyHttpService.save(alloyHttpDTO);
        return ResponseEntity.created(new URI("/api/alloy-https/" + alloyHttpDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, alloyHttpDTO.getId().toString()))
            .body(alloyHttpDTO);
    }

    /**
     * {@code PUT  /alloy-https/:id} : Updates an existing alloyHttp.
     *
     * @param id the id of the alloyHttpDTO to save.
     * @param alloyHttpDTO the alloyHttpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alloyHttpDTO,
     * or with status {@code 400 (Bad Request)} if the alloyHttpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alloyHttpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlloyHttpDTO> updateAlloyHttp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlloyHttpDTO alloyHttpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AlloyHttp : {}, {}", id, alloyHttpDTO);
        if (alloyHttpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alloyHttpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alloyHttpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        alloyHttpDTO = alloyHttpService.update(alloyHttpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alloyHttpDTO.getId().toString()))
            .body(alloyHttpDTO);
    }

    /**
     * {@code PATCH  /alloy-https/:id} : Partial updates given fields of an existing alloyHttp, field will ignore if it is null
     *
     * @param id the id of the alloyHttpDTO to save.
     * @param alloyHttpDTO the alloyHttpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alloyHttpDTO,
     * or with status {@code 400 (Bad Request)} if the alloyHttpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alloyHttpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alloyHttpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlloyHttpDTO> partialUpdateAlloyHttp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlloyHttpDTO alloyHttpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlloyHttp partially : {}, {}", id, alloyHttpDTO);
        if (alloyHttpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alloyHttpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alloyHttpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlloyHttpDTO> result = alloyHttpService.partialUpdate(alloyHttpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alloyHttpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alloy-https} : get all the alloyHttps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alloyHttps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AlloyHttpDTO>> getAllAlloyHttps(
        AlloyHttpCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AlloyHttps by criteria: {}", criteria);

        Page<AlloyHttpDTO> page = alloyHttpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alloy-https/count} : count all the alloyHttps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAlloyHttps(AlloyHttpCriteria criteria) {
        log.debug("REST request to count AlloyHttps by criteria: {}", criteria);
        return ResponseEntity.ok().body(alloyHttpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alloy-https/:id} : get the "id" alloyHttp.
     *
     * @param id the id of the alloyHttpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alloyHttpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlloyHttpDTO> getAlloyHttp(@PathVariable("id") Long id) {
        log.debug("REST request to get AlloyHttp : {}", id);
        Optional<AlloyHttpDTO> alloyHttpDTO = alloyHttpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alloyHttpDTO);
    }

    /**
     * {@code DELETE  /alloy-https/:id} : delete the "id" alloyHttp.
     *
     * @param id the id of the alloyHttpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlloyHttp(@PathVariable("id") Long id) {
        log.debug("REST request to delete AlloyHttp : {}", id);
        alloyHttpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
