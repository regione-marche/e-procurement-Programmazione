package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailDTO implements Serializable {

	private static final long serialVersionUID = 7433357719746915666L;

	private String codApp;
	private String idCfg;
	private String idCfgReadonly;
	private String server;
	private String porta;
	private String protocollo;
	private String timeout;
	private String tracciaturaMessaggi;
	private String mailMittente;
	private String idUser;
	private String dimTotaleAllegati;
	private String delay;
	private boolean passwordImpostata;

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getIdCfg() {
		return idCfg;
	}

	public void setIdCfg(String idCfg) {
		this.idCfg = idCfg;
	}

	public String getIdCfgReadonly() {
		return idCfgReadonly;
	}

	public void setIdCfgReadonly(String idCfgReadonly) {
		this.idCfgReadonly = idCfgReadonly;
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

	public boolean isPasswordImpostata() {
		return passwordImpostata;
	}

	public void setPasswordImpostata(boolean passwordImpostata) {
		this.passwordImpostata = passwordImpostata;
	}

	@Override
	public String toString() {
		return "WMailDTO [codApp=" + codApp + ", idCfg=" + idCfg + ", idCfgReadonly=" + idCfgReadonly + ", server="
				+ server + ", porta=" + porta + ", protocollo=" + protocollo + ", timeout=" + timeout
				+ ", tracciaturaMessaggi=" + tracciaturaMessaggi + ", mailMittente=" + mailMittente + ", idUser="
				+ idUser + ", dimTotaleAllegati=" + dimTotaleAllegati + ", delay=" + delay + ", passwordImpostata="
				+ passwordImpostata + "]";
	}

}
