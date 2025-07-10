package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseConclusioneInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private String interruzioneAnticipata;
	private Long motivoInterruzione;
	private Long motivoRisoluzione;
	private Date dataRisoluzione;
	@XSSValidation
	private String flagOneri;
	private Double importoOneri;
	@XSSValidation
	private String flagPolizza;
	private Date dataVerbaleConsegna;
	private Date dataTermineContrattuale;
	private Date dataUltimazione;
	private Long numInfortuni;
	private Long numInfortuniPermanenti;
	private Long numInfortuniMortali;
	private Long numgiorniProroga;
	private Double oreLavorate;
	@JsonIgnore
	private Long numAppa;
	
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
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
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getInterruzioneAnticipata() {
		return interruzioneAnticipata;
	}
	public void setInterruzioneAnticipata(String interruzioneAnticipata) {
		this.interruzioneAnticipata = interruzioneAnticipata;
	}
	public Long getMotivoInterruzione() {
		return motivoInterruzione;
	}
	public void setMotivoInterruzione(Long motivoInterruzione) {
		this.motivoInterruzione = motivoInterruzione;
	}
	public Long getMotivoRisoluzione() {
		return motivoRisoluzione;
	}
	public void setMotivoRisoluzione(Long motivoRisoluzione) {
		this.motivoRisoluzione = motivoRisoluzione;
	}
	public Date getDataRisoluzione() {
		return dataRisoluzione;
	}
	public void setDataRisoluzione(Date dataRisoluzione) {
		this.dataRisoluzione = dataRisoluzione;
	}
	public String getFlagOneri() {
		return flagOneri;
	}
	public void setFlagOneri(String flagOneri) {
		this.flagOneri = flagOneri;
	}
	public Double getImportoOneri() {
		return importoOneri;
	}
	public void setImportoOneri(Double importoOneri) {
		this.importoOneri = importoOneri;
	}
	public String getFlagPolizza() {
		return flagPolizza;
	}
	public void setFlagPolizza(String flagPolizza) {
		this.flagPolizza = flagPolizza;
	}
	public Date getDataVerbaleConsegna() {
		return dataVerbaleConsegna;
	}
	public void setDataVerbaleConsegna(Date dataVerbaleConsegna) {
		this.dataVerbaleConsegna = dataVerbaleConsegna;
	}
	public Date getDataTermineContrattuale() {
		return dataTermineContrattuale;
	}
	public void setDataTermineContrattuale(Date dataTermineContrattuale) {
		this.dataTermineContrattuale = dataTermineContrattuale;
	}
	public Date getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Long getNumInfortuni() {
		return numInfortuni;
	}
	public void setNumInfortuni(Long numInfortuni) {
		this.numInfortuni = numInfortuni;
	}
	public Long getNumInfortuniPermanenti() {
		return numInfortuniPermanenti;
	}
	public void setNumInfortuniPermanenti(Long numInfortuniPermanenti) {
		this.numInfortuniPermanenti = numInfortuniPermanenti;
	}
	public Long getNumInfortuniMortali() {
		return numInfortuniMortali;
	}
	public void setNumInfortuniMortali(Long numInfortuniMortali) {
		this.numInfortuniMortali = numInfortuniMortali;
	}
	public Long getNumgiorniProroga() {
		return numgiorniProroga;
	}
	public void setNumgiorniProroga(Long numgiorniProroga) {
		this.numgiorniProroga = numgiorniProroga;
	}
	public Double getOreLavorate() {
		return oreLavorate;
	}
	public void setOreLavorate(Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}
}
