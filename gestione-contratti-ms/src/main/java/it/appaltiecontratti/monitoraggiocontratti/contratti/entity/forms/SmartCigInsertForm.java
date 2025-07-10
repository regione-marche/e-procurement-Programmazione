package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class SmartCigInsertForm {
	
	@NotNull
	// cig w9lott
	@XSSValidation
	private String smartCig;
	// oggetto w9gara w9lott
	@XSSValidation
	private String oggetto;
	// tipo contratto lotto
	@XSSValidation
	private String tipoAppalto;
	// id_scelta_contraente w9lott
	private Long sceltaContraente;
	// ID_MODO_GARA w9lott
	private Long criteriAggiudicazione;
	// FLsg ente speciale w9lott
	@XSSValidation
	private String tipoSettore;
	// tipoApp w9gara
	private Long modalitaRealizzazione;
	// Codice tecnico gara -lotto
	@XSSValidation
	private String rup;
	@NotNull
	// codein per gara e lotto
	@XSSValidation
	private String stazioneAppaltante;
	// idufficio per gara
	private Long idUfficio;
	// indsede w9gara
	@XSSValidation
	private String indirizzoSede;
	// comsede w9gara
	@XSSValidation
	private String comuneSede;
	//provsede w9gara
	@XSSValidation
	private String provinciaSede;
	// w9gara
	private Long flagSaAgente;
	// w9gara
	@XSSValidation
	private String denomSoggStazioneAppaltante;
	// w9gara
	@XSSValidation
	private String cfAgenteStazioneAppaltante;
	// w9gara
	private Long tipologiaProcedura;
	// importo netto lotto
	private Double importoNetto;
	// importo totale lotto
	private Double importoTotale;
	// importo sicurezza lotto
	private Double importoSicurezza;
	// w9lott
	@XSSValidation
	private String esenteCup;
	// w9lott
	@XSSValidation
	private String cup;
	// cpv lotto
	@XSSValidation
	private String cpv;
	// lotto
	@XSSValidation
	private String categoriaPrev;
	// lotto
	@XSSValidation
	private String classeCategoriaPrev;
	// lotto
	@XSSValidation
	private String luogoIstat;
	// lotto
	@XSSValidation
	private String luogoNuts;
	
	private Long tipologiaStazioneAppaltante;
	
	private Long flagStipula;
	
	private Long syscon;
	
	private Date dataUltimazione;
	
	private Date dataVerbInizio;
	
	private Double importoCertificato;
	
	
	public String getSmartCig() {
		return smartCig;
	}
	public void setSmartCig(String smartCig) {
		this.smartCig = smartCig;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTipoAppalto() {
		return tipoAppalto;
	}
	public void setTipoAppalto(String tipoAppalto) {
		this.tipoAppalto = tipoAppalto;
	}
	public Long getSceltaContraente() {
		return sceltaContraente;
	}
	public void setSceltaContraente(Long sceltaContraente) {
		this.sceltaContraente = sceltaContraente;
	}
	public Long getCriteriAggiudicazione() {
		return criteriAggiudicazione;
	}
	public void setCriteriAggiudicazione(Long criteriAggiudicazione) {
		this.criteriAggiudicazione = criteriAggiudicazione;
	}
	public String getTipoSettore() {
		return tipoSettore;
	}
	public void setTipoSettore(String tipoSettore) {
		this.tipoSettore = tipoSettore;
	}
	public Long getModalitaRealizzazione() {
		return modalitaRealizzazione;
	}
	public void setModalitaRealizzazione(Long modalitaRealizzazione) {
		this.modalitaRealizzazione = modalitaRealizzazione;
	}
	public String getRup() {
		return rup;
	}
	public void setRup(String rup) {
		this.rup = rup;
	}
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public Long getIdUfficio() {
		return idUfficio;
	}
	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
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
	public Double getImportoNetto() {
		return importoNetto;
	}
	public void setImportoNetto(Double importoNetto) {
		this.importoNetto = importoNetto;
	}
	public Double getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}
	public Double getImportoSicurezza() {
		return importoSicurezza;
	}
	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}
	public String getEsenteCup() {
		return esenteCup;
	}
	public void setEsenteCup(String esenteCup) {
		this.esenteCup = esenteCup;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getCpv() {
		return cpv;
	}
	public void setCpv(String cpv) {
		this.cpv = cpv;
	}
	public String getCategoriaPrev() {
		return categoriaPrev;
	}
	public void setCategoriaPrev(String categoriaPrev) {
		this.categoriaPrev = categoriaPrev;
	}
	public String getClasseCategoriaPrev() {
		return classeCategoriaPrev;
	}
	public void setClasseCategoriaPrev(String classeCategoriaPrev) {
		this.classeCategoriaPrev = classeCategoriaPrev;
	}
	public String getLuogoIstat() {
		return luogoIstat;
	}
	public void setLuogoIstat(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}
	public String getLuogoNuts() {
		return luogoNuts;
	}
	public void setLuogoNuts(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}
	public Long getTipologiaStazioneAppaltante() {
		return tipologiaStazioneAppaltante;
	}
	public void setTipologiaStazioneAppaltante(Long tipologiaStazioneAppaltante) {
		this.tipologiaStazioneAppaltante = tipologiaStazioneAppaltante;
	}
	public Long getFlagStipula() {
		return flagStipula;
	}
	public void setFlagStipula(Long flagStipula) {
		this.flagStipula = flagStipula;
	}
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	public Date getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Date getDataVerbInizio() {
		return dataVerbInizio;
	}
	public void setDataVerbInizio(Date dataVerbInizio) {
		this.dataVerbInizio = dataVerbInizio;
	}
	public Double getImportoCertificato() {
		return importoCertificato;
	}
	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}
	
}
