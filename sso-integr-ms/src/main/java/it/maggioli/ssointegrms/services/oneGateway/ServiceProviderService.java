package it.maggioli.ssointegrms.services.oneGateway;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface ServiceProviderService {

	String getRedirectUrlByMimsClientId(final String mimsClientId);

	boolean existsClientId(final String clientId);

	boolean existsMimsClientId(final String mimsClientId);
}
