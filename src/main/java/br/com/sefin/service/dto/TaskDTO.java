package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.Task} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskDTO implements Serializable {

    private Long id;

    private String source;

    private Integer attemptCount;

    private Integer targetDateInterval;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private TaskStatusDTO status;

    private ScheduleExecutionDTO scheduleExecution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Integer getTargetDateInterval() {
        return targetDateInterval;
    }

    public void setTargetDateInterval(Integer targetDateInterval) {
        this.targetDateInterval = targetDateInterval;
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

    public TaskStatusDTO getStatus() {
        return status;
    }

    public void setStatus(TaskStatusDTO status) {
        this.status = status;
    }

    public ScheduleExecutionDTO getScheduleExecution() {
        return scheduleExecution;
    }

    public void setScheduleExecution(ScheduleExecutionDTO scheduleExecution) {
        this.scheduleExecution = scheduleExecution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        TaskDTO taskDTO = (TaskDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", attemptCount=" + getAttemptCount() +
            ", targetDateInterval=" + getTargetDateInterval() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", status=" + getStatus() +
            ", scheduleExecution=" + getScheduleExecution() +
            "}";
    }
}
