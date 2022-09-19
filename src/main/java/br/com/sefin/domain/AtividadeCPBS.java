package br.com.sefin.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AtividadeCPBS.
 */
@Entity
@Table(name = "atividade_cpbs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AtividadeCPBS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_atividade_cpbs")
    private String idAtividadeCpbs;

    @Column(name = "cnae")
    private String cnae;

    @Column(name = "descricao_cnae")
    private String descricaoCnae;

    @Column(name = "principal")
    private Boolean principal;

    @Column(name = "id_segmento_iss")
    private String idSegmentoIss;

    @Column(name = "descricao_segmento")
    private String descricaoSegmento;

    @Column(name = "id_lista_ser_cpbs")
    private String idListaSerCpbs;

    @Column(name = "codigo_lista_ser_cpbs")
    private String codigoListaSerCpbs;

    @Column(name = "descricao_lista_ser_cpbs")
    private String descricaoListaSerCpbs;

    @ManyToOne
    private InscricaoCPBS inscricaoCpbs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AtividadeCPBS id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAtividadeCpbs() {
        return this.idAtividadeCpbs;
    }

    public AtividadeCPBS idAtividadeCpbs(String idAtividadeCpbs) {
        this.setIdAtividadeCpbs(idAtividadeCpbs);
        return this;
    }

    public void setIdAtividadeCpbs(String idAtividadeCpbs) {
        this.idAtividadeCpbs = idAtividadeCpbs;
    }

    public String getCnae() {
        return this.cnae;
    }

    public AtividadeCPBS cnae(String cnae) {
        this.setCnae(cnae);
        return this;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public String getDescricaoCnae() {
        return this.descricaoCnae;
    }

    public AtividadeCPBS descricaoCnae(String descricaoCnae) {
        this.setDescricaoCnae(descricaoCnae);
        return this;
    }

    public void setDescricaoCnae(String descricaoCnae) {
        this.descricaoCnae = descricaoCnae;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public AtividadeCPBS principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getIdSegmentoIss() {
        return this.idSegmentoIss;
    }

    public AtividadeCPBS idSegmentoIss(String idSegmentoIss) {
        this.setIdSegmentoIss(idSegmentoIss);
        return this;
    }

    public void setIdSegmentoIss(String idSegmentoIss) {
        this.idSegmentoIss = idSegmentoIss;
    }

    public String getDescricaoSegmento() {
        return this.descricaoSegmento;
    }

    public AtividadeCPBS descricaoSegmento(String descricaoSegmento) {
        this.setDescricaoSegmento(descricaoSegmento);
        return this;
    }

    public void setDescricaoSegmento(String descricaoSegmento) {
        this.descricaoSegmento = descricaoSegmento;
    }

    public String getIdListaSerCpbs() {
        return this.idListaSerCpbs;
    }

    public AtividadeCPBS idListaSerCpbs(String idListaSerCpbs) {
        this.setIdListaSerCpbs(idListaSerCpbs);
        return this;
    }

    public void setIdListaSerCpbs(String idListaSerCpbs) {
        this.idListaSerCpbs = idListaSerCpbs;
    }

    public String getCodigoListaSerCpbs() {
        return this.codigoListaSerCpbs;
    }

    public AtividadeCPBS codigoListaSerCpbs(String codigoListaSerCpbs) {
        this.setCodigoListaSerCpbs(codigoListaSerCpbs);
        return this;
    }

    public void setCodigoListaSerCpbs(String codigoListaSerCpbs) {
        this.codigoListaSerCpbs = codigoListaSerCpbs;
    }

    public String getDescricaoListaSerCpbs() {
        return this.descricaoListaSerCpbs;
    }

    public AtividadeCPBS descricaoListaSerCpbs(String descricaoListaSerCpbs) {
        this.setDescricaoListaSerCpbs(descricaoListaSerCpbs);
        return this;
    }

    public void setDescricaoListaSerCpbs(String descricaoListaSerCpbs) {
        this.descricaoListaSerCpbs = descricaoListaSerCpbs;
    }

    public InscricaoCPBS getInscricaoCpbs() {
        return this.inscricaoCpbs;
    }

    public void setInscricaoCpbs(InscricaoCPBS inscricaoCPBS) {
        this.inscricaoCpbs = inscricaoCPBS;
    }

    public AtividadeCPBS inscricaoCpbs(InscricaoCPBS inscricaoCPBS) {
        this.setInscricaoCpbs(inscricaoCPBS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtividadeCPBS)) {
            return false;
        }
        return id != null && id.equals(((AtividadeCPBS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtividadeCPBS{" +
            "id=" + getId() +
            ", idAtividadeCpbs='" + getIdAtividadeCpbs() + "'" +
            ", cnae='" + getCnae() + "'" +
            ", descricaoCnae='" + getDescricaoCnae() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", idSegmentoIss='" + getIdSegmentoIss() + "'" +
            ", descricaoSegmento='" + getDescricaoSegmento() + "'" +
            ", idListaSerCpbs='" + getIdListaSerCpbs() + "'" +
            ", codigoListaSerCpbs='" + getCodigoListaSerCpbs() + "'" +
            ", descricaoListaSerCpbs='" + getDescricaoListaSerCpbs() + "'" +
            "}";
    }
}
