package it.maggioli.ssointegrms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WConfig;
import it.maggioli.ssointegrms.domain.WConfigPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WConfigRepository extends JpaRepository<WConfig, WConfigPK> {

	@Query("SELECT DISTINCT c.sezione FROM WConfig c ORDER BY c.sezione ASC")
	List<String> findSezioniDistinct();
}
