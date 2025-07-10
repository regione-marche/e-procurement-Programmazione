package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AssociazionePagamentiEntry;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseAvanzamentoInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataRaggiungimento;
	@XSSValidation
	private String denomAvanzamento;
	private Double importoSal;
	private Date dataCertificato;
	private Double importoCertificato;
	@XSSValidation
	private String flagRitardo;
	private Long numGiorniScost;
	private Long numGiorniProroga;
	private Long flagPagamento;
	private Double importoAnticipazione;
	private Date dataAnticipazione;
	@XSSValidation
	private String subappalti;
	@JsonIgnore
	private Long numAppa;

	private List<AssociazionePagamentiEntry> associazioniPagamenti;

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

	public Date getDataRaggiungimento() {
		return dataRaggiungimento;
	}

	public void setDataRaggiungimento(Date dataRaggiungimento) {
		this.dataRaggiungimento = dataRaggiungimento;
	}

	public String getDenomAvanzamento() {
		return denomAvanzamento;
	}

	public void setDenomAvanzamento(String denomAvanzamento) {
		this.denomAvanzamento = denomAvanzamento;
	}

	public Double getImportoSal() {
		return importoSal;
	}

	public void setImportoSal(Double importoSal) {
		this.importoSal = importoSal;
	}

	public Date getDataCertificato() {
		return dataCertificato;
	}

	public void setDataCertificato(Date dataCertificato) {
		this.dataCertificato = dataCertificato;
	}

	public Double getImportoCertificato() {
		return importoCertificato;
	}

	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}

	public String getFlagRitardo() {
		return flagRitardo;
	}

	public void setFlagRitardo(String flagRitardo) {
		this.flagRitardo = flagRitardo;
	}

	public Long getNumGiorniScost() {
		return numGiorniScost;
	}

	public void setNumGiorniScost(Long numGiorniScost) {
		this.numGiorniScost = numGiorniScost;
	}

	public Long getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(Long numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
	}

	public Long getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(Long flagPagamento) {
		this.flagPagamento = flagPagamento;
	}

	public Double getImportoAnticipazione() {
		return importoAnticipazione;
	}

	public void setImportoAnticipazione(Double importoAnticipazione) {
		this.importoAnticipazione = importoAnticipazione;
	}

	public Date getDataAnticipazione() {
		return dataAnticipazione;
	}

	public void setDataAnticipazione(Date dataAnticipazione) {
		this.dataAnticipazione = dataAnticipazione;
	}

	public String getSubappalti() {
		return subappalti;
	}

	public void setSubappalti(String subappalti) {
		this.subappalti = subappalti;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

	public List<AssociazionePagamentiEntry> getAssociazioniPagamenti() {
		return associazioniPagamenti;
	}

	public void setAssociazioniPagamenti(List<AssociazionePagamentiEntry> associazioniPagamenti) {
		this.associazioniPagamenti = associazioniPagamenti;
	}

}
