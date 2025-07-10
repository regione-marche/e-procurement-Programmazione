package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Tab1;
import it.maggioli.ssointegrms.domain.Tab1PK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface Tab1Repository extends JpaRepository<Tab1, Tab1PK> {

	Optional<List<Tab1>> findByIdTab1cod(final String tab1cod);

	Page<Tab1> findByIdTab1cod(final String tab1cod, final Pageable pageable);
}
