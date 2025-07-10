package it.maggioli.ssointegrms.model;

/**
 * Classe che mappa il clientId applicativo maggioli con il redirectUrl, il
 * clientId locale, il clientId MIMS e la posizione della chiave privata
 * 
 * @author Cristiano Perin
 *
 */
public class ApplicationInfo {

	private String redirectUrl;
	private String localClientId;
	private String mimsClientId;
	private String privateKeyLocation;
	private boolean internalAuthenticationActive;
	private String internalAuthenticationReturnUrl;
	private String internalAuthenticationRegistrationUrl;
	private boolean spidAuthenticationActive;
	private boolean cieAuthenticationActive;
	private boolean cnsAuthenticationActive;
	private boolean eidasAuthenticationActive;

	public ApplicationInfo(String redirectUrl, String localClientId, String mimsClientId, String privateKeyLocation) {
		this.redirectUrl = redirectUrl;
		this.localClientId = localClientId;
		this.mimsClientId = mimsClientId;
		this.privateKeyLocation = privateKeyLocation;
		this.internalAuthenticationActive = false;
		this.internalAuthenticationReturnUrl = null;
		this.internalAuthenticationRegistrationUrl = null;
		this.spidAuthenticationActive = false;
		this.cieAuthenticationActive = false;
		this.cnsAuthenticationActive = false;
		this.eidasAuthenticationActive = false;
	}

	public ApplicationInfo(String redirectUrl, String localClientId, String mimsClientId, String privateKeyLocation,
			boolean internalAuthenticationActive, String internalAuthenticationReturnUrl,
			String internalAuthenticationRegistrationUrl, boolean spidAuthenticationActive,
			boolean cieAuthenticationActive, boolean cnsAuthenticationActive, boolean eidasAuthenticationActive) {
		this.redirectUrl = redirectUrl;
		this.localClientId = localClientId;
		this.mimsClientId = mimsClientId;
		this.privateKeyLocation = privateKeyLocation;
		this.internalAuthenticationActive = internalAuthenticationActive;
		this.internalAuthenticationReturnUrl = internalAuthenticationReturnUrl;
		this.internalAuthenticationRegistrationUrl = internalAuthenticationRegistrationUrl;
		this.spidAuthenticationActive = spidAuthenticationActive;
		this.cieAuthenticationActive = cieAuthenticationActive;
		this.cnsAuthenticationActive = cnsAuthenticationActive;
		this.eidasAuthenticationActive = eidasAuthenticationActive;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getLocalClientId() {
		return localClientId;
	}

	public void setLocalClientId(String localClientId) {
		this.localClientId = localClientId;
	}

	public String getMimsClientId() {
		return mimsClientId;
	}

	public void setMimsClientId(String mimsClientId) {
		this.mimsClientId = mimsClientId;
	}

	public String getPrivateKeyLocation() {
		return privateKeyLocation;
	}

	public void setPrivateKeyLocation(String privateKeyLocation) {
		this.privateKeyLocation = privateKeyLocation;
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

	public boolean isSpidAuthenticationActive() {
		return spidAuthenticationActive;
	}

	public void setSpidAuthenticationActive(boolean spidAuthenticationActive) {
		this.spidAuthenticationActive = spidAuthenticationActive;
	}

	public boolean isCieAuthenticationActive() {
		return cieAuthenticationActive;
	}

	public void setCieAuthenticationActive(boolean cieAuthenticationActive) {
		this.cieAuthenticationActive = cieAuthenticationActive;
	}

	public boolean isCnsAuthenticationActive() {
		return cnsAuthenticationActive;
	}

	public void setCnsAuthenticationActive(boolean cnsAuthenticationActive) {
		this.cnsAuthenticationActive = cnsAuthenticationActive;
	}

	public boolean isEidasAuthenticationActive() {
		return eidasAuthenticationActive;
	}

	public void setEidasAuthenticationActive(boolean eidasAuthenticationActive) {
		this.eidasAuthenticationActive = eidasAuthenticationActive;
	}

}
