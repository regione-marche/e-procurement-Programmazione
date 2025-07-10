package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WUsrToken;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WUsrTokenRepository extends JpaRepository<WUsrToken, Long> {

	@Modifying
	@Query("DELETE FROM WUsrToken t WHERE t.id = :syscon AND t.tokenType = :tokenType")
	void deleteByIdAndTokenType(final Long syscon, final String tokenType);

	WUsrToken findByToken(final String token);
	
	@Modifying
	@Query("DELETE FROM WUsrToken t WHERE t.id = :syscon")
	void deleteById(final Long syscon);
}
