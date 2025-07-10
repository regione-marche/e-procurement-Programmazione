package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.UsrCanc;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface UsrCancRepository extends JpaRepository<UsrCanc, Long> {

	Optional<List<UsrCanc>> findByLoginIgnoreCase(final String login);
}
