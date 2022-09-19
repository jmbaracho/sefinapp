package br.com.sefin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_blocks_per_time")
    private Integer maxBlocksPerTime;

    @Column(name = "block_size")
    private Integer blockSize;

    @Column(name = "interval_between_blocks")
    private Integer intervalBetweenBlocks;

    @Column(name = "interval_between_tasks")
    private Integer intervalBetweenTasks;

    @Column(name = "max_attempts")
    private Integer maxAttempts;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "agent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agent" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Agent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxBlocksPerTime() {
        return this.maxBlocksPerTime;
    }

    public Agent maxBlocksPerTime(Integer maxBlocksPerTime) {
        this.setMaxBlocksPerTime(maxBlocksPerTime);
        return this;
    }

    public void setMaxBlocksPerTime(Integer maxBlocksPerTime) {
        this.maxBlocksPerTime = maxBlocksPerTime;
    }

    public Integer getBlockSize() {
        return this.blockSize;
    }

    public Agent blockSize(Integer blockSize) {
        this.setBlockSize(blockSize);
        return this;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getIntervalBetweenBlocks() {
        return this.intervalBetweenBlocks;
    }

    public Agent intervalBetweenBlocks(Integer intervalBetweenBlocks) {
        this.setIntervalBetweenBlocks(intervalBetweenBlocks);
        return this;
    }

    public void setIntervalBetweenBlocks(Integer intervalBetweenBlocks) {
        this.intervalBetweenBlocks = intervalBetweenBlocks;
    }

    public Integer getIntervalBetweenTasks() {
        return this.intervalBetweenTasks;
    }

    public Agent intervalBetweenTasks(Integer intervalBetweenTasks) {
        this.setIntervalBetweenTasks(intervalBetweenTasks);
        return this;
    }

    public void setIntervalBetweenTasks(Integer intervalBetweenTasks) {
        this.intervalBetweenTasks = intervalBetweenTasks;
    }

    public Integer getMaxAttempts() {
        return this.maxAttempts;
    }

    public Agent maxAttempts(Integer maxAttempts) {
        this.setMaxAttempts(maxAttempts);
        return this;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Agent createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Agent updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setAgent(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setAgent(this));
        }
        this.schedules = schedules;
    }

    public Agent schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Agent addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setAgent(this);
        return this;
    }

    public Agent removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setAgent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        return id != null && id.equals(((Agent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maxBlocksPerTime=" + getMaxBlocksPerTime() +
            ", blockSize=" + getBlockSize() +
            ", intervalBetweenBlocks=" + getIntervalBetweenBlocks() +
            ", intervalBetweenTasks=" + getIntervalBetweenTasks() +
            ", maxAttempts=" + getMaxAttempts() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
