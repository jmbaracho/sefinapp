package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.Agent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgentDTO implements Serializable {

    private Long id;

    private String name;

    private Integer maxBlocksPerTime;

    private Integer blockSize;

    private Integer intervalBetweenBlocks;

    private Integer intervalBetweenTasks;

    private Integer maxAttempts;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxBlocksPerTime() {
        return maxBlocksPerTime;
    }

    public void setMaxBlocksPerTime(Integer maxBlocksPerTime) {
        this.maxBlocksPerTime = maxBlocksPerTime;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getIntervalBetweenBlocks() {
        return intervalBetweenBlocks;
    }

    public void setIntervalBetweenBlocks(Integer intervalBetweenBlocks) {
        this.intervalBetweenBlocks = intervalBetweenBlocks;
    }

    public Integer getIntervalBetweenTasks() {
        return intervalBetweenTasks;
    }

    public void setIntervalBetweenTasks(Integer intervalBetweenTasks) {
        this.intervalBetweenTasks = intervalBetweenTasks;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgentDTO)) {
            return false;
        }

        AgentDTO agentDTO = (AgentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgentDTO{" +
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
