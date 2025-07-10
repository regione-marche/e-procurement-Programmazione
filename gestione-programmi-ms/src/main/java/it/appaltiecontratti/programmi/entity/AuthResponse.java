package it.appaltiecontratti.programmi.entity;

public class AuthResponse {

	public static final String TOKEN_NOT_FOUND = "0";	
	public static final String TOKEN_INVALID = "1";
	public static final String TOKEN_EXPIRED = "2";
	
	private boolean isValid;
	private String errorMessage;
	private String errorCode;
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
