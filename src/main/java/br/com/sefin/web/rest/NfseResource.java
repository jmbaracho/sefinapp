package br.com.sefin.web.rest;

import br.com.sefin.repository.NfseRepository;
import br.com.sefin.service.NfseService;
import br.com.sefin.service.dto.NfseDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.Nfse}.
 */
@RestController
@RequestMapping("/api")
public class NfseResource {

    private final Logger log = LoggerFactory.getLogger(NfseResource.class);

    private static final String ENTITY_NAME = "nfse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NfseService nfseService;

    private final NfseRepository nfseRepository;

    public NfseResource(NfseService nfseService, NfseRepository nfseRepository) {
        this.nfseService = nfseService;
        this.nfseRepository = nfseRepository;
    }

    /**
     * {@code POST  /nfses} : Create a new nfse.
     *
     * @param nfseDTO the nfseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nfseDTO, or with status {@code 400 (Bad Request)} if the nfse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nfses")
    public ResponseEntity<NfseDTO> createNfse(@RequestBody NfseDTO nfseDTO) throws URISyntaxException {
        log.debug("REST request to save Nfse : {}", nfseDTO);
        if (nfseDTO.getId() != null) {
            throw new BadRequestAlertException("A new nfse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NfseDTO result = nfseService.save(nfseDTO);
        return ResponseEntity
            .created(new URI("/api/nfses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nfses/:id} : Updates an existing nfse.
     *
     * @param id the id of the nfseDTO to save.
     * @param nfseDTO the nfseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nfseDTO,
     * or with status {@code 400 (Bad Request)} if the nfseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nfseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nfses/{id}")
    public ResponseEntity<NfseDTO> updateNfse(@PathVariable(value = "id", required = false) final Long id, @RequestBody NfseDTO nfseDTO)
        throws URISyntaxException {
        log.debug("REST request to update Nfse : {}, {}", id, nfseDTO);
        if (nfseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nfseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nfseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NfseDTO result = nfseService.update(nfseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nfseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nfses/:id} : Partial updates given fields of an existing nfse, field will ignore if it is null
     *
     * @param id the id of the nfseDTO to save.
     * @param nfseDTO the nfseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nfseDTO,
     * or with status {@code 400 (Bad Request)} if the nfseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nfseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nfseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nfses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NfseDTO> partialUpdateNfse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NfseDTO nfseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nfse partially : {}, {}", id, nfseDTO);
        if (nfseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nfseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nfseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NfseDTO> result = nfseService.partialUpdate(nfseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nfseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nfses} : get all the nfses.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nfses in body.
     */
    @GetMapping("/nfses")
    public ResponseEntity<List<NfseDTO>> getAllNfses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Nfses");
        Page<NfseDTO> page;
        if (eagerload) {
            page = nfseService.findAllWithEagerRelationships(pageable);
        } else {
            page = nfseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nfses/:id} : get the "id" nfse.
     *
     * @param id the id of the nfseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nfseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nfses/{id}")
    public ResponseEntity<NfseDTO> getNfse(@PathVariable Long id) {
        log.debug("REST request to get Nfse : {}", id);
        Optional<NfseDTO> nfseDTO = nfseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nfseDTO);
    }

    /**
     * {@code DELETE  /nfses/:id} : delete the "id" nfse.
     *
     * @param id the id of the nfseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nfses/{id}")
    public ResponseEntity<Void> deleteNfse(@PathVariable Long id) {
        log.debug("REST request to delete Nfse : {}", id);
        nfseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
