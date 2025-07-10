package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

public class LogonResponse {
	private String bearerToken;

	// Getter
	public String getBearerToken() {
		return bearerToken;
	}

	// Setter
	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	@Override
	public String toString() {
		return "LogonResponse{" + "bearerToken='" + bearerToken + '\'' + '}';
	}
}
