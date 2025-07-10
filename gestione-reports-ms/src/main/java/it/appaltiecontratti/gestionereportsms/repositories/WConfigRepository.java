package it.appaltiecontratti.gestionereportsms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.appaltiecontratti.gestionereportsms.domain.WConfig;
import it.appaltiecontratti.gestionereportsms.domain.WConfigPK;

/**
 * @author Cristiano Perin
 */
@Repository
public interface WConfigRepository extends JpaRepository<WConfig, WConfigPK> {

    @Query("SELECT DISTINCT c.sezione FROM WConfig c ORDER BY c.sezione ASC")
    List<String> findSezioniDistinct();

    @Query("select c from WConfig c where UPPER(c.id.codApp) = :codApp AND c.id.chiave = :chiave")
    WConfig getConfigObj(@Param("codApp") String codApp, @Param("chiave") String chiave);

    @Query("SELECT c.valore FROM WConfig c WHERE UPPER(c.id.codApp) = :codApp AND c.id.chiave = :chiave")
    String getConfigurazione(@Param("codApp") String codApp, @Param("chiave") String chiave);
}
