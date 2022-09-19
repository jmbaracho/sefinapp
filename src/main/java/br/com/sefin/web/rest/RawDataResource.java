package br.com.sefin.web.rest;

import br.com.sefin.repository.RawDataRepository;
import br.com.sefin.service.RawDataService;
import br.com.sefin.service.dto.RawDataDTO;
import br.com.sefin.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link br.com.sefin.domain.RawData}.
 */
@RestController
@RequestMapping("/api")
public class RawDataResource {

    private final Logger log = LoggerFactory.getLogger(RawDataResource.class);

    private static final String ENTITY_NAME = "rawData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RawDataService rawDataService;

    private final RawDataRepository rawDataRepository;

    public RawDataResource(RawDataService rawDataService, RawDataRepository rawDataRepository) {
        this.rawDataService = rawDataService;
        this.rawDataRepository = rawDataRepository;
    }

    /**
     * {@code POST  /raw-data} : Create a new rawData.
     *
     * @param rawDataDTO the rawDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rawDataDTO, or with status {@code 400 (Bad Request)} if the rawData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/raw-data")
    public ResponseEntity<RawDataDTO> createRawData(@RequestBody RawDataDTO rawDataDTO) throws URISyntaxException {
        log.debug("REST request to save RawData : {}", rawDataDTO);
        if (rawDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new rawData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RawDataDTO result = rawDataService.save(rawDataDTO);
        return ResponseEntity
            .created(new URI("/api/raw-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /raw-data/:id} : Updates an existing rawData.
     *
     * @param id the id of the rawDataDTO to save.
     * @param rawDataDTO the rawDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawDataDTO,
     * or with status {@code 400 (Bad Request)} if the rawDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rawDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/raw-data/{id}")
    public ResponseEntity<RawDataDTO> updateRawData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawDataDTO rawDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RawData : {}, {}", id, rawDataDTO);
        if (rawDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RawDataDTO result = rawDataService.update(rawDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /raw-data/:id} : Partial updates given fields of an existing rawData, field will ignore if it is null
     *
     * @param id the id of the rawDataDTO to save.
     * @param rawDataDTO the rawDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawDataDTO,
     * or with status {@code 400 (Bad Request)} if the rawDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rawDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rawDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/raw-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RawDataDTO> partialUpdateRawData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawDataDTO rawDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RawData partially : {}, {}", id, rawDataDTO);
        if (rawDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RawDataDTO> result = rawDataService.partialUpdate(rawDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /raw-data} : get all the rawData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rawData in body.
     */
    @GetMapping("/raw-data")
    public ResponseEntity<List<RawDataDTO>> getAllRawData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RawData");
        Page<RawDataDTO> page = rawDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /raw-data/:id} : get the "id" rawData.
     *
     * @param id the id of the rawDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rawDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/raw-data/{id}")
    public ResponseEntity<RawDataDTO> getRawData(@PathVariable Long id) {
        log.debug("REST request to get RawData : {}", id);
        Optional<RawDataDTO> rawDataDTO = rawDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rawDataDTO);
    }

    /**
     * {@code DELETE  /raw-data/:id} : delete the "id" rawData.
     *
     * @param id the id of the rawDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/raw-data/{id}")
    public ResponseEntity<Void> deleteRawData(@PathVariable Long id) {
        log.debug("REST request to delete RawData : {}", id);
        rawDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
