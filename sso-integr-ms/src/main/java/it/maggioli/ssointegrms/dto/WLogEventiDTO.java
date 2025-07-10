package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WLogEventiDTO implements Serializable {

	private static final long serialVersionUID = -5717246930594884476L;

	private Long idEvento;
	private String codApp;
	private String codProfilo;
	private Long syscon;
	private String descrizioneUtente;
	private String ipEvento;
	private Date dataOra;
	private String oggettoEvento;
	private Integer livelloEvento;
	private String codiceEvento;
	private String descrizione;
	private String errorMessage;
	private String dataOraFormatted;

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getCodProfilo() {
		return codProfilo;
	}

	public void setCodProfilo(String codProfilo) {
		this.codProfilo = codProfilo;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getDescrizioneUtente() {
		return descrizioneUtente;
	}

	public void setDescrizioneUtente(String descrizioneUtente) {
		this.descrizioneUtente = descrizioneUtente;
	}

	public String getIpEvento() {
		return ipEvento;
	}

	public void setIpEvento(String ipEvento) {
		this.ipEvento = ipEvento;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getOggettoEvento() {
		return oggettoEvento;
	}

	public void setOggettoEvento(String oggettoEvento) {
		this.oggettoEvento = oggettoEvento;
	}

	public Integer getLivelloEvento() {
		return livelloEvento;
	}

	public void setLivelloEvento(Integer livelloEvento) {
		this.livelloEvento = livelloEvento;
	}

	public String getCodiceEvento() {
		return codiceEvento;
	}

	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDataOraFormatted() {
		return dataOraFormatted;
	}

	public void setDataOraFormatted(String dataOraFormatted) {
		this.dataOraFormatted = dataOraFormatted;
	}

	@Override
	public String toString() {
		return "WLogEventiDTO [idEvento=" + idEvento + ", codApp=" + codApp + ", codProfilo=" + codProfilo + ", syscon="
				+ syscon + ", descrizioneUtente=" + descrizioneUtente + ", ipEvento=" + ipEvento + ", dataOra="
				+ dataOra + ", oggettoEvento=" + oggettoEvento + ", livelloEvento=" + livelloEvento + ", codiceEvento="
				+ codiceEvento + ", descrizione=" + descrizione + ", errorMessage=" + errorMessage + "]";
	}

}
