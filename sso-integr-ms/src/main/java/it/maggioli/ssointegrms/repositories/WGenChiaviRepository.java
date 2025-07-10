package it.maggioli.ssointegrms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.maggioli.ssointegrms.domain.WGenChiavi;

import javax.persistence.LockModeType;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
@Transactional
public interface WGenChiaviRepository extends JpaRepository<WGenChiavi, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Transactional(propagation = Propagation.MANDATORY)
	Optional<WGenChiavi> findByTabellaIgnoreCase(String id);
}
