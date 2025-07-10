package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WMessageOutRec;
import it.maggioli.ssointegrms.domain.WMessageOutRecPK;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WMessageOutRecRepository extends JpaRepository<WMessageOutRec, WMessageOutRecPK> {

}