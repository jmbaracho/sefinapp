package br.com.sefin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rule")
    private String rule;

    @Column(name = "first_interval")
    private Integer firstInterval;

    @Column(name = "second_interval")
    private Integer secondInterval;

    @Column(name = "third_interval")
    private Integer thirdInterval;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "schedules" }, allowSetters = true)
    private Agent agent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Schedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return this.rule;
    }

    public Schedule rule(String rule) {
        this.setRule(rule);
        return this;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getFirstInterval() {
        return this.firstInterval;
    }

    public Schedule firstInterval(Integer firstInterval) {
        this.setFirstInterval(firstInterval);
        return this;
    }

    public void setFirstInterval(Integer firstInterval) {
        this.firstInterval = firstInterval;
    }

    public Integer getSecondInterval() {
        return this.secondInterval;
    }

    public Schedule secondInterval(Integer secondInterval) {
        this.setSecondInterval(secondInterval);
        return this;
    }

    public void setSecondInterval(Integer secondInterval) {
        this.secondInterval = secondInterval;
    }

    public Integer getThirdInterval() {
        return this.thirdInterval;
    }

    public Schedule thirdInterval(Integer thirdInterval) {
        this.setThirdInterval(thirdInterval);
        return this;
    }

    public void setThirdInterval(Integer thirdInterval) {
        this.thirdInterval = thirdInterval;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Schedule createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Schedule updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Schedule agent(Agent agent) {
        this.setAgent(agent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", rule='" + getRule() + "'" +
            ", firstInterval=" + getFirstInterval() +
            ", secondInterval=" + getSecondInterval() +
            ", thirdInterval=" + getThirdInterval() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
