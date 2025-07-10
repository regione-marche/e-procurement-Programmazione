package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class SmartCigEntry {

	// codgara
	private Long codGara;
	// cig w9lott
	private String smartCig;
	// oggetto w9gara w9lott
	private String oggetto;
	// tipo contratto lotto
	private String tipoAppalto;
	// id_scelta_contraente w9lott
	private Long sceltaContraente;
	// ID_MODO_GARA w9lott
	private Long criteriAggiudicazione;
	// FLsg ente speciale w9lott
	private String tipoSettore;
	// tipoApp w9gara
	private Long modalitaRealizzazione;
	// Codice tecnico gara -lotto
	private RupEntry rup;
	// codein per gara e lotto
	private String stazioneAppaltante;
	// idufficio per gara
	private Long idUfficio;
	// indsede w9gara
	private String indirizzoSede;
	// comsede w9gara
	private String comuneSede;
	// provsede w9gara
	private String provinciaSede;
	// w9gara
	private Long flagSaAgente;
	// w9gara
	private String denomSoggStazioneAppaltante;
	// w9gara
	private String cfAgenteStazioneAppaltante;
	// w9gara
	private String tipologiaProcedura;
	// importo netto lotto
	private Double importoNetto;
	// importo totale lotto
	private Double importoTotale;
	// importo sicurezza lotto
	private Double importoSicurezza;
	// w9lott
	private String esenteCup;
	// w9lott
	private String cup;
	// cpv lotto
	private String cpv;
	// lotto
	private String categoriaPrev;
	// lotto
	private String classeCategoriaPrev;
	// lotto
	private String luogoIstat;
	// lotto
	private String luogoNuts;

	private String nominativoStazioneAppaltante;
	
	private Long tipologiaStazioneAppaltante;
	
	private Long flagStipula;
	
	private UffEntry ufficio;
	
	private String descCpv;

	private Long situazione;
	
	private Date dataUltimazione;
	
	private Date dataVerbInizio;
	
	private Double importoCertificato;
	
	private Long idRicevuto;
	
	private String autore;
	
	private Long daExport;
	
	private Long syscon;
	
	private boolean readOnly;
	
	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getNominativoStazioneAppaltante() {
		return nominativoStazioneAppaltante;
	}

	public void setNominativoStazioneAppaltante(String nominativoStazioneAppaltante) {
		this.nominativoStazioneAppaltante = nominativoStazioneAppaltante;
	}

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

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

	public RupEntry getRup() {
		return rup;
	}

	public void setRup(RupEntry rup) {
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

	public String getTipologiaProcedura() {
		return tipologiaProcedura;
	}

	public void setTipologiaProcedura(String tipologiaProcedura) {
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

	public UffEntry getUfficio() {
		return ufficio;
	}

	public void setUfficio(UffEntry ufficio) {
		this.ufficio = ufficio;
	}

	public String getDescCpv() {
		return descCpv;
	}

	public void setDescCpv(String descCpv) {
		this.descCpv = descCpv;
	}

	public Long getSituazione() {
		return situazione;
	}

	public void setSituazione(Long situazione) {
		this.situazione = situazione;
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

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getDaExport() {
		return daExport;
	}

	public void setDaExport(Long daExport) {
		this.daExport = daExport;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	

}
