package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Tab5;
import it.maggioli.ssointegrms.domain.Tab5PK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface Tab5Repository extends JpaRepository<Tab5, Tab5PK> {

	Optional<List<Tab5>> findByIdTab5cod(final String tab5cod);

	Page<Tab5> findByIdTab5cod(final String tab5cod, final Pageable pageable);
}
