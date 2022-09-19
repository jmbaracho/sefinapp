package br.com.sefin.web.rest;

import br.com.sefin.repository.HotelResultFacilityRepository;
import br.com.sefin.service.HotelResultFacilityService;
import br.com.sefin.service.dto.HotelResultFacilityDTO;
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
 * REST controller for managing {@link br.com.sefin.domain.HotelResultFacility}.
 */
@RestController
@RequestMapping("/api")
public class HotelResultFacilityResource {

    private final Logger log = LoggerFactory.getLogger(HotelResultFacilityResource.class);

    private static final String ENTITY_NAME = "hotelResultFacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelResultFacilityService hotelResultFacilityService;

    private final HotelResultFacilityRepository hotelResultFacilityRepository;

    public HotelResultFacilityResource(
        HotelResultFacilityService hotelResultFacilityService,
        HotelResultFacilityRepository hotelResultFacilityRepository
    ) {
        this.hotelResultFacilityService = hotelResultFacilityService;
        this.hotelResultFacilityRepository = hotelResultFacilityRepository;
    }

    /**
     * {@code POST  /hotel-result-facilities} : Create a new hotelResultFacility.
     *
     * @param hotelResultFacilityDTO the hotelResultFacilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelResultFacilityDTO, or with status {@code 400 (Bad Request)} if the hotelResultFacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-result-facilities")
    public ResponseEntity<HotelResultFacilityDTO> createHotelResultFacility(@RequestBody HotelResultFacilityDTO hotelResultFacilityDTO)
        throws URISyntaxException {
        log.debug("REST request to save HotelResultFacility : {}", hotelResultFacilityDTO);
        if (hotelResultFacilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelResultFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelResultFacilityDTO result = hotelResultFacilityService.save(hotelResultFacilityDTO);
        return ResponseEntity
            .created(new URI("/api/hotel-result-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-result-facilities/:id} : Updates an existing hotelResultFacility.
     *
     * @param id the id of the hotelResultFacilityDTO to save.
     * @param hotelResultFacilityDTO the hotelResultFacilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelResultFacilityDTO,
     * or with status {@code 400 (Bad Request)} if the hotelResultFacilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelResultFacilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-result-facilities/{id}")
    public ResponseEntity<HotelResultFacilityDTO> updateHotelResultFacility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelResultFacilityDTO hotelResultFacilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelResultFacility : {}, {}", id, hotelResultFacilityDTO);
        if (hotelResultFacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelResultFacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelResultFacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HotelResultFacilityDTO result = hotelResultFacilityService.update(hotelResultFacilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelResultFacilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotel-result-facilities/:id} : Partial updates given fields of an existing hotelResultFacility, field will ignore if it is null
     *
     * @param id the id of the hotelResultFacilityDTO to save.
     * @param hotelResultFacilityDTO the hotelResultFacilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelResultFacilityDTO,
     * or with status {@code 400 (Bad Request)} if the hotelResultFacilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelResultFacilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelResultFacilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotel-result-facilities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelResultFacilityDTO> partialUpdateHotelResultFacility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelResultFacilityDTO hotelResultFacilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelResultFacility partially : {}, {}", id, hotelResultFacilityDTO);
        if (hotelResultFacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelResultFacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelResultFacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelResultFacilityDTO> result = hotelResultFacilityService.partialUpdate(hotelResultFacilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelResultFacilityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-result-facilities} : get all the hotelResultFacilities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelResultFacilities in body.
     */
    @GetMapping("/hotel-result-facilities")
    public ResponseEntity<List<HotelResultFacilityDTO>> getAllHotelResultFacilities(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of HotelResultFacilities");
        Page<HotelResultFacilityDTO> page = hotelResultFacilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hotel-result-facilities/:id} : get the "id" hotelResultFacility.
     *
     * @param id the id of the hotelResultFacilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelResultFacilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-result-facilities/{id}")
    public ResponseEntity<HotelResultFacilityDTO> getHotelResultFacility(@PathVariable Long id) {
        log.debug("REST request to get HotelResultFacility : {}", id);
        Optional<HotelResultFacilityDTO> hotelResultFacilityDTO = hotelResultFacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelResultFacilityDTO);
    }

    /**
     * {@code DELETE  /hotel-result-facilities/:id} : delete the "id" hotelResultFacility.
     *
     * @param id the id of the hotelResultFacilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-result-facilities/{id}")
    public ResponseEntity<Void> deleteHotelResultFacility(@PathVariable Long id) {
        log.debug("REST request to delete HotelResultFacility : {}", id);
        hotelResultFacilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
