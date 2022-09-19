package br.com.sefin.service.dto;

import br.com.sefin.domain.enumeration.CPBSSituacaoEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.InscricaoCPBS} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InscricaoCPBSDTO implements Serializable {

    private Long id;

    private String inscricao;

    private String nome;

    private String nomeFantasia;

    private String numDocumento;

    private CPBSSituacaoEnum situacao;

    private Boolean optanteSimplesNacional;

    private String codigoNaturezaJuridica;

    private String descricaoNaturezaJuridica;

    private String tipoLogradouro;

    private String tituloLogradouro;

    private String logradouro;

    private String numero;

    private String complemento;

    private String nomeCidade;

    private String nomeBairro;

    private String numeroCep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public CPBSSituacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(CPBSSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public Boolean getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    public void setOptanteSimplesNacional(Boolean optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    public String getCodigoNaturezaJuridica() {
        return codigoNaturezaJuridica;
    }

    public void setCodigoNaturezaJuridica(String codigoNaturezaJuridica) {
        this.codigoNaturezaJuridica = codigoNaturezaJuridica;
    }

    public String getDescricaoNaturezaJuridica() {
        return descricaoNaturezaJuridica;
    }

    public void setDescricaoNaturezaJuridica(String descricaoNaturezaJuridica) {
        this.descricaoNaturezaJuridica = descricaoNaturezaJuridica;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getTituloLogradouro() {
        return tituloLogradouro;
    }

    public void setTituloLogradouro(String tituloLogradouro) {
        this.tituloLogradouro = tituloLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeBairro() {
        return nomeBairro;
    }

    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

    public String getNumeroCep() {
        return numeroCep;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscricaoCPBSDTO)) {
            return false;
        }

        InscricaoCPBSDTO inscricaoCPBSDTO = (InscricaoCPBSDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inscricaoCPBSDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscricaoCPBSDTO{" +
            "id=" + getId() +
            ", inscricao='" + getInscricao() + "'" +
            ", nome='" + getNome() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", numDocumento='" + getNumDocumento() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", optanteSimplesNacional='" + getOptanteSimplesNacional() + "'" +
            ", codigoNaturezaJuridica='" + getCodigoNaturezaJuridica() + "'" +
            ", descricaoNaturezaJuridica='" + getDescricaoNaturezaJuridica() + "'" +
            ", tipoLogradouro='" + getTipoLogradouro() + "'" +
            ", tituloLogradouro='" + getTituloLogradouro() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", nomeCidade='" + getNomeCidade() + "'" +
            ", nomeBairro='" + getNomeBairro() + "'" +
            ", numeroCep='" + getNumeroCep() + "'" +
            "}";
    }
}
