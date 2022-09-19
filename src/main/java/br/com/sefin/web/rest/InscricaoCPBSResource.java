package br.com.sefin.web.rest;

import br.com.sefin.repository.InscricaoCPBSRepository;
import br.com.sefin.service.InscricaoCPBSService;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.InscricaoCPBS}.
 */
@RestController
@RequestMapping("/api")
public class InscricaoCPBSResource {

    private final Logger log = LoggerFactory.getLogger(InscricaoCPBSResource.class);

    private static final String ENTITY_NAME = "inscricaoCPBS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InscricaoCPBSService inscricaoCPBSService;

    private final InscricaoCPBSRepository inscricaoCPBSRepository;

    public InscricaoCPBSResource(InscricaoCPBSService inscricaoCPBSService, InscricaoCPBSRepository inscricaoCPBSRepository) {
        this.inscricaoCPBSService = inscricaoCPBSService;
        this.inscricaoCPBSRepository = inscricaoCPBSRepository;
    }

    /**
     * {@code POST  /inscricao-cpbs} : Create a new inscricaoCPBS.
     *
     * @param inscricaoCPBSDTO the inscricaoCPBSDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inscricaoCPBSDTO, or with status {@code 400 (Bad Request)} if the inscricaoCPBS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inscricao-cpbs")
    public ResponseEntity<InscricaoCPBSDTO> createInscricaoCPBS(@RequestBody InscricaoCPBSDTO inscricaoCPBSDTO) throws URISyntaxException {
        log.debug("REST request to save InscricaoCPBS : {}", inscricaoCPBSDTO);
        if (inscricaoCPBSDTO.getId() != null) {
            throw new BadRequestAlertException("A new inscricaoCPBS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InscricaoCPBSDTO result = inscricaoCPBSService.save(inscricaoCPBSDTO);
        return ResponseEntity
            .created(new URI("/api/inscricao-cpbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inscricao-cpbs/:id} : Updates an existing inscricaoCPBS.
     *
     * @param id the id of the inscricaoCPBSDTO to save.
     * @param inscricaoCPBSDTO the inscricaoCPBSDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscricaoCPBSDTO,
     * or with status {@code 400 (Bad Request)} if the inscricaoCPBSDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inscricaoCPBSDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inscricao-cpbs/{id}")
    public ResponseEntity<InscricaoCPBSDTO> updateInscricaoCPBS(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InscricaoCPBSDTO inscricaoCPBSDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InscricaoCPBS : {}, {}", id, inscricaoCPBSDTO);
        if (inscricaoCPBSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscricaoCPBSDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscricaoCPBSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InscricaoCPBSDTO result = inscricaoCPBSService.update(inscricaoCPBSDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inscricaoCPBSDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inscricao-cpbs/:id} : Partial updates given fields of an existing inscricaoCPBS, field will ignore if it is null
     *
     * @param id the id of the inscricaoCPBSDTO to save.
     * @param inscricaoCPBSDTO the inscricaoCPBSDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscricaoCPBSDTO,
     * or with status {@code 400 (Bad Request)} if the inscricaoCPBSDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inscricaoCPBSDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inscricaoCPBSDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inscricao-cpbs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InscricaoCPBSDTO> partialUpdateInscricaoCPBS(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InscricaoCPBSDTO inscricaoCPBSDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InscricaoCPBS partially : {}, {}", id, inscricaoCPBSDTO);
        if (inscricaoCPBSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscricaoCPBSDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscricaoCPBSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InscricaoCPBSDTO> result = inscricaoCPBSService.partialUpdate(inscricaoCPBSDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inscricaoCPBSDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inscricao-cpbs} : get all the inscricaoCPBS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inscricaoCPBS in body.
     */
    @GetMapping("/inscricao-cpbs")
    public ResponseEntity<List<InscricaoCPBSDTO>> getAllInscricaoCPBS(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InscricaoCPBS");
        Page<InscricaoCPBSDTO> page = inscricaoCPBSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inscricao-cpbs/:id} : get the "id" inscricaoCPBS.
     *
     * @param id the id of the inscricaoCPBSDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inscricaoCPBSDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inscricao-cpbs/{id}")
    public ResponseEntity<InscricaoCPBSDTO> getInscricaoCPBS(@PathVariable Long id) {
        log.debug("REST request to get InscricaoCPBS : {}", id);
        Optional<InscricaoCPBSDTO> inscricaoCPBSDTO = inscricaoCPBSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscricaoCPBSDTO);
    }

    /**
     * {@code DELETE  /inscricao-cpbs/:id} : delete the "id" inscricaoCPBS.
     *
     * @param id the id of the inscricaoCPBSDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inscricao-cpbs/{id}")
    public ResponseEntity<Void> deleteInscricaoCPBS(@PathVariable Long id) {
        log.debug("REST request to delete InscricaoCPBS : {}", id);
        inscricaoCPBSService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
