package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseIstanzaRecessoInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataTermine;
	private Date dataConsegna;
	@XSSValidation
	private String tipoComunicazione;
	private Long ritardo;
	private Long durataSospensione;
	@XSSValidation
	private String motivoSospensione;
	private Date dataIstanzaRecesso;
	@XSSValidation
	private String flagAccolta;
	@XSSValidation
	private String flagTardiva;
	@XSSValidation
	private String flagRipresa;
	@XSSValidation
	private String flagRiserva;
	private Double importoSpese;
	private Double importoOneri;
	@JsonIgnore
	private Long numAppa;

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

	public Date getDataTermine() {
		return dataTermine;
	}

	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public String getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

	public Long getRitardo() {
		return ritardo;
	}

	public void setRitardo(Long ritardo) {
		this.ritardo = ritardo;
	}

	public Long getDurataSospensione() {
		return durataSospensione;
	}

	public void setDurataSospensione(Long durataSospensione) {
		this.durataSospensione = durataSospensione;
	}

	public String getMotivoSospensione() {
		return motivoSospensione;
	}

	public void setMotivoSospensione(String motivoSospensione) {
		this.motivoSospensione = motivoSospensione;
	}

	public Date getDataIstanzaRecesso() {
		return dataIstanzaRecesso;
	}

	public void setDataIstanzaRecesso(Date dataIstanzaRecesso) {
		this.dataIstanzaRecesso = dataIstanzaRecesso;
	}

	public String getFlagAccolta() {
		return flagAccolta;
	}

	public void setFlagAccolta(String flagAccolta) {
		this.flagAccolta = flagAccolta;
	}

	public String getFlagTardiva() {
		return flagTardiva;
	}

	public void setFlagTardiva(String flagTardiva) {
		this.flagTardiva = flagTardiva;
	}

	public String getFlagRipresa() {
		return flagRipresa;
	}

	public void setFlagRipresa(String flagRipresa) {
		this.flagRipresa = flagRipresa;
	}

	public String getFlagRiserva() {
		return flagRiserva;
	}

	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}

	public Double getImportoSpese() {
		return importoSpese;
	}

	public void setImportoSpese(Double importoSpese) {
		this.importoSpese = importoSpese;
	}

	public Double getImportoOneri() {
		return importoOneri;
	}

	public void setImportoOneri(Double importoOneri) {
		this.importoOneri = importoOneri;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
