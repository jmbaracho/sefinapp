package br.com.sefin.web.rest;

import br.com.sefin.repository.HotelResultRepository;
import br.com.sefin.service.HotelResultService;
import br.com.sefin.service.dto.HotelResultDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.HotelResult}.
 */
@RestController
@RequestMapping("/api")
public class HotelResultResource {

    private final Logger log = LoggerFactory.getLogger(HotelResultResource.class);

    private static final String ENTITY_NAME = "hotelResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelResultService hotelResultService;

    private final HotelResultRepository hotelResultRepository;

    public HotelResultResource(HotelResultService hotelResultService, HotelResultRepository hotelResultRepository) {
        this.hotelResultService = hotelResultService;
        this.hotelResultRepository = hotelResultRepository;
    }

    /**
     * {@code POST  /hotel-results} : Create a new hotelResult.
     *
     * @param hotelResultDTO the hotelResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelResultDTO, or with status {@code 400 (Bad Request)} if the hotelResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-results")
    public ResponseEntity<HotelResultDTO> createHotelResult(@RequestBody HotelResultDTO hotelResultDTO) throws URISyntaxException {
        log.debug("REST request to save HotelResult : {}", hotelResultDTO);
        if (hotelResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelResultDTO result = hotelResultService.save(hotelResultDTO);
        return ResponseEntity
            .created(new URI("/api/hotel-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-results/:id} : Updates an existing hotelResult.
     *
     * @param id the id of the hotelResultDTO to save.
     * @param hotelResultDTO the hotelResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelResultDTO,
     * or with status {@code 400 (Bad Request)} if the hotelResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-results/{id}")
    public ResponseEntity<HotelResultDTO> updateHotelResult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelResultDTO hotelResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelResult : {}, {}", id, hotelResultDTO);
        if (hotelResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HotelResultDTO result = hotelResultService.update(hotelResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotel-results/:id} : Partial updates given fields of an existing hotelResult, field will ignore if it is null
     *
     * @param id the id of the hotelResultDTO to save.
     * @param hotelResultDTO the hotelResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelResultDTO,
     * or with status {@code 400 (Bad Request)} if the hotelResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotel-results/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelResultDTO> partialUpdateHotelResult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelResultDTO hotelResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelResult partially : {}, {}", id, hotelResultDTO);
        if (hotelResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelResultDTO> result = hotelResultService.partialUpdate(hotelResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelResultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-results} : get all the hotelResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelResults in body.
     */
    @GetMapping("/hotel-results")
    public ResponseEntity<List<HotelResultDTO>> getAllHotelResults(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HotelResults");
        Page<HotelResultDTO> page = hotelResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hotel-results/:id} : get the "id" hotelResult.
     *
     * @param id the id of the hotelResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-results/{id}")
    public ResponseEntity<HotelResultDTO> getHotelResult(@PathVariable Long id) {
        log.debug("REST request to get HotelResult : {}", id);
        Optional<HotelResultDTO> hotelResultDTO = hotelResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelResultDTO);
    }

    /**
     * {@code DELETE  /hotel-results/:id} : delete the "id" hotelResult.
     *
     * @param id the id of the hotelResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-results/{id}")
    public ResponseEntity<Void> deleteHotelResult(@PathVariable Long id) {
        log.debug("REST request to delete HotelResult : {}", id);
        hotelResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
