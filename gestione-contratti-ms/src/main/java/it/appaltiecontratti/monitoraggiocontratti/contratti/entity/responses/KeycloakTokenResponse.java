package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakTokenResponse {
	@JsonProperty("access_token")
	private String accessToken = null;
	@JsonProperty("expires_in")
	private float expiresIn = 0;
	@JsonProperty("refresh_expires_in")
	private float refreshExpiresIn = 0;
	@JsonProperty("token_type")
	private String tokenType = null;
	@JsonProperty("id_token")
	private String idToken = null;
	@JsonProperty("not-before-policy")
	private float notBeforePolicy = 0;
	@JsonProperty("scope")
	private String scope = null;
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public KeycloakTokenResponse accessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}
	/**
	 * @return the expiresIn
	 */
	public float getExpiresIn() {
		return expiresIn;
	}
	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(float expiresIn) {
		this.expiresIn = expiresIn;
	}
	/**
	 * @return the refreshExpiresIn
	 */
	public float getRefreshExpiresIn() {
		return refreshExpiresIn;
	}
	/**
	 * @param refreshExpiresIn the refreshExpiresIn to set
	 */
	public void setRefreshExpiresIn(float refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}
	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}
	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	/**
	 * @return the idToken
	 */
	public String getIdToken() {
		return idToken;
	}
	/**
	 * @param idToken the idToken to set
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	/**
	 * @return the notBeforePolicy
	 */
	public float getNotBeforePolicy() {
		return notBeforePolicy;
	}
	/**
	 * @param notBeforePolicy the notBeforePolicy to set
	 */
	public void setNotBeforePolicy(float notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "KeycloakTokenResponse [" + (accessToken != null ? "accessToken=" + accessToken + ", " : "")
				+ "expiresIn=" + expiresIn + ", refreshExpiresIn=" + refreshExpiresIn + ", "
				+ (tokenType != null ? "tokenType=" + tokenType + ", " : "")
				+ (idToken != null ? "idToken=" + idToken + ", " : "") + "notBeforePolicy=" + notBeforePolicy + ", "
				+ (scope != null ? "scope=" + scope : "") + "]";
	}

}
