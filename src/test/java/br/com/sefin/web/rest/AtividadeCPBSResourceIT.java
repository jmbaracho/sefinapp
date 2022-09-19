package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.AtividadeCPBS;
import br.com.sefin.repository.AtividadeCPBSRepository;
import br.com.sefin.service.AtividadeCPBSService;
import br.com.sefin.service.dto.AtividadeCPBSDTO;
import br.com.sefin.service.mapper.AtividadeCPBSMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AtividadeCPBSResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AtividadeCPBSResourceIT {

    private static final String DEFAULT_ID_ATIVIDADE_CPBS = "AAAAAAAAAA";
    private static final String UPDATED_ID_ATIVIDADE_CPBS = "BBBBBBBBBB";

    private static final String DEFAULT_CNAE = "AAAAAAAAAA";
    private static final String UPDATED_CNAE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_CNAE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_CNAE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String DEFAULT_ID_SEGMENTO_ISS = "AAAAAAAAAA";
    private static final String UPDATED_ID_SEGMENTO_ISS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_SEGMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_SEGMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_LISTA_SER_CPBS = "AAAAAAAAAA";
    private static final String UPDATED_ID_LISTA_SER_CPBS = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_LISTA_SER_CPBS = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_LISTA_SER_CPBS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_LISTA_SER_CPBS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_LISTA_SER_CPBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/atividade-cpbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AtividadeCPBSRepository atividadeCPBSRepository;

    @Mock
    private AtividadeCPBSRepository atividadeCPBSRepositoryMock;

    @Autowired
    private AtividadeCPBSMapper atividadeCPBSMapper;

    @Mock
    private AtividadeCPBSService atividadeCPBSServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtividadeCPBSMockMvc;

    private AtividadeCPBS atividadeCPBS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeCPBS createEntity(EntityManager em) {
        AtividadeCPBS atividadeCPBS = new AtividadeCPBS()
            .idAtividadeCpbs(DEFAULT_ID_ATIVIDADE_CPBS)
            .cnae(DEFAULT_CNAE)
            .descricaoCnae(DEFAULT_DESCRICAO_CNAE)
            .principal(DEFAULT_PRINCIPAL)
            .idSegmentoIss(DEFAULT_ID_SEGMENTO_ISS)
            .descricaoSegmento(DEFAULT_DESCRICAO_SEGMENTO)
            .idListaSerCpbs(DEFAULT_ID_LISTA_SER_CPBS)
            .codigoListaSerCpbs(DEFAULT_CODIGO_LISTA_SER_CPBS)
            .descricaoListaSerCpbs(DEFAULT_DESCRICAO_LISTA_SER_CPBS);
        return atividadeCPBS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeCPBS createUpdatedEntity(EntityManager em) {
        AtividadeCPBS atividadeCPBS = new AtividadeCPBS()
            .idAtividadeCpbs(UPDATED_ID_ATIVIDADE_CPBS)
            .cnae(UPDATED_CNAE)
            .descricaoCnae(UPDATED_DESCRICAO_CNAE)
            .principal(UPDATED_PRINCIPAL)
            .idSegmentoIss(UPDATED_ID_SEGMENTO_ISS)
            .descricaoSegmento(UPDATED_DESCRICAO_SEGMENTO)
            .idListaSerCpbs(UPDATED_ID_LISTA_SER_CPBS)
            .codigoListaSerCpbs(UPDATED_CODIGO_LISTA_SER_CPBS)
            .descricaoListaSerCpbs(UPDATED_DESCRICAO_LISTA_SER_CPBS);
        return atividadeCPBS;
    }

    @BeforeEach
    public void initTest() {
        atividadeCPBS = createEntity(em);
    }

    @Test
    @Transactional
    void createAtividadeCPBS() throws Exception {
        int databaseSizeBeforeCreate = atividadeCPBSRepository.findAll().size();
        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);
        restAtividadeCPBSMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeCreate + 1);
        AtividadeCPBS testAtividadeCPBS = atividadeCPBSList.get(atividadeCPBSList.size() - 1);
        assertThat(testAtividadeCPBS.getIdAtividadeCpbs()).isEqualTo(DEFAULT_ID_ATIVIDADE_CPBS);
        assertThat(testAtividadeCPBS.getCnae()).isEqualTo(DEFAULT_CNAE);
        assertThat(testAtividadeCPBS.getDescricaoCnae()).isEqualTo(DEFAULT_DESCRICAO_CNAE);
        assertThat(testAtividadeCPBS.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testAtividadeCPBS.getIdSegmentoIss()).isEqualTo(DEFAULT_ID_SEGMENTO_ISS);
        assertThat(testAtividadeCPBS.getDescricaoSegmento()).isEqualTo(DEFAULT_DESCRICAO_SEGMENTO);
        assertThat(testAtividadeCPBS.getIdListaSerCpbs()).isEqualTo(DEFAULT_ID_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getCodigoListaSerCpbs()).isEqualTo(DEFAULT_CODIGO_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getDescricaoListaSerCpbs()).isEqualTo(DEFAULT_DESCRICAO_LISTA_SER_CPBS);
    }

    @Test
    @Transactional
    void createAtividadeCPBSWithExistingId() throws Exception {
        // Create the AtividadeCPBS with an existing ID
        atividadeCPBS.setId(1L);
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        int databaseSizeBeforeCreate = atividadeCPBSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeCPBSMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAtividadeCPBS() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        // Get all the atividadeCPBSList
        restAtividadeCPBSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeCPBS.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAtividadeCpbs").value(hasItem(DEFAULT_ID_ATIVIDADE_CPBS)))
            .andExpect(jsonPath("$.[*].cnae").value(hasItem(DEFAULT_CNAE)))
            .andExpect(jsonPath("$.[*].descricaoCnae").value(hasItem(DEFAULT_DESCRICAO_CNAE)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].idSegmentoIss").value(hasItem(DEFAULT_ID_SEGMENTO_ISS)))
            .andExpect(jsonPath("$.[*].descricaoSegmento").value(hasItem(DEFAULT_DESCRICAO_SEGMENTO)))
            .andExpect(jsonPath("$.[*].idListaSerCpbs").value(hasItem(DEFAULT_ID_LISTA_SER_CPBS)))
            .andExpect(jsonPath("$.[*].codigoListaSerCpbs").value(hasItem(DEFAULT_CODIGO_LISTA_SER_CPBS)))
            .andExpect(jsonPath("$.[*].descricaoListaSerCpbs").value(hasItem(DEFAULT_DESCRICAO_LISTA_SER_CPBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtividadeCPBSWithEagerRelationshipsIsEnabled() throws Exception {
        when(atividadeCPBSServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtividadeCPBSMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(atividadeCPBSServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtividadeCPBSWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(atividadeCPBSServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtividadeCPBSMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(atividadeCPBSRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAtividadeCPBS() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        // Get the atividadeCPBS
        restAtividadeCPBSMockMvc
            .perform(get(ENTITY_API_URL_ID, atividadeCPBS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atividadeCPBS.getId().intValue()))
            .andExpect(jsonPath("$.idAtividadeCpbs").value(DEFAULT_ID_ATIVIDADE_CPBS))
            .andExpect(jsonPath("$.cnae").value(DEFAULT_CNAE))
            .andExpect(jsonPath("$.descricaoCnae").value(DEFAULT_DESCRICAO_CNAE))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.idSegmentoIss").value(DEFAULT_ID_SEGMENTO_ISS))
            .andExpect(jsonPath("$.descricaoSegmento").value(DEFAULT_DESCRICAO_SEGMENTO))
            .andExpect(jsonPath("$.idListaSerCpbs").value(DEFAULT_ID_LISTA_SER_CPBS))
            .andExpect(jsonPath("$.codigoListaSerCpbs").value(DEFAULT_CODIGO_LISTA_SER_CPBS))
            .andExpect(jsonPath("$.descricaoListaSerCpbs").value(DEFAULT_DESCRICAO_LISTA_SER_CPBS));
    }

    @Test
    @Transactional
    void getNonExistingAtividadeCPBS() throws Exception {
        // Get the atividadeCPBS
        restAtividadeCPBSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAtividadeCPBS() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();

        // Update the atividadeCPBS
        AtividadeCPBS updatedAtividadeCPBS = atividadeCPBSRepository.findById(atividadeCPBS.getId()).get();
        // Disconnect from session so that the updates on updatedAtividadeCPBS are not directly saved in db
        em.detach(updatedAtividadeCPBS);
        updatedAtividadeCPBS
            .idAtividadeCpbs(UPDATED_ID_ATIVIDADE_CPBS)
            .cnae(UPDATED_CNAE)
            .descricaoCnae(UPDATED_DESCRICAO_CNAE)
            .principal(UPDATED_PRINCIPAL)
            .idSegmentoIss(UPDATED_ID_SEGMENTO_ISS)
            .descricaoSegmento(UPDATED_DESCRICAO_SEGMENTO)
            .idListaSerCpbs(UPDATED_ID_LISTA_SER_CPBS)
            .codigoListaSerCpbs(UPDATED_CODIGO_LISTA_SER_CPBS)
            .descricaoListaSerCpbs(UPDATED_DESCRICAO_LISTA_SER_CPBS);
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(updatedAtividadeCPBS);

        restAtividadeCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atividadeCPBSDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
        AtividadeCPBS testAtividadeCPBS = atividadeCPBSList.get(atividadeCPBSList.size() - 1);
        assertThat(testAtividadeCPBS.getIdAtividadeCpbs()).isEqualTo(UPDATED_ID_ATIVIDADE_CPBS);
        assertThat(testAtividadeCPBS.getCnae()).isEqualTo(UPDATED_CNAE);
        assertThat(testAtividadeCPBS.getDescricaoCnae()).isEqualTo(UPDATED_DESCRICAO_CNAE);
        assertThat(testAtividadeCPBS.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testAtividadeCPBS.getIdSegmentoIss()).isEqualTo(UPDATED_ID_SEGMENTO_ISS);
        assertThat(testAtividadeCPBS.getDescricaoSegmento()).isEqualTo(UPDATED_DESCRICAO_SEGMENTO);
        assertThat(testAtividadeCPBS.getIdListaSerCpbs()).isEqualTo(UPDATED_ID_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getCodigoListaSerCpbs()).isEqualTo(UPDATED_CODIGO_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getDescricaoListaSerCpbs()).isEqualTo(UPDATED_DESCRICAO_LISTA_SER_CPBS);
    }

    @Test
    @Transactional
    void putNonExistingAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atividadeCPBSDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtividadeCPBSWithPatch() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();

        // Update the atividadeCPBS using partial update
        AtividadeCPBS partialUpdatedAtividadeCPBS = new AtividadeCPBS();
        partialUpdatedAtividadeCPBS.setId(atividadeCPBS.getId());

        partialUpdatedAtividadeCPBS
            .idAtividadeCpbs(UPDATED_ID_ATIVIDADE_CPBS)
            .descricaoSegmento(UPDATED_DESCRICAO_SEGMENTO)
            .idListaSerCpbs(UPDATED_ID_LISTA_SER_CPBS)
            .codigoListaSerCpbs(UPDATED_CODIGO_LISTA_SER_CPBS)
            .descricaoListaSerCpbs(UPDATED_DESCRICAO_LISTA_SER_CPBS);

        restAtividadeCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeCPBS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtividadeCPBS))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
        AtividadeCPBS testAtividadeCPBS = atividadeCPBSList.get(atividadeCPBSList.size() - 1);
        assertThat(testAtividadeCPBS.getIdAtividadeCpbs()).isEqualTo(UPDATED_ID_ATIVIDADE_CPBS);
        assertThat(testAtividadeCPBS.getCnae()).isEqualTo(DEFAULT_CNAE);
        assertThat(testAtividadeCPBS.getDescricaoCnae()).isEqualTo(DEFAULT_DESCRICAO_CNAE);
        assertThat(testAtividadeCPBS.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testAtividadeCPBS.getIdSegmentoIss()).isEqualTo(DEFAULT_ID_SEGMENTO_ISS);
        assertThat(testAtividadeCPBS.getDescricaoSegmento()).isEqualTo(UPDATED_DESCRICAO_SEGMENTO);
        assertThat(testAtividadeCPBS.getIdListaSerCpbs()).isEqualTo(UPDATED_ID_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getCodigoListaSerCpbs()).isEqualTo(UPDATED_CODIGO_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getDescricaoListaSerCpbs()).isEqualTo(UPDATED_DESCRICAO_LISTA_SER_CPBS);
    }

    @Test
    @Transactional
    void fullUpdateAtividadeCPBSWithPatch() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();

        // Update the atividadeCPBS using partial update
        AtividadeCPBS partialUpdatedAtividadeCPBS = new AtividadeCPBS();
        partialUpdatedAtividadeCPBS.setId(atividadeCPBS.getId());

        partialUpdatedAtividadeCPBS
            .idAtividadeCpbs(UPDATED_ID_ATIVIDADE_CPBS)
            .cnae(UPDATED_CNAE)
            .descricaoCnae(UPDATED_DESCRICAO_CNAE)
            .principal(UPDATED_PRINCIPAL)
            .idSegmentoIss(UPDATED_ID_SEGMENTO_ISS)
            .descricaoSegmento(UPDATED_DESCRICAO_SEGMENTO)
            .idListaSerCpbs(UPDATED_ID_LISTA_SER_CPBS)
            .codigoListaSerCpbs(UPDATED_CODIGO_LISTA_SER_CPBS)
            .descricaoListaSerCpbs(UPDATED_DESCRICAO_LISTA_SER_CPBS);

        restAtividadeCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeCPBS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtividadeCPBS))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
        AtividadeCPBS testAtividadeCPBS = atividadeCPBSList.get(atividadeCPBSList.size() - 1);
        assertThat(testAtividadeCPBS.getIdAtividadeCpbs()).isEqualTo(UPDATED_ID_ATIVIDADE_CPBS);
        assertThat(testAtividadeCPBS.getCnae()).isEqualTo(UPDATED_CNAE);
        assertThat(testAtividadeCPBS.getDescricaoCnae()).isEqualTo(UPDATED_DESCRICAO_CNAE);
        assertThat(testAtividadeCPBS.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testAtividadeCPBS.getIdSegmentoIss()).isEqualTo(UPDATED_ID_SEGMENTO_ISS);
        assertThat(testAtividadeCPBS.getDescricaoSegmento()).isEqualTo(UPDATED_DESCRICAO_SEGMENTO);
        assertThat(testAtividadeCPBS.getIdListaSerCpbs()).isEqualTo(UPDATED_ID_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getCodigoListaSerCpbs()).isEqualTo(UPDATED_CODIGO_LISTA_SER_CPBS);
        assertThat(testAtividadeCPBS.getDescricaoListaSerCpbs()).isEqualTo(UPDATED_DESCRICAO_LISTA_SER_CPBS);
    }

    @Test
    @Transactional
    void patchNonExistingAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atividadeCPBSDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtividadeCPBS() throws Exception {
        int databaseSizeBeforeUpdate = atividadeCPBSRepository.findAll().size();
        atividadeCPBS.setId(count.incrementAndGet());

        // Create the AtividadeCPBS
        AtividadeCPBSDTO atividadeCPBSDTO = atividadeCPBSMapper.toDto(atividadeCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeCPBSDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeCPBS in the database
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtividadeCPBS() throws Exception {
        // Initialize the database
        atividadeCPBSRepository.saveAndFlush(atividadeCPBS);

        int databaseSizeBeforeDelete = atividadeCPBSRepository.findAll().size();

        // Delete the atividadeCPBS
        restAtividadeCPBSMockMvc
            .perform(delete(ENTITY_API_URL_ID, atividadeCPBS.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AtividadeCPBS> atividadeCPBSList = atividadeCPBSRepository.findAll();
        assertThat(atividadeCPBSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
