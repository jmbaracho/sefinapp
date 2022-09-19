package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.Schedule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduleDTO implements Serializable {

    private Long id;

    private String rule;

    private Integer firstInterval;

    private Integer secondInterval;

    private Integer thirdInterval;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private AgentDTO agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getFirstInterval() {
        return firstInterval;
    }

    public void setFirstInterval(Integer firstInterval) {
        this.firstInterval = firstInterval;
    }

    public Integer getSecondInterval() {
        return secondInterval;
    }

    public void setSecondInterval(Integer secondInterval) {
        this.secondInterval = secondInterval;
    }

    public Integer getThirdInterval() {
        return thirdInterval;
    }

    public void setThirdInterval(Integer thirdInterval) {
        this.thirdInterval = thirdInterval;
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

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleDTO)) {
            return false;
        }

        ScheduleDTO scheduleDTO = (ScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "id=" + getId() +
            ", rule='" + getRule() + "'" +
            ", firstInterval=" + getFirstInterval() +
            ", secondInterval=" + getSecondInterval() +
            ", thirdInterval=" + getThirdInterval() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", agent=" + getAgent() +
            "}";
    }
}
