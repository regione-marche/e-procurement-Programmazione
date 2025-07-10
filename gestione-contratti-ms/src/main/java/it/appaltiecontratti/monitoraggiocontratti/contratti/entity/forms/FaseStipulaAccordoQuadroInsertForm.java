package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaseStipulaAccordoQuadroInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Date dataStipula;
	private Date dataDecorrenza;
	private Date dataScadenza;
	private FaseStipulaPubbEsitoForm pubblicazioneEsito;
	@JsonIgnore
	private Long numAppa;
	private Long num;
	
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
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
	public Date getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}
	public FaseStipulaPubbEsitoForm getPubblicazioneEsito() {
		return pubblicazioneEsito;
	}
	public void setPubblicazioneEsito(FaseStipulaPubbEsitoForm pubblicazioneEsito) {
		this.pubblicazioneEsito = pubblicazioneEsito;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
