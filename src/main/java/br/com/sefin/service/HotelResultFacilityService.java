package br.com.sefin.service;

import br.com.sefin.domain.HotelResultFacility;
import br.com.sefin.repository.HotelResultFacilityRepository;
import br.com.sefin.service.dto.HotelResultFacilityDTO;
import br.com.sefin.service.mapper.HotelResultFacilityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HotelResultFacility}.
 */
@Service
@Transactional
public class HotelResultFacilityService {

    private final Logger log = LoggerFactory.getLogger(HotelResultFacilityService.class);

    private final HotelResultFacilityRepository hotelResultFacilityRepository;

    private final HotelResultFacilityMapper hotelResultFacilityMapper;

    public HotelResultFacilityService(
        HotelResultFacilityRepository hotelResultFacilityRepository,
        HotelResultFacilityMapper hotelResultFacilityMapper
    ) {
        this.hotelResultFacilityRepository = hotelResultFacilityRepository;
        this.hotelResultFacilityMapper = hotelResultFacilityMapper;
    }

    /**
     * Save a hotelResultFacility.
     *
     * @param hotelResultFacilityDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelResultFacilityDTO save(HotelResultFacilityDTO hotelResultFacilityDTO) {
        log.debug("Request to save HotelResultFacility : {}", hotelResultFacilityDTO);
        HotelResultFacility hotelResultFacility = hotelResultFacilityMapper.toEntity(hotelResultFacilityDTO);
        hotelResultFacility = hotelResultFacilityRepository.save(hotelResultFacility);
        return hotelResultFacilityMapper.toDto(hotelResultFacility);
    }

    /**
     * Update a hotelResultFacility.
     *
     * @param hotelResultFacilityDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelResultFacilityDTO update(HotelResultFacilityDTO hotelResultFacilityDTO) {
        log.debug("Request to update HotelResultFacility : {}", hotelResultFacilityDTO);
        HotelResultFacility hotelResultFacility = hotelResultFacilityMapper.toEntity(hotelResultFacilityDTO);
        hotelResultFacility = hotelResultFacilityRepository.save(hotelResultFacility);
        return hotelResultFacilityMapper.toDto(hotelResultFacility);
    }

    /**
     * Partially update a hotelResultFacility.
     *
     * @param hotelResultFacilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelResultFacilityDTO> partialUpdate(HotelResultFacilityDTO hotelResultFacilityDTO) {
        log.debug("Request to partially update HotelResultFacility : {}", hotelResultFacilityDTO);

        return hotelResultFacilityRepository
            .findById(hotelResultFacilityDTO.getId())
            .map(existingHotelResultFacility -> {
                hotelResultFacilityMapper.partialUpdate(existingHotelResultFacility, hotelResultFacilityDTO);

                return existingHotelResultFacility;
            })
            .map(hotelResultFacilityRepository::save)
            .map(hotelResultFacilityMapper::toDto);
    }

    /**
     * Get all the hotelResultFacilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelResultFacilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HotelResultFacilities");
        return hotelResultFacilityRepository.findAll(pageable).map(hotelResultFacilityMapper::toDto);
    }

    /**
     * Get one hotelResultFacility by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelResultFacilityDTO> findOne(Long id) {
        log.debug("Request to get HotelResultFacility : {}", id);
        return hotelResultFacilityRepository.findById(id).map(hotelResultFacilityMapper::toDto);
    }

    /**
     * Delete the hotelResultFacility by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelResultFacility : {}", id);
        hotelResultFacilityRepository.deleteById(id);
    }
}
