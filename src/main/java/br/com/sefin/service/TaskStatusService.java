package br.com.sefin.service;

import br.com.sefin.domain.TaskStatus;
import br.com.sefin.repository.TaskStatusRepository;
import br.com.sefin.service.dto.TaskStatusDTO;
import br.com.sefin.service.mapper.TaskStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskStatus}.
 */
@Service
@Transactional
public class TaskStatusService {

    private final Logger log = LoggerFactory.getLogger(TaskStatusService.class);

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusMapper taskStatusMapper;

    public TaskStatusService(TaskStatusRepository taskStatusRepository, TaskStatusMapper taskStatusMapper) {
        this.taskStatusRepository = taskStatusRepository;
        this.taskStatusMapper = taskStatusMapper;
    }

    /**
     * Save a taskStatus.
     *
     * @param taskStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskStatusDTO save(TaskStatusDTO taskStatusDTO) {
        log.debug("Request to save TaskStatus : {}", taskStatusDTO);
        TaskStatus taskStatus = taskStatusMapper.toEntity(taskStatusDTO);
        taskStatus = taskStatusRepository.save(taskStatus);
        return taskStatusMapper.toDto(taskStatus);
    }

    /**
     * Update a taskStatus.
     *
     * @param taskStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskStatusDTO update(TaskStatusDTO taskStatusDTO) {
        log.debug("Request to update TaskStatus : {}", taskStatusDTO);
        TaskStatus taskStatus = taskStatusMapper.toEntity(taskStatusDTO);
        taskStatus = taskStatusRepository.save(taskStatus);
        return taskStatusMapper.toDto(taskStatus);
    }

    /**
     * Partially update a taskStatus.
     *
     * @param taskStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskStatusDTO> partialUpdate(TaskStatusDTO taskStatusDTO) {
        log.debug("Request to partially update TaskStatus : {}", taskStatusDTO);

        return taskStatusRepository
            .findById(taskStatusDTO.getId())
            .map(existingTaskStatus -> {
                taskStatusMapper.partialUpdate(existingTaskStatus, taskStatusDTO);

                return existingTaskStatus;
            })
            .map(taskStatusRepository::save)
            .map(taskStatusMapper::toDto);
    }

    /**
     * Get all the taskStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStatuses");
        return taskStatusRepository.findAll(pageable).map(taskStatusMapper::toDto);
    }

    /**
     * Get one taskStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskStatusDTO> findOne(Long id) {
        log.debug("Request to get TaskStatus : {}", id);
        return taskStatusRepository.findById(id).map(taskStatusMapper::toDto);
    }

    /**
     * Delete the taskStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskStatus : {}", id);
        taskStatusRepository.deleteById(id);
    }
}
