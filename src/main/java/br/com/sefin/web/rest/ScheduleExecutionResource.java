package br.com.sefin.web.rest;

import br.com.sefin.repository.ScheduleExecutionRepository;
import br.com.sefin.service.ScheduleExecutionService;
import br.com.sefin.service.dto.ScheduleExecutionDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.ScheduleExecution}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleExecutionResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleExecutionResource.class);

    private static final String ENTITY_NAME = "scheduleExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleExecutionService scheduleExecutionService;

    private final ScheduleExecutionRepository scheduleExecutionRepository;

    public ScheduleExecutionResource(
        ScheduleExecutionService scheduleExecutionService,
        ScheduleExecutionRepository scheduleExecutionRepository
    ) {
        this.scheduleExecutionService = scheduleExecutionService;
        this.scheduleExecutionRepository = scheduleExecutionRepository;
    }

    /**
     * {@code POST  /schedule-executions} : Create a new scheduleExecution.
     *
     * @param scheduleExecutionDTO the scheduleExecutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleExecutionDTO, or with status {@code 400 (Bad Request)} if the scheduleExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-executions")
    public ResponseEntity<ScheduleExecutionDTO> createScheduleExecution(@RequestBody ScheduleExecutionDTO scheduleExecutionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ScheduleExecution : {}", scheduleExecutionDTO);
        if (scheduleExecutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleExecutionDTO result = scheduleExecutionService.save(scheduleExecutionDTO);
        return ResponseEntity
            .created(new URI("/api/schedule-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedule-executions/:id} : Updates an existing scheduleExecution.
     *
     * @param id the id of the scheduleExecutionDTO to save.
     * @param scheduleExecutionDTO the scheduleExecutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleExecutionDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleExecutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleExecutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-executions/{id}")
    public ResponseEntity<ScheduleExecutionDTO> updateScheduleExecution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScheduleExecutionDTO scheduleExecutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ScheduleExecution : {}, {}", id, scheduleExecutionDTO);
        if (scheduleExecutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduleExecutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduleExecutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScheduleExecutionDTO result = scheduleExecutionService.update(scheduleExecutionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleExecutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /schedule-executions/:id} : Partial updates given fields of an existing scheduleExecution, field will ignore if it is null
     *
     * @param id the id of the scheduleExecutionDTO to save.
     * @param scheduleExecutionDTO the scheduleExecutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleExecutionDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleExecutionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the scheduleExecutionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the scheduleExecutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/schedule-executions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScheduleExecutionDTO> partialUpdateScheduleExecution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScheduleExecutionDTO scheduleExecutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScheduleExecution partially : {}, {}", id, scheduleExecutionDTO);
        if (scheduleExecutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduleExecutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduleExecutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScheduleExecutionDTO> result = scheduleExecutionService.partialUpdate(scheduleExecutionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleExecutionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /schedule-executions} : get all the scheduleExecutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduleExecutions in body.
     */
    @GetMapping("/schedule-executions")
    public ResponseEntity<List<ScheduleExecutionDTO>> getAllScheduleExecutions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ScheduleExecutions");
        Page<ScheduleExecutionDTO> page = scheduleExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schedule-executions/:id} : get the "id" scheduleExecution.
     *
     * @param id the id of the scheduleExecutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleExecutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-executions/{id}")
    public ResponseEntity<ScheduleExecutionDTO> getScheduleExecution(@PathVariable Long id) {
        log.debug("REST request to get ScheduleExecution : {}", id);
        Optional<ScheduleExecutionDTO> scheduleExecutionDTO = scheduleExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleExecutionDTO);
    }

    /**
     * {@code DELETE  /schedule-executions/:id} : delete the "id" scheduleExecution.
     *
     * @param id the id of the scheduleExecutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-executions/{id}")
    public ResponseEntity<Void> deleteScheduleExecution(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleExecution : {}", id);
        scheduleExecutionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
