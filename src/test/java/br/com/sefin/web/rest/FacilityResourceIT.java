package br.com.sefin.web.rest;

import static br.com.sefin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.Facility;
import br.com.sefin.repository.FacilityRepository;
import br.com.sefin.service.dto.FacilityDTO;
import br.com.sefin.service.mapper.FacilityMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FacilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FacilityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/facilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityMockMvc;

    private Facility facility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facility createEntity(EntityManager em) {
        Facility facility = new Facility().name(DEFAULT_NAME).createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return facility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facility createUpdatedEntity(EntityManager em) {
        Facility facility = new Facility().name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return facility;
    }

    @BeforeEach
    public void initTest() {
        facility = createEntity(em);
    }

    @Test
    @Transactional
    void createFacility() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();
        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);
        restFacilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate + 1);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacility.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFacility.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createFacilityWithExistingId() throws Exception {
        // Create the Facility with an existing ID
        facility.setId(1L);
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFacilities() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList
        restFacilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get the facility
        restFacilityMockMvc
            .perform(get(ENTITY_API_URL_ID, facility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facility.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingFacility() throws Exception {
        // Get the facility
        restFacilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility
        Facility updatedFacility = facilityRepository.findById(facility.getId()).get();
        // Disconnect from session so that the updates on updatedFacility are not directly saved in db
        em.detach(updatedFacility);
        updatedFacility.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        FacilityDTO facilityDTO = facilityMapper.toDto(updatedFacility);

        restFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFacility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facilityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacilityWithPatch() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility using partial update
        Facility partialUpdatedFacility = new Facility();
        partialUpdatedFacility.setId(facility.getId());

        partialUpdatedFacility.createdAt(UPDATED_CREATED_AT);

        restFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacility))
            )
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFacility.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateFacilityWithPatch() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility using partial update
        Facility partialUpdatedFacility = new Facility();
        partialUpdatedFacility.setId(facility.getId());

        partialUpdatedFacility.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacility))
            )
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFacility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();
        facility.setId(count.incrementAndGet());

        // Create the Facility
        FacilityDTO facilityDTO = facilityMapper.toDto(facility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(facilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        int databaseSizeBeforeDelete = facilityRepository.findAll().size();

        // Delete the facility
        restFacilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, facility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
