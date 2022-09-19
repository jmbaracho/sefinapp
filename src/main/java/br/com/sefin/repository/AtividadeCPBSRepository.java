package br.com.sefin.repository;

import br.com.sefin.domain.AtividadeCPBS;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AtividadeCPBS entity.
 */
@Repository
public interface AtividadeCPBSRepository extends JpaRepository<AtividadeCPBS, Long> {
    default Optional<AtividadeCPBS> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AtividadeCPBS> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AtividadeCPBS> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct atividadeCPBS from AtividadeCPBS atividadeCPBS left join fetch atividadeCPBS.inscricaoCpbs",
        countQuery = "select count(distinct atividadeCPBS) from AtividadeCPBS atividadeCPBS"
    )
    Page<AtividadeCPBS> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct atividadeCPBS from AtividadeCPBS atividadeCPBS left join fetch atividadeCPBS.inscricaoCpbs")
    List<AtividadeCPBS> findAllWithToOneRelationships();

    @Query("select atividadeCPBS from AtividadeCPBS atividadeCPBS left join fetch atividadeCPBS.inscricaoCpbs where atividadeCPBS.id =:id")
    Optional<AtividadeCPBS> findOneWithToOneRelationships(@Param("id") Long id);
}
