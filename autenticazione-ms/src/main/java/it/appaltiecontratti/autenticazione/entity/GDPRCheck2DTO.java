package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class GDPRCheck2DTO implements Serializable {

	private static final long serialVersionUID = -2846550621213225663L;

	private boolean allowedCharacters;
	private boolean lowerCaseCharacters;
	private boolean upperCaseCharacters;
	private boolean numberCharacters;
	private boolean specialCharacters;

	public boolean isAllowedCharacters() {
		return allowedCharacters;
	}

	public void setAllowedCharacters(boolean allowedCharacters) {
		this.allowedCharacters = allowedCharacters;
	}

	public boolean isLowerCaseCharacters() {
		return lowerCaseCharacters;
	}

	public void setLowerCaseCharacters(boolean lowerCaseCharacters) {
		this.lowerCaseCharacters = lowerCaseCharacters;
	}

	public boolean isUpperCaseCharacters() {
		return upperCaseCharacters;
	}

	public void setUpperCaseCharacters(boolean upperCaseCharacters) {
		this.upperCaseCharacters = upperCaseCharacters;
	}

	public boolean isNumberCharacters() {
		return numberCharacters;
	}

	public void setNumberCharacters(boolean numberCharacters) {
		this.numberCharacters = numberCharacters;
	}

	public boolean isSpecialCharacters() {
		return specialCharacters;
	}

	public void setSpecialCharacters(boolean specialCharacters) {
		this.specialCharacters = specialCharacters;
	}

	public boolean pass() {
		return allowedCharacters && lowerCaseCharacters && upperCaseCharacters && numberCharacters && specialCharacters;
	}
}
