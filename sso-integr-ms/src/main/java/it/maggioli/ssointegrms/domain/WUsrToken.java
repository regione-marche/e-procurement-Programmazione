package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_usrtoken")
public class WUsrToken implements Serializable {

	private static final long serialVersionUID = -3104187276977987054L;

	@Id
	@NotNull
	@Column(name = "syscon")
	private Long syscon;

	@NotBlank
	@Size(min = 1, max = 50)
	@Column(name = "token")
	private String token;

	@NotNull
	@Column(name = "regtime")
	private Date dataCreazioneToken;

	@NotBlank
	@Size(min = 1, max = 25)
	@Column(name = "tokentype")
	private String tokenType;

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDataCreazioneToken() {
		return dataCreazioneToken;
	}

	public void setDataCreazioneToken(Date dataCreazioneToken) {
		this.dataCreazioneToken = dataCreazioneToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "WUsrToken [syscon=" + syscon + ", token=" + token + ", dataCreazioneToken=" + dataCreazioneToken
				+ ", tokenType=" + tokenType + "]";
	}

}
