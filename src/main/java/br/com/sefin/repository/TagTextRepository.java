package br.com.sefin.repository;

import br.com.sefin.domain.TagText;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TagText entity.
 */
@Repository
public interface TagTextRepository extends JpaRepository<TagText, Long> {
    default Optional<TagText> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TagText> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TagText> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct tagText from TagText tagText left join fetch tagText.nfse",
        countQuery = "select count(distinct tagText) from TagText tagText"
    )
    Page<TagText> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct tagText from TagText tagText left join fetch tagText.nfse")
    List<TagText> findAllWithToOneRelationships();

    @Query("select tagText from TagText tagText left join fetch tagText.nfse where tagText.id =:id")
    Optional<TagText> findOneWithToOneRelationships(@Param("id") Long id);
}
