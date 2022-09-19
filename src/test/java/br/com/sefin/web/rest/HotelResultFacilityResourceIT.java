package br.com.sefin.web.rest;

import static br.com.sefin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.HotelResultFacility;
import br.com.sefin.repository.HotelResultFacilityRepository;
import br.com.sefin.service.dto.HotelResultFacilityDTO;
import br.com.sefin.service.mapper.HotelResultFacilityMapper;
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
 * Integration tests for the {@link HotelResultFacilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelResultFacilityResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/hotel-result-facilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelResultFacilityRepository hotelResultFacilityRepository;

    @Autowired
    private HotelResultFacilityMapper hotelResultFacilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelResultFacilityMockMvc;

    private HotelResultFacility hotelResultFacility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelResultFacility createEntity(EntityManager em) {
        HotelResultFacility hotelResultFacility = new HotelResultFacility().createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return hotelResultFacility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelResultFacility createUpdatedEntity(EntityManager em) {
        HotelResultFacility hotelResultFacility = new HotelResultFacility().createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return hotelResultFacility;
    }

    @BeforeEach
    public void initTest() {
        hotelResultFacility = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelResultFacility() throws Exception {
        int databaseSizeBeforeCreate = hotelResultFacilityRepository.findAll().size();
        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);
        restHotelResultFacilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeCreate + 1);
        HotelResultFacility testHotelResultFacility = hotelResultFacilityList.get(hotelResultFacilityList.size() - 1);
        assertThat(testHotelResultFacility.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testHotelResultFacility.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createHotelResultFacilityWithExistingId() throws Exception {
        // Create the HotelResultFacility with an existing ID
        hotelResultFacility.setId(1L);
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        int databaseSizeBeforeCreate = hotelResultFacilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelResultFacilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelResultFacilities() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        // Get all the hotelResultFacilityList
        restHotelResultFacilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelResultFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getHotelResultFacility() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        // Get the hotelResultFacility
        restHotelResultFacilityMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelResultFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelResultFacility.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingHotelResultFacility() throws Exception {
        // Get the hotelResultFacility
        restHotelResultFacilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotelResultFacility() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();

        // Update the hotelResultFacility
        HotelResultFacility updatedHotelResultFacility = hotelResultFacilityRepository.findById(hotelResultFacility.getId()).get();
        // Disconnect from session so that the updates on updatedHotelResultFacility are not directly saved in db
        em.detach(updatedHotelResultFacility);
        updatedHotelResultFacility.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(updatedHotelResultFacility);

        restHotelResultFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelResultFacilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
        HotelResultFacility testHotelResultFacility = hotelResultFacilityList.get(hotelResultFacilityList.size() - 1);
        assertThat(testHotelResultFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHotelResultFacility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelResultFacilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelResultFacilityWithPatch() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();

        // Update the hotelResultFacility using partial update
        HotelResultFacility partialUpdatedHotelResultFacility = new HotelResultFacility();
        partialUpdatedHotelResultFacility.setId(hotelResultFacility.getId());

        partialUpdatedHotelResultFacility.updatedAt(UPDATED_UPDATED_AT);

        restHotelResultFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelResultFacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelResultFacility))
            )
            .andExpect(status().isOk());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
        HotelResultFacility testHotelResultFacility = hotelResultFacilityList.get(hotelResultFacilityList.size() - 1);
        assertThat(testHotelResultFacility.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testHotelResultFacility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateHotelResultFacilityWithPatch() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();

        // Update the hotelResultFacility using partial update
        HotelResultFacility partialUpdatedHotelResultFacility = new HotelResultFacility();
        partialUpdatedHotelResultFacility.setId(hotelResultFacility.getId());

        partialUpdatedHotelResultFacility.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restHotelResultFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelResultFacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelResultFacility))
            )
            .andExpect(status().isOk());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
        HotelResultFacility testHotelResultFacility = hotelResultFacilityList.get(hotelResultFacilityList.size() - 1);
        assertThat(testHotelResultFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHotelResultFacility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelResultFacilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelResultFacility() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultFacilityRepository.findAll().size();
        hotelResultFacility.setId(count.incrementAndGet());

        // Create the HotelResultFacility
        HotelResultFacilityDTO hotelResultFacilityDTO = hotelResultFacilityMapper.toDto(hotelResultFacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultFacilityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultFacilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelResultFacility in the database
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelResultFacility() throws Exception {
        // Initialize the database
        hotelResultFacilityRepository.saveAndFlush(hotelResultFacility);

        int databaseSizeBeforeDelete = hotelResultFacilityRepository.findAll().size();

        // Delete the hotelResultFacility
        restHotelResultFacilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelResultFacility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelResultFacility> hotelResultFacilityList = hotelResultFacilityRepository.findAll();
        assertThat(hotelResultFacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
