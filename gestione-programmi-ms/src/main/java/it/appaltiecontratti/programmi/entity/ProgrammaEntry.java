package it.appaltiecontratti.programmi.entity;

import java.util.Date;

public class ProgrammaEntry extends ProgrammaBaseEntry {

	private Long norma;
	private String codein;
	private String stazioneappaltante;
	private Long idUfficio;
	private String pubblica;
	private String responsabile;
	private Long tipoAtto;
	private String numeroAtto;
	private Date dataPubblicazioneAdo;
	private Date dataAtto;
	private String titoloAdozione;
	private String urlAdozione;
	private Long tipoApprovazione;
	private String numeroApprovazione;
	private Date dataPubblicazioneAppr;
	private Date dataApprovazione;
	private String titoloApprovazione;
	private String urlApprovazione;
	private Double importoDestVincolo1;
	private Double importoDestVincolo2;
	private Double importoDestVincolo3;
	private Double importoTrasfImmobile1;
	private Double importoTrasfImmobile2;
	private Double importoTrasfImmobile3;
	private Double importoAcquistoMutuo1;
	private Double importoAcquistoMutuo2;
	private Double importoAcquistoMutuo3;
	private Double importoCapitalePr1;
	private Double importoCapitalePr2;
	private Double importoCapitalePr3;
	private Double importoAltreRis1;
	private Double importoAltreRis2;
	private Double importoAltreRis3;
	private Double importoFinanz1;
	private Double importoFinanz2;
	private Double importoFinanz3;
	private Double importoStanzBilanciamento1;
	private Double importoStanzBilanciamento2;
	private Double importoStanzBilanciamento3;
	private Double totaleRisDisp1;
	private Double totaleRisDisp2;
	private Double totaleRisDisp3;
	private Double importoRisorseFinReg;
	private Double importoRisorseFinStato;
	private Long syscon;
	private RupEntry rup;
	private UffEntry ufficio;
	private boolean existProgrammaAnnoPrecedente;
	private boolean showUfficio;

	private boolean showConfrontoProgrammi;

	public Long getNorma() {
		return norma;
	}

	public void setNorma(Long norma) {
		this.norma = norma;
	}

	public String getStazioneappaltante() {
		return stazioneappaltante;
	}

	public void setStazioneappaltante(String stazioneappaltante) {
		this.stazioneappaltante = stazioneappaltante;
	}

