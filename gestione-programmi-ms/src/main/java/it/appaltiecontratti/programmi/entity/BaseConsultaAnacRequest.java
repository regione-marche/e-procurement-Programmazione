package it.appaltiecontratti.programmi.entity;

public class BaseConsultaAnacRequest {

	private String password;
	private String username;
	private String passwordSimog;
	private String usernameSimog;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordSimog() {
		return passwordSimog;
	}

	public void setPasswordSimog(String passwordSimog) {
		this.passwordSimog = passwordSimog;
	}

	public String getUsernameSimog() {
		return usernameSimog;
	}

	public void setUsernameSimog(String usernameSimog) {
		this.usernameSimog = usernameSimog;
	}
}
