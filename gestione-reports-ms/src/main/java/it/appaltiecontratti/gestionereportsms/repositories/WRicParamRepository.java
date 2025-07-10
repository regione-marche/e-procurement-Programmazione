package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WRicParam;
import it.appaltiecontratti.gestionereportsms.domain.WRicParamPK;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author andrea.chinellato
 * */

@Repository
public interface WRicParamRepository extends JpaRepository<WRicParam, WRicParamPK>, JpaSpecificationExecutor<WRicParam> {

    @Query("SELECT MAX(wp.id.progressivo) FROM WRicParam wp WHERE wp.id.idRicerca = :idRicerca")
    Long getMaxProgr(@Param("idRicerca") Long idRicerca);

    @Query("SELECT wr FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca AND wr.codice = :codice")
    Optional<WRicParam> findByIdRicercaAndCodice(@Param("idRicerca") Long idRicerca, @Param("codice") String codice);

    @Query("SELECT wr FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca AND wr.id.progressivo = :progressivo")
    Optional<WRicParam> findByIdRicercaAndProgressivo(@Param("idRicerca") Long idRicerca, @Param("progressivo") Long progressivo);

    @Query("SELECT wr FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca AND wr.id.progressivo = (SELECT MAX(wp.id.progressivo) FROM WRicParam wp WHERE wp.id.idRicerca = :idRicerca) ")
    Optional<WRicParam> findByIdRicercaAndMaxProgressivo(@Param("idRicerca") Long idRicerca);

    @Query("SELECT CASE WHEN COUNT(wr) > 0 THEN true ELSE false END FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca AND wr.id.progressivo = :progressivo")
    boolean existsByIdRicercaAndProgressivo(@Param("idRicerca") Long idRicerca, @Param("progressivo") Long progressivo);

    @Query("SELECT COUNT(*) FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca")
    Long countByIdRicerca(@Param("idRicerca") Long idRicerca);

    @Query("SELECT wr FROM WRicParam wr WHERE wr.id.idRicerca = :idRicerca")
    List<WRicParam> findByIdRicerca(@Param("idRicerca") Long idRicerca);

    @Query("SELECT CASE WHEN COUNT(wr) > 0 THEN true ELSE false END FROM WRicParam wr WHERE wr.codice = :codice AND wr.id.idRicerca = :idRicerca")
    boolean existsByCodiceReport(@Param("codice") String codice, @Param("idRicerca") Long idRicerca);
}
