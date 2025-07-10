package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseSospensioneInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataVerbSosp;	
	private Date dataVerbRipr; 
	private Long motivoSosp;
	@XSSValidation
	private String flagSuperoTempo;
	@XSSValidation
	private String flagRiserve;
	@XSSValidation
	private String flagVerbale;
	@JsonIgnore
	private Long numAppa;
	@XSSValidation
	private String sospParziale;
	private Double incCrono;
	private Date dataSuperQuarto;

	
	public Double getIncCrono() {
		return incCrono;
	}
	public void setIncCrono(Double incCrono) {
		this.incCrono = incCrono;
	}
	public Date getDataSuperQuarto() {
		return dataSuperQuarto;
	}
	public void setDataSuperQuarto(Date dataSuperQuarto) {
		this.dataSuperQuarto = dataSuperQuarto;
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
	public Date getDataVerbSosp() {
		return dataVerbSosp;
	}
	public void setDataVerbSosp(Date dataVerbSosp) {
		this.dataVerbSosp = dataVerbSosp;
	}
	public Date getDataVerbRipr() {
		return dataVerbRipr;
	}
	public void setDataVerbRipr(Date dataVerbRipr) {
		this.dataVerbRipr = dataVerbRipr;
	}
	public Long getMotivoSosp() {
		return motivoSosp;
	}
	public void setMotivoSosp(Long motivoSosp) {
		this.motivoSosp = motivoSosp;
	}
	public String getFlagSuperoTempo() {
		return flagSuperoTempo;
	}
	public void setFlagSuperoTempo(String flagSuperoTempo) {
		this.flagSuperoTempo = flagSuperoTempo;
	}
	public String getFlagRiserve() {
		return flagRiserve;
	}
	public void setFlagRiserve(String flagRiserve) {
		this.flagRiserve = flagRiserve;
	}
	public String getFlagVerbale() {
		return flagVerbale;
	}
	public void setFlagVerbale(String flagVerbale) {
		this.flagVerbale = flagVerbale;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public String getSospParziale() {
		return sospParziale;
	}
	public void setSospParziale(String sospParziale) {
		this.sospParziale = sospParziale;
	}
	
}
