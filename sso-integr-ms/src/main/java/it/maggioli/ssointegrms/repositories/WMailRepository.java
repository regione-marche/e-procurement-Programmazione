package it.maggioli.ssointegrms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.maggioli.ssointegrms.domain.WMail;
import it.maggioli.ssointegrms.domain.WMailId;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Repository
public interface WMailRepository extends JpaRepository<WMail, WMailId> {
	
	@Query("SELECT m FROM WMail m WHERE m.id.codApp = :codApp AND m.id.idCfg = :configurazione")
	WMail getInfoMailServer(final String codApp, final String configurazione);
	
	@Query("SELECT m.mailMittente FROM WMail m WHERE m.id.codApp = :codApp AND m.id.idCfg = :configurazione")
	String getEmailMittente(final String codApp, final String configurazione);

}
