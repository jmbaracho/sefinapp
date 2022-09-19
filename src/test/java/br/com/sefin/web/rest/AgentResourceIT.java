package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.Agent;
import br.com.sefin.repository.AgentRepository;
import br.com.sefin.service.dto.AgentDTO;
import br.com.sefin.service.mapper.AgentMapper;
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
 * Integration tests for the {@link AgentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_BLOCKS_PER_TIME = 1;
    private static final Integer UPDATED_MAX_BLOCKS_PER_TIME = 2;

    private static final Integer DEFAULT_BLOCK_SIZE = 1;
    private static final Integer UPDATED_BLOCK_SIZE = 2;

    private static final Integer DEFAULT_INTERVAL_BETWEEN_BLOCKS = 1;
    private static final Integer UPDATED_INTERVAL_BETWEEN_BLOCKS = 2;

    private static final Integer DEFAULT_INTERVAL_BETWEEN_TASKS = 1;
    private static final Integer UPDATED_INTERVAL_BETWEEN_TASKS = 2;

    private static final Integer DEFAULT_MAX_ATTEMPTS = 1;
    private static final Integer UPDATED_MAX_ATTEMPTS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/agents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgentMockMvc;

    private Agent agent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(DEFAULT_NAME)
            .maxBlocksPerTime(DEFAULT_MAX_BLOCKS_PER_TIME)
            .blockSize(DEFAULT_BLOCK_SIZE)
            .intervalBetweenBlocks(DEFAULT_INTERVAL_BETWEEN_BLOCKS)
            .intervalBetweenTasks(DEFAULT_INTERVAL_BETWEEN_TASKS)
            .maxAttempts(DEFAULT_MAX_ATTEMPTS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return agent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createUpdatedEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(UPDATED_NAME)
            .maxBlocksPerTime(UPDATED_MAX_BLOCKS_PER_TIME)
            .blockSize(UPDATED_BLOCK_SIZE)
            .intervalBetweenBlocks(UPDATED_INTERVAL_BETWEEN_BLOCKS)
            .intervalBetweenTasks(UPDATED_INTERVAL_BETWEEN_TASKS)
            .maxAttempts(UPDATED_MAX_ATTEMPTS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return agent;
    }

    @BeforeEach
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();
        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);
        restAgentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgent.getMaxBlocksPerTime()).isEqualTo(DEFAULT_MAX_BLOCKS_PER_TIME);
        assertThat(testAgent.getBlockSize()).isEqualTo(DEFAULT_BLOCK_SIZE);
        assertThat(testAgent.getIntervalBetweenBlocks()).isEqualTo(DEFAULT_INTERVAL_BETWEEN_BLOCKS);
        assertThat(testAgent.getIntervalBetweenTasks()).isEqualTo(DEFAULT_INTERVAL_BETWEEN_TASKS);
        assertThat(testAgent.getMaxAttempts()).isEqualTo(DEFAULT_MAX_ATTEMPTS);
        assertThat(testAgent.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAgent.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createAgentWithExistingId() throws Exception {
        // Create the Agent with an existing ID
        agent.setId(1L);
        AgentDTO agentDTO = agentMapper.toDto(agent);

        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].maxBlocksPerTime").value(hasItem(DEFAULT_MAX_BLOCKS_PER_TIME)))
            .andExpect(jsonPath("$.[*].blockSize").value(hasItem(DEFAULT_BLOCK_SIZE)))
            .andExpect(jsonPath("$.[*].intervalBetweenBlocks").value(hasItem(DEFAULT_INTERVAL_BETWEEN_BLOCKS)))
            .andExpect(jsonPath("$.[*].intervalBetweenTasks").value(hasItem(DEFAULT_INTERVAL_BETWEEN_TASKS)))
            .andExpect(jsonPath("$.[*].maxAttempts").value(hasItem(DEFAULT_MAX_ATTEMPTS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc
            .perform(get(ENTITY_API_URL_ID, agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.maxBlocksPerTime").value(DEFAULT_MAX_BLOCKS_PER_TIME))
            .andExpect(jsonPath("$.blockSize").value(DEFAULT_BLOCK_SIZE))
            .andExpect(jsonPath("$.intervalBetweenBlocks").value(DEFAULT_INTERVAL_BETWEEN_BLOCKS))
            .andExpect(jsonPath("$.intervalBetweenTasks").value(DEFAULT_INTERVAL_BETWEEN_TASKS))
            .andExpect(jsonPath("$.maxAttempts").value(DEFAULT_MAX_ATTEMPTS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findById(agent.getId()).get();
        // Disconnect from session so that the updates on updatedAgent are not directly saved in db
        em.detach(updatedAgent);
        updatedAgent
            .name(UPDATED_NAME)
            .maxBlocksPerTime(UPDATED_MAX_BLOCKS_PER_TIME)
            .blockSize(UPDATED_BLOCK_SIZE)
            .intervalBetweenBlocks(UPDATED_INTERVAL_BETWEEN_BLOCKS)
            .intervalBetweenTasks(UPDATED_INTERVAL_BETWEEN_TASKS)
            .maxAttempts(UPDATED_MAX_ATTEMPTS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        AgentDTO agentDTO = agentMapper.toDto(updatedAgent);

        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getMaxBlocksPerTime()).isEqualTo(UPDATED_MAX_BLOCKS_PER_TIME);
        assertThat(testAgent.getBlockSize()).isEqualTo(UPDATED_BLOCK_SIZE);
        assertThat(testAgent.getIntervalBetweenBlocks()).isEqualTo(UPDATED_INTERVAL_BETWEEN_BLOCKS);
        assertThat(testAgent.getIntervalBetweenTasks()).isEqualTo(UPDATED_INTERVAL_BETWEEN_TASKS);
        assertThat(testAgent.getMaxAttempts()).isEqualTo(UPDATED_MAX_ATTEMPTS);
        assertThat(testAgent.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAgent.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent
            .name(UPDATED_NAME)
            .intervalBetweenBlocks(UPDATED_INTERVAL_BETWEEN_BLOCKS)
            .maxAttempts(UPDATED_MAX_ATTEMPTS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getMaxBlocksPerTime()).isEqualTo(DEFAULT_MAX_BLOCKS_PER_TIME);
        assertThat(testAgent.getBlockSize()).isEqualTo(DEFAULT_BLOCK_SIZE);
        assertThat(testAgent.getIntervalBetweenBlocks()).isEqualTo(UPDATED_INTERVAL_BETWEEN_BLOCKS);
        assertThat(testAgent.getIntervalBetweenTasks()).isEqualTo(DEFAULT_INTERVAL_BETWEEN_TASKS);
        assertThat(testAgent.getMaxAttempts()).isEqualTo(UPDATED_MAX_ATTEMPTS);
        assertThat(testAgent.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAgent.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent
            .name(UPDATED_NAME)
            .maxBlocksPerTime(UPDATED_MAX_BLOCKS_PER_TIME)
            .blockSize(UPDATED_BLOCK_SIZE)
            .intervalBetweenBlocks(UPDATED_INTERVAL_BETWEEN_BLOCKS)
            .intervalBetweenTasks(UPDATED_INTERVAL_BETWEEN_TASKS)
            .maxAttempts(UPDATED_MAX_ATTEMPTS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getMaxBlocksPerTime()).isEqualTo(UPDATED_MAX_BLOCKS_PER_TIME);
        assertThat(testAgent.getBlockSize()).isEqualTo(UPDATED_BLOCK_SIZE);
        assertThat(testAgent.getIntervalBetweenBlocks()).isEqualTo(UPDATED_INTERVAL_BETWEEN_BLOCKS);
        assertThat(testAgent.getIntervalBetweenTasks()).isEqualTo(UPDATED_INTERVAL_BETWEEN_TASKS);
        assertThat(testAgent.getMaxAttempts()).isEqualTo(UPDATED_MAX_ATTEMPTS);
        assertThat(testAgent.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAgent.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Delete the agent
        restAgentMockMvc
            .perform(delete(ENTITY_API_URL_ID, agent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
