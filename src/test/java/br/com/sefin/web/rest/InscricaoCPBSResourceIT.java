package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.InscricaoCPBS;
import br.com.sefin.domain.enumeration.CPBSSituacaoEnum;
import br.com.sefin.repository.InscricaoCPBSRepository;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
import br.com.sefin.service.mapper.InscricaoCPBSMapper;
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
 * Integration tests for the {@link InscricaoCPBSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InscricaoCPBSResourceIT {

    private static final String DEFAULT_INSCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DOCUMENTO = "BBBBBBBBBB";

    private static final CPBSSituacaoEnum DEFAULT_SITUACAO = CPBSSituacaoEnum.ATIVA;
    private static final CPBSSituacaoEnum UPDATED_SITUACAO = CPBSSituacaoEnum.SUSPENSA;

    private static final Boolean DEFAULT_OPTANTE_SIMPLES_NACIONAL = false;
    private static final Boolean UPDATED_OPTANTE_SIMPLES_NACIONAL = true;

    private static final String DEFAULT_CODIGO_NATUREZA_JURIDICA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_NATUREZA_JURIDICA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_NATUREZA_JURIDICA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_NATUREZA_JURIDICA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CEP = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inscricao-cpbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InscricaoCPBSRepository inscricaoCPBSRepository;

    @Autowired
    private InscricaoCPBSMapper inscricaoCPBSMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscricaoCPBSMockMvc;

    private InscricaoCPBS inscricaoCPBS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InscricaoCPBS createEntity(EntityManager em) {
        InscricaoCPBS inscricaoCPBS = new InscricaoCPBS()
            .inscricao(DEFAULT_INSCRICAO)
            .nome(DEFAULT_NOME)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .numDocumento(DEFAULT_NUM_DOCUMENTO)
            .situacao(DEFAULT_SITUACAO)
            .optanteSimplesNacional(DEFAULT_OPTANTE_SIMPLES_NACIONAL)
            .codigoNaturezaJuridica(DEFAULT_CODIGO_NATUREZA_JURIDICA)
            .descricaoNaturezaJuridica(DEFAULT_DESCRICAO_NATUREZA_JURIDICA)
            .tipoLogradouro(DEFAULT_TIPO_LOGRADOURO)
            .tituloLogradouro(DEFAULT_TITULO_LOGRADOURO)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .nomeCidade(DEFAULT_NOME_CIDADE)
            .nomeBairro(DEFAULT_NOME_BAIRRO)
            .numeroCep(DEFAULT_NUMERO_CEP);
        return inscricaoCPBS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InscricaoCPBS createUpdatedEntity(EntityManager em) {
        InscricaoCPBS inscricaoCPBS = new InscricaoCPBS()
            .inscricao(UPDATED_INSCRICAO)
            .nome(UPDATED_NOME)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .numDocumento(UPDATED_NUM_DOCUMENTO)
            .situacao(UPDATED_SITUACAO)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .codigoNaturezaJuridica(UPDATED_CODIGO_NATUREZA_JURIDICA)
            .descricaoNaturezaJuridica(UPDATED_DESCRICAO_NATUREZA_JURIDICA)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .tituloLogradouro(UPDATED_TITULO_LOGRADOURO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .nomeCidade(UPDATED_NOME_CIDADE)
            .nomeBairro(UPDATED_NOME_BAIRRO)
            .numeroCep(UPDATED_NUMERO_CEP);
        return inscricaoCPBS;
    }

    @BeforeEach
    public void initTest() {
        inscricaoCPBS = createEntity(em);
    }

    @Test
    @Transactional
    void createInscricaoCPBS() throws Exception {
        int databaseSizeBeforeCreate = inscricaoCPBSRepository.findAll().size();
        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);
        restInscricaoCPBSMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeCreate + 1);
        InscricaoCPBS testInscricaoCPBS = inscricaoCPBSList.get(inscricaoCPBSList.size() - 1);
        assertThat(testInscricaoCPBS.getInscricao()).isEqualTo(DEFAULT_INSCRICAO);
        assertThat(testInscricaoCPBS.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testInscricaoCPBS.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testInscricaoCPBS.getNumDocumento()).isEqualTo(DEFAULT_NUM_DOCUMENTO);
        assertThat(testInscricaoCPBS.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testInscricaoCPBS.getOptanteSimplesNacional()).isEqualTo(DEFAULT_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testInscricaoCPBS.getCodigoNaturezaJuridica()).isEqualTo(DEFAULT_CODIGO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getDescricaoNaturezaJuridica()).isEqualTo(DEFAULT_DESCRICAO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getTipoLogradouro()).isEqualTo(DEFAULT_TIPO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getTituloLogradouro()).isEqualTo(DEFAULT_TITULO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testInscricaoCPBS.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInscricaoCPBS.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testInscricaoCPBS.getNomeCidade()).isEqualTo(DEFAULT_NOME_CIDADE);
        assertThat(testInscricaoCPBS.getNomeBairro()).isEqualTo(DEFAULT_NOME_BAIRRO);
        assertThat(testInscricaoCPBS.getNumeroCep()).isEqualTo(DEFAULT_NUMERO_CEP);
    }

    @Test
    @Transactional
    void createInscricaoCPBSWithExistingId() throws Exception {
        // Create the InscricaoCPBS with an existing ID
        inscricaoCPBS.setId(1L);
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        int databaseSizeBeforeCreate = inscricaoCPBSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscricaoCPBSMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInscricaoCPBS() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        // Get all the inscricaoCPBSList
        restInscricaoCPBSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscricaoCPBS.getId().intValue())))
            .andExpect(jsonPath("$.[*].inscricao").value(hasItem(DEFAULT_INSCRICAO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].numDocumento").value(hasItem(DEFAULT_NUM_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].optanteSimplesNacional").value(hasItem(DEFAULT_OPTANTE_SIMPLES_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoNaturezaJuridica").value(hasItem(DEFAULT_CODIGO_NATUREZA_JURIDICA)))
            .andExpect(jsonPath("$.[*].descricaoNaturezaJuridica").value(hasItem(DEFAULT_DESCRICAO_NATUREZA_JURIDICA)))
            .andExpect(jsonPath("$.[*].tipoLogradouro").value(hasItem(DEFAULT_TIPO_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].tituloLogradouro").value(hasItem(DEFAULT_TITULO_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].nomeCidade").value(hasItem(DEFAULT_NOME_CIDADE)))
            .andExpect(jsonPath("$.[*].nomeBairro").value(hasItem(DEFAULT_NOME_BAIRRO)))
            .andExpect(jsonPath("$.[*].numeroCep").value(hasItem(DEFAULT_NUMERO_CEP)));
    }

    @Test
    @Transactional
    void getInscricaoCPBS() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        // Get the inscricaoCPBS
        restInscricaoCPBSMockMvc
            .perform(get(ENTITY_API_URL_ID, inscricaoCPBS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscricaoCPBS.getId().intValue()))
            .andExpect(jsonPath("$.inscricao").value(DEFAULT_INSCRICAO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA))
            .andExpect(jsonPath("$.numDocumento").value(DEFAULT_NUM_DOCUMENTO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.optanteSimplesNacional").value(DEFAULT_OPTANTE_SIMPLES_NACIONAL.booleanValue()))
            .andExpect(jsonPath("$.codigoNaturezaJuridica").value(DEFAULT_CODIGO_NATUREZA_JURIDICA))
            .andExpect(jsonPath("$.descricaoNaturezaJuridica").value(DEFAULT_DESCRICAO_NATUREZA_JURIDICA))
            .andExpect(jsonPath("$.tipoLogradouro").value(DEFAULT_TIPO_LOGRADOURO))
            .andExpect(jsonPath("$.tituloLogradouro").value(DEFAULT_TITULO_LOGRADOURO))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.nomeCidade").value(DEFAULT_NOME_CIDADE))
            .andExpect(jsonPath("$.nomeBairro").value(DEFAULT_NOME_BAIRRO))
            .andExpect(jsonPath("$.numeroCep").value(DEFAULT_NUMERO_CEP));
    }

    @Test
    @Transactional
    void getNonExistingInscricaoCPBS() throws Exception {
        // Get the inscricaoCPBS
        restInscricaoCPBSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInscricaoCPBS() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();

        // Update the inscricaoCPBS
        InscricaoCPBS updatedInscricaoCPBS = inscricaoCPBSRepository.findById(inscricaoCPBS.getId()).get();
        // Disconnect from session so that the updates on updatedInscricaoCPBS are not directly saved in db
        em.detach(updatedInscricaoCPBS);
        updatedInscricaoCPBS
            .inscricao(UPDATED_INSCRICAO)
            .nome(UPDATED_NOME)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .numDocumento(UPDATED_NUM_DOCUMENTO)
            .situacao(UPDATED_SITUACAO)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .codigoNaturezaJuridica(UPDATED_CODIGO_NATUREZA_JURIDICA)
            .descricaoNaturezaJuridica(UPDATED_DESCRICAO_NATUREZA_JURIDICA)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .tituloLogradouro(UPDATED_TITULO_LOGRADOURO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .nomeCidade(UPDATED_NOME_CIDADE)
            .nomeBairro(UPDATED_NOME_BAIRRO)
            .numeroCep(UPDATED_NUMERO_CEP);
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(updatedInscricaoCPBS);

        restInscricaoCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscricaoCPBSDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isOk());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
        InscricaoCPBS testInscricaoCPBS = inscricaoCPBSList.get(inscricaoCPBSList.size() - 1);
        assertThat(testInscricaoCPBS.getInscricao()).isEqualTo(UPDATED_INSCRICAO);
        assertThat(testInscricaoCPBS.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInscricaoCPBS.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testInscricaoCPBS.getNumDocumento()).isEqualTo(UPDATED_NUM_DOCUMENTO);
        assertThat(testInscricaoCPBS.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testInscricaoCPBS.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testInscricaoCPBS.getCodigoNaturezaJuridica()).isEqualTo(UPDATED_CODIGO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getDescricaoNaturezaJuridica()).isEqualTo(UPDATED_DESCRICAO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getTituloLogradouro()).isEqualTo(UPDATED_TITULO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testInscricaoCPBS.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInscricaoCPBS.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testInscricaoCPBS.getNomeCidade()).isEqualTo(UPDATED_NOME_CIDADE);
        assertThat(testInscricaoCPBS.getNomeBairro()).isEqualTo(UPDATED_NOME_BAIRRO);
        assertThat(testInscricaoCPBS.getNumeroCep()).isEqualTo(UPDATED_NUMERO_CEP);
    }

    @Test
    @Transactional
    void putNonExistingInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscricaoCPBSDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInscricaoCPBSWithPatch() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();

        // Update the inscricaoCPBS using partial update
        InscricaoCPBS partialUpdatedInscricaoCPBS = new InscricaoCPBS();
        partialUpdatedInscricaoCPBS.setId(inscricaoCPBS.getId());

        partialUpdatedInscricaoCPBS
            .inscricao(UPDATED_INSCRICAO)
            .nome(UPDATED_NOME)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .situacao(UPDATED_SITUACAO)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .logradouro(UPDATED_LOGRADOURO);

        restInscricaoCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscricaoCPBS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscricaoCPBS))
            )
            .andExpect(status().isOk());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
        InscricaoCPBS testInscricaoCPBS = inscricaoCPBSList.get(inscricaoCPBSList.size() - 1);
        assertThat(testInscricaoCPBS.getInscricao()).isEqualTo(UPDATED_INSCRICAO);
        assertThat(testInscricaoCPBS.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInscricaoCPBS.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testInscricaoCPBS.getNumDocumento()).isEqualTo(DEFAULT_NUM_DOCUMENTO);
        assertThat(testInscricaoCPBS.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testInscricaoCPBS.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testInscricaoCPBS.getCodigoNaturezaJuridica()).isEqualTo(DEFAULT_CODIGO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getDescricaoNaturezaJuridica()).isEqualTo(DEFAULT_DESCRICAO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getTituloLogradouro()).isEqualTo(DEFAULT_TITULO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testInscricaoCPBS.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInscricaoCPBS.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testInscricaoCPBS.getNomeCidade()).isEqualTo(DEFAULT_NOME_CIDADE);
        assertThat(testInscricaoCPBS.getNomeBairro()).isEqualTo(DEFAULT_NOME_BAIRRO);
        assertThat(testInscricaoCPBS.getNumeroCep()).isEqualTo(DEFAULT_NUMERO_CEP);
    }

    @Test
    @Transactional
    void fullUpdateInscricaoCPBSWithPatch() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();

        // Update the inscricaoCPBS using partial update
        InscricaoCPBS partialUpdatedInscricaoCPBS = new InscricaoCPBS();
        partialUpdatedInscricaoCPBS.setId(inscricaoCPBS.getId());

        partialUpdatedInscricaoCPBS
            .inscricao(UPDATED_INSCRICAO)
            .nome(UPDATED_NOME)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .numDocumento(UPDATED_NUM_DOCUMENTO)
            .situacao(UPDATED_SITUACAO)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .codigoNaturezaJuridica(UPDATED_CODIGO_NATUREZA_JURIDICA)
            .descricaoNaturezaJuridica(UPDATED_DESCRICAO_NATUREZA_JURIDICA)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .tituloLogradouro(UPDATED_TITULO_LOGRADOURO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .nomeCidade(UPDATED_NOME_CIDADE)
            .nomeBairro(UPDATED_NOME_BAIRRO)
            .numeroCep(UPDATED_NUMERO_CEP);

        restInscricaoCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscricaoCPBS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscricaoCPBS))
            )
            .andExpect(status().isOk());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
        InscricaoCPBS testInscricaoCPBS = inscricaoCPBSList.get(inscricaoCPBSList.size() - 1);
        assertThat(testInscricaoCPBS.getInscricao()).isEqualTo(UPDATED_INSCRICAO);
        assertThat(testInscricaoCPBS.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInscricaoCPBS.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testInscricaoCPBS.getNumDocumento()).isEqualTo(UPDATED_NUM_DOCUMENTO);
        assertThat(testInscricaoCPBS.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testInscricaoCPBS.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testInscricaoCPBS.getCodigoNaturezaJuridica()).isEqualTo(UPDATED_CODIGO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getDescricaoNaturezaJuridica()).isEqualTo(UPDATED_DESCRICAO_NATUREZA_JURIDICA);
        assertThat(testInscricaoCPBS.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getTituloLogradouro()).isEqualTo(UPDATED_TITULO_LOGRADOURO);
        assertThat(testInscricaoCPBS.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testInscricaoCPBS.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInscricaoCPBS.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testInscricaoCPBS.getNomeCidade()).isEqualTo(UPDATED_NOME_CIDADE);
        assertThat(testInscricaoCPBS.getNomeBairro()).isEqualTo(UPDATED_NOME_BAIRRO);
        assertThat(testInscricaoCPBS.getNumeroCep()).isEqualTo(UPDATED_NUMERO_CEP);
    }

    @Test
    @Transactional
    void patchNonExistingInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscricaoCPBSDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInscricaoCPBS() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoCPBSRepository.findAll().size();
        inscricaoCPBS.setId(count.incrementAndGet());

        // Create the InscricaoCPBS
        InscricaoCPBSDTO inscricaoCPBSDTO = inscricaoCPBSMapper.toDto(inscricaoCPBS);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoCPBSMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscricaoCPBSDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InscricaoCPBS in the database
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInscricaoCPBS() throws Exception {
        // Initialize the database
        inscricaoCPBSRepository.saveAndFlush(inscricaoCPBS);

        int databaseSizeBeforeDelete = inscricaoCPBSRepository.findAll().size();

        // Delete the inscricaoCPBS
        restInscricaoCPBSMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscricaoCPBS.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InscricaoCPBS> inscricaoCPBSList = inscricaoCPBSRepository.findAll();
        assertThat(inscricaoCPBSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
