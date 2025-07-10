package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WPreferiti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author andrea.chinellato
 * Repository per l'entity W_PREFERITI
 */

@Repository
public interface WPreferitiRepository extends JpaRepository<WPreferiti, Long> {

    @Query("select p from WPreferiti p where p.syscon = :syscon and p.key1 = :key1 and p.tabella = :tabella")
    Optional<WPreferiti> findBySysconAndByIdRicercaAndByKey1(@Param("syscon") Long syscon, @Param("key1") String key1, @Param("tabella") String tabella);

    @Query("select p from WPreferiti p where p.syscon = :syscon")
    List<WPreferiti> findBySyscon(@Param("syscon") Long syscon);
}
