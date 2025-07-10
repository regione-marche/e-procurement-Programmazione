package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class GaraInsertForm {
	@ApiModelProperty(value = "codice univoco della gara")
	private Long codgara;
	@ApiModelProperty(value = "oggetto di gara")
	@XSSValidation
	private String oggetto;
	@ApiModelProperty(value = "situazione di gara")
	private Long situazione;
	@ApiModelProperty(value = "tipologia di gara")
	private Long tipoApp;
	@ApiModelProperty(value = "importo della gara")
	private double importoGara;
	private Long provenienzaDato;
	@XSSValidation
	private String identificativoGara;
	@XSSValidation
	private String tipoSettore;
	private Long modalitaIndizione;
	@XSSValidation
	private String cigQuadro;
	private Long sommaUrgenza;
	private Long numLotti;
	private Date dataPubblicazione;
	@XSSValidation
	private String codiceTecnico;
	private Long ricostruzioneAlluvione;
	private Long dispArtDlgs;
	private Long sisma;
	private Long versioneSimog;
	@XSSValidation
	private String codiceStazioneAppaltante;
	private Long idCentroDiCosto;
	@XSSValidation
	private String indirizzoSede;
	@XSSValidation
	private String comuneSede;
	@XSSValidation
	private String provinciaSede;
	private Long durataAcquadro;
	private Long flagSaAgente;
	private Long tipologiaStazioneAppaltante;
	@XSSValidation
	private String denomSoggStazioneAppaltante;
	@XSSValidation
	private String cfAgenteStazioneAppaltante;
	private Long tipologiaProcedura;
	private Long flagStipula;
	private Long idUfficio;
	private Date dataScadenza;
	private Long syscon;
	@XSSValidation
	private String altreSA;
	private Long modalitaIndizioneAllegato9;
	private Long motivoSommaUrgenza;
	@XSSValidation
	private String idAppalto;
	private Long idFDelegate;
	@XSSValidation
	private String drp;
	private Date dataScadPresentazioneOfferta;
	@XSSValidation
	private String urlDocumentazione;
	
	
	public Long getModalitaIndizioneAllegato9() {
		return modalitaIndizioneAllegato9;
	}

	public void setModalitaIndizioneAllegato9(Long modalitaIndizioneAllegato9) {
		this.modalitaIndizioneAllegato9 = modalitaIndizioneAllegato9;
	}

	public Long getMotivoSommaUrgenza() {
		return motivoSommaUrgenza;
	}

	public void setMotivoSommaUrgenza(Long motivoSommaUrgenza) {
		this.motivoSommaUrgenza = motivoSommaUrgenza;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getAltreSA() {
		return altreSA;
	}

	public void setAltreSA(String altreSA) {
		this.altreSA = altreSA;
	}
	
	public Long getCodgara() {
		return codgara;
	}

	public void setCodgara(Long codgara) {
		this.codgara = codgara;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Long getSituazione() {
		return situazione;
	}

	public void setSituazione(Long situazione) {
		this.situazione = situazione;
	}

	public Long getTipoApp() {
		return tipoApp;
	}

	public void setTipoApp(Long tipoApp) {
		this.tipoApp = tipoApp;
	}

	public double getImportoGara() {
		return importoGara;
	}

	public void setImportoGara(double importoGara) {
		this.importoGara = importoGara;
	}

	public Long getProvenienzaDato() {
		return provenienzaDato;
	}

	public void setProvenienzaDato(Long provenienzaDato) {
		this.provenienzaDato = provenienzaDato;
	}

	public String getIdentificativoGara() {
		return identificativoGara;
	}

	public void setIdentificativoGara(String identificativoGara) {
		this.identificativoGara = identificativoGara;
	}

	public String getTipoSettore() {
		return tipoSettore;
	}

	public void setTipoSettore(String tipoSettore) {
		this.tipoSettore = tipoSettore;
	}

	public Long getModalitaIndizione() {
		return modalitaIndizione;
	}

	public void setModalitaIndizione(Long modalitaIndizione) {
		this.modalitaIndizione = modalitaIndizione;
	}

	public String getCigQuadro() {
		return cigQuadro;
	}

	public void setCigQuadro(String cigQuadro) {
		this.cigQuadro = cigQuadro;
	}

	public Long getSommaUrgenza() {
		return sommaUrgenza;
	}

	public void setSommaUrgenza(Long sommaUrgenza) {
		this.sommaUrgenza = sommaUrgenza;
	}

	public Long getNumLotti() {
		return numLotti;
	}

	public void setNumLotti(Long numLotti) {
		this.numLotti = numLotti;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Long getRicostruzioneAlluvione() {
		return ricostruzioneAlluvione;
	}

	public void setRicostruzioneAlluvione(Long ricostruzioneAlluvione) {
		this.ricostruzioneAlluvione = ricostruzioneAlluvione;
	}

	public Long getDispArtDlgs() {
		return dispArtDlgs;
	}

	public void setDispArtDlgs(Long dispArtDlgs) {
		this.dispArtDlgs = dispArtDlgs;
	}

	public Long getSisma() {
		return sisma;
	}

	public void setSisma(Long sisma) {
		this.sisma = sisma;
	}

	public Long getVersioneSimog() {
		return versioneSimog;
	}

	public void setVersioneSimog(Long versioneSimog) {
		this.versioneSimog = versioneSimog;
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

	public Long getDurataAcquadro() {
		return durataAcquadro;
	}

	public void setDurataAcquadro(Long durataAcquadro) {
		this.durataAcquadro = durataAcquadro;
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

	public String getCodiceTecnico() {
		return codiceTecnico;
	}

	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}

	public String getCodiceStazioneAppaltante() {
		return codiceStazioneAppaltante;
	}

	public void setCodiceStazioneAppaltante(String codiceStazioneAppaltante) {
		this.codiceStazioneAppaltante = codiceStazioneAppaltante;
	}

	public Long getIdCentroDiCosto() {
		return idCentroDiCosto;
	}

	public void setIdCentroDiCosto(Long idCentroDiCosto) {
		this.idCentroDiCosto = idCentroDiCosto;
	}

	public Long getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}

	public String getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(String idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdFDelegate() {
		return idFDelegate;
	}

	public void setIdFDelegate(Long idFDelegate) {
		this.idFDelegate = idFDelegate;
	}

	public String getDrp() {
		return drp;
	}

	public void setDrp(String drp) {
		this.drp = drp;
	}

	public Date getDataScadPresentazioneOfferta() {
		return dataScadPresentazioneOfferta;
	}

	public void setDataScadPresentazioneOfferta(Date dataScadPresentazioneOfferta) {
		this.dataScadPresentazioneOfferta = dataScadPresentazioneOfferta;
	}

	public String getUrlDocumentazione() {
		return urlDocumentazione;
	}

	public void setUrlDocumentazione(String urlDocumentazione) {
		this.urlDocumentazione = urlDocumentazione;
	}
}
