package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WGruppi;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WGruppiRepository extends JpaRepository<WGruppi, Long> {

	Optional<List<WGruppi>> findByCodiceProfiloIn(final List<String> listaCodiciProfilo);
}