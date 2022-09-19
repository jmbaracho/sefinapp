package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.ScheduleExecution} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduleExecutionDTO implements Serializable {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private ScheduleDTO schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleExecutionDTO)) {
            return false;
        }

        ScheduleExecutionDTO scheduleExecutionDTO = (ScheduleExecutionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scheduleExecutionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleExecutionDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", schedule=" + getSchedule() +
            "}";
    }
}
