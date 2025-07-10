package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SSOTokenInfo implements Serializable {

	private static final long serialVersionUID = -4941258760863384459L;

	private String jwtToken;
	private String refreshToken;
	private Date createdAt;
	private String loa;
	
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	@Override
	public String toString() {
		return "SSOTokenInfo{" +
				"jwtToken='" + jwtToken + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
