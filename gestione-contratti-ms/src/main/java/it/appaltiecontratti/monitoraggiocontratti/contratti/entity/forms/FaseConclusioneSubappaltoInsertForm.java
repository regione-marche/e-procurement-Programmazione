package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.MandanteEntry;

public class FaseConclusioneSubappaltoInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Double importoEffettivo;
	private Date dataUltimazione;
	private Long motivoMancataEsec;
		
	public Date getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Long getMotivoMancataEsec() {
		return motivoMancataEsec;
	}
	public void setMotivoMancataEsec(Long motivoMancataEsec) {
		this.motivoMancataEsec = motivoMancataEsec;
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
	public Double getImportoEffettivo() {
		return importoEffettivo;
	}
	public void setImportoEffettivo(Double importoEffettivo) {
		this.importoEffettivo = importoEffettivo;
	}
	
		
}
