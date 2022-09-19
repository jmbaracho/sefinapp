package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.RawData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawDataDTO implements Serializable {

    private Long id;

    private String content;

    private Boolean processed;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private TaskDTO task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawDataDTO)) {
            return false;
        }

        RawDataDTO rawDataDTO = (RawDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rawDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", processed='" + getProcessed() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", task=" + getTask() +
            "}";
    }
}
