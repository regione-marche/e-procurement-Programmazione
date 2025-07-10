package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.Eldaver;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface EldaverRepository extends JpaRepository<Eldaver, String> {

}
