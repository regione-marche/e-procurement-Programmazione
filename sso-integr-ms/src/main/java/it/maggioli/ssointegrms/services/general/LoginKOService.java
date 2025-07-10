package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.LoginKODTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface LoginKOService {

	void insertLoginKO(final String username, final String ipAddress);

	Integer countCurrentLoginAttempts(final String username);

	LoginKODTO getLastLoginAttempt(final String username);
	
	void clearLoginAttempts(final String username);

}
