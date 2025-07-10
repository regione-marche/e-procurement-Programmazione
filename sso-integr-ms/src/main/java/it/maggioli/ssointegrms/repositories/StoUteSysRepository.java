package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.StoUteSys;
import it.maggioli.ssointegrms.domain.StoUteSysPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface StoUteSysRepository extends JpaRepository<StoUteSys, StoUteSysPK> {

	@Query(value = "SELECT COUNT(s) from StoUteSys s WHERE s.syscon = :syscon")
	Optional<Integer> countBySyscon(final Long syscon);

	Optional<List<StoUteSys>> findBySysconOrderBySysdatDesc(final Long syscon);

	void deleteBySyscon(final Long syscon);

}
