package br.com.sefin.web.rest;

import static br.com.sefin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.HotelResult;
import br.com.sefin.repository.HotelResultRepository;
import br.com.sefin.service.dto.HotelResultDTO;
import br.com.sefin.service.mapper.HotelResultMapper;
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
 * Integration tests for the {@link HotelResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelResultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARS_COUNT = 1;
    private static final Integer UPDATED_STARS_COUNT = 2;

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/hotel-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelResultRepository hotelResultRepository;

    @Autowired
    private HotelResultMapper hotelResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelResultMockMvc;

    private HotelResult hotelResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelResult createEntity(EntityManager em) {
        HotelResult hotelResult = new HotelResult()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .starsCount(DEFAULT_STARS_COUNT)
            .rating(DEFAULT_RATING)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return hotelResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelResult createUpdatedEntity(EntityManager em) {
        HotelResult hotelResult = new HotelResult()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .starsCount(UPDATED_STARS_COUNT)
            .rating(UPDATED_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return hotelResult;
    }

    @BeforeEach
    public void initTest() {
        hotelResult = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelResult() throws Exception {
        int databaseSizeBeforeCreate = hotelResultRepository.findAll().size();
        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);
        restHotelResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeCreate + 1);
        HotelResult testHotelResult = hotelResultList.get(hotelResultList.size() - 1);
        assertThat(testHotelResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotelResult.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHotelResult.getStarsCount()).isEqualTo(DEFAULT_STARS_COUNT);
        assertThat(testHotelResult.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testHotelResult.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testHotelResult.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createHotelResultWithExistingId() throws Exception {
        // Create the HotelResult with an existing ID
        hotelResult.setId(1L);
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        int databaseSizeBeforeCreate = hotelResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelResults() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        // Get all the hotelResultList
        restHotelResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].starsCount").value(hasItem(DEFAULT_STARS_COUNT)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getHotelResult() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        // Get the hotelResult
        restHotelResultMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.starsCount").value(DEFAULT_STARS_COUNT))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingHotelResult() throws Exception {
        // Get the hotelResult
        restHotelResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotelResult() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();

        // Update the hotelResult
        HotelResult updatedHotelResult = hotelResultRepository.findById(hotelResult.getId()).get();
        // Disconnect from session so that the updates on updatedHotelResult are not directly saved in db
        em.detach(updatedHotelResult);
        updatedHotelResult
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .starsCount(UPDATED_STARS_COUNT)
            .rating(UPDATED_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(updatedHotelResult);

        restHotelResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
        HotelResult testHotelResult = hotelResultList.get(hotelResultList.size() - 1);
        assertThat(testHotelResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotelResult.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHotelResult.getStarsCount()).isEqualTo(UPDATED_STARS_COUNT);
        assertThat(testHotelResult.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testHotelResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHotelResult.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelResultDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelResultWithPatch() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();

        // Update the hotelResult using partial update
        HotelResult partialUpdatedHotelResult = new HotelResult();
        partialUpdatedHotelResult.setId(hotelResult.getId());

        partialUpdatedHotelResult.address(UPDATED_ADDRESS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restHotelResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelResult))
            )
            .andExpect(status().isOk());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
        HotelResult testHotelResult = hotelResultList.get(hotelResultList.size() - 1);
        assertThat(testHotelResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotelResult.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHotelResult.getStarsCount()).isEqualTo(DEFAULT_STARS_COUNT);
        assertThat(testHotelResult.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testHotelResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHotelResult.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateHotelResultWithPatch() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();

        // Update the hotelResult using partial update
        HotelResult partialUpdatedHotelResult = new HotelResult();
        partialUpdatedHotelResult.setId(hotelResult.getId());

        partialUpdatedHotelResult
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .starsCount(UPDATED_STARS_COUNT)
            .rating(UPDATED_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restHotelResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelResult))
            )
            .andExpect(status().isOk());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
        HotelResult testHotelResult = hotelResultList.get(hotelResultList.size() - 1);
        assertThat(testHotelResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotelResult.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHotelResult.getStarsCount()).isEqualTo(UPDATED_STARS_COUNT);
        assertThat(testHotelResult.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testHotelResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testHotelResult.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelResult() throws Exception {
        int databaseSizeBeforeUpdate = hotelResultRepository.findAll().size();
        hotelResult.setId(count.incrementAndGet());

        // Create the HotelResult
        HotelResultDTO hotelResultDTO = hotelResultMapper.toDto(hotelResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelResultMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hotelResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelResult in the database
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelResult() throws Exception {
        // Initialize the database
        hotelResultRepository.saveAndFlush(hotelResult);

        int databaseSizeBeforeDelete = hotelResultRepository.findAll().size();

        // Delete the hotelResult
        restHotelResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelResult> hotelResultList = hotelResultRepository.findAll();
        assertThat(hotelResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
