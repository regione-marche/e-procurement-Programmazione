package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

public class GaraUpdateForm {

	private Long codGara;
	@XSSValidation
	private String oggetto;
	@XSSValidation
	private String tipoSettore;
	private double importoGara;
	@XSSValidation
	private String codiceTecnico;
	private Long ricostruzioneAlluvione;
	@XSSValidation
	private String indirizzoSede;
	@XSSValidation
	private String comuneSede;
	@XSSValidation
	private String provinciaSede;
	private Long flagSaAgente;
	private Long tipologiaStazioneAppaltante;
	@XSSValidation
	private String denomSoggStazioneAppaltante;
	@XSSValidation
	private String cfAgenteStazioneAppaltante;
	private Long tipologiaProcedura;
	private Long flagStipula;
	private Long tipoApp;
	private Long durataAcquadro;
	private PubblicitaGaraInsertForm pubblicitaGara;
	private Long ufficio;
	private Date dataLetteraInvito;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipoSettore() {
		return tipoSettore;
	}

	public void setTipoSettore(String tipoSettore) {
		this.tipoSettore = tipoSettore;
	}

	public double getImportoGara() {
		return importoGara;
	}

	public void setImportoGara(double importoGara) {
		this.importoGara = importoGara;
	}

	public String getCodiceTecnico() {
		return codiceTecnico;
	}

	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}

	public Long getRicostruzioneAlluvione() {
		return ricostruzioneAlluvione;
	}

	public void setRicostruzioneAlluvione(Long ricostruzioneAlluvione) {
		this.ricostruzioneAlluvione = ricostruzioneAlluvione;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public String getComuneSede() {
		return comuneSede;
	}

	public void setComuneSede(String comuneSede) {
		this.comuneSede = comuneSede;
	}

	public String getProvinciaSede() {
		return provinciaSede;
	}

	public void setProvinciaSede(String provinciaSede) {
		this.provinciaSede = provinciaSede;
	}

	public Long getFlagSaAgente() {
		return flagSaAgente;
	}

	public void setFlagSaAgente(Long flagSaAgente) {
		this.flagSaAgente = flagSaAgente;
	}

	public Long getTipologiaStazioneAppaltante() {
		return tipologiaStazioneAppaltante;
	}

	public void setTipologiaStazioneAppaltante(Long tipologiaStazioneAppaltante) {
		this.tipologiaStazioneAppaltante = tipologiaStazioneAppaltante;
	}

	public String getDenomSoggStazioneAppaltante() {
		return denomSoggStazioneAppaltante;
	}

	public void setDenomSoggStazioneAppaltante(String denomSoggStazioneAppaltante) {
		this.denomSoggStazioneAppaltante = denomSoggStazioneAppaltante;
	}

	public String getCfAgenteStazioneAppaltante() {
		return cfAgenteStazioneAppaltante;
	}

	public void setCfAgenteStazioneAppaltante(String cfAgenteStazioneAppaltante) {
		this.cfAgenteStazioneAppaltante = cfAgenteStazioneAppaltante;
	}

	public Long getTipologiaProcedura() {
		return tipologiaProcedura;
	}

	public void setTipologiaProcedura(Long tipologiaProcedura) {
		this.tipologiaProcedura = tipologiaProcedura;
	}

	public Long getFlagStipula() {
		return flagStipula;
	}

	public void setFlagStipula(Long flagStipula) {
		this.flagStipula = flagStipula;
	}

	public Long getTipoApp() {
		return tipoApp;
	}

	public void setTipoApp(Long tipoApp) {
		this.tipoApp = tipoApp;
	}

	public Long getDurataAcquadro() {
		return durataAcquadro;
	}

	public void setDurataAcquadro(Long durataAcquadro) {
		this.durataAcquadro = durataAcquadro;
	}

	public PubblicitaGaraInsertForm getPubblicitaGara() {
		return pubblicitaGara;
	}

	public void setPubblicitaGara(PubblicitaGaraInsertForm pubblicitaGara) {
		this.pubblicitaGara = pubblicitaGara;
	}

	public Long getUfficio() {
		return ufficio;
	}

	public void setUfficio(Long ufficio) {
		this.ufficio = ufficio;
	}

	public Date getDataLetteraInvito() {
		return dataLetteraInvito;
	}

	public void setDataLetteraInvito(Date dataLetteraInvito) {
		this.dataLetteraInvito = dataLetteraInvito;
	}

	

}
