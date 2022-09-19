package br.com.sefin.web.rest;

import br.com.sefin.repository.TaskStatusRepository;
import br.com.sefin.service.TaskStatusService;
import br.com.sefin.service.dto.TaskStatusDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.TaskStatus}.
 */
@RestController
@RequestMapping("/api")
public class TaskStatusResource {

    private final Logger log = LoggerFactory.getLogger(TaskStatusResource.class);

    private static final String ENTITY_NAME = "taskStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStatusService taskStatusService;

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusResource(TaskStatusService taskStatusService, TaskStatusRepository taskStatusRepository) {
        this.taskStatusService = taskStatusService;
        this.taskStatusRepository = taskStatusRepository;
    }

    /**
     * {@code POST  /task-statuses} : Create a new taskStatus.
     *
     * @param taskStatusDTO the taskStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStatusDTO, or with status {@code 400 (Bad Request)} if the taskStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-statuses")
    public ResponseEntity<TaskStatusDTO> createTaskStatus(@RequestBody TaskStatusDTO taskStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TaskStatus : {}", taskStatusDTO);
        if (taskStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStatusDTO result = taskStatusService.save(taskStatusDTO);
        return ResponseEntity
            .created(new URI("/api/task-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-statuses/:id} : Updates an existing taskStatus.
     *
     * @param id the id of the taskStatusDTO to save.
     * @param taskStatusDTO the taskStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStatusDTO,
     * or with status {@code 400 (Bad Request)} if the taskStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-statuses/{id}")
    public ResponseEntity<TaskStatusDTO> updateTaskStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskStatusDTO taskStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaskStatus : {}, {}", id, taskStatusDTO);
        if (taskStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskStatusDTO result = taskStatusService.update(taskStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-statuses/:id} : Partial updates given fields of an existing taskStatus, field will ignore if it is null
     *
     * @param id the id of the taskStatusDTO to save.
     * @param taskStatusDTO the taskStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStatusDTO,
     * or with status {@code 400 (Bad Request)} if the taskStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taskStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaskStatusDTO> partialUpdateTaskStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskStatusDTO taskStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskStatus partially : {}, {}", id, taskStatusDTO);
        if (taskStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskStatusDTO> result = taskStatusService.partialUpdate(taskStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /task-statuses} : get all the taskStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskStatuses in body.
     */
    @GetMapping("/task-statuses")
    public ResponseEntity<List<TaskStatusDTO>> getAllTaskStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TaskStatuses");
        Page<TaskStatusDTO> page = taskStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-statuses/:id} : get the "id" taskStatus.
     *
     * @param id the id of the taskStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-statuses/{id}")
    public ResponseEntity<TaskStatusDTO> getTaskStatus(@PathVariable Long id) {
        log.debug("REST request to get TaskStatus : {}", id);
        Optional<TaskStatusDTO> taskStatusDTO = taskStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStatusDTO);
    }

    /**
     * {@code DELETE  /task-statuses/:id} : delete the "id" taskStatus.
     *
     * @param id the id of the taskStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-statuses/{id}")
    public ResponseEntity<Void> deleteTaskStatus(@PathVariable Long id) {
        log.debug("REST request to delete TaskStatus : {}", id);
        taskStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
