package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.Uffint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andrea.chinellato
 * */

@Repository
public interface UffIntRepository extends JpaRepository<Uffint, String> {

    @Query("SELECT u FROM Uffint u WHERE u.codice IN :ids ORDER BY u.denominazione ASC, u.codice ASC")
    List<Uffint> findByIds(@Param("ids") final List<String> ids);

    @Query("SELECT u FROM Uffint u WHERE u.codice = :codeIn")
    Uffint findByCodeIn(final String codeIn);
}
