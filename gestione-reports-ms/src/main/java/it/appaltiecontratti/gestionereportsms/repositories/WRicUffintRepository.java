package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WRicuffint;
import it.appaltiecontratti.gestionereportsms.domain.WRicuffintPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WRicUffintRepository extends JpaRepository<WRicuffint, WRicuffintPK> {

    @Query("SELECT w FROM WRicuffint w WHERE w.id.idRicerca = :idRicerca")
    List<WRicuffint> findByIdRicerca(@Param("idRicerca") Long idRicerca);

    @Modifying
    @Query("DELETE FROM WRicuffint w WHERE w.id.idRicerca = :idRicerca")
    void deleteByIdRicerca(@Param("idRicerca") Long idRicerca);
}