	public Long getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}

	public String getPubblica() {
		return pubblica;
	}

	public void setPubblica(String pubblica) {
		this.pubblica = pubblica;
	}

	public String getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}

	public Long getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(Long tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public Date getDataPubblicazioneAdo() {
		return dataPubblicazioneAdo;
	}

	public void setDataPubblicazioneAdo(Date dataPubblicazioneAdo) {
		this.dataPubblicazioneAdo = dataPubblicazioneAdo;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getTitoloAdozione() {
		return titoloAdozione;
	}

	public void setTitoloAdozione(String titoloAdozione) {
		this.titoloAdozione = titoloAdozione;
	}

	public String getUrlAdozione() {
		return urlAdozione;
	}

	public void setUrlAdozione(String urlAdozione) {
		this.urlAdozione = urlAdozione;
	}

	public Long getTipoApprovazione() {
		return tipoApprovazione;
	}

	public void setTipoApprovazione(Long tipoApprovazione) {
		this.tipoApprovazione = tipoApprovazione;
	}

	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}

	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}

	public Date getDataPubblicazioneAppr() {
		return dataPubblicazioneAppr;
	}

	public void setDataPubblicazioneAppr(Date dataPubblicazioneAppr) {
		this.dataPubblicazioneAppr = dataPubblicazioneAppr;
	}

	public Date getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public String getTitoloApprovazione() {
		return titoloApprovazione;
	}

	public void setTitoloApprovazione(String titoloApprovazione) {
		this.titoloApprovazione = titoloApprovazione;
	}

	public String getUrlApprovazione() {
		return urlApprovazione;
	}

	public void setUrlApprovazione(String urlApprovazione) {
		this.urlApprovazione = urlApprovazione;
	}

	public Double getImportoDestVincolo1() {
		return importoDestVincolo1;
	}

	public void setImportoDestVincolo1(Double importoDestVincolo1) {
		this.importoDestVincolo1 = importoDestVincolo1;
	}

	public Double getImportoDestVincolo2() {
		return importoDestVincolo2;
	}

	public void setImportoDestVincolo2(Double importoDestVincolo2) {
		this.importoDestVincolo2 = importoDestVincolo2;
	}

	public Double getImportoDestVincolo3() {
		return importoDestVincolo3;
	}

	public void setImportoDestVincolo3(Double importoDestVincolo3) {
		this.importoDestVincolo3 = importoDestVincolo3;
	}

	public Double getImportoTrasfImmobile1() {
		return importoTrasfImmobile1;
	}

	public void setImportoTrasfImmobile1(Double importoTrasfImmobile1) {
		this.importoTrasfImmobile1 = importoTrasfImmobile1;
	}

	public Double getImportoTrasfImmobile2() {
		return importoTrasfImmobile2;
	}

	public void setImportoTrasfImmobile2(Double importoTrasfImmobile2) {
		this.importoTrasfImmobile2 = importoTrasfImmobile2;
	}

	public Double getImportoTrasfImmobile3() {
		return importoTrasfImmobile3;
	}

	public void setImportoTrasfImmobile3(Double importoTrasfImmobile3) {
		this.importoTrasfImmobile3 = importoTrasfImmobile3;
	}

	public Double getImportoAcquistoMutuo1() {
		return importoAcquistoMutuo1;
	}

	public void setImportoAcquistoMutuo1(Double importoAcquistoMutuo1) {
		this.importoAcquistoMutuo1 = importoAcquistoMutuo1;
	}

	public Double getImportoAcquistoMutuo2() {
		return importoAcquistoMutuo2;
	}

	public void setImportoAcquistoMutuo2(Double importoAcquistoMutuo2) {
		this.importoAcquistoMutuo2 = importoAcquistoMutuo2;
	}

	public Double getImportoAcquistoMutuo3() {
		return importoAcquistoMutuo3;
	}

	public void setImportoAcquistoMutuo3(Double importoAcquistoMutuo3) {
		this.importoAcquistoMutuo3 = importoAcquistoMutuo3;
	}

	public Double getImportoCapitalePr1() {
		return importoCapitalePr1;
	}

	public void setImportoCapitalePr1(Double importoCapitalePr1) {
		this.importoCapitalePr1 = importoCapitalePr1;
	}

	public Double getImportoCapitalePr2() {
		return importoCapitalePr2;
	}

	public void setImportoCapitalePr2(Double importoCapitalePr2) {
		this.importoCapitalePr2 = importoCapitalePr2;
	}

	public Double getImportoCapitalePr3() {
		return importoCapitalePr3;
	}

	public void setImportoCapitalePr3(Double importoCapitalePr3) {
		this.importoCapitalePr3 = importoCapitalePr3;
	}

	public Double getImportoAltreRis1() {
		return importoAltreRis1;
	}

	public void setImportoAltreRis1(Double importoAltreRis1) {
		this.importoAltreRis1 = importoAltreRis1;
	}

	public Double getImportoAltreRis2() {
		return importoAltreRis2;
	}

	public void setImportoAltreRis2(Double importoAltreRis2) {
		this.importoAltreRis2 = importoAltreRis2;
	}

	public Double getImportoAltreRis3() {
		return importoAltreRis3;
	}

	public void setImportoAltreRis3(Double importoAltreRis3) {
		this.importoAltreRis3 = importoAltreRis3;
	}

	public Double getImportoFinanz1() {
		return importoFinanz1;
	}

	public void setImportoFinanz1(Double importoFinanz1) {
		this.importoFinanz1 = importoFinanz1;
	}

	public Double getImportoFinanz2() {
		return importoFinanz2;
	}

	public void setImportoFinanz2(Double importoFinanz2) {
		this.importoFinanz2 = importoFinanz2;
	}

	public Double getImportoFinanz3() {
		return importoFinanz3;
	}

	public void setImportoFinanz3(Double importoFinanz3) {
		this.importoFinanz3 = importoFinanz3;
	}

	public Double getImportoStanzBilanciamento1() {
		return importoStanzBilanciamento1;
	}

	public void setImportoStanzBilanciamento1(Double importoStanzBilanciamento1) {
		this.importoStanzBilanciamento1 = importoStanzBilanciamento1;
	}

	public Double getImportoStanzBilanciamento2() {
		return importoStanzBilanciamento2;
	}

	public void setImportoStanzBilanciamento2(Double importoStanzBilanciamento2) {
		this.importoStanzBilanciamento2 = importoStanzBilanciamento2;
	}

	public Double getImportoStanzBilanciamento3() {
		return importoStanzBilanciamento3;
	}

	public void setImportoStanzBilanciamento3(Double importoStanzBilanciamento3) {
		this.importoStanzBilanciamento3 = importoStanzBilanciamento3;
	}

	public Double getTotaleRisDisp1() {
		return totaleRisDisp1;
	}

	public void setTotaleRisDisp1(Double totaleRisDisp1) {
		this.totaleRisDisp1 = totaleRisDisp1;
	}

	public Double getTotaleRisDisp2() {
		return totaleRisDisp2;
	}

	public void setTotaleRisDisp2(Double totaleRisDisp2) {
		this.totaleRisDisp2 = totaleRisDisp2;
	}

	public Double getTotaleRisDisp3() {
		return totaleRisDisp3;
	}

	public void setTotaleRisDisp3(Double totaleRisDisp3) {
		this.totaleRisDisp3 = totaleRisDisp3;
	}

	public Double getImportoRisorseFinReg() {
		return importoRisorseFinReg;
	}

	public void setImportoRisorseFinReg(Double importoRisorseFinReg) {
		this.importoRisorseFinReg = importoRisorseFinReg;
	}

	public Double getImportoRisorseFinStato() {
		return importoRisorseFinStato;
	}

	public void setImportoRisorseFinStato(Double importoRisorseFinStato) {
		this.importoRisorseFinStato = importoRisorseFinStato;
	}

	public RupEntry getRup() {
		return rup;
	}

	public void setRup(RupEntry rup) {
		this.rup = rup;
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

	public boolean isExistProgrammaAnnoPrecedente() {
		return existProgrammaAnnoPrecedente;
	}

	public void setExistProgrammaAnnoPrecedente(boolean existProgrammaAnnoPrecedente) {
		this.existProgrammaAnnoPrecedente = existProgrammaAnnoPrecedente;
	}

	public boolean isShowUfficio() {
		return showUfficio;
	}

	public void setShowUfficio(boolean showUfficio) {
		this.showUfficio = showUfficio;
	}

	public boolean isShowConfrontoProgrammi() {
		return showConfrontoProgrammi;
	}

	public void setShowConfrontoProgrammi(boolean showConfrontoProgrammi) {
		this.showConfrontoProgrammi = showConfrontoProgrammi;
	}

	public String getCodein() {
		return codein;
	}

	public void setCodein(String codein) {
		this.codein = codein;
	}
}
