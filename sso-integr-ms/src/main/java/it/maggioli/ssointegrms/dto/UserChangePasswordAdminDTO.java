package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserChangePasswordAdminDTO implements Serializable {

	private static final long serialVersionUID = 5873253439130524241L;

	@NotBlank
	@Size(min = 1, max = 30)
	private String password;
	@NotBlank
	@Size(min = 1, max = 30)
	private String confermaPassword;

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

}
