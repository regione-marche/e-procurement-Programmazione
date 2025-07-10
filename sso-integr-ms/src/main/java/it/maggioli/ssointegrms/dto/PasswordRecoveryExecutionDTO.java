package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class PasswordRecoveryExecutionDTO implements Serializable {

	private static final long serialVersionUID = -5954350509502028287L;

	@NotBlank(message = "Token obbligatorio")
	private String token;

	@Size(min = 1, max = 30)
	@NotBlank(message = "Password obbligatoria")
	private String password;

	@Size(min = 1, max = 30)
	@NotBlank(message = "Conferma password obbligatoria")
	private String confermaPassword;

	@JsonIgnore
	private String ipAddress;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
