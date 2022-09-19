package br.com.sefin.service;

import br.com.sefin.domain.AtividadeCPBS;
import br.com.sefin.repository.AtividadeCPBSRepository;
import br.com.sefin.service.dto.AtividadeCPBSDTO;
import br.com.sefin.service.mapper.AtividadeCPBSMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AtividadeCPBS}.
 */
@Service
@Transactional
public class AtividadeCPBSService {

    private final Logger log = LoggerFactory.getLogger(AtividadeCPBSService.class);

    private final AtividadeCPBSRepository atividadeCPBSRepository;

    private final AtividadeCPBSMapper atividadeCPBSMapper;

    public AtividadeCPBSService(AtividadeCPBSRepository atividadeCPBSRepository, AtividadeCPBSMapper atividadeCPBSMapper) {
        this.atividadeCPBSRepository = atividadeCPBSRepository;
        this.atividadeCPBSMapper = atividadeCPBSMapper;
    }

    /**
     * Save a atividadeCPBS.
     *
     * @param atividadeCPBSDTO the entity to save.
     * @return the persisted entity.
     */
    public AtividadeCPBSDTO save(AtividadeCPBSDTO atividadeCPBSDTO) {
        log.debug("Request to save AtividadeCPBS : {}", atividadeCPBSDTO);
        AtividadeCPBS atividadeCPBS = atividadeCPBSMapper.toEntity(atividadeCPBSDTO);
        atividadeCPBS = atividadeCPBSRepository.save(atividadeCPBS);
        return atividadeCPBSMapper.toDto(atividadeCPBS);
    }

    /**
     * Update a atividadeCPBS.
     *
     * @param atividadeCPBSDTO the entity to save.
     * @return the persisted entity.
     */
    public AtividadeCPBSDTO update(AtividadeCPBSDTO atividadeCPBSDTO) {
        log.debug("Request to update AtividadeCPBS : {}", atividadeCPBSDTO);
        AtividadeCPBS atividadeCPBS = atividadeCPBSMapper.toEntity(atividadeCPBSDTO);
        atividadeCPBS = atividadeCPBSRepository.save(atividadeCPBS);
        return atividadeCPBSMapper.toDto(atividadeCPBS);
    }

    /**
     * Partially update a atividadeCPBS.
     *
     * @param atividadeCPBSDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AtividadeCPBSDTO> partialUpdate(AtividadeCPBSDTO atividadeCPBSDTO) {
        log.debug("Request to partially update AtividadeCPBS : {}", atividadeCPBSDTO);

        return atividadeCPBSRepository
            .findById(atividadeCPBSDTO.getId())
            .map(existingAtividadeCPBS -> {
                atividadeCPBSMapper.partialUpdate(existingAtividadeCPBS, atividadeCPBSDTO);

                return existingAtividadeCPBS;
            })
            .map(atividadeCPBSRepository::save)
            .map(atividadeCPBSMapper::toDto);
    }

    /**
     * Get all the atividadeCPBS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtividadeCPBSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AtividadeCPBS");
        return atividadeCPBSRepository.findAll(pageable).map(atividadeCPBSMapper::toDto);
    }

    /**
     * Get all the atividadeCPBS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AtividadeCPBSDTO> findAllWithEagerRelationships(Pageable pageable) {
        return atividadeCPBSRepository.findAllWithEagerRelationships(pageable).map(atividadeCPBSMapper::toDto);
    }

    /**
     * Get one atividadeCPBS by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtividadeCPBSDTO> findOne(Long id) {
        log.debug("Request to get AtividadeCPBS : {}", id);
        return atividadeCPBSRepository.findOneWithEagerRelationships(id).map(atividadeCPBSMapper::toDto);
    }

    /**
     * Delete the atividadeCPBS by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtividadeCPBS : {}", id);
        atividadeCPBSRepository.deleteById(id);
    }
}
