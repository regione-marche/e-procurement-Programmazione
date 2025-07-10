package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WAccgrp;
import it.maggioli.ssointegrms.domain.WAccgrpPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WAccgrpRepository extends JpaRepository<WAccgrp, WAccgrpPK> {

	void deleteByIdIdAccount(final Long syscon);
}
