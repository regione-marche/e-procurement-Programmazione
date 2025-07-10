package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class PasswordRecoveryRequestDTO implements Serializable {

	private static final long serialVersionUID = -9196024605449382808L;

	@NotBlank(message = "Username obbligatorio")
	private String username;
	private String currentUrl;
	private String captchaSolution;
	@JsonIgnore
	private String ipAddress;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getCaptchaSolution() {
		return captchaSolution;
	}

	public void setCaptchaSolution(String captchaSolution) {
		this.captchaSolution = captchaSolution;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "PasswordRecoveryRequestDTO{" +
				"username='" + username + '\'' +
				", currentUrl='" + currentUrl + '\'' +
				'}';
	}
}
