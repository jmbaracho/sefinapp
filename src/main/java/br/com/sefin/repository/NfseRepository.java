package br.com.sefin.repository;

import br.com.sefin.domain.Nfse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nfse entity.
 */
@Repository
public interface NfseRepository extends JpaRepository<Nfse, Long> {
    default Optional<Nfse> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Nfse> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Nfse> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct nfse from Nfse nfse left join fetch nfse.inscricaoCpbs",
        countQuery = "select count(distinct nfse) from Nfse nfse"
    )
    Page<Nfse> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct nfse from Nfse nfse left join fetch nfse.inscricaoCpbs")
    List<Nfse> findAllWithToOneRelationships();

    @Query("select nfse from Nfse nfse left join fetch nfse.inscricaoCpbs where nfse.id =:id")
    Optional<Nfse> findOneWithToOneRelationships(@Param("id") Long id);
}
