package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class AuthenticationMethodsDTO implements Serializable {

	private static final long serialVersionUID = 5618102622620427669L;

	private boolean internal;
	private String internalRegistration;
	private boolean spid;
	private boolean cie;
	private boolean cns;
	private boolean eidas;
	private boolean iam;
	private boolean federa;

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public String getInternalRegistration() {
		return internalRegistration;
	}

	public void setInternalRegistration(String internalRegistration) {
		this.internalRegistration = internalRegistration;
	}

	public boolean isSpid() {
		return spid;
	}

	public void setSpid(boolean spid) {
		this.spid = spid;
	}

	public boolean isCie() {
		return cie;
	}

	public void setCie(boolean cie) {
		this.cie = cie;
	}

	public boolean isCns() {
		return cns;
	}

	public void setCns(boolean cns) {
		this.cns = cns;
	}

	public boolean isEidas() {
		return eidas;
	}

	public void setEidas(boolean eidas) {
		this.eidas = eidas;
	}

	public boolean isIam() {
		return iam;
	}

	public void setIam(boolean iam) {
		this.iam = iam;
	}

	public boolean isFedera() {
		return federa;
	}

	public void setFedera(boolean federa) {
		this.federa = federa;
	}

}
