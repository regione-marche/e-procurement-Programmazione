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
@Table(name = "g_loginko")
public class LoginKO implements Serializable {

	private static final long serialVersionUID = 1242286369310485429L;

	@Id
	@NotNull
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Size(min = 1, max = 60)
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "logintime")
	private Date loginTime;

	@Size(min = 0, max = 40)
	@Column(name = "ipaddress")
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

	@Override
	public String toString() {
		return "LoginKO [id=" + id + ", username=" + username + ", loginTime=" + loginTime + ", ipAddress=" + ipAddress
				+ "]";
	}

}
