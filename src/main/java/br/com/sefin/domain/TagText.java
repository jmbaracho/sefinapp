package br.com.sefin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TagText.
 */
@Entity
@Table(name = "tag_text")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagText implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_position")
    private Integer startPosition;

    @Column(name = "end_position")
    private Integer endPosition;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inscricaoCpbs" }, allowSetters = true)
    private Nfse nfse;

    @ManyToOne
    private Tag tag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TagText id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartPosition() {
        return this.startPosition;
    }

    public TagText startPosition(Integer startPosition) {
        this.setStartPosition(startPosition);
        return this;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return this.endPosition;
    }

    public TagText endPosition(Integer endPosition) {
        this.setEndPosition(endPosition);
        return this;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    public Nfse getNfse() {
        return this.nfse;
    }

    public void setNfse(Nfse nfse) {
        this.nfse = nfse;
    }

    public TagText nfse(Nfse nfse) {
        this.setNfse(nfse);
        return this;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public TagText tag(Tag tag) {
        this.setTag(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagText)) {
            return false;
        }
        return id != null && id.equals(((TagText) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagText{" +
            "id=" + getId() +
            ", startPosition=" + getStartPosition() +
            ", endPosition=" + getEndPosition() +
            "}";
    }
}
