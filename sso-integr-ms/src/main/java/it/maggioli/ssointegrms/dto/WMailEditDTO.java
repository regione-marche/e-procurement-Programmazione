package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailEditDTO implements Serializable {

	private static final long serialVersionUID = -6055068005335187150L;

	@NotBlank
	@Size(min = 1, max = 16)
	private String idCfg;
	@NotBlank
	@Size(min = 1, max = 100)
	private String server;
	private Long porta;
	@Size(min = 0, max = 100)
	private String protocollo;
	@Size(min = 0, max = 100)
	private String timeout;
	@Size(min = 0, max = 1)
	private String tracciaturaMessaggi;
	@NotBlank
	@Size(min = 1, max = 100)
	private String mailMittente;
	@Size(min = 0, max = 100)
	private String idUser;
	private Long dimTotaleAllegati;
	private Long delay;

	public String getIdCfg() {
		return idCfg;
	}

	public void setIdCfg(String idCfg) {
		this.idCfg = idCfg;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Long getPorta() {
		return porta;
	}

	public void setPorta(Long porta) {
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

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Long getDimTotaleAllegati() {
		return dimTotaleAllegati;
	}

	public void setDimTotaleAllegati(Long dimTotaleAllegati) {
		this.dimTotaleAllegati = dimTotaleAllegati;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	@Override
	public String toString() {
		return "WMailEditDTO [idCfg=" + idCfg + ", server=" + server + ", porta=" + porta + ", protocollo=" + protocollo
				+ ", timeout=" + timeout + ", tracciaturaMessaggi=" + tracciaturaMessaggi + ", mailMittente="
				+ mailMittente + ", idUser=" + idUser + ", dimTotaleAllegati=" + dimTotaleAllegati + ", delay=" + delay
				+ "]";
	}

}
