package it.appaltiecontratti.programmi.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import javax.validation.constraints.NotNull;

public class RisorsaDiCapitoloInsertForm {

	@NotNull
    private Long idProgramma;
	@NotNull
	private Long idIntervento;
	private Long id;
	private Double importoComplessivo1;
	private Double importoComplessivo2;
	private Double importoComplessivo3;
	private Double importoComplessivoSucc;
	@XSSValidation
	private String numCapBilancio;
	@XSSValidation
	private String impegniSpesa;
	private Double importoRisFinanz;
	private Double importoRisFinanzStato;
	private Double importoRisFinanzAltro;
	@XSSValidation
	private String verificaCoerenza;
	private Double entrateDestVincLegge1;
	private Double entrateDestVincLegge2;
	private Double entrateDestVincLegge3;
	private Double entrateDestVincLeggeSucc;
	private Double entrateContrMutuo1;
	private Double entrateContrMutuo2;
	private Double entrateContrMutuo3;
	private Double entrateContrMutuoSucc;
	private Double stanziamentiBilancio1;
	private Double stanziamentiBilancio2;
	private Double stanziamentiBilancio3;
	private Double stanziamentiBilancioSucc;
	private Double impFinanz1;
	private Double impFinanz2;
	private Double impFinanz3;
	private Double impFinanzSucc;
	private Double altreRisDisp1;
	private Double altreRisDisp2;
	private Double altreRisDisp3;
	private Double altreRisDispSucc;
	private Double importoIva1;
	private Double importoIva2;
	private Double importoIva3;
	private Double importoIvaSucc;
	private Double speseSostenute;
	@XSSValidation
	private String note;
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getIdIntervento() {
		return idIntervento;
	}
	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getImportoComplessivo1() {
		return importoComplessivo1;
	}
	public void setImportoComplessivo1(Double importoComplessivo1) {
		this.importoComplessivo1 = importoComplessivo1;
	}
	public Double getImportoComplessivo2() {
		return importoComplessivo2;
	}
	public void setImportoComplessivo2(Double importoComplessivo2) {
		this.importoComplessivo2 = importoComplessivo2;
	}
	public Double getImportoComplessivo3() {
		return importoComplessivo3;
	}
	public void setImportoComplessivo3(Double importoComplessivo3) {
		this.importoComplessivo3 = importoComplessivo3;
	}
	public Double getImportoComplessivoSucc() {
		return importoComplessivoSucc;
	}
	public void setImportoComplessivoSucc(Double importoComplessivoSucc) {
		this.importoComplessivoSucc = importoComplessivoSucc;
	}
	public String getNumCapBilancio() {
		return numCapBilancio;
	}
	public void setNumCapBilancio(String numCapBilancio) {
		this.numCapBilancio = numCapBilancio;
	}
	public String getImpegniSpesa() {
		return impegniSpesa;
	}
	public void setImpegniSpesa(String impegniSpesa) {
		this.impegniSpesa = impegniSpesa;
	}
	public Double getImportoRisFinanz() {
		return importoRisFinanz;
	}
	public void setImportoRisFinanz(Double importoRisFinanz) {
		this.importoRisFinanz = importoRisFinanz;
	}
	public Double getImportoRisFinanzStato() {
		return importoRisFinanzStato;
	}
	public void setImportoRisFinanzStato(Double importoRisFinanzStato) {
		this.importoRisFinanzStato = importoRisFinanzStato;
	}
	public Double getImportoRisFinanzAltro() {
		return importoRisFinanzAltro;
	}
	public void setImportoRisFinanzAltro(Double importoRisFinanzAltro) {
		this.importoRisFinanzAltro = importoRisFinanzAltro;
	}
	public String getVerificaCoerenza() {
		return verificaCoerenza;
	}
	public void setVerificaCoerenza(String verificaCoerenza) {
		this.verificaCoerenza = verificaCoerenza;
	}
	public Double getEntrateDestVincLegge1() {
		return entrateDestVincLegge1;
	}
	public void setEntrateDestVincLegge1(Double entrateDestVincLegge1) {
		this.entrateDestVincLegge1 = entrateDestVincLegge1;
	}
	public Double getEntrateDestVincLegge2() {
		return entrateDestVincLegge2;
	}
	public void setEntrateDestVincLegge2(Double entrateDestVincLegge2) {
		this.entrateDestVincLegge2 = entrateDestVincLegge2;
	}
	public Double getEntrateDestVincLegge3() {
		return entrateDestVincLegge3;
	}
	public void setEntrateDestVincLegge3(Double entrateDestVincLegge3) {
		this.entrateDestVincLegge3 = entrateDestVincLegge3;
	}
	public Double getEntrateDestVincLeggeSucc() {
		return entrateDestVincLeggeSucc;
	}
	public void setEntrateDestVincLeggeSucc(Double entrateDestVincLeggeSucc) {
		this.entrateDestVincLeggeSucc = entrateDestVincLeggeSucc;
	}
	public Double getEntrateContrMutuo1() {
		return entrateContrMutuo1;
	}
	public void setEntrateContrMutuo1(Double entrateContrMutuo1) {
		this.entrateContrMutuo1 = entrateContrMutuo1;
	}
	public Double getEntrateContrMutuo2() {
		return entrateContrMutuo2;
	}
	public void setEntrateContrMutuo2(Double entrateContrMutuo2) {
		this.entrateContrMutuo2 = entrateContrMutuo2;
	}
	public Double getEntrateContrMutuo3() {
		return entrateContrMutuo3;
	}
	public void setEntrateContrMutuo3(Double entrateContrMutuo3) {
		this.entrateContrMutuo3 = entrateContrMutuo3;
	}
	public Double getEntrateContrMutuoSucc() {
		return entrateContrMutuoSucc;
	}
	public void setEntrateContrMutuoSucc(Double entrateContrMutuoSucc) {
		this.entrateContrMutuoSucc = entrateContrMutuoSucc;
	}
	public Double getStanziamentiBilancio1() {
		return stanziamentiBilancio1;
	}
	public void setStanziamentiBilancio1(Double stanziamentiBilancio1) {
		this.stanziamentiBilancio1 = stanziamentiBilancio1;
	}
	public Double getStanziamentiBilancio2() {
		return stanziamentiBilancio2;
	}
	public void setStanziamentiBilancio2(Double stanziamentiBilancio2) {
		this.stanziamentiBilancio2 = stanziamentiBilancio2;
	}
	public Double getStanziamentiBilancio3() {
		return stanziamentiBilancio3;
	}
	public void setStanziamentiBilancio3(Double stanziamentiBilancio3) {
		this.stanziamentiBilancio3 = stanziamentiBilancio3;
	}
	public Double getStanziamentiBilancioSucc() {
		return stanziamentiBilancioSucc;
	}
	public void setStanziamentiBilancioSucc(Double stanziamentiBilancioSucc) {
		this.stanziamentiBilancioSucc = stanziamentiBilancioSucc;
	}
	public Double getImpFinanz1() {
		return impFinanz1;
	}
	public void setImpFinanz1(Double impFinanz1) {
		this.impFinanz1 = impFinanz1;
	}
	public Double getImpFinanz2() {
		return impFinanz2;
	}
	public void setImpFinanz2(Double impFinanz2) {
		this.impFinanz2 = impFinanz2;
	}
	public Double getImpFinanz3() {
		return impFinanz3;
	}
	public void setImpFinanz3(Double impFinanz3) {
		this.impFinanz3 = impFinanz3;
	}
	public Double getImpFinanzSucc() {
		return impFinanzSucc;
	}
	public void setImpFinanzSucc(Double impFinanzSucc) {
		this.impFinanzSucc = impFinanzSucc;
	}
	public Double getAltreRisDisp1() {
		return altreRisDisp1;
	}
	public void setAltreRisDisp1(Double altreRisDisp1) {
		this.altreRisDisp1 = altreRisDisp1;
	}
	public Double getAltreRisDisp2() {
		return altreRisDisp2;
	}
	public void setAltreRisDisp2(Double altreRisDisp2) {
		this.altreRisDisp2 = altreRisDisp2;
	}
	public Double getAltreRisDisp3() {
		return altreRisDisp3;
	}
	public void setAltreRisDisp3(Double altreRisDisp3) {
		this.altreRisDisp3 = altreRisDisp3;
	}
	public Double getAltreRisDispSucc() {
		return altreRisDispSucc;
	}
	public void setAltreRisDispSucc(Double altreRisDispSucc) {
		this.altreRisDispSucc = altreRisDispSucc;
	}
	public Double getImportoIva1() {
		return importoIva1;
	}
	public void setImportoIva1(Double importoIva1) {
		this.importoIva1 = importoIva1;
	}
	public Double getImportoIva2() {
		return importoIva2;
	}
	public void setImportoIva2(Double importoIva2) {
		this.importoIva2 = importoIva2;
	}
	public Double getImportoIvaSucc() {
		return importoIvaSucc;
	}
	public void setImportoIvaSucc(Double importoIvaSucc) {
		this.importoIvaSucc = importoIvaSucc;
	}
	public Double getSpeseSostenute() {
		return speseSostenute;
	}
	public void setSpeseSostenute(Double speseSostenute) {
		this.speseSostenute = speseSostenute;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public Double getImportoIva3() {
		return importoIva3;
	}

	public void setImportoIva3(Double importoIva3) {
		this.importoIva3 = importoIva3;
	}



}
