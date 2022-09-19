package br.com.sefin.service;

import br.com.sefin.domain.Facility;
import br.com.sefin.repository.FacilityRepository;
import br.com.sefin.service.dto.FacilityDTO;
import br.com.sefin.service.mapper.FacilityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Facility}.
 */
@Service
@Transactional
public class FacilityService {

    private final Logger log = LoggerFactory.getLogger(FacilityService.class);

    private final FacilityRepository facilityRepository;

    private final FacilityMapper facilityMapper;

    public FacilityService(FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
    }

    /**
     * Save a facility.
     *
     * @param facilityDTO the entity to save.
     * @return the persisted entity.
     */
    public FacilityDTO save(FacilityDTO facilityDTO) {
        log.debug("Request to save Facility : {}", facilityDTO);
        Facility facility = facilityMapper.toEntity(facilityDTO);
        facility = facilityRepository.save(facility);
        return facilityMapper.toDto(facility);
    }

    /**
     * Update a facility.
     *
     * @param facilityDTO the entity to save.
     * @return the persisted entity.
     */
    public FacilityDTO update(FacilityDTO facilityDTO) {
        log.debug("Request to update Facility : {}", facilityDTO);
        Facility facility = facilityMapper.toEntity(facilityDTO);
        facility = facilityRepository.save(facility);
        return facilityMapper.toDto(facility);
    }

    /**
     * Partially update a facility.
     *
     * @param facilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FacilityDTO> partialUpdate(FacilityDTO facilityDTO) {
        log.debug("Request to partially update Facility : {}", facilityDTO);

        return facilityRepository
            .findById(facilityDTO.getId())
            .map(existingFacility -> {
                facilityMapper.partialUpdate(existingFacility, facilityDTO);

                return existingFacility;
            })
            .map(facilityRepository::save)
            .map(facilityMapper::toDto);
    }

    /**
     * Get all the facilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Facilities");
        return facilityRepository.findAll(pageable).map(facilityMapper::toDto);
    }

    /**
     * Get one facility by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacilityDTO> findOne(Long id) {
        log.debug("Request to get Facility : {}", id);
        return facilityRepository.findById(id).map(facilityMapper::toDto);
    }

    /**
     * Delete the facility by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Facility : {}", id);
        facilityRepository.deleteById(id);
    }
}
