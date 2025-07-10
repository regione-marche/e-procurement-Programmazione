package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WCacheRicPar;
import it.appaltiecontratti.gestionereportsms.domain.WCacheRicParPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author andrea.chinellato
 * */

@Repository
public interface WCacheRicParRepository extends JpaRepository<WCacheRicPar, WCacheRicParPK> {

    @Query("SELECT w FROM WCacheRicPar w WHERE w.id.idAccount = :idAccount AND w.id.idRicerca = :idRicerca AND w.id.codice = :codice ")
    Optional<WCacheRicPar> findByIdAccountAndIdRicercaAndCodice(@Param(value = "idAccount") Long idAccount, @Param(value = "idRicerca") Long idRicerca, @Param(value = "codice") String codice);
}
