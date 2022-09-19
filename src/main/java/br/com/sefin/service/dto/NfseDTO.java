package br.com.sefin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.Nfse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NfseDTO implements Serializable {

    private Long id;

    private String idNfse;

    private String competencia;

    private String dataEmissao;

    private String ip;

    private String optanteSimplesNacional;

    private String outrasInformacoes;

    private String idNfseSubstituida;

    private String usuario;

    private String statusNfse;

    private Integer naturezaOperacao;

    private String rps;

    private String inscricaoPrestador;

    private String tipoDocDigitado;

    private Integer statusAceite;

    private String exclusaoLogica;

    private String ajustada;

    private String observacaoAjuste;

    private String aliquotaServico;

    private String art;

    private String baseCalculo;

    private String codigoObra;

    private String valorServico;

    private String valorPis;

    private String valorLiquidoNfse;

    private String valorIssRetido;

    private String valorIssOutroMunicipio;

    private String valorIss;

    private String valorIr;

    private String valorInss;

    private String valorDescontoCondicionado;

    private String valorDescontoIncondicionado;

    private String valorDeducoes;

    private String valorCsll;

    private String valorCredito;

    private String valorCofins;

    private String outrasRetencoes;

    private String issRetido;

    private String discriminacao;

    private String municipioPrestacaoServico;

    private String paisPrestacaoServico;

    private Integer codRegimeEspecialTributacao;

    private String codigoTributacaoMunicipio;

    private String itemListaServicoMunicipio;

    private String cpfCnpjTomador;

    private String cpfCnpjPrestador;

    private String inscricaoMunicipalTomador;

    private Integer indicadorCpfCnpjTomador;

    private Integer indicadorCpfCnpjPrestador;

    private String paisTomador;

    private String paisPrestador;

    private Integer tipoTomador;

    private String codCidadeTomadorIBGE;

    private String codCidadePrestadorIBGE;

    private Integer tipoPrestador;

    private String numeroEmpenho;

    private String tipoTributacaoPrestExterno;

    private String cepPrestador;

    private String cepTomador;

    private String idTipoAnexoSimplesNacional;

    private Integer idBloqueioNfse;

    private String idPgdasd2018Atividade;

    private InscricaoCPBSDTO inscricaoCpbs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNfse() {
        return idNfse;
    }

    public void setIdNfse(String idNfse) {
        this.idNfse = idNfse;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    public void setOptanteSimplesNacional(String optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

    public String getIdNfseSubstituida() {
        return idNfseSubstituida;
    }

    public void setIdNfseSubstituida(String idNfseSubstituida) {
        this.idNfseSubstituida = idNfseSubstituida;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getStatusNfse() {
        return statusNfse;
    }

    public void setStatusNfse(String statusNfse) {
        this.statusNfse = statusNfse;
    }

    public Integer getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(Integer naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public String getRps() {
        return rps;
    }

    public void setRps(String rps) {
        this.rps = rps;
    }

    public String getInscricaoPrestador() {
        return inscricaoPrestador;
    }

    public void setInscricaoPrestador(String inscricaoPrestador) {
        this.inscricaoPrestador = inscricaoPrestador;
    }

    public String getTipoDocDigitado() {
        return tipoDocDigitado;
    }

    public void setTipoDocDigitado(String tipoDocDigitado) {
        this.tipoDocDigitado = tipoDocDigitado;
    }

    public Integer getStatusAceite() {
        return statusAceite;
    }

    public void setStatusAceite(Integer statusAceite) {
        this.statusAceite = statusAceite;
    }

    public String getExclusaoLogica() {
        return exclusaoLogica;
    }

    public void setExclusaoLogica(String exclusaoLogica) {
        this.exclusaoLogica = exclusaoLogica;
    }

    public String getAjustada() {
        return ajustada;
    }

    public void setAjustada(String ajustada) {
        this.ajustada = ajustada;
    }

    public String getObservacaoAjuste() {
        return observacaoAjuste;
    }

    public void setObservacaoAjuste(String observacaoAjuste) {
        this.observacaoAjuste = observacaoAjuste;
    }

    public String getAliquotaServico() {
        return aliquotaServico;
    }

    public void setAliquotaServico(String aliquotaServico) {
        this.aliquotaServico = aliquotaServico;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public String getCodigoObra() {
        return codigoObra;
    }

    public void setCodigoObra(String codigoObra) {
        this.codigoObra = codigoObra;
    }

    public String getValorServico() {
        return valorServico;
    }

    public void setValorServico(String valorServico) {
        this.valorServico = valorServico;
    }

    public String getValorPis() {
        return valorPis;
    }

    public void setValorPis(String valorPis) {
        this.valorPis = valorPis;
    }

    public String getValorLiquidoNfse() {
        return valorLiquidoNfse;
    }

    public void setValorLiquidoNfse(String valorLiquidoNfse) {
        this.valorLiquidoNfse = valorLiquidoNfse;
    }

    public String getValorIssRetido() {
        return valorIssRetido;
    }

    public void setValorIssRetido(String valorIssRetido) {
        this.valorIssRetido = valorIssRetido;
    }

    public String getValorIssOutroMunicipio() {
        return valorIssOutroMunicipio;
    }

    public void setValorIssOutroMunicipio(String valorIssOutroMunicipio) {
        this.valorIssOutroMunicipio = valorIssOutroMunicipio;
    }

    public String getValorIss() {
        return valorIss;
    }

    public void setValorIss(String valorIss) {
        this.valorIss = valorIss;
    }

    public String getValorIr() {
        return valorIr;
    }

    public void setValorIr(String valorIr) {
        this.valorIr = valorIr;
    }

    public String getValorInss() {
        return valorInss;
    }

    public void setValorInss(String valorInss) {
        this.valorInss = valorInss;
    }

    public String getValorDescontoCondicionado() {
        return valorDescontoCondicionado;
    }

    public void setValorDescontoCondicionado(String valorDescontoCondicionado) {
        this.valorDescontoCondicionado = valorDescontoCondicionado;
    }

    public String getValorDescontoIncondicionado() {
        return valorDescontoIncondicionado;
    }

    public void setValorDescontoIncondicionado(String valorDescontoIncondicionado) {
        this.valorDescontoIncondicionado = valorDescontoIncondicionado;
    }

    public String getValorDeducoes() {
        return valorDeducoes;
    }

    public void setValorDeducoes(String valorDeducoes) {
        this.valorDeducoes = valorDeducoes;
    }

    public String getValorCsll() {
        return valorCsll;
    }

    public void setValorCsll(String valorCsll) {
        this.valorCsll = valorCsll;
    }

    public String getValorCredito() {
        return valorCredito;
    }

    public void setValorCredito(String valorCredito) {
        this.valorCredito = valorCredito;
    }

    public String getValorCofins() {
        return valorCofins;
    }

    public void setValorCofins(String valorCofins) {
        this.valorCofins = valorCofins;
    }

    public String getOutrasRetencoes() {
        return outrasRetencoes;
    }

    public void setOutrasRetencoes(String outrasRetencoes) {
        this.outrasRetencoes = outrasRetencoes;
    }

    public String getIssRetido() {
        return issRetido;
    }

    public void setIssRetido(String issRetido) {
        this.issRetido = issRetido;
    }

    public String getDiscriminacao() {
        return discriminacao;
    }

    public void setDiscriminacao(String discriminacao) {
        this.discriminacao = discriminacao;
    }

    public String getMunicipioPrestacaoServico() {
        return municipioPrestacaoServico;
    }

    public void setMunicipioPrestacaoServico(String municipioPrestacaoServico) {
        this.municipioPrestacaoServico = municipioPrestacaoServico;
    }

    public String getPaisPrestacaoServico() {
        return paisPrestacaoServico;
    }

    public void setPaisPrestacaoServico(String paisPrestacaoServico) {
        this.paisPrestacaoServico = paisPrestacaoServico;
    }

    public Integer getCodRegimeEspecialTributacao() {
        return codRegimeEspecialTributacao;
    }

    public void setCodRegimeEspecialTributacao(Integer codRegimeEspecialTributacao) {
        this.codRegimeEspecialTributacao = codRegimeEspecialTributacao;
    }

    public String getCodigoTributacaoMunicipio() {
        return codigoTributacaoMunicipio;
    }

    public void setCodigoTributacaoMunicipio(String codigoTributacaoMunicipio) {
        this.codigoTributacaoMunicipio = codigoTributacaoMunicipio;
    }

    public String getItemListaServicoMunicipio() {
        return itemListaServicoMunicipio;
    }

    public void setItemListaServicoMunicipio(String itemListaServicoMunicipio) {
        this.itemListaServicoMunicipio = itemListaServicoMunicipio;
    }

    public String getCpfCnpjTomador() {
        return cpfCnpjTomador;
    }

    public void setCpfCnpjTomador(String cpfCnpjTomador) {
        this.cpfCnpjTomador = cpfCnpjTomador;
    }

    public String getCpfCnpjPrestador() {
        return cpfCnpjPrestador;
    }

    public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
        this.cpfCnpjPrestador = cpfCnpjPrestador;
    }

    public String getInscricaoMunicipalTomador() {
        return inscricaoMunicipalTomador;
    }

    public void setInscricaoMunicipalTomador(String inscricaoMunicipalTomador) {
        this.inscricaoMunicipalTomador = inscricaoMunicipalTomador;
    }

    public Integer getIndicadorCpfCnpjTomador() {
        return indicadorCpfCnpjTomador;
    }

    public void setIndicadorCpfCnpjTomador(Integer indicadorCpfCnpjTomador) {
        this.indicadorCpfCnpjTomador = indicadorCpfCnpjTomador;
    }

    public Integer getIndicadorCpfCnpjPrestador() {
        return indicadorCpfCnpjPrestador;
    }

    public void setIndicadorCpfCnpjPrestador(Integer indicadorCpfCnpjPrestador) {
        this.indicadorCpfCnpjPrestador = indicadorCpfCnpjPrestador;
    }

    public String getPaisTomador() {
        return paisTomador;
    }

    public void setPaisTomador(String paisTomador) {
        this.paisTomador = paisTomador;
    }

    public String getPaisPrestador() {
        return paisPrestador;
    }

    public void setPaisPrestador(String paisPrestador) {
        this.paisPrestador = paisPrestador;
    }

    public Integer getTipoTomador() {
        return tipoTomador;
    }

    public void setTipoTomador(Integer tipoTomador) {
        this.tipoTomador = tipoTomador;
    }

    public String getCodCidadeTomadorIBGE() {
        return codCidadeTomadorIBGE;
    }

    public void setCodCidadeTomadorIBGE(String codCidadeTomadorIBGE) {
        this.codCidadeTomadorIBGE = codCidadeTomadorIBGE;
    }

    public String getCodCidadePrestadorIBGE() {
        return codCidadePrestadorIBGE;
    }

    public void setCodCidadePrestadorIBGE(String codCidadePrestadorIBGE) {
        this.codCidadePrestadorIBGE = codCidadePrestadorIBGE;
    }

    public Integer getTipoPrestador() {
        return tipoPrestador;
    }

    public void setTipoPrestador(Integer tipoPrestador) {
        this.tipoPrestador = tipoPrestador;
    }

    public String getNumeroEmpenho() {
        return numeroEmpenho;
    }

    public void setNumeroEmpenho(String numeroEmpenho) {
        this.numeroEmpenho = numeroEmpenho;
    }

    public String getTipoTributacaoPrestExterno() {
        return tipoTributacaoPrestExterno;
    }

    public void setTipoTributacaoPrestExterno(String tipoTributacaoPrestExterno) {
        this.tipoTributacaoPrestExterno = tipoTributacaoPrestExterno;
    }

    public String getCepPrestador() {
        return cepPrestador;
    }

    public void setCepPrestador(String cepPrestador) {
        this.cepPrestador = cepPrestador;
    }

    public String getCepTomador() {
        return cepTomador;
    }

    public void setCepTomador(String cepTomador) {
        this.cepTomador = cepTomador;
    }

    public String getIdTipoAnexoSimplesNacional() {
        return idTipoAnexoSimplesNacional;
    }

    public void setIdTipoAnexoSimplesNacional(String idTipoAnexoSimplesNacional) {
        this.idTipoAnexoSimplesNacional = idTipoAnexoSimplesNacional;
    }

    public Integer getIdBloqueioNfse() {
        return idBloqueioNfse;
    }

    public void setIdBloqueioNfse(Integer idBloqueioNfse) {
        this.idBloqueioNfse = idBloqueioNfse;
    }

    public String getIdPgdasd2018Atividade() {
        return idPgdasd2018Atividade;
    }

    public void setIdPgdasd2018Atividade(String idPgdasd2018Atividade) {
        this.idPgdasd2018Atividade = idPgdasd2018Atividade;
    }

    public InscricaoCPBSDTO getInscricaoCpbs() {
        return inscricaoCpbs;
    }

    public void setInscricaoCpbs(InscricaoCPBSDTO inscricaoCpbs) {
        this.inscricaoCpbs = inscricaoCpbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NfseDTO)) {
            return false;
        }

        NfseDTO nfseDTO = (NfseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nfseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NfseDTO{" +
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
            ", inscricaoCpbs=" + getInscricaoCpbs() +
            "}";
    }
}
