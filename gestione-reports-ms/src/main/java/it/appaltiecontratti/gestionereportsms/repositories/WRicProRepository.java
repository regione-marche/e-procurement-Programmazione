package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WRicpro;
import it.appaltiecontratti.gestionereportsms.domain.WRicproPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andrea.chinellato
 * */

@Repository
public interface WRicProRepository extends JpaRepository<WRicpro, WRicproPK> {

    @Query("SELECT wrp FROM WRicpro wrp WHERE wrp.id.idRicerca =:idRicerca")
    List<WRicpro> findByIdRicerca(@Param(value = "idRicerca") Long idRicerca);

    @Modifying
    @Query("DELETE FROM WRicpro wrp WHERE wrp.id.idRicerca =:idRicerca")
    void deleteByIdRicerca(@Param(value = "idRicerca") Long idRicerca);
}
