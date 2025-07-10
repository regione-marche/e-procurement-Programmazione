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
public class ChangePasswordDTO implements Serializable {

	private static final long serialVersionUID = 6385394233401541948L;

	@Size(min = 1, max = 60)
	@NotBlank(message = "Username obbligatorio")
	private String username;
	
	@Size(min = 1, max = 30)
	@NotBlank(message = "Vecchia Password obbligatoria")
	private String oldPassword;
	
	@Size(min = 1, max = 30)
	@NotBlank(message = "Nuova Password obbligatoria")
	private String newPassword;
	
	@Size(min = 1, max = 30)
	@NotBlank(message = "Conferma Nuova Password obbligatoria")
	private String confirmNewPassword;
	
	@JsonIgnore
	private String ipAddress;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
