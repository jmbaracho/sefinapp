package br.com.sefin.service;

import br.com.sefin.domain.InscricaoCPBS;
import br.com.sefin.repository.InscricaoCPBSRepository;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
import br.com.sefin.service.mapper.InscricaoCPBSMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InscricaoCPBS}.
 */
@Service
@Transactional
public class InscricaoCPBSService {

    private final Logger log = LoggerFactory.getLogger(InscricaoCPBSService.class);

    private final InscricaoCPBSRepository inscricaoCPBSRepository;

    private final InscricaoCPBSMapper inscricaoCPBSMapper;

    public InscricaoCPBSService(InscricaoCPBSRepository inscricaoCPBSRepository, InscricaoCPBSMapper inscricaoCPBSMapper) {
        this.inscricaoCPBSRepository = inscricaoCPBSRepository;
        this.inscricaoCPBSMapper = inscricaoCPBSMapper;
    }

    /**
     * Save a inscricaoCPBS.
     *
     * @param inscricaoCPBSDTO the entity to save.
     * @return the persisted entity.
     */
    public InscricaoCPBSDTO save(InscricaoCPBSDTO inscricaoCPBSDTO) {
        log.debug("Request to save InscricaoCPBS : {}", inscricaoCPBSDTO);
        InscricaoCPBS inscricaoCPBS = inscricaoCPBSMapper.toEntity(inscricaoCPBSDTO);
        inscricaoCPBS = inscricaoCPBSRepository.save(inscricaoCPBS);
        return inscricaoCPBSMapper.toDto(inscricaoCPBS);
    }

    /**
     * Update a inscricaoCPBS.
     *
     * @param inscricaoCPBSDTO the entity to save.
     * @return the persisted entity.
     */
    public InscricaoCPBSDTO update(InscricaoCPBSDTO inscricaoCPBSDTO) {
        log.debug("Request to update InscricaoCPBS : {}", inscricaoCPBSDTO);
        InscricaoCPBS inscricaoCPBS = inscricaoCPBSMapper.toEntity(inscricaoCPBSDTO);
        inscricaoCPBS = inscricaoCPBSRepository.save(inscricaoCPBS);
        return inscricaoCPBSMapper.toDto(inscricaoCPBS);
    }

    /**
     * Partially update a inscricaoCPBS.
     *
     * @param inscricaoCPBSDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InscricaoCPBSDTO> partialUpdate(InscricaoCPBSDTO inscricaoCPBSDTO) {
        log.debug("Request to partially update InscricaoCPBS : {}", inscricaoCPBSDTO);

        return inscricaoCPBSRepository
            .findById(inscricaoCPBSDTO.getId())
            .map(existingInscricaoCPBS -> {
                inscricaoCPBSMapper.partialUpdate(existingInscricaoCPBS, inscricaoCPBSDTO);

                return existingInscricaoCPBS;
            })
            .map(inscricaoCPBSRepository::save)
            .map(inscricaoCPBSMapper::toDto);
    }

    /**
     * Get all the inscricaoCPBS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InscricaoCPBSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InscricaoCPBS");
        return inscricaoCPBSRepository.findAll(pageable).map(inscricaoCPBSMapper::toDto);
    }

    /**
     * Get one inscricaoCPBS by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InscricaoCPBSDTO> findOne(Long id) {
        log.debug("Request to get InscricaoCPBS : {}", id);
        return inscricaoCPBSRepository.findById(id).map(inscricaoCPBSMapper::toDto);
    }

    /**
     * Delete the inscricaoCPBS by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InscricaoCPBS : {}", id);
        inscricaoCPBSRepository.deleteById(id);
    }
}
