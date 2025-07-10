package it.maggioli.ssointegrms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Profilo;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface ProfiloRepository extends JpaRepository<Profilo, String> {

	@Query("SELECT p FROM Profilo p WHERE p.codice IN :ids ORDER BY p.descrizione ASC, p.codice ASC")
	List<Profilo> findByIds(@Param("ids") final List<String> ids);

	@Query("SELECT p FROM Profilo p WHERE LOWER(p.codice) LIKE %:searchData% OR LOWER(p.nome) LIKE %:searchData% ORDER BY p.nome ASC")
	List<Profilo> getListaOpzioniProfilo(final String searchData);

}
