package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.GPermessi;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface GPermessiRepository extends JpaRepository<GPermessi, Long> {

	void deleteBySyscon(final Long syscon);
}
