package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Tab3;
import it.maggioli.ssointegrms.domain.Tab3PK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface Tab3Repository extends JpaRepository<Tab3, Tab3PK> {

	Optional<List<Tab3>> findByIdTab3cod(final String tab3cod);

	Page<Tab3> findByIdTab3cod(final String tab3cod, final Pageable pageable);
}
