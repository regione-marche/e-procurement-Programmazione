package it.maggioli.ssointegrms.services.oneGateway;

import java.util.Map;

import it.maggioli.ssointegrms.exceptions.oneGateway.DecryptException;
import it.maggioli.ssointegrms.exceptions.oneGateway.EncryptException;
import it.maggioli.ssointegrms.model.AbstractJwtToken;
import it.maggioli.ssointegrms.model.ResponseSuccess;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface CryptoService {

	<T extends AbstractJwtToken> String encryptIdpJwtToken(final String clientId, final T idpJwtToken)
			throws EncryptException;

	Map<String, Object> decryptAndVerifyIdpJwtToken(final String mimsClientId, final String jweString)
			throws DecryptException;

	String encryptSpJwtToken(final ResponseSuccess responseSuccess) throws EncryptException;
}
