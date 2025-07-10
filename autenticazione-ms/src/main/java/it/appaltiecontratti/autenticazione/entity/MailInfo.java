package it.appaltiecontratti.autenticazione.entity;

public class MailInfo {

	private String host;
	private Integer port;
	private String mailMittente;
	private String username;
	private String password;
	private String timeout;
	private String protocollo;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	public String getMailMittente() {
		return mailMittente;
	}

	public void setMailMittente(String mailMittente) {
		this.mailMittente = mailMittente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
}
