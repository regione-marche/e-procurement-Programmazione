package it.maggioli.ssointegrms.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.GConfCod;
import it.maggioli.ssointegrms.domain.GConfCodPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface GConfCodRepository extends JpaRepository<GConfCod, GConfCodPK> {
	
	List<GConfCod> findByCodAppIn(final List<String> listaCodApp, Sort sort);
}
