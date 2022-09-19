package br.com.sefin.repository;

import br.com.sefin.domain.HotelResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HotelResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelResultRepository extends JpaRepository<HotelResult, Long> {}
