package br.com.sefin.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nfse.
 */
@Entity
@Table(name = "nfse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nfse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_nfse")
    private String idNfse;

    @Column(name = "competencia")
    private String competencia;

    @Column(name = "data_emissao")
    private String dataEmissao;

    @Column(name = "ip")
    private String ip;

    @Column(name = "optante_simples_nacional")
    private String optanteSimplesNacional;

    @Column(name = "outras_informacoes")
    private String outrasInformacoes;

    @Column(name = "id_nfse_substituida")
    private String idNfseSubstituida;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "status_nfse")
    private String statusNfse;

    @Column(name = "natureza_operacao")
    private Integer naturezaOperacao;

    @Column(name = "rps")
    private String rps;

    @Column(name = "inscricao_prestador")
    private String inscricaoPrestador;

    @Column(name = "tipo_doc_digitado")
    private String tipoDocDigitado;

    @Column(name = "status_aceite")
    private Integer statusAceite;

    @Column(name = "exclusao_logica")
    private String exclusaoLogica;

    @Column(name = "ajustada")
    private String ajustada;

    @Column(name = "observacao_ajuste")
    private String observacaoAjuste;

    @Column(name = "aliquota_servico")
    private String aliquotaServico;

    @Column(name = "art")
    private String art;

    @Column(name = "base_calculo")
    private String baseCalculo;

    @Column(name = "codigo_obra")
    private String codigoObra;

    @Column(name = "valor_servico")
    private String valorServico;

    @Column(name = "valor_pis")
    private String valorPis;

    @Column(name = "valor_liquido_nfse")
    private String valorLiquidoNfse;

    @Column(name = "valor_iss_retido")
    private String valorIssRetido;

    @Column(name = "valor_iss_outro_municipio")
    private String valorIssOutroMunicipio;

    @Column(name = "valor_iss")
    private String valorIss;

    @Column(name = "valor_ir")
    private String valorIr;

    @Column(name = "valor_inss")
    private String valorInss;

    @Column(name = "valor_desconto_condicionado")
    private String valorDescontoCondicionado;

    @Column(name = "valor_desconto_incondicionado")
    private String valorDescontoIncondicionado;

    @Column(name = "valor_deducoes")
    private String valorDeducoes;

    @Column(name = "valor_csll")
    private String valorCsll;

    @Column(name = "valor_credito")
    private String valorCredito;

    @Column(name = "valor_cofins")
    private String valorCofins;

    @Column(name = "outras_retencoes")
    private String outrasRetencoes;

    @Column(name = "iss_retido")
    private String issRetido;

    @Column(name = "discriminacao")
    private String discriminacao;

    @Column(name = "municipio_prestacao_servico")
    private String municipioPrestacaoServico;

    @Column(name = "pais_prestacao_servico")
    private String paisPrestacaoServico;

    @Column(name = "cod_regime_especial_tributacao")
    private Integer codRegimeEspecialTributacao;

    @Column(name = "codigo_tributacao_municipio")
    private String codigoTributacaoMunicipio;

    @Column(name = "item_lista_servico_municipio")
    private String itemListaServicoMunicipio;

    @Column(name = "cpf_cnpj_tomador")
    private String cpfCnpjTomador;

    @Column(name = "cpf_cnpj_prestador")
    private String cpfCnpjPrestador;

    @Column(name = "inscricao_municipal_tomador")
    private String inscricaoMunicipalTomador;

    @Column(name = "indicador_cpf_cnpj_tomador")
    private Integer indicadorCpfCnpjTomador;

    @Column(name = "indicador_cpf_cnpj_prestador")
    private Integer indicadorCpfCnpjPrestador;

    @Column(name = "pais_tomador")
    private String paisTomador;

    @Column(name = "pais_prestador")
    private String paisPrestador;

    @Column(name = "tipo_tomador")
    private Integer tipoTomador;

    @Column(name = "cod_cidade_tomador_ibge")
    private String codCidadeTomadorIBGE;

    @Column(name = "cod_cidade_prestador_ibge")
    private String codCidadePrestadorIBGE;

    @Column(name = "tipo_prestador")
    private Integer tipoPrestador;

    @Column(name = "numero_empenho")
    private String numeroEmpenho;

    @Column(name = "tipo_tributacao_prest_externo")
    private String tipoTributacaoPrestExterno;

    @Column(name = "cep_prestador")
    private String cepPrestador;

    @Column(name = "cep_tomador")
    private String cepTomador;

    @Column(name = "id_tipo_anexo_simples_nacional")
    private String idTipoAnexoSimplesNacional;

    @Column(name = "id_bloqueio_nfse")
    private Integer idBloqueioNfse;

    @Column(name = "id_pgdasd_2018_atividade")
    private String idPgdasd2018Atividade;

    @ManyToOne
    private InscricaoCPBS inscricaoCpbs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nfse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNfse() {
        return this.idNfse;
    }

    public Nfse idNfse(String idNfse) {
        this.setIdNfse(idNfse);
        return this;
    }

    public void setIdNfse(String idNfse) {
        this.idNfse = idNfse;
    }

    public String getCompetencia() {
        return this.competencia;
    }

    public Nfse competencia(String competencia) {
        this.setCompetencia(competencia);
        return this;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getDataEmissao() {
        return this.dataEmissao;
    }

    public Nfse dataEmissao(String dataEmissao) {
        this.setDataEmissao(dataEmissao);
        return this;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getIp() {
        return this.ip;
    }

    public Nfse ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOptanteSimplesNacional() {
        return this.optanteSimplesNacional;
    }

    public Nfse optanteSimplesNacional(String optanteSimplesNacional) {
        this.setOptanteSimplesNacional(optanteSimplesNacional);
        return this;
    }

    public void setOptanteSimplesNacional(String optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    public String getOutrasInformacoes() {
        return this.outrasInformacoes;
    }

    public Nfse outrasInformacoes(String outrasInformacoes) {
        this.setOutrasInformacoes(outrasInformacoes);
        return this;
    }

    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

    public String getIdNfseSubstituida() {
        return this.idNfseSubstituida;
    }

    public Nfse idNfseSubstituida(String idNfseSubstituida) {
        this.setIdNfseSubstituida(idNfseSubstituida);
        return this;
    }

    public void setIdNfseSubstituida(String idNfseSubstituida) {
        this.idNfseSubstituida = idNfseSubstituida;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Nfse usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getStatusNfse() {
        return this.statusNfse;
    }

    public Nfse statusNfse(String statusNfse) {
        this.setStatusNfse(statusNfse);
        return this;
    }

    public void setStatusNfse(String statusNfse) {
        this.statusNfse = statusNfse;
    }

    public Integer getNaturezaOperacao() {
        return this.naturezaOperacao;
    }

    public Nfse naturezaOperacao(Integer naturezaOperacao) {
        this.setNaturezaOperacao(naturezaOperacao);
        return this;
    }

    public void setNaturezaOperacao(Integer naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public String getRps() {
        return this.rps;
    }

    public Nfse rps(String rps) {
        this.setRps(rps);
        return this;
    }

    public void setRps(String rps) {
        this.rps = rps;
    }

    public String getInscricaoPrestador() {
        return this.inscricaoPrestador;
    }

    public Nfse inscricaoPrestador(String inscricaoPrestador) {
        this.setInscricaoPrestador(inscricaoPrestador);
        return this;
    }

    public void setInscricaoPrestador(String inscricaoPrestador) {
        this.inscricaoPrestador = inscricaoPrestador;
    }

    public String getTipoDocDigitado() {
        return this.tipoDocDigitado;
    }

    public Nfse tipoDocDigitado(String tipoDocDigitado) {
        this.setTipoDocDigitado(tipoDocDigitado);
        return this;
    }

    public void setTipoDocDigitado(String tipoDocDigitado) {
        this.tipoDocDigitado = tipoDocDigitado;
    }

    public Integer getStatusAceite() {
        return this.statusAceite;
    }

    public Nfse statusAceite(Integer statusAceite) {
        this.setStatusAceite(statusAceite);
        return this;
    }

    public void setStatusAceite(Integer statusAceite) {
        this.statusAceite = statusAceite;
    }

    public String getExclusaoLogica() {
        return this.exclusaoLogica;
    }

    public Nfse exclusaoLogica(String exclusaoLogica) {
        this.setExclusaoLogica(exclusaoLogica);
        return this;
    }

    public void setExclusaoLogica(String exclusaoLogica) {
        this.exclusaoLogica = exclusaoLogica;
    }

    public String getAjustada() {
        return this.ajustada;
    }

    public Nfse ajustada(String ajustada) {
        this.setAjustada(ajustada);
        return this;
    }

    public void setAjustada(String ajustada) {
        this.ajustada = ajustada;
    }

    public String getObservacaoAjuste() {
        return this.observacaoAjuste;
    }

    public Nfse observacaoAjuste(String observacaoAjuste) {
        this.setObservacaoAjuste(observacaoAjuste);
        return this;
    }

    public void setObservacaoAjuste(String observacaoAjuste) {
        this.observacaoAjuste = observacaoAjuste;
    }

    public String getAliquotaServico() {
        return this.aliquotaServico;
    }

    public Nfse aliquotaServico(String aliquotaServico) {
        this.setAliquotaServico(aliquotaServico);
        return this;
    }

    public void setAliquotaServico(String aliquotaServico) {
        this.aliquotaServico = aliquotaServico;
    }

    public String getArt() {
        return this.art;
    }

    public Nfse art(String art) {
        this.setArt(art);
        return this;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getBaseCalculo() {
        return this.baseCalculo;
    }

    public Nfse baseCalculo(String baseCalculo) {
        this.setBaseCalculo(baseCalculo);
        return this;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public String getCodigoObra() {
        return this.codigoObra;
    }

    public Nfse codigoObra(String codigoObra) {
        this.setCodigoObra(codigoObra);
        return this;
    }

    public void setCodigoObra(String codigoObra) {
        this.codigoObra = codigoObra;
    }

    public String getValorServico() {
        return this.valorServico;
    }

    public Nfse valorServico(String valorServico) {
        this.setValorServico(valorServico);
        return this;
    }

    public void setValorServico(String valorServico) {
        this.valorServico = valorServico;
    }

    public String getValorPis() {
        return this.valorPis;
    }

    public Nfse valorPis(String valorPis) {
        this.setValorPis(valorPis);
        return this;
    }

    public void setValorPis(String valorPis) {
        this.valorPis = valorPis;
    }

    public String getValorLiquidoNfse() {
        return this.valorLiquidoNfse;
    }

    public Nfse valorLiquidoNfse(String valorLiquidoNfse) {
        this.setValorLiquidoNfse(valorLiquidoNfse);
        return this;
    }

    public void setValorLiquidoNfse(String valorLiquidoNfse) {
        this.valorLiquidoNfse = valorLiquidoNfse;
    }

    public String getValorIssRetido() {
        return this.valorIssRetido;
    }

    public Nfse valorIssRetido(String valorIssRetido) {
        this.setValorIssRetido(valorIssRetido);
        return this;
    }

    public void setValorIssRetido(String valorIssRetido) {
        this.valorIssRetido = valorIssRetido;
    }

    public String getValorIssOutroMunicipio() {
        return this.valorIssOutroMunicipio;
    }

    public Nfse valorIssOutroMunicipio(String valorIssOutroMunicipio) {
        this.setValorIssOutroMunicipio(valorIssOutroMunicipio);
        return this;
    }

    public void setValorIssOutroMunicipio(String valorIssOutroMunicipio) {
        this.valorIssOutroMunicipio = valorIssOutroMunicipio;
    }

    public String getValorIss() {
        return this.valorIss;
    }

    public Nfse valorIss(String valorIss) {
        this.setValorIss(valorIss);
        return this;
    }

    public void setValorIss(String valorIss) {
        this.valorIss = valorIss;
    }

    public String getValorIr() {
        return this.valorIr;
    }

    public Nfse valorIr(String valorIr) {
        this.setValorIr(valorIr);
        return this;
    }

    public void setValorIr(String valorIr) {
        this.valorIr = valorIr;
    }

    public String getValorInss() {
        return this.valorInss;
    }

    public Nfse valorInss(String valorInss) {
        this.setValorInss(valorInss);
        return this;
    }

    public void setValorInss(String valorInss) {
        this.valorInss = valorInss;
    }

    public String getValorDescontoCondicionado() {
        return this.valorDescontoCondicionado;
    }

    public Nfse valorDescontoCondicionado(String valorDescontoCondicionado) {
        this.setValorDescontoCondicionado(valorDescontoCondicionado);
        return this;
    }

    public void setValorDescontoCondicionado(String valorDescontoCondicionado) {
        this.valorDescontoCondicionado = valorDescontoCondicionado;
    }

    public String getValorDescontoIncondicionado() {
        return this.valorDescontoIncondicionado;
    }

    public Nfse valorDescontoIncondicionado(String valorDescontoIncondicionado) {
        this.setValorDescontoIncondicionado(valorDescontoIncondicionado);
        return this;
    }

    public void setValorDescontoIncondicionado(String valorDescontoIncondicionado) {
        this.valorDescontoIncondicionado = valorDescontoIncondicionado;
    }

    public String getValorDeducoes() {
        return this.valorDeducoes;
    }

    public Nfse valorDeducoes(String valorDeducoes) {
        this.setValorDeducoes(valorDeducoes);
        return this;
    }

    public void setValorDeducoes(String valorDeducoes) {
        this.valorDeducoes = valorDeducoes;
    }

    public String getValorCsll() {
        return this.valorCsll;
    }

    public Nfse valorCsll(String valorCsll) {
        this.setValorCsll(valorCsll);
        return this;
    }

    public void setValorCsll(String valorCsll) {
        this.valorCsll = valorCsll;
    }

    public String getValorCredito() {
        return this.valorCredito;
    }

    public Nfse valorCredito(String valorCredito) {
        this.setValorCredito(valorCredito);
        return this;
    }

    public void setValorCredito(String valorCredito) {
        this.valorCredito = valorCredito;
    }

    public String getValorCofins() {
        return this.valorCofins;
    }

    public Nfse valorCofins(String valorCofins) {
        this.setValorCofins(valorCofins);
        return this;
    }

    public void setValorCofins(String valorCofins) {
        this.valorCofins = valorCofins;
    }

    public String getOutrasRetencoes() {
        return this.outrasRetencoes;
    }

    public Nfse outrasRetencoes(String outrasRetencoes) {
        this.setOutrasRetencoes(outrasRetencoes);
        return this;
    }

    public void setOutrasRetencoes(String outrasRetencoes) {
        this.outrasRetencoes = outrasRetencoes;
    }

    public String getIssRetido() {
        return this.issRetido;
    }

    public Nfse issRetido(String issRetido) {
        this.setIssRetido(issRetido);
        return this;
    }

    public void setIssRetido(String issRetido) {
        this.issRetido = issRetido;
    }

    public String getDiscriminacao() {
        return this.discriminacao;
    }

    public Nfse discriminacao(String discriminacao) {
        this.setDiscriminacao(discriminacao);
        return this;
    }

    public void setDiscriminacao(String discriminacao) {
        this.discriminacao = discriminacao;
    }

    public String getMunicipioPrestacaoServico() {
        return this.municipioPrestacaoServico;
    }

    public Nfse municipioPrestacaoServico(String municipioPrestacaoServico) {
        this.setMunicipioPrestacaoServico(municipioPrestacaoServico);
        return this;
    }

    public void setMunicipioPrestacaoServico(String municipioPrestacaoServico) {
        this.municipioPrestacaoServico = municipioPrestacaoServico;
    }

    public String getPaisPrestacaoServico() {
        return this.paisPrestacaoServico;
    }

    public Nfse paisPrestacaoServico(String paisPrestacaoServico) {
        this.setPaisPrestacaoServico(paisPrestacaoServico);
        return this;
    }

    public void setPaisPrestacaoServico(String paisPrestacaoServico) {
        this.paisPrestacaoServico = paisPrestacaoServico;
    }

    public Integer getCodRegimeEspecialTributacao() {
        return this.codRegimeEspecialTributacao;
    }

    public Nfse codRegimeEspecialTributacao(Integer codRegimeEspecialTributacao) {
        this.setCodRegimeEspecialTributacao(codRegimeEspecialTributacao);
        return this;
    }

    public void setCodRegimeEspecialTributacao(Integer codRegimeEspecialTributacao) {
        this.codRegimeEspecialTributacao = codRegimeEspecialTributacao;
    }

    public String getCodigoTributacaoMunicipio() {
        return this.codigoTributacaoMunicipio;
    }

    public Nfse codigoTributacaoMunicipio(String codigoTributacaoMunicipio) {
        this.setCodigoTributacaoMunicipio(codigoTributacaoMunicipio);
        return this;
    }

    public void setCodigoTributacaoMunicipio(String codigoTributacaoMunicipio) {
        this.codigoTributacaoMunicipio = codigoTributacaoMunicipio;
    }

    public String getItemListaServicoMunicipio() {
        return this.itemListaServicoMunicipio;
    }

    public Nfse itemListaServicoMunicipio(String itemListaServicoMunicipio) {
        this.setItemListaServicoMunicipio(itemListaServicoMunicipio);
        return this;
    }

    public void setItemListaServicoMunicipio(String itemListaServicoMunicipio) {
        this.itemListaServicoMunicipio = itemListaServicoMunicipio;
    }

    public String getCpfCnpjTomador() {
        return this.cpfCnpjTomador;
    }

    public Nfse cpfCnpjTomador(String cpfCnpjTomador) {
        this.setCpfCnpjTomador(cpfCnpjTomador);
        return this;
    }

    public void setCpfCnpjTomador(String cpfCnpjTomador) {
        this.cpfCnpjTomador = cpfCnpjTomador;
    }

    public String getCpfCnpjPrestador() {
        return this.cpfCnpjPrestador;
    }

    public Nfse cpfCnpjPrestador(String cpfCnpjPrestador) {
        this.setCpfCnpjPrestador(cpfCnpjPrestador);
        return this;
    }

    public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
        this.cpfCnpjPrestador = cpfCnpjPrestador;
    }

    public String getInscricaoMunicipalTomador() {
        return this.inscricaoMunicipalTomador;
    }

    public Nfse inscricaoMunicipalTomador(String inscricaoMunicipalTomador) {
        this.setInscricaoMunicipalTomador(inscricaoMunicipalTomador);
        return this;
    }

    public void setInscricaoMunicipalTomador(String inscricaoMunicipalTomador) {
        this.inscricaoMunicipalTomador = inscricaoMunicipalTomador;
    }

    public Integer getIndicadorCpfCnpjTomador() {
        return this.indicadorCpfCnpjTomador;
    }

    public Nfse indicadorCpfCnpjTomador(Integer indicadorCpfCnpjTomador) {
        this.setIndicadorCpfCnpjTomador(indicadorCpfCnpjTomador);
        return this;
    }

    public void setIndicadorCpfCnpjTomador(Integer indicadorCpfCnpjTomador) {
        this.indicadorCpfCnpjTomador = indicadorCpfCnpjTomador;
    }

    public Integer getIndicadorCpfCnpjPrestador() {
        return this.indicadorCpfCnpjPrestador;
    }

    public Nfse indicadorCpfCnpjPrestador(Integer indicadorCpfCnpjPrestador) {
        this.setIndicadorCpfCnpjPrestador(indicadorCpfCnpjPrestador);
        return this;
    }

    public void setIndicadorCpfCnpjPrestador(Integer indicadorCpfCnpjPrestador) {
        this.indicadorCpfCnpjPrestador = indicadorCpfCnpjPrestador;
    }

    public String getPaisTomador() {
        return this.paisTomador;
    }

    public Nfse paisTomador(String paisTomador) {
        this.setPaisTomador(paisTomador);
        return this;
    }

    public void setPaisTomador(String paisTomador) {
        this.paisTomador = paisTomador;
    }

    public String getPaisPrestador() {
        return this.paisPrestador;
    }

    public Nfse paisPrestador(String paisPrestador) {
        this.setPaisPrestador(paisPrestador);
        return this;
    }

    public void setPaisPrestador(String paisPrestador) {
        this.paisPrestador = paisPrestador;
    }

    public Integer getTipoTomador() {
        return this.tipoTomador;
    }

    public Nfse tipoTomador(Integer tipoTomador) {
        this.setTipoTomador(tipoTomador);
        return this;
    }

    public void setTipoTomador(Integer tipoTomador) {
        this.tipoTomador = tipoTomador;
    }

    public String getCodCidadeTomadorIBGE() {
        return this.codCidadeTomadorIBGE;
    }

    public Nfse codCidadeTomadorIBGE(String codCidadeTomadorIBGE) {
        this.setCodCidadeTomadorIBGE(codCidadeTomadorIBGE);
        return this;
    }

    public void setCodCidadeTomadorIBGE(String codCidadeTomadorIBGE) {
        this.codCidadeTomadorIBGE = codCidadeTomadorIBGE;
    }

    public String getCodCidadePrestadorIBGE() {
        return this.codCidadePrestadorIBGE;
    }

    public Nfse codCidadePrestadorIBGE(String codCidadePrestadorIBGE) {
        this.setCodCidadePrestadorIBGE(codCidadePrestadorIBGE);
        return this;
    }

    public void setCodCidadePrestadorIBGE(String codCidadePrestadorIBGE) {
        this.codCidadePrestadorIBGE = codCidadePrestadorIBGE;
    }

    public Integer getTipoPrestador() {
        return this.tipoPrestador;
    }

    public Nfse tipoPrestador(Integer tipoPrestador) {
        this.setTipoPrestador(tipoPrestador);
        return this;
    }

    public void setTipoPrestador(Integer tipoPrestador) {
        this.tipoPrestador = tipoPrestador;
    }

    public String getNumeroEmpenho() {
        return this.numeroEmpenho;
    }

    public Nfse numeroEmpenho(String numeroEmpenho) {
        this.setNumeroEmpenho(numeroEmpenho);
        return this;
    }

    public void setNumeroEmpenho(String numeroEmpenho) {
        this.numeroEmpenho = numeroEmpenho;
    }

    public String getTipoTributacaoPrestExterno() {
        return this.tipoTributacaoPrestExterno;
    }

    public Nfse tipoTributacaoPrestExterno(String tipoTributacaoPrestExterno) {
        this.setTipoTributacaoPrestExterno(tipoTributacaoPrestExterno);
        return this;
    }

    public void setTipoTributacaoPrestExterno(String tipoTributacaoPrestExterno) {
        this.tipoTributacaoPrestExterno = tipoTributacaoPrestExterno;
    }

    public String getCepPrestador() {
        return this.cepPrestador;
    }

    public Nfse cepPrestador(String cepPrestador) {
        this.setCepPrestador(cepPrestador);
        return this;
    }

    public void setCepPrestador(String cepPrestador) {
        this.cepPrestador = cepPrestador;
    }

    public String getCepTomador() {
        return this.cepTomador;
    }

    public Nfse cepTomador(String cepTomador) {
        this.setCepTomador(cepTomador);
        return this;
    }

    public void setCepTomador(String cepTomador) {
        this.cepTomador = cepTomador;
    }

    public String getIdTipoAnexoSimplesNacional() {
        return this.idTipoAnexoSimplesNacional;
    }

    public Nfse idTipoAnexoSimplesNacional(String idTipoAnexoSimplesNacional) {
        this.setIdTipoAnexoSimplesNacional(idTipoAnexoSimplesNacional);
        return this;
    }

    public void setIdTipoAnexoSimplesNacional(String idTipoAnexoSimplesNacional) {
        this.idTipoAnexoSimplesNacional = idTipoAnexoSimplesNacional;
    }

    public Integer getIdBloqueioNfse() {
        return this.idBloqueioNfse;
    }

    public Nfse idBloqueioNfse(Integer idBloqueioNfse) {
        this.setIdBloqueioNfse(idBloqueioNfse);
        return this;
    }

    public void setIdBloqueioNfse(Integer idBloqueioNfse) {
        this.idBloqueioNfse = idBloqueioNfse;
    }

    public String getIdPgdasd2018Atividade() {
        return this.idPgdasd2018Atividade;
    }

    public Nfse idPgdasd2018Atividade(String idPgdasd2018Atividade) {
        this.setIdPgdasd2018Atividade(idPgdasd2018Atividade);
        return this;
    }

    public void setIdPgdasd2018Atividade(String idPgdasd2018Atividade) {
        this.idPgdasd2018Atividade = idPgdasd2018Atividade;
    }

    public InscricaoCPBS getInscricaoCpbs() {
        return this.inscricaoCpbs;
    }

    public void setInscricaoCpbs(InscricaoCPBS inscricaoCPBS) {
        this.inscricaoCpbs = inscricaoCPBS;
    }

    public Nfse inscricaoCpbs(InscricaoCPBS inscricaoCPBS) {
        this.setInscricaoCpbs(inscricaoCPBS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nfse)) {
            return false;
        }
        return id != null && id.equals(((Nfse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nfse{" +
            "id=" + getId() +
            ", idNfse='" + getIdNfse() + "'" +
            ", competencia='" + getCompetencia() + "'" +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", ip='" + getIp() + "'" +
            ", optanteSimplesNacional='" + getOptanteSimplesNacional() + "'" +
            ", outrasInformacoes='" + getOutrasInformacoes() + "'" +
            ", idNfseSubstituida='" + getIdNfseSubstituida() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", statusNfse='" + getStatusNfse() + "'" +
            ", naturezaOperacao=" + getNaturezaOperacao() +
            ", rps='" + getRps() + "'" +
            ", inscricaoPrestador='" + getInscricaoPrestador() + "'" +
            ", tipoDocDigitado='" + getTipoDocDigitado() + "'" +
            ", statusAceite=" + getStatusAceite() +
            ", exclusaoLogica='" + getExclusaoLogica() + "'" +
            ", ajustada='" + getAjustada() + "'" +
            ", observacaoAjuste='" + getObservacaoAjuste() + "'" +
            ", aliquotaServico='" + getAliquotaServico() + "'" +
            ", art='" + getArt() + "'" +
            ", baseCalculo='" + getBaseCalculo() + "'" +
            ", codigoObra='" + getCodigoObra() + "'" +
            ", valorServico='" + getValorServico() + "'" +
            ", valorPis='" + getValorPis() + "'" +
            ", valorLiquidoNfse='" + getValorLiquidoNfse() + "'" +
            ", valorIssRetido='" + getValorIssRetido() + "'" +
            ", valorIssOutroMunicipio='" + getValorIssOutroMunicipio() + "'" +
            ", valorIss='" + getValorIss() + "'" +
            ", valorIr='" + getValorIr() + "'" +
            ", valorInss='" + getValorInss() + "'" +
            ", valorDescontoCondicionado='" + getValorDescontoCondicionado() + "'" +
            ", valorDescontoIncondicionado='" + getValorDescontoIncondicionado() + "'" +
            ", valorDeducoes='" + getValorDeducoes() + "'" +
            ", valorCsll='" + getValorCsll() + "'" +
            ", valorCredito='" + getValorCredito() + "'" +
            ", valorCofins='" + getValorCofins() + "'" +
            ", outrasRetencoes='" + getOutrasRetencoes() + "'" +
            ", issRetido='" + getIssRetido() + "'" +
            ", discriminacao='" + getDiscriminacao() + "'" +
            ", municipioPrestacaoServico='" + getMunicipioPrestacaoServico() + "'" +
            ", paisPrestacaoServico='" + getPaisPrestacaoServico() + "'" +
            ", codRegimeEspecialTributacao=" + getCodRegimeEspecialTributacao() +
            ", codigoTributacaoMunicipio='" + getCodigoTributacaoMunicipio() + "'" +
            ", itemListaServicoMunicipio='" + getItemListaServicoMunicipio() + "'" +
            ", cpfCnpjTomador='" + getCpfCnpjTomador() + "'" +
            ", cpfCnpjPrestador='" + getCpfCnpjPrestador() + "'" +
            ", inscricaoMunicipalTomador='" + getInscricaoMunicipalTomador() + "'" +
            ", indicadorCpfCnpjTomador=" + getIndicadorCpfCnpjTomador() +
            ", indicadorCpfCnpjPrestador=" + getIndicadorCpfCnpjPrestador() +
            ", paisTomador='" + getPaisTomador() + "'" +
            ", paisPrestador='" + getPaisPrestador() + "'" +
            ", tipoTomador=" + getTipoTomador() +
            ", codCidadeTomadorIBGE='" + getCodCidadeTomadorIBGE() + "'" +
            ", codCidadePrestadorIBGE='" + getCodCidadePrestadorIBGE() + "'" +
            ", tipoPrestador=" + getTipoPrestador() +
            ", numeroEmpenho='" + getNumeroEmpenho() + "'" +
            ", tipoTributacaoPrestExterno='" + getTipoTributacaoPrestExterno() + "'" +
            ", cepPrestador='" + getCepPrestador() + "'" +
            ", cepTomador='" + getCepTomador() + "'" +
            ", idTipoAnexoSimplesNacional='" + getIdTipoAnexoSimplesNacional() + "'" +
            ", idBloqueioNfse=" + getIdBloqueioNfse() +
            ", idPgdasd2018Atividade='" + getIdPgdasd2018Atividade() + "'" +
            "}";
    }
}
