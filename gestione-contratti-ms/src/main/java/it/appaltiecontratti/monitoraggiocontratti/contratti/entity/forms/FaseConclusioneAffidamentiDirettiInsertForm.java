package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AssociazionePagamentiEntry;

public class FaseConclusioneAffidamentiDirettiInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataVerbInizio;
	private Date dataUltimazione;
	private Double importoCertificato;

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

	public Date getDataVerbInizio() {
		return dataVerbInizio;
	}

	public void setDataVerbInizio(Date dataVerbInizio) {
		this.dataVerbInizio = dataVerbInizio;
	}

	public Date getDataUltimazione() {
		return dataUltimazione;
	}

	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}

	public Double getImportoCertificato() {
		return importoCertificato;
	}

	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}

	public List<AssociazionePagamentiEntry> getAssociazioniPagamenti() {
		return associazioniPagamenti;
	}

	public void setAssociazioniPagamenti(List<AssociazionePagamentiEntry> associazioniPagamenti) {
		this.associazioniPagamenti = associazioniPagamenti;
	}

}
