package it.maggioli.ssointegrms.model;

/**
 * Classe utilizzata nello store da associare all'uuid di richiesta
 * 
 * @author Cristiano Perin
 *
 */
public class AuthenticationRequestType {

	private String clientId;
	private String authenticationType;

	public AuthenticationRequestType(String clientId, String authenticationType) {
		super();
		this.clientId = clientId;
		this.authenticationType = authenticationType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	@Override
	public String toString() {
		return "[clientId=" + clientId + ", authenticationType=" + authenticationType + "]";
	}

}
