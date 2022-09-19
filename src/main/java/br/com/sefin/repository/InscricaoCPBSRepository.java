package br.com.sefin.repository;

import br.com.sefin.domain.InscricaoCPBS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InscricaoCPBS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscricaoCPBSRepository extends JpaRepository<InscricaoCPBS, Long> {}
