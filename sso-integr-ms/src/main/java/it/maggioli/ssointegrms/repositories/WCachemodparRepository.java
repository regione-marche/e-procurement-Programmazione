package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WCachemodpar;
import it.maggioli.ssointegrms.domain.WCachemodparPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WCachemodparRepository extends JpaRepository<WCachemodpar, WCachemodparPK> {

	void deleteByIdIdAccount(final Long syscon);
}
