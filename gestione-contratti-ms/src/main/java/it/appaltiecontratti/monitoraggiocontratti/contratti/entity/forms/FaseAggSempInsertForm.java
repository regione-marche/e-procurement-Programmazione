package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FaseAggSempInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long counter;
	@XSSValidation
	private String astaElettronica;
	private Double importoSub;
	private Double importoSicurezza;
	private Double importoComplAppalto;
	private Double iva;
	private Double importoIva;
	private Double altreSomme;
	private Double importoDisposizione;
	private Double importoComplIntervento;
	private Double percRibassoAgg;
	private Double percOffAumento;
	private Date dataVerbAgg;
	private Double importoAgg;
	private Long tipoAtto;
	private Date dataAtto;
	@XSSValidation
	private String numeroAtto;
	private Date dataStipula;
	private Long durataCon;
	private Long num;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	public String getAstaElettronica() {
		return astaElettronica;
	}

	public void setAstaElettronica(String astaElettronica) {
		this.astaElettronica = astaElettronica;
	}

	public Double getImportoSub() {
		return importoSub;
	}

	public void setImportoSub(Double importoSub) {
		this.importoSub = importoSub;
	}

	public Double getImportoSicurezza() {
		return importoSicurezza;
	}

	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}

	public Double getImportoComplAppalto() {
		return importoComplAppalto;
	}

	public void setImportoComplAppalto(Double importoComplAppalto) {
		this.importoComplAppalto = importoComplAppalto;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getImportoIva() {
		return importoIva;
	}

	public void setImportoIva(Double importoIva) {
		this.importoIva = importoIva;
	}

	public Double getAltreSomme() {
		return altreSomme;
	}

	public void setAltreSomme(Double altreSomme) {
		this.altreSomme = altreSomme;
	}

	public Double getImportoDisposizione() {
		return importoDisposizione;
	}

	public void setImportoDisposizione(Double importoDisposizione) {
		this.importoDisposizione = importoDisposizione;
	}

	public Double getImportoComplIntervento() {
		return importoComplIntervento;
	}

	public void setImportoComplIntervento(Double importoComplIntervento) {
		this.importoComplIntervento = importoComplIntervento;
	}

	public Double getPercRibassoAgg() {
		return percRibassoAgg;
	}

	public void setPercRibassoAgg(Double percRibassoAgg) {
		this.percRibassoAgg = percRibassoAgg;
	}

	public Double getPercOffAumento() {
		return percOffAumento;
	}

	public void setPercOffAumento(Double percOffAumento) {
		this.percOffAumento = percOffAumento;
	}

	public Date getDataVerbAgg() {
		return dataVerbAgg;
	}

	public void setDataVerbAgg(Date dataVerbAgg) {
		this.dataVerbAgg = dataVerbAgg;
	}

	public Double getImportoAgg() {
		return importoAgg;
	}

	public void setImportoAgg(Double importoAgg) {
		this.importoAgg = importoAgg;
	}

	public Long getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(Long tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public Long getDurataCon() {
		return durataCon;
	}

	public void setDurataCon(Long durataCon) {
		this.durataCon = durataCon;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
