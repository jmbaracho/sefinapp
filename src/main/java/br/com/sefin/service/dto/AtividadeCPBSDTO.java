package br.com.sefin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.AtividadeCPBS} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AtividadeCPBSDTO implements Serializable {

    private Long id;

    private String idAtividadeCpbs;

    private String cnae;

    private String descricaoCnae;

    private Boolean principal;

    private String idSegmentoIss;

    private String descricaoSegmento;

    private String idListaSerCpbs;

    private String codigoListaSerCpbs;

    private String descricaoListaSerCpbs;

    private InscricaoCPBSDTO inscricaoCpbs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAtividadeCpbs() {
        return idAtividadeCpbs;
    }

    public void setIdAtividadeCpbs(String idAtividadeCpbs) {
        this.idAtividadeCpbs = idAtividadeCpbs;
    }

    public String getCnae() {
        return cnae;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public String getDescricaoCnae() {
        return descricaoCnae;
    }

    public void setDescricaoCnae(String descricaoCnae) {
        this.descricaoCnae = descricaoCnae;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getIdSegmentoIss() {
        return idSegmentoIss;
    }

    public void setIdSegmentoIss(String idSegmentoIss) {
        this.idSegmentoIss = idSegmentoIss;
    }

    public String getDescricaoSegmento() {
        return descricaoSegmento;
    }

    public void setDescricaoSegmento(String descricaoSegmento) {
        this.descricaoSegmento = descricaoSegmento;
    }

    public String getIdListaSerCpbs() {
        return idListaSerCpbs;
    }

    public void setIdListaSerCpbs(String idListaSerCpbs) {
        this.idListaSerCpbs = idListaSerCpbs;
    }

    public String getCodigoListaSerCpbs() {
        return codigoListaSerCpbs;
    }

    public void setCodigoListaSerCpbs(String codigoListaSerCpbs) {
        this.codigoListaSerCpbs = codigoListaSerCpbs;
    }

    public String getDescricaoListaSerCpbs() {
        return descricaoListaSerCpbs;
    }

    public void setDescricaoListaSerCpbs(String descricaoListaSerCpbs) {
        this.descricaoListaSerCpbs = descricaoListaSerCpbs;
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
        if (!(o instanceof AtividadeCPBSDTO)) {
            return false;
        }

        AtividadeCPBSDTO atividadeCPBSDTO = (AtividadeCPBSDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, atividadeCPBSDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtividadeCPBSDTO{" +
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
            ", inscricaoCpbs=" + getInscricaoCpbs() +
            "}";
    }
}
