package br.com.sefin.service;

import br.com.sefin.domain.RawData;
import br.com.sefin.repository.RawDataRepository;
import br.com.sefin.service.dto.RawDataDTO;
import br.com.sefin.service.mapper.RawDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RawData}.
 */
@Service
@Transactional
public class RawDataService {

    private final Logger log = LoggerFactory.getLogger(RawDataService.class);

    private final RawDataRepository rawDataRepository;

    private final RawDataMapper rawDataMapper;

    public RawDataService(RawDataRepository rawDataRepository, RawDataMapper rawDataMapper) {
        this.rawDataRepository = rawDataRepository;
        this.rawDataMapper = rawDataMapper;
    }

    /**
     * Save a rawData.
     *
     * @param rawDataDTO the entity to save.
     * @return the persisted entity.
     */
    public RawDataDTO save(RawDataDTO rawDataDTO) {
        log.debug("Request to save RawData : {}", rawDataDTO);
        RawData rawData = rawDataMapper.toEntity(rawDataDTO);
        rawData = rawDataRepository.save(rawData);
        return rawDataMapper.toDto(rawData);
    }

    /**
     * Update a rawData.
     *
     * @param rawDataDTO the entity to save.
     * @return the persisted entity.
     */
    public RawDataDTO update(RawDataDTO rawDataDTO) {
        log.debug("Request to update RawData : {}", rawDataDTO);
        RawData rawData = rawDataMapper.toEntity(rawDataDTO);
        rawData = rawDataRepository.save(rawData);
        return rawDataMapper.toDto(rawData);
    }

    /**
     * Partially update a rawData.
     *
     * @param rawDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RawDataDTO> partialUpdate(RawDataDTO rawDataDTO) {
        log.debug("Request to partially update RawData : {}", rawDataDTO);

        return rawDataRepository
            .findById(rawDataDTO.getId())
            .map(existingRawData -> {
                rawDataMapper.partialUpdate(existingRawData, rawDataDTO);

                return existingRawData;
            })
            .map(rawDataRepository::save)
            .map(rawDataMapper::toDto);
    }

    /**
     * Get all the rawData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RawDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RawData");
        return rawDataRepository.findAll(pageable).map(rawDataMapper::toDto);
    }

    /**
     * Get one rawData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RawDataDTO> findOne(Long id) {
        log.debug("Request to get RawData : {}", id);
        return rawDataRepository.findById(id).map(rawDataMapper::toDto);
    }

    /**
     * Delete the rawData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RawData : {}", id);
        rawDataRepository.deleteById(id);
    }
}
