package it.maggioli.ssointegrms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.LoginKO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface LoginKORepository extends JpaRepository<LoginKO, Long> {

	@Query(value = "SELECT COUNT(l) from LoginKO l WHERE LOWER(l.username) = :username")
	Optional<Integer> countByUsername(final String username);

	@Query(value = "SELECT l from LoginKO l WHERE LOWER(l.username) = :username AND l.loginTime = (SELECT MAX(l1.loginTime) FROM LoginKO l1 WHERE l.username = l1.username)")
	Optional<LoginKO> getLastLoginAttempt(final String username);
	
	long deleteByUsernameIgnoreCase(final String username);
}
