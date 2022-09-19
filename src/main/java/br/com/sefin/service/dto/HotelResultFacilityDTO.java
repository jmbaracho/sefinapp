package br.com.sefin.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.sefin.domain.HotelResultFacility} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelResultFacilityDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private HotelResultDTO hotelResult;

    private FacilityDTO facility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HotelResultDTO getHotelResult() {
        return hotelResult;
    }

    public void setHotelResult(HotelResultDTO hotelResult) {
        this.hotelResult = hotelResult;
    }

    public FacilityDTO getFacility() {
        return facility;
    }

    public void setFacility(FacilityDTO facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelResultFacilityDTO)) {
            return false;
        }

        HotelResultFacilityDTO hotelResultFacilityDTO = (HotelResultFacilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelResultFacilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelResultFacilityDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", hotelResult=" + getHotelResult() +
            ", facility=" + getFacility() +
            "}";
    }
}
