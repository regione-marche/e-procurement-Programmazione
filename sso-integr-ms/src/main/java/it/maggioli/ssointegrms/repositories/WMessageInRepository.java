package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WMessageIn;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WMessageInRepository extends JpaRepository<WMessageIn, Long> {

	@Query("SELECT MAX(m.id) FROM WMessageIn m")
	Long getMaxId();
}