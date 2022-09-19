package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.TaskStatus;
import br.com.sefin.repository.TaskStatusRepository;
import br.com.sefin.service.dto.TaskStatusDTO;
import br.com.sefin.service.mapper.TaskStatusMapper;
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
 * Integration tests for the {@link TaskStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/task-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskStatusMockMvc;

    private TaskStatus taskStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStatus createEntity(EntityManager em) {
        TaskStatus taskStatus = new TaskStatus().name(DEFAULT_NAME).createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return taskStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStatus createUpdatedEntity(EntityManager em) {
        TaskStatus taskStatus = new TaskStatus().name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return taskStatus;
    }

    @BeforeEach
    public void initTest() {
        taskStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskStatus() throws Exception {
        int databaseSizeBeforeCreate = taskStatusRepository.findAll().size();
        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);
        restTaskStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStatus.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTaskStatus.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTaskStatusWithExistingId() throws Exception {
        // Create the TaskStatus with an existing ID
        taskStatus.setId(1L);
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        int databaseSizeBeforeCreate = taskStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList
        restTaskStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTaskStatus() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get the taskStatus
        restTaskStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, taskStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTaskStatus() throws Exception {
        // Get the taskStatus
        restTaskStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaskStatus() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();

        // Update the taskStatus
        TaskStatus updatedTaskStatus = taskStatusRepository.findById(taskStatus.getId()).get();
        // Disconnect from session so that the updates on updatedTaskStatus are not directly saved in db
        em.detach(updatedTaskStatus);
        updatedTaskStatus.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(updatedTaskStatus);

        restTaskStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStatus.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTaskStatus.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskStatusWithPatch() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();

        // Update the taskStatus using partial update
        TaskStatus partialUpdatedTaskStatus = new TaskStatus();
        partialUpdatedTaskStatus.setId(taskStatus.getId());

        restTaskStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStatus))
            )
            .andExpect(status().isOk());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStatus.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTaskStatus.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTaskStatusWithPatch() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();

        // Update the taskStatus using partial update
        TaskStatus partialUpdatedTaskStatus = new TaskStatus();
        partialUpdatedTaskStatus.setId(taskStatus.getId());

        partialUpdatedTaskStatus.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restTaskStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStatus))
            )
            .andExpect(status().isOk());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStatus.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTaskStatus.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();
        taskStatus.setId(count.incrementAndGet());

        // Create the TaskStatus
        TaskStatusDTO taskStatusDTO = taskStatusMapper.toDto(taskStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taskStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskStatus() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        int databaseSizeBeforeDelete = taskStatusRepository.findAll().size();

        // Delete the taskStatus
        restTaskStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
