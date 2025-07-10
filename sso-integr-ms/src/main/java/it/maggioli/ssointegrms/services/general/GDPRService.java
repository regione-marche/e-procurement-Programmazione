package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.AccountConfigurationDTO;
import it.maggioli.ssointegrms.dto.ChangePasswordDTO;
import it.maggioli.ssointegrms.dto.GDPRCheck2DTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface GDPRService {

	boolean executeCheck1(final User user, final ChangePasswordDTO changePasswordDTO);

	boolean executeCheck1Insert(final String newPassword, final boolean isAdministrator, final boolean passwordSicuraEnabled);

	GDPRCheck2DTO executeCheck2(final User user, final ChangePasswordDTO changePasswordDTO);

	GDPRCheck2DTO executeCheck2Insert(final String newPassword, final boolean passwordSicuraEnabled);

	boolean executeCheck3A(final AccountConfigurationDTO accountConfigurationDTO, final User user,
			final String ipAddress);

	boolean executeCheck3B(final User user, final ChangePasswordDTO changePasswordDTO);

	boolean executeCheck3BInsert(final String username, final String newPassword, final boolean passwordSicuraEnabled);

	boolean executeCheck4A(final User user);

	boolean executeCheck5A5C(final User user, final ChangePasswordDTO changePasswordDTO) throws CriptazioneException;

	boolean executeCheck6A(final AccountConfigurationDTO accountConfigurationDTO, final User user);

	boolean executeCheck6B(final AccountConfigurationDTO accountConfigurationDTO, final User user);
}
