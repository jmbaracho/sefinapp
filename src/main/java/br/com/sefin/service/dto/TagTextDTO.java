package br.com.sefin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.TagText} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagTextDTO implements Serializable {

    private Long id;

    private Integer startPosition;

    private Integer endPosition;

    private NfseDTO nfse;

    private TagDTO tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    public NfseDTO getNfse() {
        return nfse;
    }

    public void setNfse(NfseDTO nfse) {
        this.nfse = nfse;
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagTextDTO)) {
            return false;
        }

        TagTextDTO tagTextDTO = (TagTextDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagTextDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagTextDTO{" +
            "id=" + getId() +
            ", startPosition=" + getStartPosition() +
            ", endPosition=" + getEndPosition() +
            ", nfse=" + getNfse() +
            ", tag=" + getTag() +
            "}";
    }
}
