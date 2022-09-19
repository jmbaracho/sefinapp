package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.ScheduleExecution;
import br.com.sefin.repository.ScheduleExecutionRepository;
import br.com.sefin.service.dto.ScheduleExecutionDTO;
import br.com.sefin.service.mapper.ScheduleExecutionMapper;
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
 * Integration tests for the {@link ScheduleExecutionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScheduleExecutionResourceIT {

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/schedule-executions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScheduleExecutionRepository scheduleExecutionRepository;

    @Autowired
    private ScheduleExecutionMapper scheduleExecutionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleExecutionMockMvc;

    private ScheduleExecution scheduleExecution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleExecution createEntity(EntityManager em) {
        ScheduleExecution scheduleExecution = new ScheduleExecution().createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return scheduleExecution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleExecution createUpdatedEntity(EntityManager em) {
        ScheduleExecution scheduleExecution = new ScheduleExecution().createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return scheduleExecution;
    }

    @BeforeEach
    public void initTest() {
        scheduleExecution = createEntity(em);
    }

    @Test
    @Transactional
    void createScheduleExecution() throws Exception {
        int databaseSizeBeforeCreate = scheduleExecutionRepository.findAll().size();
        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);
        restScheduleExecutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleExecution testScheduleExecution = scheduleExecutionList.get(scheduleExecutionList.size() - 1);
        assertThat(testScheduleExecution.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testScheduleExecution.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createScheduleExecutionWithExistingId() throws Exception {
        // Create the ScheduleExecution with an existing ID
        scheduleExecution.setId(1L);
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        int databaseSizeBeforeCreate = scheduleExecutionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleExecutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScheduleExecutions() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        // Get all the scheduleExecutionList
        restScheduleExecutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleExecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getScheduleExecution() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        // Get the scheduleExecution
        restScheduleExecutionMockMvc
            .perform(get(ENTITY_API_URL_ID, scheduleExecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleExecution.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingScheduleExecution() throws Exception {
        // Get the scheduleExecution
        restScheduleExecutionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScheduleExecution() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();

        // Update the scheduleExecution
        ScheduleExecution updatedScheduleExecution = scheduleExecutionRepository.findById(scheduleExecution.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleExecution are not directly saved in db
        em.detach(updatedScheduleExecution);
        updatedScheduleExecution.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(updatedScheduleExecution);

        restScheduleExecutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduleExecutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleExecution testScheduleExecution = scheduleExecutionList.get(scheduleExecutionList.size() - 1);
        assertThat(testScheduleExecution.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testScheduleExecution.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduleExecutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScheduleExecutionWithPatch() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();

        // Update the scheduleExecution using partial update
        ScheduleExecution partialUpdatedScheduleExecution = new ScheduleExecution();
        partialUpdatedScheduleExecution.setId(scheduleExecution.getId());

        restScheduleExecutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduleExecution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScheduleExecution))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleExecution testScheduleExecution = scheduleExecutionList.get(scheduleExecutionList.size() - 1);
        assertThat(testScheduleExecution.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testScheduleExecution.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateScheduleExecutionWithPatch() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();

        // Update the scheduleExecution using partial update
        ScheduleExecution partialUpdatedScheduleExecution = new ScheduleExecution();
        partialUpdatedScheduleExecution.setId(scheduleExecution.getId());

        partialUpdatedScheduleExecution.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restScheduleExecutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduleExecution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScheduleExecution))
            )
            .andExpect(status().isOk());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
        ScheduleExecution testScheduleExecution = scheduleExecutionList.get(scheduleExecutionList.size() - 1);
        assertThat(testScheduleExecution.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testScheduleExecution.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scheduleExecutionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScheduleExecution() throws Exception {
        int databaseSizeBeforeUpdate = scheduleExecutionRepository.findAll().size();
        scheduleExecution.setId(count.incrementAndGet());

        // Create the ScheduleExecution
        ScheduleExecutionDTO scheduleExecutionDTO = scheduleExecutionMapper.toDto(scheduleExecution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduleExecutionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scheduleExecutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduleExecution in the database
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScheduleExecution() throws Exception {
        // Initialize the database
        scheduleExecutionRepository.saveAndFlush(scheduleExecution);

        int databaseSizeBeforeDelete = scheduleExecutionRepository.findAll().size();

        // Delete the scheduleExecution
        restScheduleExecutionMockMvc
            .perform(delete(ENTITY_API_URL_ID, scheduleExecution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleExecution> scheduleExecutionList = scheduleExecutionRepository.findAll();
        assertThat(scheduleExecutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
