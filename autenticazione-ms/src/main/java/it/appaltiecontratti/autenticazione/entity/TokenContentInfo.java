package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

public class TokenContentInfo implements Serializable{
	
	private static final long serialVersionUID = 2003477846360659329L;

	private String token;
	
	private String refreshToken;
	
	private boolean uffintOk;
	
	private boolean userExists;
	
	private String loa;
	
	private Boolean abilitaRegistrazione;
	
	private String ssoJwtToken;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public boolean isUffintOk() {
		return uffintOk;
	}

	public void setUffintOk(boolean uffintOk) {
		this.uffintOk = uffintOk;
	}

	public boolean isUserExists() {
		return userExists;
	}

	public void setUserExists(boolean userExists) {
		this.userExists = userExists;
	}

	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	public Boolean getAbilitaRegistrazione() {
		return abilitaRegistrazione;
	}

	public void setAbilitaRegistrazione(Boolean abilitaRegistrazione) {
		this.abilitaRegistrazione = abilitaRegistrazione;
	}
	
	public String getSsoJwtToken() {
		return ssoJwtToken;
	}

	public void setSsoJwtToken(String ssoJwtToken) {
		this.ssoJwtToken = ssoJwtToken;
	}

}
