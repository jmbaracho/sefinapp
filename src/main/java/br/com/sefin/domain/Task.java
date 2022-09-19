package br.com.sefin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "attempt_count")
    private Integer attemptCount;

    @Column(name = "target_date_interval")
    private Integer targetDateInterval;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tasks" }, allowSetters = true)
    private TaskStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tasks", "schedule" }, allowSetters = true)
    private ScheduleExecution scheduleExecution;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Task id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public Task source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getAttemptCount() {
        return this.attemptCount;
    }

    public Task attemptCount(Integer attemptCount) {
        this.setAttemptCount(attemptCount);
        return this;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Integer getTargetDateInterval() {
        return this.targetDateInterval;
    }

    public Task targetDateInterval(Integer targetDateInterval) {
        this.setTargetDateInterval(targetDateInterval);
        return this;
    }

    public void setTargetDateInterval(Integer targetDateInterval) {
        this.targetDateInterval = targetDateInterval;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Task createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Task updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus taskStatus) {
        this.status = taskStatus;
    }

    public Task status(TaskStatus taskStatus) {
        this.setStatus(taskStatus);
        return this;
    }

    public ScheduleExecution getScheduleExecution() {
        return this.scheduleExecution;
    }

    public void setScheduleExecution(ScheduleExecution scheduleExecution) {
        this.scheduleExecution = scheduleExecution;
    }

    public Task scheduleExecution(ScheduleExecution scheduleExecution) {
        this.setScheduleExecution(scheduleExecution);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", attemptCount=" + getAttemptCount() +
            ", targetDateInterval=" + getTargetDateInterval() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
