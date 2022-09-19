package br.com.sefin.service;

import br.com.sefin.domain.TagText;
import br.com.sefin.repository.TagTextRepository;
import br.com.sefin.service.dto.TagTextDTO;
import br.com.sefin.service.mapper.TagTextMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TagText}.
 */
@Service
@Transactional
public class TagTextService {

    private final Logger log = LoggerFactory.getLogger(TagTextService.class);

    private final TagTextRepository tagTextRepository;

    private final TagTextMapper tagTextMapper;

    public TagTextService(TagTextRepository tagTextRepository, TagTextMapper tagTextMapper) {
        this.tagTextRepository = tagTextRepository;
        this.tagTextMapper = tagTextMapper;
    }

    /**
     * Save a tagText.
     *
     * @param tagTextDTO the entity to save.
     * @return the persisted entity.
     */
    public TagTextDTO save(TagTextDTO tagTextDTO) {
        log.debug("Request to save TagText : {}", tagTextDTO);
        TagText tagText = tagTextMapper.toEntity(tagTextDTO);
        tagText = tagTextRepository.save(tagText);
        return tagTextMapper.toDto(tagText);
    }

    /**
     * Update a tagText.
     *
     * @param tagTextDTO the entity to save.
     * @return the persisted entity.
     */
    public TagTextDTO update(TagTextDTO tagTextDTO) {
        log.debug("Request to update TagText : {}", tagTextDTO);
        TagText tagText = tagTextMapper.toEntity(tagTextDTO);
        tagText = tagTextRepository.save(tagText);
        return tagTextMapper.toDto(tagText);
    }

    /**
     * Partially update a tagText.
     *
     * @param tagTextDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TagTextDTO> partialUpdate(TagTextDTO tagTextDTO) {
        log.debug("Request to partially update TagText : {}", tagTextDTO);

        return tagTextRepository
            .findById(tagTextDTO.getId())
            .map(existingTagText -> {
                tagTextMapper.partialUpdate(existingTagText, tagTextDTO);

                return existingTagText;
            })
            .map(tagTextRepository::save)
            .map(tagTextMapper::toDto);
    }

    /**
     * Get all the tagTexts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TagTextDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagTexts");
        return tagTextRepository.findAll(pageable).map(tagTextMapper::toDto);
    }

    /**
     * Get all the tagTexts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TagTextDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tagTextRepository.findAllWithEagerRelationships(pageable).map(tagTextMapper::toDto);
    }

    /**
     * Get one tagText by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TagTextDTO> findOne(Long id) {
        log.debug("Request to get TagText : {}", id);
        return tagTextRepository.findOneWithEagerRelationships(id).map(tagTextMapper::toDto);
    }

    /**
     * Delete the tagText by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TagText : {}", id);
        tagTextRepository.deleteById(id);
    }
}
