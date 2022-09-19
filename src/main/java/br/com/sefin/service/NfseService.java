package br.com.sefin.service;

import br.com.sefin.domain.Nfse;
import br.com.sefin.repository.NfseRepository;
import br.com.sefin.service.dto.NfseDTO;
import br.com.sefin.service.mapper.NfseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nfse}.
 */
@Service
@Transactional
public class NfseService {

    private final Logger log = LoggerFactory.getLogger(NfseService.class);

    private final NfseRepository nfseRepository;

    private final NfseMapper nfseMapper;

    public NfseService(NfseRepository nfseRepository, NfseMapper nfseMapper) {
        this.nfseRepository = nfseRepository;
        this.nfseMapper = nfseMapper;
    }

    /**
     * Save a nfse.
     *
     * @param nfseDTO the entity to save.
     * @return the persisted entity.
     */
    public NfseDTO save(NfseDTO nfseDTO) {
        log.debug("Request to save Nfse : {}", nfseDTO);
        Nfse nfse = nfseMapper.toEntity(nfseDTO);
        nfse = nfseRepository.save(nfse);
        return nfseMapper.toDto(nfse);
    }

    /**
     * Update a nfse.
     *
     * @param nfseDTO the entity to save.
     * @return the persisted entity.
     */
    public NfseDTO update(NfseDTO nfseDTO) {
        log.debug("Request to update Nfse : {}", nfseDTO);
        Nfse nfse = nfseMapper.toEntity(nfseDTO);
        nfse = nfseRepository.save(nfse);
        return nfseMapper.toDto(nfse);
    }

    /**
     * Partially update a nfse.
     *
     * @param nfseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NfseDTO> partialUpdate(NfseDTO nfseDTO) {
        log.debug("Request to partially update Nfse : {}", nfseDTO);

        return nfseRepository
            .findById(nfseDTO.getId())
            .map(existingNfse -> {
                nfseMapper.partialUpdate(existingNfse, nfseDTO);

                return existingNfse;
            })
            .map(nfseRepository::save)
            .map(nfseMapper::toDto);
    }

    /**
     * Get all the nfses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NfseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nfses");
        return nfseRepository.findAll(pageable).map(nfseMapper::toDto);
    }

    /**
     * Get all the nfses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<NfseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return nfseRepository.findAllWithEagerRelationships(pageable).map(nfseMapper::toDto);
    }

    /**
     * Get one nfse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NfseDTO> findOne(Long id) {
        log.debug("Request to get Nfse : {}", id);
        return nfseRepository.findOneWithEagerRelationships(id).map(nfseMapper::toDto);
    }

    /**
     * Delete the nfse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nfse : {}", id);
        nfseRepository.deleteById(id);
    }
}
