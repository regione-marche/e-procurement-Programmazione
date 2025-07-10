package it.maggioli.ssointegrms.services.oneGateway;

import it.maggioli.ssointegrms.exceptions.oneGateway.EncryptException;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface GatewayService {

	String authenticate(final String clientId) throws EncryptException;

}
