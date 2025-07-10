package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseInidoneitaContributivaInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	@XSSValidation
	private String descrizione;
	@XSSValidation
	private String codImpresa;
	@XSSValidation
	private String estremiDurc;
	private Date dataDurc;
	@XSSValidation
	private String cassaEdile;
	private Long riscontroIrregolare;
	private Date dataComunicazione;
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public String getEstremiDurc() {
		return estremiDurc;
	}
	public void setEstremiDurc(String estremiDurc) {
		this.estremiDurc = estremiDurc;
	}
	public Date getDataDurc() {
		return dataDurc;
	}
	public void setDataDurc(Date dataDurc) {
		this.dataDurc = dataDurc;
	}
	public String getCassaEdile() {
		return cassaEdile;
	}
	public void setCassaEdile(String cassaEdile) {
		this.cassaEdile = cassaEdile;
	}
	public Long getRiscontroIrregolare() {
		return riscontroIrregolare;
	}
	public void setRiscontroIrregolare(Long riscontroIrregolare) {
		this.riscontroIrregolare = riscontroIrregolare;
	}
	public Date getDataComunicazione() {
		return dataComunicazione;
	}
	public void setDataComunicazione(Date dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	
}
