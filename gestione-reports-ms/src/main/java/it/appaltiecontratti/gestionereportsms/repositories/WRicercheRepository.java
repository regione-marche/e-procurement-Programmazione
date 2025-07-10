package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WRicerche;
import it.appaltiecontratti.gestionereportsms.dto.WRicercheDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * Repository per l'Entity WRicerche
 *
 * @author andrea.chinellato
 *
 * */

@Repository
public interface WRicercheRepository extends JpaRepository<WRicerche, Long>, JpaSpecificationExecutor<WRicerche> {

    @Query("SELECT MAX(wr.idRicerca) FROM WRicerche wr")
    Long getMaxId();

    @Query("SELECT CASE WHEN COUNT(wr) > 0 THEN true ELSE false END FROM WRicerche wr WHERE wr.nome = :nome")
    boolean existsByNome(@Param("nome") String nome);

    @Query("SELECT MAX(w.idRicerca) FROM WRicerche w")
    Long getMaxidRicerca();

    @Query("SELECT w FROM WRicerche w WHERE w.nome = :nome")
    Optional<WRicerche> findByNomeReport(@Param("nome") String nome);

    @Query("SELECT w FROM WRicerche w WHERE w.codReportWs = :codReportWs")
    Optional<WRicerche> findByCodReportWs(@Param("codReportWs") String codReportWs);

    @Query("SELECT COUNT(*) FROM WRicerche w WHERE w.codReportWs = :codReportWs")
    Long countByCodReportWs(@Param("codReportWs") String codReportWs);

    @Query("SELECT COUNT(*) FROM WRicerche w WHERE w.nome = :nome")
    Long countByNome(@Param("nome") String nome);

    @Query("SELECT w.defSql FROM WRicerche w WHERE w.idRicerca = :idRicerca")
    String getDefSqlByIdRicerca(@Param("idRicerca") Long idRicerca);
}
