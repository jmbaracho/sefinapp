package br.com.sefin.domain;

import br.com.sefin.domain.enumeration.CPBSSituacaoEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InscricaoCPBS.
 */
@Entity
@Table(name = "inscricao_cpbs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InscricaoCPBS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "inscricao")
    private String inscricao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "num_documento")
    private String numDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private CPBSSituacaoEnum situacao;

    @Column(name = "optante_simples_nacional")
    private Boolean optanteSimplesNacional;

    @Column(name = "codigo_natureza_juridica")
    private String codigoNaturezaJuridica;

    @Column(name = "descricao_natureza_juridica")
    private String descricaoNaturezaJuridica;

    @Column(name = "tipo_logradouro")
    private String tipoLogradouro;

    @Column(name = "titulo_logradouro")
    private String tituloLogradouro;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "nome_cidade")
    private String nomeCidade;

    @Column(name = "nome_bairro")
    private String nomeBairro;

    @Column(name = "numero_cep")
    private String numeroCep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InscricaoCPBS id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInscricao() {
        return this.inscricao;
    }

    public InscricaoCPBS inscricao(String inscricao) {
        this.setInscricao(inscricao);
        return this;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getNome() {
        return this.nome;
    }

    public InscricaoCPBS nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public InscricaoCPBS nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNumDocumento() {
        return this.numDocumento;
    }

    public InscricaoCPBS numDocumento(String numDocumento) {
        this.setNumDocumento(numDocumento);
        return this;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public CPBSSituacaoEnum getSituacao() {
        return this.situacao;
    }

    public InscricaoCPBS situacao(CPBSSituacaoEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(CPBSSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public Boolean getOptanteSimplesNacional() {
        return this.optanteSimplesNacional;
    }

    public InscricaoCPBS optanteSimplesNacional(Boolean optanteSimplesNacional) {
        this.setOptanteSimplesNacional(optanteSimplesNacional);
        return this;
    }

    public void setOptanteSimplesNacional(Boolean optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    public String getCodigoNaturezaJuridica() {
        return this.codigoNaturezaJuridica;
    }

    public InscricaoCPBS codigoNaturezaJuridica(String codigoNaturezaJuridica) {
        this.setCodigoNaturezaJuridica(codigoNaturezaJuridica);
        return this;
    }

    public void setCodigoNaturezaJuridica(String codigoNaturezaJuridica) {
        this.codigoNaturezaJuridica = codigoNaturezaJuridica;
    }

    public String getDescricaoNaturezaJuridica() {
        return this.descricaoNaturezaJuridica;
    }

    public InscricaoCPBS descricaoNaturezaJuridica(String descricaoNaturezaJuridica) {
        this.setDescricaoNaturezaJuridica(descricaoNaturezaJuridica);
        return this;
    }

    public void setDescricaoNaturezaJuridica(String descricaoNaturezaJuridica) {
        this.descricaoNaturezaJuridica = descricaoNaturezaJuridica;
    }

    public String getTipoLogradouro() {
        return this.tipoLogradouro;
    }

    public InscricaoCPBS tipoLogradouro(String tipoLogradouro) {
        this.setTipoLogradouro(tipoLogradouro);
        return this;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getTituloLogradouro() {
        return this.tituloLogradouro;
    }

    public InscricaoCPBS tituloLogradouro(String tituloLogradouro) {
        this.setTituloLogradouro(tituloLogradouro);
        return this;
    }

    public void setTituloLogradouro(String tituloLogradouro) {
        this.tituloLogradouro = tituloLogradouro;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public InscricaoCPBS logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public InscricaoCPBS numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public InscricaoCPBS complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNomeCidade() {
        return this.nomeCidade;
    }

    public InscricaoCPBS nomeCidade(String nomeCidade) {
        this.setNomeCidade(nomeCidade);
        return this;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeBairro() {
        return this.nomeBairro;
    }

    public InscricaoCPBS nomeBairro(String nomeBairro) {
        this.setNomeBairro(nomeBairro);
        return this;
    }

    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

    public String getNumeroCep() {
        return this.numeroCep;
    }

    public InscricaoCPBS numeroCep(String numeroCep) {
        this.setNumeroCep(numeroCep);
        return this;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscricaoCPBS)) {
            return false;
        }
        return id != null && id.equals(((InscricaoCPBS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscricaoCPBS{" +
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
