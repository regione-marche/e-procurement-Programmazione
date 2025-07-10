package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class ImmobileInsertForm {
	@ApiModelProperty(value = "Id del programma")
	private Long idProgramma;
	@ApiModelProperty(value = "Id dell'intervento")
	private Long idIntervento;
	@ApiModelProperty(value = "Id dell'opera")
	private Long idOpera;
	@ApiModelProperty(value = "Id dell'immobile")
	private Long id;
	@ApiModelProperty(value = "Numero CUI")
	@XSSValidation
	private String cui;
	@ApiModelProperty(value = "Descrizione")
	@XSSValidation
	private String descrizione;
	@ApiModelProperty(value = "codice istat comune")
	@XSSValidation
	private String codIstat;
	@ApiModelProperty(value = "Numero NUTS")
	@XSSValidation
	private String nuts;
	@ApiModelProperty(value = "Trasferimento immobile corrispondente")
	private Long trasfImmCorrisp;
	@ApiModelProperty(value = "Diritto di godimento")
	private Long dirittoGodimento;
	@ApiModelProperty(value = "Alienati")
	@XSSValidation
	private String alienati;
	@ApiModelProperty(value = "Programma dismissione")
	private Long progDismiss;
	@ApiModelProperty(value = "Tipo disposizione")
	private Long tipoDisp;
	@ApiModelProperty(value = "Valore stimato primo anno")
	private Double valStimato1;
	@ApiModelProperty(value = "Valore stimato secondo anno")	
	private Double valStimato2;
	@ApiModelProperty(value = "Valore stimato terzo anno")
	private Double valStimato3;
	@ApiModelProperty(value = "Valore stimato anni successivi")
	private Double valAnnoSucc;
	@ApiModelProperty(value = "Valore stimato totale")
	private Double valoreStimato;
	

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
	public Long getIdOpera() {
		return idOpera;
	}
	public void setIdOpera(Long idOpera) {
		this.idOpera = idOpera;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCui() {
		return cui;
	}
	public void setCui(String cui) {
		this.cui = cui;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}
	public String getNuts() {
		return nuts;
	}
	public void setNuts(String nuts) {
		this.nuts = nuts;
	}
	public Long getTrasfImmCorrisp() {
		return trasfImmCorrisp;
	}
	public void setTrasfImmCorrisp(Long trasfImmCorrisp) {
		this.trasfImmCorrisp = trasfImmCorrisp;
	}
	public Long getDirittoGodimento() {
		return dirittoGodimento;
	}
	public void setDirittoGodimento(Long dirittoGodimento) {
		this.dirittoGodimento = dirittoGodimento;
	}
	public String getAlienati() {
		return alienati;
	}
	public void setAlienati(String alienati) {
		this.alienati = alienati;
	}
	public Long getProgDismiss() {
		return progDismiss;
	}
	public void setProgDismiss(Long progDismiss) {
		this.progDismiss = progDismiss;
	}
	public Long getTipoDisp() {
		return tipoDisp;
	}
	public void setTipoDisp(Long tipoDisp) {
		this.tipoDisp = tipoDisp;
	}
	public Double getValStimato1() {
		return valStimato1;
	}
	public void setValStimato1(Double valStimato1) {
		this.valStimato1 = valStimato1;
	}
	public Double getValStimato2() {
		return valStimato2;
	}
	public void setValStimato2(Double valStimato2) {
		this.valStimato2 = valStimato2;
	}
	public Double getValStimato3() {
		return valStimato3;
	}
	public void setValStimato3(Double valStimato3) {
		this.valStimato3 = valStimato3;
	}
	public Double getValAnnoSucc() {
		return valAnnoSucc;
	}
	public void setValAnnoSucc(Double valAnnoSucc) {
		this.valAnnoSucc = valAnnoSucc;
	}
	public Double getValoreStimato() {
		return valoreStimato;
	}
	public void setValoreStimato(Double valoreStimato) {
		this.valoreStimato = valoreStimato;
	}
}
