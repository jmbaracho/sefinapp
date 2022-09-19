package br.com.sefin.service;

import br.com.sefin.domain.ScheduleExecution;
import br.com.sefin.repository.ScheduleExecutionRepository;
import br.com.sefin.service.dto.ScheduleExecutionDTO;
import br.com.sefin.service.mapper.ScheduleExecutionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScheduleExecution}.
 */
@Service
@Transactional
public class ScheduleExecutionService {

    private final Logger log = LoggerFactory.getLogger(ScheduleExecutionService.class);

    private final ScheduleExecutionRepository scheduleExecutionRepository;

    private final ScheduleExecutionMapper scheduleExecutionMapper;

    public ScheduleExecutionService(
        ScheduleExecutionRepository scheduleExecutionRepository,
        ScheduleExecutionMapper scheduleExecutionMapper
    ) {
        this.scheduleExecutionRepository = scheduleExecutionRepository;
        this.scheduleExecutionMapper = scheduleExecutionMapper;
    }

    /**
     * Save a scheduleExecution.
     *
     * @param scheduleExecutionDTO the entity to save.
     * @return the persisted entity.
     */
    public ScheduleExecutionDTO save(ScheduleExecutionDTO scheduleExecutionDTO) {
        log.debug("Request to save ScheduleExecution : {}", scheduleExecutionDTO);
        ScheduleExecution scheduleExecution = scheduleExecutionMapper.toEntity(scheduleExecutionDTO);
        scheduleExecution = scheduleExecutionRepository.save(scheduleExecution);
        return scheduleExecutionMapper.toDto(scheduleExecution);
    }

    /**
     * Update a scheduleExecution.
     *
     * @param scheduleExecutionDTO the entity to save.
     * @return the persisted entity.
     */
    public ScheduleExecutionDTO update(ScheduleExecutionDTO scheduleExecutionDTO) {
        log.debug("Request to update ScheduleExecution : {}", scheduleExecutionDTO);
        ScheduleExecution scheduleExecution = scheduleExecutionMapper.toEntity(scheduleExecutionDTO);
        scheduleExecution = scheduleExecutionRepository.save(scheduleExecution);
        return scheduleExecutionMapper.toDto(scheduleExecution);
    }

    /**
     * Partially update a scheduleExecution.
     *
     * @param scheduleExecutionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ScheduleExecutionDTO> partialUpdate(ScheduleExecutionDTO scheduleExecutionDTO) {
        log.debug("Request to partially update ScheduleExecution : {}", scheduleExecutionDTO);

        return scheduleExecutionRepository
            .findById(scheduleExecutionDTO.getId())
            .map(existingScheduleExecution -> {
                scheduleExecutionMapper.partialUpdate(existingScheduleExecution, scheduleExecutionDTO);

                return existingScheduleExecution;
            })
            .map(scheduleExecutionRepository::save)
            .map(scheduleExecutionMapper::toDto);
    }

    /**
     * Get all the scheduleExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScheduleExecutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduleExecutions");
        return scheduleExecutionRepository.findAll(pageable).map(scheduleExecutionMapper::toDto);
    }

    /**
     * Get one scheduleExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScheduleExecutionDTO> findOne(Long id) {
        log.debug("Request to get ScheduleExecution : {}", id);
        return scheduleExecutionRepository.findById(id).map(scheduleExecutionMapper::toDto);
    }

    /**
     * Delete the scheduleExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ScheduleExecution : {}", id);
        scheduleExecutionRepository.deleteById(id);
    }
}
