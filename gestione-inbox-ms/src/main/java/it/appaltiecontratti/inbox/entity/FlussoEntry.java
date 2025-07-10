package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class FlussoEntry {

	private Long numComunicazione;
	private Long statoComunicazione;
	private String tipoInvio;
	private Long tipologiaFaseEsecuzione;	
	private Long numeroFase;
	private String codFiscSa;
	private String denominazioneSa;
	private String nomeAutoreInvio;	
	private String codOgg;
	private Date dataRicezione;
	private Date dataImportazione;
	private String messaggioErrore;	
	private String idEgov;	
	private String requestPayload;
	private String versionRequestPayload;	
	
	public Long getNumComunicazione() {
		return numComunicazione;
	}
	public void setNumComunicazione(Long numComunicazione) {
		this.numComunicazione = numComunicazione;
	}
	public Long getStatoComunicazione() {
		return statoComunicazione;
	}
	public void setStatoComunicazione(Long statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public Long getTipologiaFaseEsecuzione() {
		return tipologiaFaseEsecuzione;
	}
	public void setTipologiaFaseEsecuzione(Long tipologiaFaseEsecuzione) {
		this.tipologiaFaseEsecuzione = tipologiaFaseEsecuzione;
	}
	public Long getNumeroFase() {
		return numeroFase;
	}
	public void setNumeroFase(Long numeroFase) {
		this.numeroFase = numeroFase;
	}
	public String getCodFiscSa() {
		return codFiscSa;
	}
	public void setCodFiscSa(String codFiscSa) {
		this.codFiscSa = codFiscSa;
	}
	public String getDenominazioneSa() {
		return denominazioneSa;
	}
	public void setDenominazioneSa(String denominazioneSa) {
		this.denominazioneSa = denominazioneSa;
	}
	public String getNomeAutoreInvio() {
		return nomeAutoreInvio;
	}
	public void setNomeAutoreInvio(String nomeAutoreInvio) {
		this.nomeAutoreInvio = nomeAutoreInvio;
	}
	
	public String getCodOgg() {
		return codOgg;
	}
	public void setCodOgg(String codOgg) {
		this.codOgg = codOgg;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Date getDataImportazione() {
		return dataImportazione;
	}
	public void setDataImportazione(Date dataImportazione) {
		this.dataImportazione = dataImportazione;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public String getIdEgov() {
		return idEgov;
	}
	public void setIdEgov(String idEgov) {
		this.idEgov = idEgov;
	}
	public String getRequestPayload() {
		return requestPayload;
	}
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}
	public String getVersionRequestPayload() {
		return versionRequestPayload;
	}
	public void setVersionRequestPayload(String versionRequestPayload) {
		this.versionRequestPayload = versionRequestPayload;
	}
	
	
}
