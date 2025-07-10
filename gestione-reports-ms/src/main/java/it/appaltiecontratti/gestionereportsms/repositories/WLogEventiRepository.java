package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WLogEventi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author andrea.chinellato
 * */

@Repository
public interface WLogEventiRepository extends JpaRepository<WLogEventi, Long> {
}
