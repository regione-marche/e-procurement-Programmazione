package it.maggioli.ssointegrms.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WLogEventi;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WLogEventiRepository extends JpaRepository<WLogEventi, Long> {

	@Query(value = "SELECT w from WLogEventi w WHERE w.codiceEvento = :codiceEvento AND w.user.syscon = :syscon AND w.dataOra = (SELECT MAX(w1.dataOra) FROM WLogEventi w1 WHERE w.user.syscon = w1.user.syscon)")
	Optional<WLogEventi> getLastLogBySyscon(final String codiceEvento, final Long syscon);
	
	@Query("SELECT DISTINCT e.codiceEvento FROM WLogEventi e WHERE e.codApp = :codApp ORDER BY e.codiceEvento ASC")
	List<String> findCodiciEventiDistinct(final String codApp);

	@Query("SELECT e FROM WLogEventi e WHERE (e.codiceEvento = :login OR e.codiceEvento = :logout) AND e.user.syscon = :syscon AND e.dataOra >= :intervalDate ORDER BY e.dataOra DESC")
	List<WLogEventi> loadUltimiAccessi(@Param("syscon") Long syscon, @Param("login") String login, @Param("logout") String logout, @Param("intervalDate") Date intervalDate);
}
