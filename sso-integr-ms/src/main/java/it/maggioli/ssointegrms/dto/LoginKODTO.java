package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class LoginKODTO implements Serializable {

	private static final long serialVersionUID = 7758292376215650198L;

	private Long id;
	private String username;
	private Date loginTime;
	private String ipAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
