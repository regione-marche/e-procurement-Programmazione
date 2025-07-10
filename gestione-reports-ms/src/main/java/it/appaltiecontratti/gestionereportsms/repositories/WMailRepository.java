package it.appaltiecontratti.gestionereportsms.repositories;

import it.appaltiecontratti.gestionereportsms.domain.WMail;
import it.appaltiecontratti.gestionereportsms.domain.WMailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WMailRepository extends JpaRepository<WMail, WMailPK> {

    @Query("SELECT m.mailMittente FROM WMail m WHERE m.id.codApp = :codApp AND m.id.configurazione = :configurazione")
    String getEmailMittente(@Param("codApp") final String codApp, @Param("configurazione") final String configurazione);

    @Query("SELECT m FROM WMail m WHERE m.id.codApp = :codApp AND m.id.configurazione = :configurazione")
    WMail getInfoMailServer(@Param("codApp") final String codApp, @Param("configurazione") final String configurazione);
}
