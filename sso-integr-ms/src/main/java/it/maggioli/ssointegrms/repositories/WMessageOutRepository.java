package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WMessageOut;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WMessageOutRepository extends JpaRepository<WMessageOut, Long> {
	
	@Query("SELECT MAX(m.id) FROM WMessageOut m")
	Long getMaxId();

}