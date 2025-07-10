package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_mail")
public class WMail implements Serializable {

	private static final long serialVersionUID = 5900081146186451690L;

	@EmbeddedId
	private WMailId id;

	@Size(min = 0, max = 100)
	@Column(name = "server")
	private String server;

	@Size(min = 0, max = 100)
	@Column(name = "porta")
	private String porta;

	@Size(min = 0, max = 100)
	@Column(name = "protocollo")
	private String protocollo;

	@Size(min = 0, max = 100)
	@Column(name = "timeout")
	private String timeout;

	@Size(min = 0, max = 1)
	@Column(name = "tracciatura_messaggi")
	private String tracciaturaMessaggi;

	@Size(min = 0, max = 100)
	@Column(name = "mail_mittente")
	private String mailMittente;

	@Size(min = 0, max = 100)
	@Column(name = "password")
	private String password;

	@Size(min = 0, max = 100)
	@Column(name = "id_user")
	private String idUser;

	@Size(min = 0, max = 100)
	@Column(name = "dim_tot_allegati")
	private String dimTotaleAllegati;

	@Size(min = 0, max = 6)
	@Column(name = "delay")
	private String delay;

	public WMailId getId() {
		return id;
	}

	public void setId(WMailId id) {
		this.id = id;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getTracciaturaMessaggi() {
		return tracciaturaMessaggi;
	}

	public void setTracciaturaMessaggi(String tracciaturaMessaggi) {
		this.tracciaturaMessaggi = tracciaturaMessaggi;
	}

	public String getMailMittente() {
		return mailMittente;
	}

	public void setMailMittente(String mailMittente) {
		this.mailMittente = mailMittente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDimTotaleAllegati() {
		return dimTotaleAllegati;
	}

	public void setDimTotaleAllegati(String dimTotaleAllegati) {
		this.dimTotaleAllegati = dimTotaleAllegati;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

}
