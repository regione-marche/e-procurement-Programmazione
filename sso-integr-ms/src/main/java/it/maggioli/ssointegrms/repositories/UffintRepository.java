package it.maggioli.ssointegrms.repositories;

import it.maggioli.ssointegrms.domain.Uffint;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cristiano Perin
 */
@Repository
public interface UffintRepository extends JpaRepository<Uffint, String> {

    @Query("SELECT u FROM Uffint u WHERE u.codice IN :ids ORDER BY u.denominazione ASC, u.codice ASC")
    List<Uffint> findByIds(@Param("ids") final List<String> ids);

    @Query("SELECT u FROM Uffint u WHERE LOWER(u.codice) LIKE %:searchData% OR LOWER(u.denominazione) LIKE %:searchData% ORDER BY u.denominazione ASC")
    List<Uffint> getListaOpzioniUffint(final String searchData);

    @Query("SELECT u FROM Uffint u WHERE u.codice IN :listaStazioniAppaltantiAssociate AND (LOWER(u.codice) LIKE %:searchData% OR LOWER(u.denominazione) LIKE %:searchData%) ORDER BY u.denominazione ASC")
    List<Uffint> getListaOpzioniUffintDelegato(final List<String> listaStazioniAppaltantiAssociate, final String searchData);

}
