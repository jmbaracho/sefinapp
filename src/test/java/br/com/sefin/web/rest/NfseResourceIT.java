package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.Nfse;
import br.com.sefin.repository.NfseRepository;
import br.com.sefin.service.NfseService;
import br.com.sefin.service.dto.NfseDTO;
import br.com.sefin.service.mapper.NfseMapper;
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
 * Integration tests for the {@link NfseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NfseResourceIT {

    private static final String DEFAULT_ID_NFSE = "AAAAAAAAAA";
    private static final String UPDATED_ID_NFSE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCIA = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_EMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_EMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_OPTANTE_SIMPLES_NACIONAL = "AAAAAAAAAA";
    private static final String UPDATED_OPTANTE_SIMPLES_NACIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_OUTRAS_INFORMACOES = "AAAAAAAAAA";
    private static final String UPDATED_OUTRAS_INFORMACOES = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NFSE_SUBSTITUIDA = "AAAAAAAAAA";
    private static final String UPDATED_ID_NFSE_SUBSTITUIDA = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_NFSE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NFSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NATUREZA_OPERACAO = 1;
    private static final Integer UPDATED_NATUREZA_OPERACAO = 2;

    private static final String DEFAULT_RPS = "AAAAAAAAAA";
    private static final String UPDATED_RPS = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_PRESTADOR = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_PRESTADOR = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DOC_DIGITADO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DOC_DIGITADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS_ACEITE = 1;
    private static final Integer UPDATED_STATUS_ACEITE = 2;

    private static final String DEFAULT_EXCLUSAO_LOGICA = "AAAAAAAAAA";
    private static final String UPDATED_EXCLUSAO_LOGICA = "BBBBBBBBBB";

    private static final String DEFAULT_AJUSTADA = "AAAAAAAAAA";
    private static final String UPDATED_AJUSTADA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_AJUSTE = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_AJUSTE = "BBBBBBBBBB";

    private static final String DEFAULT_ALIQUOTA_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_ALIQUOTA_SERVICO = "BBBBBBBBBB";

    private static final String DEFAULT_ART = "AAAAAAAAAA";
    private static final String UPDATED_ART = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_CALCULO = "AAAAAAAAAA";
    private static final String UPDATED_BASE_CALCULO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_OBRA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_OBRA = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_SERVICO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_PIS = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_PIS = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_LIQUIDO_NFSE = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_LIQUIDO_NFSE = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_ISS_RETIDO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_ISS_RETIDO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_ISS_OUTRO_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_ISS_OUTRO_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_ISS = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_ISS = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_IR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_IR = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_INSS = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_INSS = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_DESCONTO_CONDICIONADO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_DESCONTO_CONDICIONADO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_DESCONTO_INCONDICIONADO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_DESCONTO_INCONDICIONADO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_DEDUCOES = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_DEDUCOES = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_CSLL = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_CSLL = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_CREDITO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_CREDITO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_COFINS = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_COFINS = "BBBBBBBBBB";

    private static final String DEFAULT_OUTRAS_RETENCOES = "AAAAAAAAAA";
    private static final String UPDATED_OUTRAS_RETENCOES = "BBBBBBBBBB";

    private static final String DEFAULT_ISS_RETIDO = "AAAAAAAAAA";
    private static final String UPDATED_ISS_RETIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIMINACAO = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIMINACAO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO_PRESTACAO_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO_PRESTACAO_SERVICO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS_PRESTACAO_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_PAIS_PRESTACAO_SERVICO = "BBBBBBBBBB";

    private static final Integer DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO = 1;
    private static final Integer UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO = 2;

    private static final String DEFAULT_CODIGO_TRIBUTACAO_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_CNPJ_TOMADOR = "AAAAAAAAAA";
    private static final String UPDATED_CPF_CNPJ_TOMADOR = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_CNPJ_PRESTADOR = "AAAAAAAAAA";
    private static final String UPDATED_CPF_CNPJ_PRESTADOR = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_MUNICIPAL_TOMADOR = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_MUNICIPAL_TOMADOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDICADOR_CPF_CNPJ_TOMADOR = 1;
    private static final Integer UPDATED_INDICADOR_CPF_CNPJ_TOMADOR = 2;

    private static final Integer DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR = 1;
    private static final Integer UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR = 2;

    private static final String DEFAULT_PAIS_TOMADOR = "AAAAAAAAAA";
    private static final String UPDATED_PAIS_TOMADOR = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS_PRESTADOR = "AAAAAAAAAA";
    private static final String UPDATED_PAIS_PRESTADOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIPO_TOMADOR = 1;
    private static final Integer UPDATED_TIPO_TOMADOR = 2;

    private static final String DEFAULT_COD_CIDADE_TOMADOR_IBGE = "AAAAAAAAAA";
    private static final String UPDATED_COD_CIDADE_TOMADOR_IBGE = "BBBBBBBBBB";

    private static final String DEFAULT_COD_CIDADE_PRESTADOR_IBGE = "AAAAAAAAAA";
    private static final String UPDATED_COD_CIDADE_PRESTADOR_IBGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIPO_PRESTADOR = 1;
    private static final Integer UPDATED_TIPO_PRESTADOR = 2;

    private static final String DEFAULT_NUMERO_EMPENHO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_EMPENHO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_TRIBUTACAO_PREST_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP_PRESTADOR = "AAAAAAAAAA";
    private static final String UPDATED_CEP_PRESTADOR = "BBBBBBBBBB";

    private static final String DEFAULT_CEP_TOMADOR = "AAAAAAAAAA";
    private static final String UPDATED_CEP_TOMADOR = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_BLOQUEIO_NFSE = 1;
    private static final Integer UPDATED_ID_BLOQUEIO_NFSE = 2;

    private static final String DEFAULT_ID_PGDASD_2018_ATIVIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ID_PGDASD_2018_ATIVIDADE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nfses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NfseRepository nfseRepository;

    @Mock
    private NfseRepository nfseRepositoryMock;

    @Autowired
    private NfseMapper nfseMapper;

    @Mock
    private NfseService nfseServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNfseMockMvc;

    private Nfse nfse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nfse createEntity(EntityManager em) {
        Nfse nfse = new Nfse()
            .idNfse(DEFAULT_ID_NFSE)
            .competencia(DEFAULT_COMPETENCIA)
            .dataEmissao(DEFAULT_DATA_EMISSAO)
            .ip(DEFAULT_IP)
            .optanteSimplesNacional(DEFAULT_OPTANTE_SIMPLES_NACIONAL)
            .outrasInformacoes(DEFAULT_OUTRAS_INFORMACOES)
            .idNfseSubstituida(DEFAULT_ID_NFSE_SUBSTITUIDA)
            .usuario(DEFAULT_USUARIO)
            .statusNfse(DEFAULT_STATUS_NFSE)
            .naturezaOperacao(DEFAULT_NATUREZA_OPERACAO)
            .rps(DEFAULT_RPS)
            .inscricaoPrestador(DEFAULT_INSCRICAO_PRESTADOR)
            .tipoDocDigitado(DEFAULT_TIPO_DOC_DIGITADO)
            .statusAceite(DEFAULT_STATUS_ACEITE)
            .exclusaoLogica(DEFAULT_EXCLUSAO_LOGICA)
            .ajustada(DEFAULT_AJUSTADA)
            .observacaoAjuste(DEFAULT_OBSERVACAO_AJUSTE)
            .aliquotaServico(DEFAULT_ALIQUOTA_SERVICO)
            .art(DEFAULT_ART)
            .baseCalculo(DEFAULT_BASE_CALCULO)
            .codigoObra(DEFAULT_CODIGO_OBRA)
            .valorServico(DEFAULT_VALOR_SERVICO)
            .valorPis(DEFAULT_VALOR_PIS)
            .valorLiquidoNfse(DEFAULT_VALOR_LIQUIDO_NFSE)
            .valorIssRetido(DEFAULT_VALOR_ISS_RETIDO)
            .valorIssOutroMunicipio(DEFAULT_VALOR_ISS_OUTRO_MUNICIPIO)
            .valorIss(DEFAULT_VALOR_ISS)
            .valorIr(DEFAULT_VALOR_IR)
            .valorInss(DEFAULT_VALOR_INSS)
            .valorDescontoCondicionado(DEFAULT_VALOR_DESCONTO_CONDICIONADO)
            .valorDescontoIncondicionado(DEFAULT_VALOR_DESCONTO_INCONDICIONADO)
            .valorDeducoes(DEFAULT_VALOR_DEDUCOES)
            .valorCsll(DEFAULT_VALOR_CSLL)
            .valorCredito(DEFAULT_VALOR_CREDITO)
            .valorCofins(DEFAULT_VALOR_COFINS)
            .outrasRetencoes(DEFAULT_OUTRAS_RETENCOES)
            .issRetido(DEFAULT_ISS_RETIDO)
            .discriminacao(DEFAULT_DISCRIMINACAO)
            .municipioPrestacaoServico(DEFAULT_MUNICIPIO_PRESTACAO_SERVICO)
            .paisPrestacaoServico(DEFAULT_PAIS_PRESTACAO_SERVICO)
            .codRegimeEspecialTributacao(DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO)
            .codigoTributacaoMunicipio(DEFAULT_CODIGO_TRIBUTACAO_MUNICIPIO)
            .itemListaServicoMunicipio(DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO)
            .cpfCnpjTomador(DEFAULT_CPF_CNPJ_TOMADOR)
            .cpfCnpjPrestador(DEFAULT_CPF_CNPJ_PRESTADOR)
            .inscricaoMunicipalTomador(DEFAULT_INSCRICAO_MUNICIPAL_TOMADOR)
            .indicadorCpfCnpjTomador(DEFAULT_INDICADOR_CPF_CNPJ_TOMADOR)
            .indicadorCpfCnpjPrestador(DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR)
            .paisTomador(DEFAULT_PAIS_TOMADOR)
            .paisPrestador(DEFAULT_PAIS_PRESTADOR)
            .tipoTomador(DEFAULT_TIPO_TOMADOR)
            .codCidadeTomadorIBGE(DEFAULT_COD_CIDADE_TOMADOR_IBGE)
            .codCidadePrestadorIBGE(DEFAULT_COD_CIDADE_PRESTADOR_IBGE)
            .tipoPrestador(DEFAULT_TIPO_PRESTADOR)
            .numeroEmpenho(DEFAULT_NUMERO_EMPENHO)
            .tipoTributacaoPrestExterno(DEFAULT_TIPO_TRIBUTACAO_PREST_EXTERNO)
            .cepPrestador(DEFAULT_CEP_PRESTADOR)
            .cepTomador(DEFAULT_CEP_TOMADOR)
            .idTipoAnexoSimplesNacional(DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL)
            .idBloqueioNfse(DEFAULT_ID_BLOQUEIO_NFSE)
            .idPgdasd2018Atividade(DEFAULT_ID_PGDASD_2018_ATIVIDADE);
        return nfse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nfse createUpdatedEntity(EntityManager em) {
        Nfse nfse = new Nfse()
            .idNfse(UPDATED_ID_NFSE)
            .competencia(UPDATED_COMPETENCIA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .ip(UPDATED_IP)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .outrasInformacoes(UPDATED_OUTRAS_INFORMACOES)
            .idNfseSubstituida(UPDATED_ID_NFSE_SUBSTITUIDA)
            .usuario(UPDATED_USUARIO)
            .statusNfse(UPDATED_STATUS_NFSE)
            .naturezaOperacao(UPDATED_NATUREZA_OPERACAO)
            .rps(UPDATED_RPS)
            .inscricaoPrestador(UPDATED_INSCRICAO_PRESTADOR)
            .tipoDocDigitado(UPDATED_TIPO_DOC_DIGITADO)
            .statusAceite(UPDATED_STATUS_ACEITE)
            .exclusaoLogica(UPDATED_EXCLUSAO_LOGICA)
            .ajustada(UPDATED_AJUSTADA)
            .observacaoAjuste(UPDATED_OBSERVACAO_AJUSTE)
            .aliquotaServico(UPDATED_ALIQUOTA_SERVICO)
            .art(UPDATED_ART)
            .baseCalculo(UPDATED_BASE_CALCULO)
            .codigoObra(UPDATED_CODIGO_OBRA)
            .valorServico(UPDATED_VALOR_SERVICO)
            .valorPis(UPDATED_VALOR_PIS)
            .valorLiquidoNfse(UPDATED_VALOR_LIQUIDO_NFSE)
            .valorIssRetido(UPDATED_VALOR_ISS_RETIDO)
            .valorIssOutroMunicipio(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO)
            .valorIss(UPDATED_VALOR_ISS)
            .valorIr(UPDATED_VALOR_IR)
            .valorInss(UPDATED_VALOR_INSS)
            .valorDescontoCondicionado(UPDATED_VALOR_DESCONTO_CONDICIONADO)
            .valorDescontoIncondicionado(UPDATED_VALOR_DESCONTO_INCONDICIONADO)
            .valorDeducoes(UPDATED_VALOR_DEDUCOES)
            .valorCsll(UPDATED_VALOR_CSLL)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorCofins(UPDATED_VALOR_COFINS)
            .outrasRetencoes(UPDATED_OUTRAS_RETENCOES)
            .issRetido(UPDATED_ISS_RETIDO)
            .discriminacao(UPDATED_DISCRIMINACAO)
            .municipioPrestacaoServico(UPDATED_MUNICIPIO_PRESTACAO_SERVICO)
            .paisPrestacaoServico(UPDATED_PAIS_PRESTACAO_SERVICO)
            .codRegimeEspecialTributacao(UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO)
            .codigoTributacaoMunicipio(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO)
            .itemListaServicoMunicipio(UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO)
            .cpfCnpjTomador(UPDATED_CPF_CNPJ_TOMADOR)
            .cpfCnpjPrestador(UPDATED_CPF_CNPJ_PRESTADOR)
            .inscricaoMunicipalTomador(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR)
            .indicadorCpfCnpjTomador(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR)
            .indicadorCpfCnpjPrestador(UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR)
            .paisTomador(UPDATED_PAIS_TOMADOR)
            .paisPrestador(UPDATED_PAIS_PRESTADOR)
            .tipoTomador(UPDATED_TIPO_TOMADOR)
            .codCidadeTomadorIBGE(UPDATED_COD_CIDADE_TOMADOR_IBGE)
            .codCidadePrestadorIBGE(UPDATED_COD_CIDADE_PRESTADOR_IBGE)
            .tipoPrestador(UPDATED_TIPO_PRESTADOR)
            .numeroEmpenho(UPDATED_NUMERO_EMPENHO)
            .tipoTributacaoPrestExterno(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO)
            .cepPrestador(UPDATED_CEP_PRESTADOR)
            .cepTomador(UPDATED_CEP_TOMADOR)
            .idTipoAnexoSimplesNacional(UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL)
            .idBloqueioNfse(UPDATED_ID_BLOQUEIO_NFSE)
            .idPgdasd2018Atividade(UPDATED_ID_PGDASD_2018_ATIVIDADE);
        return nfse;
    }

    @BeforeEach
    public void initTest() {
        nfse = createEntity(em);
    }

    @Test
    @Transactional
    void createNfse() throws Exception {
        int databaseSizeBeforeCreate = nfseRepository.findAll().size();
        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);
        restNfseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nfseDTO)))
            .andExpect(status().isCreated());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeCreate + 1);
        Nfse testNfse = nfseList.get(nfseList.size() - 1);
        assertThat(testNfse.getIdNfse()).isEqualTo(DEFAULT_ID_NFSE);
        assertThat(testNfse.getCompetencia()).isEqualTo(DEFAULT_COMPETENCIA);
        assertThat(testNfse.getDataEmissao()).isEqualTo(DEFAULT_DATA_EMISSAO);
        assertThat(testNfse.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testNfse.getOptanteSimplesNacional()).isEqualTo(DEFAULT_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testNfse.getOutrasInformacoes()).isEqualTo(DEFAULT_OUTRAS_INFORMACOES);
        assertThat(testNfse.getIdNfseSubstituida()).isEqualTo(DEFAULT_ID_NFSE_SUBSTITUIDA);
        assertThat(testNfse.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testNfse.getStatusNfse()).isEqualTo(DEFAULT_STATUS_NFSE);
        assertThat(testNfse.getNaturezaOperacao()).isEqualTo(DEFAULT_NATUREZA_OPERACAO);
        assertThat(testNfse.getRps()).isEqualTo(DEFAULT_RPS);
        assertThat(testNfse.getInscricaoPrestador()).isEqualTo(DEFAULT_INSCRICAO_PRESTADOR);
        assertThat(testNfse.getTipoDocDigitado()).isEqualTo(DEFAULT_TIPO_DOC_DIGITADO);
        assertThat(testNfse.getStatusAceite()).isEqualTo(DEFAULT_STATUS_ACEITE);
        assertThat(testNfse.getExclusaoLogica()).isEqualTo(DEFAULT_EXCLUSAO_LOGICA);
        assertThat(testNfse.getAjustada()).isEqualTo(DEFAULT_AJUSTADA);
        assertThat(testNfse.getObservacaoAjuste()).isEqualTo(DEFAULT_OBSERVACAO_AJUSTE);
        assertThat(testNfse.getAliquotaServico()).isEqualTo(DEFAULT_ALIQUOTA_SERVICO);
        assertThat(testNfse.getArt()).isEqualTo(DEFAULT_ART);
        assertThat(testNfse.getBaseCalculo()).isEqualTo(DEFAULT_BASE_CALCULO);
        assertThat(testNfse.getCodigoObra()).isEqualTo(DEFAULT_CODIGO_OBRA);
        assertThat(testNfse.getValorServico()).isEqualTo(DEFAULT_VALOR_SERVICO);
        assertThat(testNfse.getValorPis()).isEqualTo(DEFAULT_VALOR_PIS);
        assertThat(testNfse.getValorLiquidoNfse()).isEqualTo(DEFAULT_VALOR_LIQUIDO_NFSE);
        assertThat(testNfse.getValorIssRetido()).isEqualTo(DEFAULT_VALOR_ISS_RETIDO);
        assertThat(testNfse.getValorIssOutroMunicipio()).isEqualTo(DEFAULT_VALOR_ISS_OUTRO_MUNICIPIO);
        assertThat(testNfse.getValorIss()).isEqualTo(DEFAULT_VALOR_ISS);
        assertThat(testNfse.getValorIr()).isEqualTo(DEFAULT_VALOR_IR);
        assertThat(testNfse.getValorInss()).isEqualTo(DEFAULT_VALOR_INSS);
        assertThat(testNfse.getValorDescontoCondicionado()).isEqualTo(DEFAULT_VALOR_DESCONTO_CONDICIONADO);
        assertThat(testNfse.getValorDescontoIncondicionado()).isEqualTo(DEFAULT_VALOR_DESCONTO_INCONDICIONADO);
        assertThat(testNfse.getValorDeducoes()).isEqualTo(DEFAULT_VALOR_DEDUCOES);
        assertThat(testNfse.getValorCsll()).isEqualTo(DEFAULT_VALOR_CSLL);
        assertThat(testNfse.getValorCredito()).isEqualTo(DEFAULT_VALOR_CREDITO);
        assertThat(testNfse.getValorCofins()).isEqualTo(DEFAULT_VALOR_COFINS);
        assertThat(testNfse.getOutrasRetencoes()).isEqualTo(DEFAULT_OUTRAS_RETENCOES);
        assertThat(testNfse.getIssRetido()).isEqualTo(DEFAULT_ISS_RETIDO);
        assertThat(testNfse.getDiscriminacao()).isEqualTo(DEFAULT_DISCRIMINACAO);
        assertThat(testNfse.getMunicipioPrestacaoServico()).isEqualTo(DEFAULT_MUNICIPIO_PRESTACAO_SERVICO);
        assertThat(testNfse.getPaisPrestacaoServico()).isEqualTo(DEFAULT_PAIS_PRESTACAO_SERVICO);
        assertThat(testNfse.getCodRegimeEspecialTributacao()).isEqualTo(DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO);
        assertThat(testNfse.getCodigoTributacaoMunicipio()).isEqualTo(DEFAULT_CODIGO_TRIBUTACAO_MUNICIPIO);
        assertThat(testNfse.getItemListaServicoMunicipio()).isEqualTo(DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO);
        assertThat(testNfse.getCpfCnpjTomador()).isEqualTo(DEFAULT_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getCpfCnpjPrestador()).isEqualTo(DEFAULT_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getInscricaoMunicipalTomador()).isEqualTo(DEFAULT_INSCRICAO_MUNICIPAL_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjTomador()).isEqualTo(DEFAULT_INDICADOR_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjPrestador()).isEqualTo(DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getPaisTomador()).isEqualTo(DEFAULT_PAIS_TOMADOR);
        assertThat(testNfse.getPaisPrestador()).isEqualTo(DEFAULT_PAIS_PRESTADOR);
        assertThat(testNfse.getTipoTomador()).isEqualTo(DEFAULT_TIPO_TOMADOR);
        assertThat(testNfse.getCodCidadeTomadorIBGE()).isEqualTo(DEFAULT_COD_CIDADE_TOMADOR_IBGE);
        assertThat(testNfse.getCodCidadePrestadorIBGE()).isEqualTo(DEFAULT_COD_CIDADE_PRESTADOR_IBGE);
        assertThat(testNfse.getTipoPrestador()).isEqualTo(DEFAULT_TIPO_PRESTADOR);
        assertThat(testNfse.getNumeroEmpenho()).isEqualTo(DEFAULT_NUMERO_EMPENHO);
        assertThat(testNfse.getTipoTributacaoPrestExterno()).isEqualTo(DEFAULT_TIPO_TRIBUTACAO_PREST_EXTERNO);
        assertThat(testNfse.getCepPrestador()).isEqualTo(DEFAULT_CEP_PRESTADOR);
        assertThat(testNfse.getCepTomador()).isEqualTo(DEFAULT_CEP_TOMADOR);
        assertThat(testNfse.getIdTipoAnexoSimplesNacional()).isEqualTo(DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL);
        assertThat(testNfse.getIdBloqueioNfse()).isEqualTo(DEFAULT_ID_BLOQUEIO_NFSE);
        assertThat(testNfse.getIdPgdasd2018Atividade()).isEqualTo(DEFAULT_ID_PGDASD_2018_ATIVIDADE);
    }

    @Test
    @Transactional
    void createNfseWithExistingId() throws Exception {
        // Create the Nfse with an existing ID
        nfse.setId(1L);
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        int databaseSizeBeforeCreate = nfseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNfseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nfseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNfses() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        // Get all the nfseList
        restNfseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nfse.getId().intValue())))
            .andExpect(jsonPath("$.[*].idNfse").value(hasItem(DEFAULT_ID_NFSE)))
            .andExpect(jsonPath("$.[*].competencia").value(hasItem(DEFAULT_COMPETENCIA)))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].optanteSimplesNacional").value(hasItem(DEFAULT_OPTANTE_SIMPLES_NACIONAL)))
            .andExpect(jsonPath("$.[*].outrasInformacoes").value(hasItem(DEFAULT_OUTRAS_INFORMACOES)))
            .andExpect(jsonPath("$.[*].idNfseSubstituida").value(hasItem(DEFAULT_ID_NFSE_SUBSTITUIDA)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].statusNfse").value(hasItem(DEFAULT_STATUS_NFSE)))
            .andExpect(jsonPath("$.[*].naturezaOperacao").value(hasItem(DEFAULT_NATUREZA_OPERACAO)))
            .andExpect(jsonPath("$.[*].rps").value(hasItem(DEFAULT_RPS)))
            .andExpect(jsonPath("$.[*].inscricaoPrestador").value(hasItem(DEFAULT_INSCRICAO_PRESTADOR)))
            .andExpect(jsonPath("$.[*].tipoDocDigitado").value(hasItem(DEFAULT_TIPO_DOC_DIGITADO)))
            .andExpect(jsonPath("$.[*].statusAceite").value(hasItem(DEFAULT_STATUS_ACEITE)))
            .andExpect(jsonPath("$.[*].exclusaoLogica").value(hasItem(DEFAULT_EXCLUSAO_LOGICA)))
            .andExpect(jsonPath("$.[*].ajustada").value(hasItem(DEFAULT_AJUSTADA)))
            .andExpect(jsonPath("$.[*].observacaoAjuste").value(hasItem(DEFAULT_OBSERVACAO_AJUSTE)))
            .andExpect(jsonPath("$.[*].aliquotaServico").value(hasItem(DEFAULT_ALIQUOTA_SERVICO)))
            .andExpect(jsonPath("$.[*].art").value(hasItem(DEFAULT_ART)))
            .andExpect(jsonPath("$.[*].baseCalculo").value(hasItem(DEFAULT_BASE_CALCULO)))
            .andExpect(jsonPath("$.[*].codigoObra").value(hasItem(DEFAULT_CODIGO_OBRA)))
            .andExpect(jsonPath("$.[*].valorServico").value(hasItem(DEFAULT_VALOR_SERVICO)))
            .andExpect(jsonPath("$.[*].valorPis").value(hasItem(DEFAULT_VALOR_PIS)))
            .andExpect(jsonPath("$.[*].valorLiquidoNfse").value(hasItem(DEFAULT_VALOR_LIQUIDO_NFSE)))
            .andExpect(jsonPath("$.[*].valorIssRetido").value(hasItem(DEFAULT_VALOR_ISS_RETIDO)))
            .andExpect(jsonPath("$.[*].valorIssOutroMunicipio").value(hasItem(DEFAULT_VALOR_ISS_OUTRO_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].valorIss").value(hasItem(DEFAULT_VALOR_ISS)))
            .andExpect(jsonPath("$.[*].valorIr").value(hasItem(DEFAULT_VALOR_IR)))
            .andExpect(jsonPath("$.[*].valorInss").value(hasItem(DEFAULT_VALOR_INSS)))
            .andExpect(jsonPath("$.[*].valorDescontoCondicionado").value(hasItem(DEFAULT_VALOR_DESCONTO_CONDICIONADO)))
            .andExpect(jsonPath("$.[*].valorDescontoIncondicionado").value(hasItem(DEFAULT_VALOR_DESCONTO_INCONDICIONADO)))
            .andExpect(jsonPath("$.[*].valorDeducoes").value(hasItem(DEFAULT_VALOR_DEDUCOES)))
            .andExpect(jsonPath("$.[*].valorCsll").value(hasItem(DEFAULT_VALOR_CSLL)))
            .andExpect(jsonPath("$.[*].valorCredito").value(hasItem(DEFAULT_VALOR_CREDITO)))
            .andExpect(jsonPath("$.[*].valorCofins").value(hasItem(DEFAULT_VALOR_COFINS)))
            .andExpect(jsonPath("$.[*].outrasRetencoes").value(hasItem(DEFAULT_OUTRAS_RETENCOES)))
            .andExpect(jsonPath("$.[*].issRetido").value(hasItem(DEFAULT_ISS_RETIDO)))
            .andExpect(jsonPath("$.[*].discriminacao").value(hasItem(DEFAULT_DISCRIMINACAO)))
            .andExpect(jsonPath("$.[*].municipioPrestacaoServico").value(hasItem(DEFAULT_MUNICIPIO_PRESTACAO_SERVICO)))
            .andExpect(jsonPath("$.[*].paisPrestacaoServico").value(hasItem(DEFAULT_PAIS_PRESTACAO_SERVICO)))
            .andExpect(jsonPath("$.[*].codRegimeEspecialTributacao").value(hasItem(DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO)))
            .andExpect(jsonPath("$.[*].codigoTributacaoMunicipio").value(hasItem(DEFAULT_CODIGO_TRIBUTACAO_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].itemListaServicoMunicipio").value(hasItem(DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].cpfCnpjTomador").value(hasItem(DEFAULT_CPF_CNPJ_TOMADOR)))
            .andExpect(jsonPath("$.[*].cpfCnpjPrestador").value(hasItem(DEFAULT_CPF_CNPJ_PRESTADOR)))
            .andExpect(jsonPath("$.[*].inscricaoMunicipalTomador").value(hasItem(DEFAULT_INSCRICAO_MUNICIPAL_TOMADOR)))
            .andExpect(jsonPath("$.[*].indicadorCpfCnpjTomador").value(hasItem(DEFAULT_INDICADOR_CPF_CNPJ_TOMADOR)))
            .andExpect(jsonPath("$.[*].indicadorCpfCnpjPrestador").value(hasItem(DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR)))
            .andExpect(jsonPath("$.[*].paisTomador").value(hasItem(DEFAULT_PAIS_TOMADOR)))
            .andExpect(jsonPath("$.[*].paisPrestador").value(hasItem(DEFAULT_PAIS_PRESTADOR)))
            .andExpect(jsonPath("$.[*].tipoTomador").value(hasItem(DEFAULT_TIPO_TOMADOR)))
            .andExpect(jsonPath("$.[*].codCidadeTomadorIBGE").value(hasItem(DEFAULT_COD_CIDADE_TOMADOR_IBGE)))
            .andExpect(jsonPath("$.[*].codCidadePrestadorIBGE").value(hasItem(DEFAULT_COD_CIDADE_PRESTADOR_IBGE)))
            .andExpect(jsonPath("$.[*].tipoPrestador").value(hasItem(DEFAULT_TIPO_PRESTADOR)))
            .andExpect(jsonPath("$.[*].numeroEmpenho").value(hasItem(DEFAULT_NUMERO_EMPENHO)))
            .andExpect(jsonPath("$.[*].tipoTributacaoPrestExterno").value(hasItem(DEFAULT_TIPO_TRIBUTACAO_PREST_EXTERNO)))
            .andExpect(jsonPath("$.[*].cepPrestador").value(hasItem(DEFAULT_CEP_PRESTADOR)))
            .andExpect(jsonPath("$.[*].cepTomador").value(hasItem(DEFAULT_CEP_TOMADOR)))
            .andExpect(jsonPath("$.[*].idTipoAnexoSimplesNacional").value(hasItem(DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL)))
            .andExpect(jsonPath("$.[*].idBloqueioNfse").value(hasItem(DEFAULT_ID_BLOQUEIO_NFSE)))
            .andExpect(jsonPath("$.[*].idPgdasd2018Atividade").value(hasItem(DEFAULT_ID_PGDASD_2018_ATIVIDADE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNfsesWithEagerRelationshipsIsEnabled() throws Exception {
        when(nfseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNfseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nfseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNfsesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nfseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNfseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nfseRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNfse() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        // Get the nfse
        restNfseMockMvc
            .perform(get(ENTITY_API_URL_ID, nfse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nfse.getId().intValue()))
            .andExpect(jsonPath("$.idNfse").value(DEFAULT_ID_NFSE))
            .andExpect(jsonPath("$.competencia").value(DEFAULT_COMPETENCIA))
            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.optanteSimplesNacional").value(DEFAULT_OPTANTE_SIMPLES_NACIONAL))
            .andExpect(jsonPath("$.outrasInformacoes").value(DEFAULT_OUTRAS_INFORMACOES))
            .andExpect(jsonPath("$.idNfseSubstituida").value(DEFAULT_ID_NFSE_SUBSTITUIDA))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.statusNfse").value(DEFAULT_STATUS_NFSE))
            .andExpect(jsonPath("$.naturezaOperacao").value(DEFAULT_NATUREZA_OPERACAO))
            .andExpect(jsonPath("$.rps").value(DEFAULT_RPS))
            .andExpect(jsonPath("$.inscricaoPrestador").value(DEFAULT_INSCRICAO_PRESTADOR))
            .andExpect(jsonPath("$.tipoDocDigitado").value(DEFAULT_TIPO_DOC_DIGITADO))
            .andExpect(jsonPath("$.statusAceite").value(DEFAULT_STATUS_ACEITE))
            .andExpect(jsonPath("$.exclusaoLogica").value(DEFAULT_EXCLUSAO_LOGICA))
            .andExpect(jsonPath("$.ajustada").value(DEFAULT_AJUSTADA))
            .andExpect(jsonPath("$.observacaoAjuste").value(DEFAULT_OBSERVACAO_AJUSTE))
            .andExpect(jsonPath("$.aliquotaServico").value(DEFAULT_ALIQUOTA_SERVICO))
            .andExpect(jsonPath("$.art").value(DEFAULT_ART))
            .andExpect(jsonPath("$.baseCalculo").value(DEFAULT_BASE_CALCULO))
            .andExpect(jsonPath("$.codigoObra").value(DEFAULT_CODIGO_OBRA))
            .andExpect(jsonPath("$.valorServico").value(DEFAULT_VALOR_SERVICO))
            .andExpect(jsonPath("$.valorPis").value(DEFAULT_VALOR_PIS))
            .andExpect(jsonPath("$.valorLiquidoNfse").value(DEFAULT_VALOR_LIQUIDO_NFSE))
            .andExpect(jsonPath("$.valorIssRetido").value(DEFAULT_VALOR_ISS_RETIDO))
            .andExpect(jsonPath("$.valorIssOutroMunicipio").value(DEFAULT_VALOR_ISS_OUTRO_MUNICIPIO))
            .andExpect(jsonPath("$.valorIss").value(DEFAULT_VALOR_ISS))
            .andExpect(jsonPath("$.valorIr").value(DEFAULT_VALOR_IR))
            .andExpect(jsonPath("$.valorInss").value(DEFAULT_VALOR_INSS))
            .andExpect(jsonPath("$.valorDescontoCondicionado").value(DEFAULT_VALOR_DESCONTO_CONDICIONADO))
            .andExpect(jsonPath("$.valorDescontoIncondicionado").value(DEFAULT_VALOR_DESCONTO_INCONDICIONADO))
            .andExpect(jsonPath("$.valorDeducoes").value(DEFAULT_VALOR_DEDUCOES))
            .andExpect(jsonPath("$.valorCsll").value(DEFAULT_VALOR_CSLL))
            .andExpect(jsonPath("$.valorCredito").value(DEFAULT_VALOR_CREDITO))
            .andExpect(jsonPath("$.valorCofins").value(DEFAULT_VALOR_COFINS))
            .andExpect(jsonPath("$.outrasRetencoes").value(DEFAULT_OUTRAS_RETENCOES))
            .andExpect(jsonPath("$.issRetido").value(DEFAULT_ISS_RETIDO))
            .andExpect(jsonPath("$.discriminacao").value(DEFAULT_DISCRIMINACAO))
            .andExpect(jsonPath("$.municipioPrestacaoServico").value(DEFAULT_MUNICIPIO_PRESTACAO_SERVICO))
            .andExpect(jsonPath("$.paisPrestacaoServico").value(DEFAULT_PAIS_PRESTACAO_SERVICO))
            .andExpect(jsonPath("$.codRegimeEspecialTributacao").value(DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO))
            .andExpect(jsonPath("$.codigoTributacaoMunicipio").value(DEFAULT_CODIGO_TRIBUTACAO_MUNICIPIO))
            .andExpect(jsonPath("$.itemListaServicoMunicipio").value(DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO))
            .andExpect(jsonPath("$.cpfCnpjTomador").value(DEFAULT_CPF_CNPJ_TOMADOR))
            .andExpect(jsonPath("$.cpfCnpjPrestador").value(DEFAULT_CPF_CNPJ_PRESTADOR))
            .andExpect(jsonPath("$.inscricaoMunicipalTomador").value(DEFAULT_INSCRICAO_MUNICIPAL_TOMADOR))
            .andExpect(jsonPath("$.indicadorCpfCnpjTomador").value(DEFAULT_INDICADOR_CPF_CNPJ_TOMADOR))
            .andExpect(jsonPath("$.indicadorCpfCnpjPrestador").value(DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR))
            .andExpect(jsonPath("$.paisTomador").value(DEFAULT_PAIS_TOMADOR))
            .andExpect(jsonPath("$.paisPrestador").value(DEFAULT_PAIS_PRESTADOR))
            .andExpect(jsonPath("$.tipoTomador").value(DEFAULT_TIPO_TOMADOR))
            .andExpect(jsonPath("$.codCidadeTomadorIBGE").value(DEFAULT_COD_CIDADE_TOMADOR_IBGE))
            .andExpect(jsonPath("$.codCidadePrestadorIBGE").value(DEFAULT_COD_CIDADE_PRESTADOR_IBGE))
            .andExpect(jsonPath("$.tipoPrestador").value(DEFAULT_TIPO_PRESTADOR))
            .andExpect(jsonPath("$.numeroEmpenho").value(DEFAULT_NUMERO_EMPENHO))
            .andExpect(jsonPath("$.tipoTributacaoPrestExterno").value(DEFAULT_TIPO_TRIBUTACAO_PREST_EXTERNO))
            .andExpect(jsonPath("$.cepPrestador").value(DEFAULT_CEP_PRESTADOR))
            .andExpect(jsonPath("$.cepTomador").value(DEFAULT_CEP_TOMADOR))
            .andExpect(jsonPath("$.idTipoAnexoSimplesNacional").value(DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL))
            .andExpect(jsonPath("$.idBloqueioNfse").value(DEFAULT_ID_BLOQUEIO_NFSE))
            .andExpect(jsonPath("$.idPgdasd2018Atividade").value(DEFAULT_ID_PGDASD_2018_ATIVIDADE));
    }

    @Test
    @Transactional
    void getNonExistingNfse() throws Exception {
        // Get the nfse
        restNfseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNfse() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();

        // Update the nfse
        Nfse updatedNfse = nfseRepository.findById(nfse.getId()).get();
        // Disconnect from session so that the updates on updatedNfse are not directly saved in db
        em.detach(updatedNfse);
        updatedNfse
            .idNfse(UPDATED_ID_NFSE)
            .competencia(UPDATED_COMPETENCIA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .ip(UPDATED_IP)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .outrasInformacoes(UPDATED_OUTRAS_INFORMACOES)
            .idNfseSubstituida(UPDATED_ID_NFSE_SUBSTITUIDA)
            .usuario(UPDATED_USUARIO)
            .statusNfse(UPDATED_STATUS_NFSE)
            .naturezaOperacao(UPDATED_NATUREZA_OPERACAO)
            .rps(UPDATED_RPS)
            .inscricaoPrestador(UPDATED_INSCRICAO_PRESTADOR)
            .tipoDocDigitado(UPDATED_TIPO_DOC_DIGITADO)
            .statusAceite(UPDATED_STATUS_ACEITE)
            .exclusaoLogica(UPDATED_EXCLUSAO_LOGICA)
            .ajustada(UPDATED_AJUSTADA)
            .observacaoAjuste(UPDATED_OBSERVACAO_AJUSTE)
            .aliquotaServico(UPDATED_ALIQUOTA_SERVICO)
            .art(UPDATED_ART)
            .baseCalculo(UPDATED_BASE_CALCULO)
            .codigoObra(UPDATED_CODIGO_OBRA)
            .valorServico(UPDATED_VALOR_SERVICO)
            .valorPis(UPDATED_VALOR_PIS)
            .valorLiquidoNfse(UPDATED_VALOR_LIQUIDO_NFSE)
            .valorIssRetido(UPDATED_VALOR_ISS_RETIDO)
            .valorIssOutroMunicipio(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO)
            .valorIss(UPDATED_VALOR_ISS)
            .valorIr(UPDATED_VALOR_IR)
            .valorInss(UPDATED_VALOR_INSS)
            .valorDescontoCondicionado(UPDATED_VALOR_DESCONTO_CONDICIONADO)
            .valorDescontoIncondicionado(UPDATED_VALOR_DESCONTO_INCONDICIONADO)
            .valorDeducoes(UPDATED_VALOR_DEDUCOES)
            .valorCsll(UPDATED_VALOR_CSLL)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorCofins(UPDATED_VALOR_COFINS)
            .outrasRetencoes(UPDATED_OUTRAS_RETENCOES)
            .issRetido(UPDATED_ISS_RETIDO)
            .discriminacao(UPDATED_DISCRIMINACAO)
            .municipioPrestacaoServico(UPDATED_MUNICIPIO_PRESTACAO_SERVICO)
            .paisPrestacaoServico(UPDATED_PAIS_PRESTACAO_SERVICO)
            .codRegimeEspecialTributacao(UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO)
            .codigoTributacaoMunicipio(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO)
            .itemListaServicoMunicipio(UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO)
            .cpfCnpjTomador(UPDATED_CPF_CNPJ_TOMADOR)
            .cpfCnpjPrestador(UPDATED_CPF_CNPJ_PRESTADOR)
            .inscricaoMunicipalTomador(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR)
            .indicadorCpfCnpjTomador(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR)
            .indicadorCpfCnpjPrestador(UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR)
            .paisTomador(UPDATED_PAIS_TOMADOR)
            .paisPrestador(UPDATED_PAIS_PRESTADOR)
            .tipoTomador(UPDATED_TIPO_TOMADOR)
            .codCidadeTomadorIBGE(UPDATED_COD_CIDADE_TOMADOR_IBGE)
            .codCidadePrestadorIBGE(UPDATED_COD_CIDADE_PRESTADOR_IBGE)
            .tipoPrestador(UPDATED_TIPO_PRESTADOR)
            .numeroEmpenho(UPDATED_NUMERO_EMPENHO)
            .tipoTributacaoPrestExterno(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO)
            .cepPrestador(UPDATED_CEP_PRESTADOR)
            .cepTomador(UPDATED_CEP_TOMADOR)
            .idTipoAnexoSimplesNacional(UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL)
            .idBloqueioNfse(UPDATED_ID_BLOQUEIO_NFSE)
            .idPgdasd2018Atividade(UPDATED_ID_PGDASD_2018_ATIVIDADE);
        NfseDTO nfseDTO = nfseMapper.toDto(updatedNfse);

        restNfseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nfseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nfseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
        Nfse testNfse = nfseList.get(nfseList.size() - 1);
        assertThat(testNfse.getIdNfse()).isEqualTo(UPDATED_ID_NFSE);
        assertThat(testNfse.getCompetencia()).isEqualTo(UPDATED_COMPETENCIA);
        assertThat(testNfse.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testNfse.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testNfse.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testNfse.getOutrasInformacoes()).isEqualTo(UPDATED_OUTRAS_INFORMACOES);
        assertThat(testNfse.getIdNfseSubstituida()).isEqualTo(UPDATED_ID_NFSE_SUBSTITUIDA);
        assertThat(testNfse.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testNfse.getStatusNfse()).isEqualTo(UPDATED_STATUS_NFSE);
        assertThat(testNfse.getNaturezaOperacao()).isEqualTo(UPDATED_NATUREZA_OPERACAO);
        assertThat(testNfse.getRps()).isEqualTo(UPDATED_RPS);
        assertThat(testNfse.getInscricaoPrestador()).isEqualTo(UPDATED_INSCRICAO_PRESTADOR);
        assertThat(testNfse.getTipoDocDigitado()).isEqualTo(UPDATED_TIPO_DOC_DIGITADO);
        assertThat(testNfse.getStatusAceite()).isEqualTo(UPDATED_STATUS_ACEITE);
        assertThat(testNfse.getExclusaoLogica()).isEqualTo(UPDATED_EXCLUSAO_LOGICA);
        assertThat(testNfse.getAjustada()).isEqualTo(UPDATED_AJUSTADA);
        assertThat(testNfse.getObservacaoAjuste()).isEqualTo(UPDATED_OBSERVACAO_AJUSTE);
        assertThat(testNfse.getAliquotaServico()).isEqualTo(UPDATED_ALIQUOTA_SERVICO);
        assertThat(testNfse.getArt()).isEqualTo(UPDATED_ART);
        assertThat(testNfse.getBaseCalculo()).isEqualTo(UPDATED_BASE_CALCULO);
        assertThat(testNfse.getCodigoObra()).isEqualTo(UPDATED_CODIGO_OBRA);
        assertThat(testNfse.getValorServico()).isEqualTo(UPDATED_VALOR_SERVICO);
        assertThat(testNfse.getValorPis()).isEqualTo(UPDATED_VALOR_PIS);
        assertThat(testNfse.getValorLiquidoNfse()).isEqualTo(UPDATED_VALOR_LIQUIDO_NFSE);
        assertThat(testNfse.getValorIssRetido()).isEqualTo(UPDATED_VALOR_ISS_RETIDO);
        assertThat(testNfse.getValorIssOutroMunicipio()).isEqualTo(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO);
        assertThat(testNfse.getValorIss()).isEqualTo(UPDATED_VALOR_ISS);
        assertThat(testNfse.getValorIr()).isEqualTo(UPDATED_VALOR_IR);
        assertThat(testNfse.getValorInss()).isEqualTo(UPDATED_VALOR_INSS);
        assertThat(testNfse.getValorDescontoCondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_CONDICIONADO);
        assertThat(testNfse.getValorDescontoIncondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_INCONDICIONADO);
        assertThat(testNfse.getValorDeducoes()).isEqualTo(UPDATED_VALOR_DEDUCOES);
        assertThat(testNfse.getValorCsll()).isEqualTo(UPDATED_VALOR_CSLL);
        assertThat(testNfse.getValorCredito()).isEqualTo(UPDATED_VALOR_CREDITO);
        assertThat(testNfse.getValorCofins()).isEqualTo(UPDATED_VALOR_COFINS);
        assertThat(testNfse.getOutrasRetencoes()).isEqualTo(UPDATED_OUTRAS_RETENCOES);
        assertThat(testNfse.getIssRetido()).isEqualTo(UPDATED_ISS_RETIDO);
        assertThat(testNfse.getDiscriminacao()).isEqualTo(UPDATED_DISCRIMINACAO);
        assertThat(testNfse.getMunicipioPrestacaoServico()).isEqualTo(UPDATED_MUNICIPIO_PRESTACAO_SERVICO);
        assertThat(testNfse.getPaisPrestacaoServico()).isEqualTo(UPDATED_PAIS_PRESTACAO_SERVICO);
        assertThat(testNfse.getCodRegimeEspecialTributacao()).isEqualTo(UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO);
        assertThat(testNfse.getCodigoTributacaoMunicipio()).isEqualTo(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO);
        assertThat(testNfse.getItemListaServicoMunicipio()).isEqualTo(UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO);
        assertThat(testNfse.getCpfCnpjTomador()).isEqualTo(UPDATED_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getCpfCnpjPrestador()).isEqualTo(UPDATED_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getInscricaoMunicipalTomador()).isEqualTo(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjTomador()).isEqualTo(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjPrestador()).isEqualTo(UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getPaisTomador()).isEqualTo(UPDATED_PAIS_TOMADOR);
        assertThat(testNfse.getPaisPrestador()).isEqualTo(UPDATED_PAIS_PRESTADOR);
        assertThat(testNfse.getTipoTomador()).isEqualTo(UPDATED_TIPO_TOMADOR);
        assertThat(testNfse.getCodCidadeTomadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_TOMADOR_IBGE);
        assertThat(testNfse.getCodCidadePrestadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_PRESTADOR_IBGE);
        assertThat(testNfse.getTipoPrestador()).isEqualTo(UPDATED_TIPO_PRESTADOR);
        assertThat(testNfse.getNumeroEmpenho()).isEqualTo(UPDATED_NUMERO_EMPENHO);
        assertThat(testNfse.getTipoTributacaoPrestExterno()).isEqualTo(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO);
        assertThat(testNfse.getCepPrestador()).isEqualTo(UPDATED_CEP_PRESTADOR);
        assertThat(testNfse.getCepTomador()).isEqualTo(UPDATED_CEP_TOMADOR);
        assertThat(testNfse.getIdTipoAnexoSimplesNacional()).isEqualTo(UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL);
        assertThat(testNfse.getIdBloqueioNfse()).isEqualTo(UPDATED_ID_BLOQUEIO_NFSE);
        assertThat(testNfse.getIdPgdasd2018Atividade()).isEqualTo(UPDATED_ID_PGDASD_2018_ATIVIDADE);
    }

    @Test
    @Transactional
    void putNonExistingNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nfseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nfseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nfseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nfseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNfseWithPatch() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();

        // Update the nfse using partial update
        Nfse partialUpdatedNfse = new Nfse();
        partialUpdatedNfse.setId(nfse.getId());

        partialUpdatedNfse
            .idNfse(UPDATED_ID_NFSE)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .ip(UPDATED_IP)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .idNfseSubstituida(UPDATED_ID_NFSE_SUBSTITUIDA)
            .exclusaoLogica(UPDATED_EXCLUSAO_LOGICA)
            .art(UPDATED_ART)
            .baseCalculo(UPDATED_BASE_CALCULO)
            .valorServico(UPDATED_VALOR_SERVICO)
            .valorPis(UPDATED_VALOR_PIS)
            .valorLiquidoNfse(UPDATED_VALOR_LIQUIDO_NFSE)
            .valorIssOutroMunicipio(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO)
            .valorIss(UPDATED_VALOR_ISS)
            .valorIr(UPDATED_VALOR_IR)
            .valorDescontoCondicionado(UPDATED_VALOR_DESCONTO_CONDICIONADO)
            .valorDescontoIncondicionado(UPDATED_VALOR_DESCONTO_INCONDICIONADO)
            .valorDeducoes(UPDATED_VALOR_DEDUCOES)
            .valorCsll(UPDATED_VALOR_CSLL)
            .valorCofins(UPDATED_VALOR_COFINS)
            .outrasRetencoes(UPDATED_OUTRAS_RETENCOES)
            .discriminacao(UPDATED_DISCRIMINACAO)
            .municipioPrestacaoServico(UPDATED_MUNICIPIO_PRESTACAO_SERVICO)
            .paisPrestacaoServico(UPDATED_PAIS_PRESTACAO_SERVICO)
            .codigoTributacaoMunicipio(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO)
            .cpfCnpjTomador(UPDATED_CPF_CNPJ_TOMADOR)
            .inscricaoMunicipalTomador(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR)
            .indicadorCpfCnpjTomador(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR)
            .tipoTomador(UPDATED_TIPO_TOMADOR)
            .codCidadeTomadorIBGE(UPDATED_COD_CIDADE_TOMADOR_IBGE)
            .codCidadePrestadorIBGE(UPDATED_COD_CIDADE_PRESTADOR_IBGE)
            .tipoPrestador(UPDATED_TIPO_PRESTADOR)
            .tipoTributacaoPrestExterno(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO)
            .cepTomador(UPDATED_CEP_TOMADOR)
            .idBloqueioNfse(UPDATED_ID_BLOQUEIO_NFSE)
            .idPgdasd2018Atividade(UPDATED_ID_PGDASD_2018_ATIVIDADE);

        restNfseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNfse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNfse))
            )
            .andExpect(status().isOk());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
        Nfse testNfse = nfseList.get(nfseList.size() - 1);
        assertThat(testNfse.getIdNfse()).isEqualTo(UPDATED_ID_NFSE);
        assertThat(testNfse.getCompetencia()).isEqualTo(DEFAULT_COMPETENCIA);
        assertThat(testNfse.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testNfse.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testNfse.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testNfse.getOutrasInformacoes()).isEqualTo(DEFAULT_OUTRAS_INFORMACOES);
        assertThat(testNfse.getIdNfseSubstituida()).isEqualTo(UPDATED_ID_NFSE_SUBSTITUIDA);
        assertThat(testNfse.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testNfse.getStatusNfse()).isEqualTo(DEFAULT_STATUS_NFSE);
        assertThat(testNfse.getNaturezaOperacao()).isEqualTo(DEFAULT_NATUREZA_OPERACAO);
        assertThat(testNfse.getRps()).isEqualTo(DEFAULT_RPS);
        assertThat(testNfse.getInscricaoPrestador()).isEqualTo(DEFAULT_INSCRICAO_PRESTADOR);
        assertThat(testNfse.getTipoDocDigitado()).isEqualTo(DEFAULT_TIPO_DOC_DIGITADO);
        assertThat(testNfse.getStatusAceite()).isEqualTo(DEFAULT_STATUS_ACEITE);
        assertThat(testNfse.getExclusaoLogica()).isEqualTo(UPDATED_EXCLUSAO_LOGICA);
        assertThat(testNfse.getAjustada()).isEqualTo(DEFAULT_AJUSTADA);
        assertThat(testNfse.getObservacaoAjuste()).isEqualTo(DEFAULT_OBSERVACAO_AJUSTE);
        assertThat(testNfse.getAliquotaServico()).isEqualTo(DEFAULT_ALIQUOTA_SERVICO);
        assertThat(testNfse.getArt()).isEqualTo(UPDATED_ART);
        assertThat(testNfse.getBaseCalculo()).isEqualTo(UPDATED_BASE_CALCULO);
        assertThat(testNfse.getCodigoObra()).isEqualTo(DEFAULT_CODIGO_OBRA);
        assertThat(testNfse.getValorServico()).isEqualTo(UPDATED_VALOR_SERVICO);
        assertThat(testNfse.getValorPis()).isEqualTo(UPDATED_VALOR_PIS);
        assertThat(testNfse.getValorLiquidoNfse()).isEqualTo(UPDATED_VALOR_LIQUIDO_NFSE);
        assertThat(testNfse.getValorIssRetido()).isEqualTo(DEFAULT_VALOR_ISS_RETIDO);
        assertThat(testNfse.getValorIssOutroMunicipio()).isEqualTo(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO);
        assertThat(testNfse.getValorIss()).isEqualTo(UPDATED_VALOR_ISS);
        assertThat(testNfse.getValorIr()).isEqualTo(UPDATED_VALOR_IR);
        assertThat(testNfse.getValorInss()).isEqualTo(DEFAULT_VALOR_INSS);
        assertThat(testNfse.getValorDescontoCondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_CONDICIONADO);
        assertThat(testNfse.getValorDescontoIncondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_INCONDICIONADO);
        assertThat(testNfse.getValorDeducoes()).isEqualTo(UPDATED_VALOR_DEDUCOES);
        assertThat(testNfse.getValorCsll()).isEqualTo(UPDATED_VALOR_CSLL);
        assertThat(testNfse.getValorCredito()).isEqualTo(DEFAULT_VALOR_CREDITO);
        assertThat(testNfse.getValorCofins()).isEqualTo(UPDATED_VALOR_COFINS);
        assertThat(testNfse.getOutrasRetencoes()).isEqualTo(UPDATED_OUTRAS_RETENCOES);
        assertThat(testNfse.getIssRetido()).isEqualTo(DEFAULT_ISS_RETIDO);
        assertThat(testNfse.getDiscriminacao()).isEqualTo(UPDATED_DISCRIMINACAO);
        assertThat(testNfse.getMunicipioPrestacaoServico()).isEqualTo(UPDATED_MUNICIPIO_PRESTACAO_SERVICO);
        assertThat(testNfse.getPaisPrestacaoServico()).isEqualTo(UPDATED_PAIS_PRESTACAO_SERVICO);
        assertThat(testNfse.getCodRegimeEspecialTributacao()).isEqualTo(DEFAULT_COD_REGIME_ESPECIAL_TRIBUTACAO);
        assertThat(testNfse.getCodigoTributacaoMunicipio()).isEqualTo(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO);
        assertThat(testNfse.getItemListaServicoMunicipio()).isEqualTo(DEFAULT_ITEM_LISTA_SERVICO_MUNICIPIO);
        assertThat(testNfse.getCpfCnpjTomador()).isEqualTo(UPDATED_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getCpfCnpjPrestador()).isEqualTo(DEFAULT_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getInscricaoMunicipalTomador()).isEqualTo(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjTomador()).isEqualTo(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjPrestador()).isEqualTo(DEFAULT_INDICADOR_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getPaisTomador()).isEqualTo(DEFAULT_PAIS_TOMADOR);
        assertThat(testNfse.getPaisPrestador()).isEqualTo(DEFAULT_PAIS_PRESTADOR);
        assertThat(testNfse.getTipoTomador()).isEqualTo(UPDATED_TIPO_TOMADOR);
        assertThat(testNfse.getCodCidadeTomadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_TOMADOR_IBGE);
        assertThat(testNfse.getCodCidadePrestadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_PRESTADOR_IBGE);
        assertThat(testNfse.getTipoPrestador()).isEqualTo(UPDATED_TIPO_PRESTADOR);
        assertThat(testNfse.getNumeroEmpenho()).isEqualTo(DEFAULT_NUMERO_EMPENHO);
        assertThat(testNfse.getTipoTributacaoPrestExterno()).isEqualTo(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO);
        assertThat(testNfse.getCepPrestador()).isEqualTo(DEFAULT_CEP_PRESTADOR);
        assertThat(testNfse.getCepTomador()).isEqualTo(UPDATED_CEP_TOMADOR);
        assertThat(testNfse.getIdTipoAnexoSimplesNacional()).isEqualTo(DEFAULT_ID_TIPO_ANEXO_SIMPLES_NACIONAL);
        assertThat(testNfse.getIdBloqueioNfse()).isEqualTo(UPDATED_ID_BLOQUEIO_NFSE);
        assertThat(testNfse.getIdPgdasd2018Atividade()).isEqualTo(UPDATED_ID_PGDASD_2018_ATIVIDADE);
    }

    @Test
    @Transactional
    void fullUpdateNfseWithPatch() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();

        // Update the nfse using partial update
        Nfse partialUpdatedNfse = new Nfse();
        partialUpdatedNfse.setId(nfse.getId());

        partialUpdatedNfse
            .idNfse(UPDATED_ID_NFSE)
            .competencia(UPDATED_COMPETENCIA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .ip(UPDATED_IP)
            .optanteSimplesNacional(UPDATED_OPTANTE_SIMPLES_NACIONAL)
            .outrasInformacoes(UPDATED_OUTRAS_INFORMACOES)
            .idNfseSubstituida(UPDATED_ID_NFSE_SUBSTITUIDA)
            .usuario(UPDATED_USUARIO)
            .statusNfse(UPDATED_STATUS_NFSE)
            .naturezaOperacao(UPDATED_NATUREZA_OPERACAO)
            .rps(UPDATED_RPS)
            .inscricaoPrestador(UPDATED_INSCRICAO_PRESTADOR)
            .tipoDocDigitado(UPDATED_TIPO_DOC_DIGITADO)
            .statusAceite(UPDATED_STATUS_ACEITE)
            .exclusaoLogica(UPDATED_EXCLUSAO_LOGICA)
            .ajustada(UPDATED_AJUSTADA)
            .observacaoAjuste(UPDATED_OBSERVACAO_AJUSTE)
            .aliquotaServico(UPDATED_ALIQUOTA_SERVICO)
            .art(UPDATED_ART)
            .baseCalculo(UPDATED_BASE_CALCULO)
            .codigoObra(UPDATED_CODIGO_OBRA)
            .valorServico(UPDATED_VALOR_SERVICO)
            .valorPis(UPDATED_VALOR_PIS)
            .valorLiquidoNfse(UPDATED_VALOR_LIQUIDO_NFSE)
            .valorIssRetido(UPDATED_VALOR_ISS_RETIDO)
            .valorIssOutroMunicipio(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO)
            .valorIss(UPDATED_VALOR_ISS)
            .valorIr(UPDATED_VALOR_IR)
            .valorInss(UPDATED_VALOR_INSS)
            .valorDescontoCondicionado(UPDATED_VALOR_DESCONTO_CONDICIONADO)
            .valorDescontoIncondicionado(UPDATED_VALOR_DESCONTO_INCONDICIONADO)
            .valorDeducoes(UPDATED_VALOR_DEDUCOES)
            .valorCsll(UPDATED_VALOR_CSLL)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorCofins(UPDATED_VALOR_COFINS)
            .outrasRetencoes(UPDATED_OUTRAS_RETENCOES)
            .issRetido(UPDATED_ISS_RETIDO)
            .discriminacao(UPDATED_DISCRIMINACAO)
            .municipioPrestacaoServico(UPDATED_MUNICIPIO_PRESTACAO_SERVICO)
            .paisPrestacaoServico(UPDATED_PAIS_PRESTACAO_SERVICO)
            .codRegimeEspecialTributacao(UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO)
            .codigoTributacaoMunicipio(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO)
            .itemListaServicoMunicipio(UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO)
            .cpfCnpjTomador(UPDATED_CPF_CNPJ_TOMADOR)
            .cpfCnpjPrestador(UPDATED_CPF_CNPJ_PRESTADOR)
            .inscricaoMunicipalTomador(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR)
            .indicadorCpfCnpjTomador(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR)
            .indicadorCpfCnpjPrestador(UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR)
            .paisTomador(UPDATED_PAIS_TOMADOR)
            .paisPrestador(UPDATED_PAIS_PRESTADOR)
            .tipoTomador(UPDATED_TIPO_TOMADOR)
            .codCidadeTomadorIBGE(UPDATED_COD_CIDADE_TOMADOR_IBGE)
            .codCidadePrestadorIBGE(UPDATED_COD_CIDADE_PRESTADOR_IBGE)
            .tipoPrestador(UPDATED_TIPO_PRESTADOR)
            .numeroEmpenho(UPDATED_NUMERO_EMPENHO)
            .tipoTributacaoPrestExterno(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO)
            .cepPrestador(UPDATED_CEP_PRESTADOR)
            .cepTomador(UPDATED_CEP_TOMADOR)
            .idTipoAnexoSimplesNacional(UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL)
            .idBloqueioNfse(UPDATED_ID_BLOQUEIO_NFSE)
            .idPgdasd2018Atividade(UPDATED_ID_PGDASD_2018_ATIVIDADE);

        restNfseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNfse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNfse))
            )
            .andExpect(status().isOk());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
        Nfse testNfse = nfseList.get(nfseList.size() - 1);
        assertThat(testNfse.getIdNfse()).isEqualTo(UPDATED_ID_NFSE);
        assertThat(testNfse.getCompetencia()).isEqualTo(UPDATED_COMPETENCIA);
        assertThat(testNfse.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testNfse.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testNfse.getOptanteSimplesNacional()).isEqualTo(UPDATED_OPTANTE_SIMPLES_NACIONAL);
        assertThat(testNfse.getOutrasInformacoes()).isEqualTo(UPDATED_OUTRAS_INFORMACOES);
        assertThat(testNfse.getIdNfseSubstituida()).isEqualTo(UPDATED_ID_NFSE_SUBSTITUIDA);
        assertThat(testNfse.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testNfse.getStatusNfse()).isEqualTo(UPDATED_STATUS_NFSE);
        assertThat(testNfse.getNaturezaOperacao()).isEqualTo(UPDATED_NATUREZA_OPERACAO);
        assertThat(testNfse.getRps()).isEqualTo(UPDATED_RPS);
        assertThat(testNfse.getInscricaoPrestador()).isEqualTo(UPDATED_INSCRICAO_PRESTADOR);
        assertThat(testNfse.getTipoDocDigitado()).isEqualTo(UPDATED_TIPO_DOC_DIGITADO);
        assertThat(testNfse.getStatusAceite()).isEqualTo(UPDATED_STATUS_ACEITE);
        assertThat(testNfse.getExclusaoLogica()).isEqualTo(UPDATED_EXCLUSAO_LOGICA);
        assertThat(testNfse.getAjustada()).isEqualTo(UPDATED_AJUSTADA);
        assertThat(testNfse.getObservacaoAjuste()).isEqualTo(UPDATED_OBSERVACAO_AJUSTE);
        assertThat(testNfse.getAliquotaServico()).isEqualTo(UPDATED_ALIQUOTA_SERVICO);
        assertThat(testNfse.getArt()).isEqualTo(UPDATED_ART);
        assertThat(testNfse.getBaseCalculo()).isEqualTo(UPDATED_BASE_CALCULO);
        assertThat(testNfse.getCodigoObra()).isEqualTo(UPDATED_CODIGO_OBRA);
        assertThat(testNfse.getValorServico()).isEqualTo(UPDATED_VALOR_SERVICO);
        assertThat(testNfse.getValorPis()).isEqualTo(UPDATED_VALOR_PIS);
        assertThat(testNfse.getValorLiquidoNfse()).isEqualTo(UPDATED_VALOR_LIQUIDO_NFSE);
        assertThat(testNfse.getValorIssRetido()).isEqualTo(UPDATED_VALOR_ISS_RETIDO);
        assertThat(testNfse.getValorIssOutroMunicipio()).isEqualTo(UPDATED_VALOR_ISS_OUTRO_MUNICIPIO);
        assertThat(testNfse.getValorIss()).isEqualTo(UPDATED_VALOR_ISS);
        assertThat(testNfse.getValorIr()).isEqualTo(UPDATED_VALOR_IR);
        assertThat(testNfse.getValorInss()).isEqualTo(UPDATED_VALOR_INSS);
        assertThat(testNfse.getValorDescontoCondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_CONDICIONADO);
        assertThat(testNfse.getValorDescontoIncondicionado()).isEqualTo(UPDATED_VALOR_DESCONTO_INCONDICIONADO);
        assertThat(testNfse.getValorDeducoes()).isEqualTo(UPDATED_VALOR_DEDUCOES);
        assertThat(testNfse.getValorCsll()).isEqualTo(UPDATED_VALOR_CSLL);
        assertThat(testNfse.getValorCredito()).isEqualTo(UPDATED_VALOR_CREDITO);
        assertThat(testNfse.getValorCofins()).isEqualTo(UPDATED_VALOR_COFINS);
        assertThat(testNfse.getOutrasRetencoes()).isEqualTo(UPDATED_OUTRAS_RETENCOES);
        assertThat(testNfse.getIssRetido()).isEqualTo(UPDATED_ISS_RETIDO);
        assertThat(testNfse.getDiscriminacao()).isEqualTo(UPDATED_DISCRIMINACAO);
        assertThat(testNfse.getMunicipioPrestacaoServico()).isEqualTo(UPDATED_MUNICIPIO_PRESTACAO_SERVICO);
        assertThat(testNfse.getPaisPrestacaoServico()).isEqualTo(UPDATED_PAIS_PRESTACAO_SERVICO);
        assertThat(testNfse.getCodRegimeEspecialTributacao()).isEqualTo(UPDATED_COD_REGIME_ESPECIAL_TRIBUTACAO);
        assertThat(testNfse.getCodigoTributacaoMunicipio()).isEqualTo(UPDATED_CODIGO_TRIBUTACAO_MUNICIPIO);
        assertThat(testNfse.getItemListaServicoMunicipio()).isEqualTo(UPDATED_ITEM_LISTA_SERVICO_MUNICIPIO);
        assertThat(testNfse.getCpfCnpjTomador()).isEqualTo(UPDATED_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getCpfCnpjPrestador()).isEqualTo(UPDATED_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getInscricaoMunicipalTomador()).isEqualTo(UPDATED_INSCRICAO_MUNICIPAL_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjTomador()).isEqualTo(UPDATED_INDICADOR_CPF_CNPJ_TOMADOR);
        assertThat(testNfse.getIndicadorCpfCnpjPrestador()).isEqualTo(UPDATED_INDICADOR_CPF_CNPJ_PRESTADOR);
        assertThat(testNfse.getPaisTomador()).isEqualTo(UPDATED_PAIS_TOMADOR);
        assertThat(testNfse.getPaisPrestador()).isEqualTo(UPDATED_PAIS_PRESTADOR);
        assertThat(testNfse.getTipoTomador()).isEqualTo(UPDATED_TIPO_TOMADOR);
        assertThat(testNfse.getCodCidadeTomadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_TOMADOR_IBGE);
        assertThat(testNfse.getCodCidadePrestadorIBGE()).isEqualTo(UPDATED_COD_CIDADE_PRESTADOR_IBGE);
        assertThat(testNfse.getTipoPrestador()).isEqualTo(UPDATED_TIPO_PRESTADOR);
        assertThat(testNfse.getNumeroEmpenho()).isEqualTo(UPDATED_NUMERO_EMPENHO);
        assertThat(testNfse.getTipoTributacaoPrestExterno()).isEqualTo(UPDATED_TIPO_TRIBUTACAO_PREST_EXTERNO);
        assertThat(testNfse.getCepPrestador()).isEqualTo(UPDATED_CEP_PRESTADOR);
        assertThat(testNfse.getCepTomador()).isEqualTo(UPDATED_CEP_TOMADOR);
        assertThat(testNfse.getIdTipoAnexoSimplesNacional()).isEqualTo(UPDATED_ID_TIPO_ANEXO_SIMPLES_NACIONAL);
        assertThat(testNfse.getIdBloqueioNfse()).isEqualTo(UPDATED_ID_BLOQUEIO_NFSE);
        assertThat(testNfse.getIdPgdasd2018Atividade()).isEqualTo(UPDATED_ID_PGDASD_2018_ATIVIDADE);
    }

    @Test
    @Transactional
    void patchNonExistingNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nfseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nfseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nfseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNfse() throws Exception {
        int databaseSizeBeforeUpdate = nfseRepository.findAll().size();
        nfse.setId(count.incrementAndGet());

        // Create the Nfse
        NfseDTO nfseDTO = nfseMapper.toDto(nfse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNfseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nfseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nfse in the database
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNfse() throws Exception {
        // Initialize the database
        nfseRepository.saveAndFlush(nfse);

        int databaseSizeBeforeDelete = nfseRepository.findAll().size();

        // Delete the nfse
        restNfseMockMvc
            .perform(delete(ENTITY_API_URL_ID, nfse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nfse> nfseList = nfseRepository.findAll();
        assertThat(nfseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
