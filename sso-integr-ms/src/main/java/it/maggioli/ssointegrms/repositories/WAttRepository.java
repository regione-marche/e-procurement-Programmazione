package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WAtt;
import it.maggioli.ssointegrms.domain.WAttPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WAttRepository extends JpaRepository<WAtt, WAttPK> {

}
