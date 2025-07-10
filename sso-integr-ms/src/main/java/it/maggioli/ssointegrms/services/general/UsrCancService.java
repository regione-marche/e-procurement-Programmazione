package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.domain.UsrCanc;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface UsrCancService {

	UsrCanc insertCancellazione(final Long syscon, final String syslogin);
}
