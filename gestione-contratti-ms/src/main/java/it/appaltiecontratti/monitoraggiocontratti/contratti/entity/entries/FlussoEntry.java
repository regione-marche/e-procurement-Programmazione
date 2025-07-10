package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.io.Serializable;
import java.util.Date;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlussoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	private Long id;

	private Long area;

	private Long key01;

	private Long key02;

	private Long key03;

	private Long key04;

	private Long tipoInvio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataInvio;

	private Long idAutore;

	private Long idComunicazione;

	@XSSValidation
	private String codiceFiscaleSA;

	@XSSValidation
	private String oggetto;

	@XSSValidation
	private String versioneXML;

	@XSSValidation
	private String autore;

	@XSSValidation
	private String note;

	@XSSValidation
	private String json;

	@XSSValidation
	private String govwayMessageId;

	@XSSValidation
	private String govwayTransactionId;	

	public String getGovwayMessageId() {
		return govwayMessageId;
	}

	public void setGovwayMessageId(String govwayMessageId) {
		this.govwayMessageId = govwayMessageId;
	}

	public String getGovwayTransactionId() {
		return govwayTransactionId;
	}

	public void setGovwayTransactionId(String govwayTransactionId) {
		this.govwayTransactionId = govwayTransactionId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getArea() {
		return area;
	}

	public void setTipoInvio(Long tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public Long getTipoInvio() {
		return tipoInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setKey01(Long key01) {
		this.key01 = key01;
	}

	public Long getKey01() {
		return key01;
	}

	public void setKey02(Long key02) {
		this.key02 = key02;
	}

	public Long getKey02() {
		return key02;
	}

	public void setKey03(Long key03) {
		this.key03 = key03;
	}

	public Long getKey03() {
		return key03;
	}

	public void setKey04(Long key04) {
		this.key04 = key04;
	}

	public Long getKey04() {
		return key04;
	}

	public void setIdAutore(Long idAutore) {
		this.idAutore = idAutore;
	}

	public Long getIdAutore() {
		return idAutore;
	}

	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public Long getIdComunicazione() {
		return idComunicazione;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = StringUtils.stripToNull(codiceFiscaleSA);
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = StringUtils.stripToNull(oggetto);
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setVersioneXML(String versioneXML) {
		this.versioneXML = StringUtils.stripToNull(versioneXML);
	}

	public String getVersioneXML() {
		return versioneXML;
	}

	public void setAutore(String autore) {
		this.autore = StringUtils.stripToNull(autore);
	}

	public String getAutore() {
		return autore;
	}

	public void setNote(String note) {
		this.note = StringUtils.stripToNull(note);
	}

	public String getNote() {
		return note;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getJson() {
		return json;
	}

}
