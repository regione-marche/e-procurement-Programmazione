package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WQuartz;
import it.maggioli.ssointegrms.domain.WQuartzPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WQuartzRepository extends JpaRepository<WQuartz, WQuartzPK> {

}
