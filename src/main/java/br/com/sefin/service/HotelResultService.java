package br.com.sefin.service;

import br.com.sefin.domain.HotelResult;
import br.com.sefin.repository.HotelResultRepository;
import br.com.sefin.service.dto.HotelResultDTO;
import br.com.sefin.service.mapper.HotelResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HotelResult}.
 */
@Service
@Transactional
public class HotelResultService {

    private final Logger log = LoggerFactory.getLogger(HotelResultService.class);

    private final HotelResultRepository hotelResultRepository;

    private final HotelResultMapper hotelResultMapper;

    public HotelResultService(HotelResultRepository hotelResultRepository, HotelResultMapper hotelResultMapper) {
        this.hotelResultRepository = hotelResultRepository;
        this.hotelResultMapper = hotelResultMapper;
    }

    /**
     * Save a hotelResult.
     *
     * @param hotelResultDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelResultDTO save(HotelResultDTO hotelResultDTO) {
        log.debug("Request to save HotelResult : {}", hotelResultDTO);
        HotelResult hotelResult = hotelResultMapper.toEntity(hotelResultDTO);
        hotelResult = hotelResultRepository.save(hotelResult);
        return hotelResultMapper.toDto(hotelResult);
    }

    /**
     * Update a hotelResult.
     *
     * @param hotelResultDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelResultDTO update(HotelResultDTO hotelResultDTO) {
        log.debug("Request to update HotelResult : {}", hotelResultDTO);
        HotelResult hotelResult = hotelResultMapper.toEntity(hotelResultDTO);
        hotelResult = hotelResultRepository.save(hotelResult);
        return hotelResultMapper.toDto(hotelResult);
    }

    /**
     * Partially update a hotelResult.
     *
     * @param hotelResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelResultDTO> partialUpdate(HotelResultDTO hotelResultDTO) {
        log.debug("Request to partially update HotelResult : {}", hotelResultDTO);

        return hotelResultRepository
            .findById(hotelResultDTO.getId())
            .map(existingHotelResult -> {
                hotelResultMapper.partialUpdate(existingHotelResult, hotelResultDTO);

                return existingHotelResult;
            })
            .map(hotelResultRepository::save)
            .map(hotelResultMapper::toDto);
    }

    /**
     * Get all the hotelResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HotelResults");
        return hotelResultRepository.findAll(pageable).map(hotelResultMapper::toDto);
    }

    /**
     * Get one hotelResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelResultDTO> findOne(Long id) {
        log.debug("Request to get HotelResult : {}", id);
        return hotelResultRepository.findById(id).map(hotelResultMapper::toDto);
    }

    /**
     * Delete the hotelResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelResult : {}", id);
        hotelResultRepository.deleteById(id);
    }
}
