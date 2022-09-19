package br.com.sefin.repository;

import br.com.sefin.domain.ScheduleExecution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScheduleExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleExecutionRepository extends JpaRepository<ScheduleExecution, Long> {}
