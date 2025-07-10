package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.C0Campi;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface C0CampiRepository extends JpaRepository<C0Campi, String> {

	@Query("SELECT c FROM C0Campi c WHERE c.coc_mne_uni LIKE %:campo% AND c.c0c_tip IN ('E', 'P')")
	C0Campi findByCampoLike(final String campo);
}
