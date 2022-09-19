package br.com.sefin.repository;

import br.com.sefin.domain.HotelResultFacility;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HotelResultFacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelResultFacilityRepository extends JpaRepository<HotelResultFacility, Long> {}
