package it.maggioli.ssointegrms.services.general;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface MessageService {

	void insertMessageToUsersAdministrator(final String subject, final String body, final Long mittenteId);
}
