package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WGenChiavi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author andrea.chinellato
 * */

@Repository
@Transactional
public interface WGenChiaviRepository extends JpaRepository<WGenChiavi, String> {

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Optional<WGenChiavi> findById(String id);

    @Transactional(propagation = Propagation.MANDATORY)
    Optional<WGenChiavi> findByTabellaIgnoreCase(String id);

    @Transactional(propagation = Propagation.MANDATORY)
    @Modifying
    @Query(value = "UPDATE WGenChiavi w SET w.chiave = :chiaveIncrementata WHERE UPPER(w.tabella) = UPPER(:tabella)")
    void incrementChiavePerTabella(@Param(value = "chiaveIncrementata") long chiaveIncrementata, String tabella);

    @Transactional(propagation = Propagation.REQUIRED)
    @Query("SELECT w FROM WGenChiavi w WHERE UPPER(w.tabella) = UPPER(:tabella)")
    Optional<WGenChiavi> findByTabellaIgnoreCaseScheduler(String tabella);

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query(value = "UPDATE WGenChiavi w SET w.chiave = :chiaveIncrementata WHERE UPPER(w.tabella) = UPPER(:tabella)")
    void incrementChiavePerTabellaScheduler(@Param(value = "chiaveIncrementata") long chiaveIncrementata, String tabella);
}
