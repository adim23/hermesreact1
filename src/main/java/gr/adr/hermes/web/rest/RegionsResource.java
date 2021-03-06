package gr.adr.hermes.web.rest;

import gr.adr.hermes.domain.Regions;
import gr.adr.hermes.repository.RegionsRepository;
import gr.adr.hermes.service.RegionsQueryService;
import gr.adr.hermes.service.RegionsService;
import gr.adr.hermes.service.criteria.RegionsCriteria;
import gr.adr.hermes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link gr.adr.hermes.domain.Regions}.
 */
@RestController
@RequestMapping("/api")
public class RegionsResource {

    private final Logger log = LoggerFactory.getLogger(RegionsResource.class);

    private static final String ENTITY_NAME = "regions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionsService regionsService;

    private final RegionsRepository regionsRepository;

    private final RegionsQueryService regionsQueryService;

    public RegionsResource(RegionsService regionsService, RegionsRepository regionsRepository, RegionsQueryService regionsQueryService) {
        this.regionsService = regionsService;
        this.regionsRepository = regionsRepository;
        this.regionsQueryService = regionsQueryService;
    }

    /**
     * {@code POST  /regions} : Create a new regions.
     *
     * @param regions the regions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regions, or with status {@code 400 (Bad Request)} if the regions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regions")
    public ResponseEntity<Regions> createRegions(@Valid @RequestBody Regions regions) throws URISyntaxException {
        log.debug("REST request to save Regions : {}", regions);
        if (regions.getId() != null) {
            throw new BadRequestAlertException("A new regions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regions result = regionsService.save(regions);
        return ResponseEntity
            .created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions/:id} : Updates an existing regions.
     *
     * @param id the id of the regions to save.
     * @param regions the regions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regions,
     * or with status {@code 400 (Bad Request)} if the regions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regions/{id}")
    public ResponseEntity<Regions> updateRegions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Regions regions
    ) throws URISyntaxException {
        log.debug("REST request to update Regions : {}, {}", id, regions);
        if (regions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Regions result = regionsService.save(regions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /regions/:id} : Partial updates given fields of an existing regions, field will ignore if it is null
     *
     * @param id the id of the regions to save.
     * @param regions the regions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regions,
     * or with status {@code 400 (Bad Request)} if the regions is not valid,
     * or with status {@code 404 (Not Found)} if the regions is not found,
     * or with status {@code 500 (Internal Server Error)} if the regions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regions> partialUpdateRegions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Regions regions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regions partially : {}, {}", id, regions);
        if (regions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regions> result = regionsService.partialUpdate(regions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regions.getId().toString())
        );
    }

    /**
     * {@code GET  /regions} : get all the regions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regions in body.
     */
    @GetMapping("/regions")
    public ResponseEntity<List<Regions>> getAllRegions(
        RegionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        Page<Regions> page = regionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regions/count} : count all the regions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regions/count")
    public ResponseEntity<Long> countRegions(RegionsCriteria criteria) {
        log.debug("REST request to count Regions by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regions/:id} : get the "id" regions.
     *
     * @param id the id of the regions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regions/{id}")
    public ResponseEntity<Regions> getRegions(@PathVariable Long id) {
        log.debug("REST request to get Regions : {}", id);
        Optional<Regions> regions = regionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regions);
    }

    /**
     * {@code DELETE  /regions/:id} : delete the "id" regions.
     *
     * @param id the id of the regions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Void> deleteRegions(@PathVariable Long id) {
        log.debug("REST request to delete Regions : {}", id);
        regionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
