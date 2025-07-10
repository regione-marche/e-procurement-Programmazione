package it.maggioli.ssointegrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Tab2;
import it.maggioli.ssointegrms.domain.Tab2PK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface Tab2Repository extends JpaRepository<Tab2, Tab2PK> {

	Optional<List<Tab2>> findByIdTab2cod(final String tab2cod);

	Page<Tab2> findByIdTab2cod(final String tab2cod, final Pageable pageable);
}
