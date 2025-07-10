package it.maggioli.ssointegrms.model;

/**
 * Classe che mappa il clientId applicativo maggioli con il redirectUrl
 * 
 * @author Cristiano Perin
 *
 */
public class SitarApplicationInfo {

	private String redirectUrl;
	private String clientId;
	private boolean internalAuthenticationActive;
	private String internalAuthenticationReturnUrl;
	private String internalAuthenticationRegistrationUrl;
	private boolean iamAuthenticationActive;
	private String iamAuthenticationReturnUrl;
	private boolean federaAuthenticationActive;
	private String federaAuthenticationReturnUrl;

	public SitarApplicationInfo(String redirectUrl, String clientId) {
		this.redirectUrl = redirectUrl;
		this.clientId = clientId;
		this.internalAuthenticationActive = false;
		this.internalAuthenticationReturnUrl = null;
		this.internalAuthenticationRegistrationUrl = null;
		this.iamAuthenticationActive = false;
		this.iamAuthenticationReturnUrl = null;
		this.federaAuthenticationActive = false;
		this.federaAuthenticationReturnUrl = null;
	}

	public SitarApplicationInfo(String redirectUrl, String clientId, boolean internalAuthenticationActive,
			String internalAuthenticationReturnUrl, String internalAuthenticationRegistrationUrl,
			boolean iamAuthenticationActive, String iamAuthenticationReturnUrl, boolean federaAuthenticationActive,
			String federaAuthenticationReturnUrl) {
		this.redirectUrl = redirectUrl;
		this.clientId = clientId;
		this.internalAuthenticationActive = internalAuthenticationActive;
		this.internalAuthenticationReturnUrl = internalAuthenticationReturnUrl;
		this.internalAuthenticationRegistrationUrl = internalAuthenticationRegistrationUrl;
		this.iamAuthenticationActive = iamAuthenticationActive;
		this.iamAuthenticationReturnUrl = iamAuthenticationReturnUrl;
		this.federaAuthenticationActive = federaAuthenticationActive;
		this.federaAuthenticationReturnUrl = federaAuthenticationReturnUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean isInternalAuthenticationActive() {
		return internalAuthenticationActive;
	}

	public void setInternalAuthenticationActive(boolean internalAuthenticationActive) {
		this.internalAuthenticationActive = internalAuthenticationActive;
	}

	public String getInternalAuthenticationReturnUrl() {
		return internalAuthenticationReturnUrl;
	}

	public void setInternalAuthenticationReturnUrl(String internalAuthenticationReturnUrl) {
		this.internalAuthenticationReturnUrl = internalAuthenticationReturnUrl;
	}

	public String getInternalAuthenticationRegistrationUrl() {
		return internalAuthenticationRegistrationUrl;
	}

	public void setInternalAuthenticationRegistrationUrl(String internalAuthenticationRegistrationUrl) {
		this.internalAuthenticationRegistrationUrl = internalAuthenticationRegistrationUrl;
	}

	public boolean isIamAuthenticationActive() {
		return iamAuthenticationActive;
	}

	public void setIamAuthenticationActive(boolean iamAuthenticationActive) {
		this.iamAuthenticationActive = iamAuthenticationActive;
	}

	public String getIamAuthenticationReturnUrl() {
		return iamAuthenticationReturnUrl;
	}

	public void setIamAuthenticationReturnUrl(String iamAuthenticationReturnUrl) {
		this.iamAuthenticationReturnUrl = iamAuthenticationReturnUrl;
	}

	public boolean isFederaAuthenticationActive() {
		return federaAuthenticationActive;
	}

	public void setFederaAuthenticationActive(boolean federaAuthenticationActive) {
		this.federaAuthenticationActive = federaAuthenticationActive;
	}

	public String getFederaAuthenticationReturnUrl() {
		return federaAuthenticationReturnUrl;
	}

	public void setFederaAuthenticationReturnUrl(String federaAuthenticationReturnUrl) {
		this.federaAuthenticationReturnUrl = federaAuthenticationReturnUrl;
	}

}
