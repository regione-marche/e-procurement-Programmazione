package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Tab0;
import it.maggioli.ssointegrms.domain.Tab0PK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface Tab0Repository extends JpaRepository<Tab0, Tab0PK> {

	Optional<List<Tab0>> findByIdTab0cod(final String tab0cod);

	Page<Tab0> findByIdTab0cod(final String tab0cod, final Pageable pageable);
}
