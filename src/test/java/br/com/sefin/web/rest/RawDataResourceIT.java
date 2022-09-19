package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.RawData;
import br.com.sefin.repository.RawDataRepository;
import br.com.sefin.service.dto.RawDataDTO;
import br.com.sefin.service.mapper.RawDataMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RawDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RawDataResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/raw-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawDataRepository rawDataRepository;

    @Autowired
    private RawDataMapper rawDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawDataMockMvc;

    private RawData rawData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawData createEntity(EntityManager em) {
        RawData rawData = new RawData()
            .content(DEFAULT_CONTENT)
            .processed(DEFAULT_PROCESSED)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return rawData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawData createUpdatedEntity(EntityManager em) {
        RawData rawData = new RawData()
            .content(UPDATED_CONTENT)
            .processed(UPDATED_PROCESSED)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return rawData;
    }

    @BeforeEach
    public void initTest() {
        rawData = createEntity(em);
    }

    @Test
    @Transactional
    void createRawData() throws Exception {
        int databaseSizeBeforeCreate = rawDataRepository.findAll().size();
        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);
        restRawDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawDataDTO)))
            .andExpect(status().isCreated());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeCreate + 1);
        RawData testRawData = rawDataList.get(rawDataList.size() - 1);
        assertThat(testRawData.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testRawData.getProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testRawData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRawData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createRawDataWithExistingId() throws Exception {
        // Create the RawData with an existing ID
        rawData.setId(1L);
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        int databaseSizeBeforeCreate = rawDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawData() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        // Get all the rawDataList
        restRawDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getRawData() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        // Get the rawData
        restRawDataMockMvc
            .perform(get(ENTITY_API_URL_ID, rawData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRawData() throws Exception {
        // Get the rawData
        restRawDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRawData() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();

        // Update the rawData
        RawData updatedRawData = rawDataRepository.findById(rawData.getId()).get();
        // Disconnect from session so that the updates on updatedRawData are not directly saved in db
        em.detach(updatedRawData);
        updatedRawData.content(UPDATED_CONTENT).processed(UPDATED_PROCESSED).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        RawDataDTO rawDataDTO = rawDataMapper.toDto(updatedRawData);

        restRawDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
        RawData testRawData = rawDataList.get(rawDataList.size() - 1);
        assertThat(testRawData.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testRawData.getProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testRawData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRawData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawDataWithPatch() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();

        // Update the rawData using partial update
        RawData partialUpdatedRawData = new RawData();
        partialUpdatedRawData.setId(rawData.getId());

        partialUpdatedRawData.processed(UPDATED_PROCESSED).createdAt(UPDATED_CREATED_AT);

        restRawDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawData))
            )
            .andExpect(status().isOk());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
        RawData testRawData = rawDataList.get(rawDataList.size() - 1);
        assertThat(testRawData.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testRawData.getProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testRawData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRawData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateRawDataWithPatch() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();

        // Update the rawData using partial update
        RawData partialUpdatedRawData = new RawData();
        partialUpdatedRawData.setId(rawData.getId());

        partialUpdatedRawData
            .content(UPDATED_CONTENT)
            .processed(UPDATED_PROCESSED)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restRawDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawData))
            )
            .andExpect(status().isOk());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
        RawData testRawData = rawDataList.get(rawDataList.size() - 1);
        assertThat(testRawData.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testRawData.getProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testRawData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRawData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawData() throws Exception {
        int databaseSizeBeforeUpdate = rawDataRepository.findAll().size();
        rawData.setId(count.incrementAndGet());

        // Create the RawData
        RawDataDTO rawDataDTO = rawDataMapper.toDto(rawData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rawDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawData in the database
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawData() throws Exception {
        // Initialize the database
        rawDataRepository.saveAndFlush(rawData);

        int databaseSizeBeforeDelete = rawDataRepository.findAll().size();

        // Delete the rawData
        restRawDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawData> rawDataList = rawDataRepository.findAll();
        assertThat(rawDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
