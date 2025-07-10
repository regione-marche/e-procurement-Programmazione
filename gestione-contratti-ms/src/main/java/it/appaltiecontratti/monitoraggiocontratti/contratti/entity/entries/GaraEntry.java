package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class GaraEntry extends GaraBaseEntry {

	private Long provenienzaDato;	
	private String identificativoGara;
	private String tipoSettore;
	private Long modalitaIndizione;
	private String cigQuadro;
	private Long sommaUrgenza;
	private Long numLotti;
	private String codiceTecnico;
	private RupEntry tecnico;
	private RupEntry delegato;
	private Long ricostruzioneAlluvione;
	private Long dispArtDlgs;
	private Long sisma;	
	private String codiceStazioneAppaltante;
	private String nominativoStazioneAppaltante;
	private Long idCentroDiCosto;
	private String codiceCentroCosto;
	private String nominativoCentroCosto;
	private String nominativoUfficio;
	private String indirizzoSede;
	private String comuneSede;
	private String provinciaSede;
	private Long durataAcquadro;
	private Long flagSaAgente;
	private Long tipologiaStazioneAppaltante;
	private String denomSoggStazioneAppaltante;
	private String cfAgenteStazioneAppaltante;
	private String tipologiaProcedura;
	private Long flagStipula;
	private Long idUfficio;
	private CentriCostoEntry centroDicosto;
	private Long idRicevuto;
	private UffEntry ufficio;
	private Long syscon;
	private Date dataCreazione;
	private Long idFDelegate;
	private String idFDelegateDescription;
	private PubblicitaGaraEntry pubblicitaGara;
	private Long codlott;
	private Date dataLetteraInvito;
	private Boolean visibleDataLetteraInvito;
	private Boolean perfezionata;
	private Boolean readOnly;
	private Long ruoloCollaboratore;
	private Boolean sceltaContraenteValorizzata;
	private Long modalitaIndizioneAllegato9;
	private Long motivoSommaUrgenza;
	private Boolean sceltaContra4;
	private boolean pcp;
	private String drp;
	private boolean riallineaVisible;
	private boolean existsFasiNonPubb;
	private Date dataScadPresentazioneOfferta;
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


	public RupEntry getTecnico() {
		return tecnico;
	}

	public void setTecnico(RupEntry tecnico) {
		this.tecnico = tecnico;
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

	public String getNominativoStazioneAppaltante() {
		return nominativoStazioneAppaltante;
	}

	public void setNominativoStazioneAppaltante(String nominativoStazioneAppaltante) {
		this.nominativoStazioneAppaltante = nominativoStazioneAppaltante;
	}

	public String getCodiceCentroCosto() {
		return codiceCentroCosto;
	}

	public void setCodiceCentroCosto(String codiceCentroCosto) {
		this.codiceCentroCosto = codiceCentroCosto;
	}

	public String getNominativoCentroCosto() {
		return nominativoCentroCosto;
	}

	public void setNominativoCentroCosto(String nominativoCentroCosto) {
		this.nominativoCentroCosto = nominativoCentroCosto;
	}

	public String getNominativoUfficio() {
		return nominativoUfficio;
	}

	public void setNominativoUfficio(String nominativoUfficio) {
		this.nominativoUfficio = nominativoUfficio;
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

	public String getTipologiaProcedura() {
		return tipologiaProcedura;
	}

	public void setTipologiaProcedura(String tipologiaProcedura) {
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

	public CentriCostoEntry getCentroDicosto() {
		return centroDicosto;
	}

	public void setCentroDicosto(CentriCostoEntry centroDicosto) {
		this.centroDicosto = centroDicosto;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public UffEntry getUfficio() {
		return ufficio;
	}

	public void setUfficio(UffEntry ufficio) {
		this.ufficio = ufficio;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Long getIdFDelegate() {
		return idFDelegate;
	}

	public void setIdFDelegate(Long idFDelegate) {
		this.idFDelegate = idFDelegate;
	}

	public PubblicitaGaraEntry getPubblicitaGara() {
		return pubblicitaGara;
	}

	public void setPubblicitaGara(PubblicitaGaraEntry pubblicitaGara) {
		this.pubblicitaGara = pubblicitaGara;
	}

	public Long getCodlott() {
		return codlott;
	}

	public void setCodlott(Long codlott) {
		this.codlott = codlott;
	}

	public Date getDataLetteraInvito() {
		return dataLetteraInvito;
	}

	public void setDataLetteraInvito(Date dataLetteraInvito) {
		this.dataLetteraInvito = dataLetteraInvito;
	}

	public Boolean getPerfezionata() {
		return perfezionata;
	}

	public void setPerfezionata(Boolean perfezionata) {
		this.perfezionata = perfezionata;
	}

	public Boolean getVisibleDataLetteraInvito() {
		return visibleDataLetteraInvito;
	}

	public void setVisibleDataLetteraInvito(Boolean visibleDataLetteraInvito) {
		this.visibleDataLetteraInvito = visibleDataLetteraInvito;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Long getRuoloCollaboratore() {
		return ruoloCollaboratore;
	}

	public void setRuoloCollaboratore(Long ruoloCollaboratore) {
		this.ruoloCollaboratore = ruoloCollaboratore;
	}

	public Boolean getSceltaContraenteValorizzata() {
		return sceltaContraenteValorizzata;
	}

	public void setSceltaContraenteValorizzata(Boolean sceltaContraenteValorizzata) {
		this.sceltaContraenteValorizzata = sceltaContraenteValorizzata;
	}

	public Boolean getSceltaContra4() {
		return sceltaContra4;
	}

	public void setSceltaContra4(Boolean sceltaContra4) {
		this.sceltaContra4 = sceltaContra4;
	}

	public String getIdFDelegateDescription() {
		return idFDelegateDescription;
	}

	public void setIdFDelegateDescription(String idFDelegateDescription) {
		this.idFDelegateDescription = idFDelegateDescription;
	}

	public boolean isPcp() {
		return pcp;
	}

	public void setPcp(boolean pcp) {
		this.pcp = pcp;
	}

	public String getDrp() {
		return drp;
	}

	public void setDrp(String drp) {
		this.drp = drp;
	}

	public RupEntry getDelegato() {
		return delegato;
	}

	public void setDelegato(RupEntry delegato) {
		this.delegato = delegato;
	}

	public boolean isRiallineaVisible() {
		return riallineaVisible;
	}

	public void setRiallineaVisible(boolean riallineaVisible) {
		this.riallineaVisible = riallineaVisible;
	}

	public boolean isExistsFasiNonPubb() {
		return existsFasiNonPubb;
	}

	public void setExistsFasiNonPubb(boolean existsFasiNonPubb) {
		this.existsFasiNonPubb = existsFasiNonPubb;
	}

	public Date getDataScadPresentazioneOfferta() {
		return dataScadPresentazioneOfferta;
	}

	public void setDataScadPresentazioneOfferta(Date dataScadPresentazioneOfferta) {
		this.dataScadPresentazioneOfferta = dataScadPresentazioneOfferta;
	}

	public String getUrlDocumentazione() { return urlDocumentazione; }

	public void setUrlDocumentazione(String urlDocumentazione) { this.urlDocumentazione = urlDocumentazione; }
	
}
