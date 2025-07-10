package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.Profilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfiloRepository extends JpaRepository<Profilo, String> {

    @Query("SELECT p FROM Profilo p WHERE p.codice IN :ids ORDER BY p.descrizione ASC, p.codice ASC")
    List<Profilo> findByIds(@Param("ids") final List<String> ids);

    @Query("SELECT p FROM Profilo p WHERE p.codice = :codice")
    Profilo findByCodProfilo(final String codice);
}
