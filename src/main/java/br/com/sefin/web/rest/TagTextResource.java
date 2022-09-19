package br.com.sefin.web.rest;

import br.com.sefin.repository.TagTextRepository;
import br.com.sefin.service.TagTextService;
import br.com.sefin.service.dto.TagTextDTO;
import br.com.sefin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.sefin.domain.TagText}.
 */
@RestController
@RequestMapping("/api")
public class TagTextResource {

    private final Logger log = LoggerFactory.getLogger(TagTextResource.class);

    private static final String ENTITY_NAME = "tagText";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagTextService tagTextService;

    private final TagTextRepository tagTextRepository;

    public TagTextResource(TagTextService tagTextService, TagTextRepository tagTextRepository) {
        this.tagTextService = tagTextService;
        this.tagTextRepository = tagTextRepository;
    }

    /**
     * {@code POST  /tag-texts} : Create a new tagText.
     *
     * @param tagTextDTO the tagTextDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagTextDTO, or with status {@code 400 (Bad Request)} if the tagText has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-texts")
    public ResponseEntity<TagTextDTO> createTagText(@RequestBody TagTextDTO tagTextDTO) throws URISyntaxException {
        log.debug("REST request to save TagText : {}", tagTextDTO);
        if (tagTextDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagText cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagTextDTO result = tagTextService.save(tagTextDTO);
        return ResponseEntity
            .created(new URI("/api/tag-texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-texts/:id} : Updates an existing tagText.
     *
     * @param id the id of the tagTextDTO to save.
     * @param tagTextDTO the tagTextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagTextDTO,
     * or with status {@code 400 (Bad Request)} if the tagTextDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagTextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-texts/{id}")
    public ResponseEntity<TagTextDTO> updateTagText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagTextDTO tagTextDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TagText : {}, {}", id, tagTextDTO);
        if (tagTextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagTextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TagTextDTO result = tagTextService.update(tagTextDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagTextDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tag-texts/:id} : Partial updates given fields of an existing tagText, field will ignore if it is null
     *
     * @param id the id of the tagTextDTO to save.
     * @param tagTextDTO the tagTextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagTextDTO,
     * or with status {@code 400 (Bad Request)} if the tagTextDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tagTextDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tagTextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tag-texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TagTextDTO> partialUpdateTagText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagTextDTO tagTextDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TagText partially : {}, {}", id, tagTextDTO);
        if (tagTextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagTextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TagTextDTO> result = tagTextService.partialUpdate(tagTextDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagTextDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tag-texts} : get all the tagTexts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagTexts in body.
     */
    @GetMapping("/tag-texts")
    public ResponseEntity<List<TagTextDTO>> getAllTagTexts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TagTexts");
        Page<TagTextDTO> page;
        if (eagerload) {
            page = tagTextService.findAllWithEagerRelationships(pageable);
        } else {
            page = tagTextService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-texts/:id} : get the "id" tagText.
     *
     * @param id the id of the tagTextDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagTextDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-texts/{id}")
    public ResponseEntity<TagTextDTO> getTagText(@PathVariable Long id) {
        log.debug("REST request to get TagText : {}", id);
        Optional<TagTextDTO> tagTextDTO = tagTextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagTextDTO);
    }

    /**
     * {@code DELETE  /tag-texts/:id} : delete the "id" tagText.
     *
     * @param id the id of the tagTextDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-texts/{id}")
    public ResponseEntity<Void> deleteTagText(@PathVariable Long id) {
        log.debug("REST request to delete TagText : {}", id);
        tagTextService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
