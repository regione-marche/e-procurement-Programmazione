package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

public class SimogLoginOptions {

	private boolean success;
	private boolean delega;
	private boolean rpnt;
	private boolean errorRupCredentialsMissing;

	private boolean fatalError;
	private String errorMessage;

	private String username;
	private String password;
	private String cfRup;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isDelega() {
		return delega;
	}

	public void setDelega(boolean delega) {
		this.delega = delega;
	}

	public boolean isErrorRupCredentialsMissing() {
		return errorRupCredentialsMissing;
	}

	public void setErrorRupCredentialsMissing(boolean errorRupCredentialsMissing) {
		this.errorRupCredentialsMissing = errorRupCredentialsMissing;
	}

	public boolean isRpnt() {
		return rpnt;
	}

	public void setRpnt(boolean rpnt) {
		this.rpnt = rpnt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCfRup() {
		return cfRup;
	}

	public void setCfRup(String cfRup) {
		this.cfRup = cfRup;
	}

	public boolean isFatalError() {
		return fatalError;
	}

	public void setFatalError(boolean fatalError) {
		this.fatalError = fatalError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
