package br.com.sefin.repository;

import br.com.sefin.domain.RawData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RawData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RawDataRepository extends JpaRepository<RawData, Long> {}
