package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.StoUteSysDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface StoUteSysService {

	StoUteSysDTO getCurrentPasswordInformation(final String sysnom, final String syspwd);

	Integer countUserPasswordInformationBySyscon(final Long syscon);

	List<String> getChangedPasswords(final Long syscon);

	StoUteSysDTO insertChangedPassword(final User user, final String passwordCifrata) throws CriptazioneException;
	
	void deleteBySyscon(final Long syscon);
}
