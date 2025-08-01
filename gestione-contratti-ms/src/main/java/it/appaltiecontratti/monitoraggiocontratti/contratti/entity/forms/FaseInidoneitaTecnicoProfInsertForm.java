package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseInidoneitaTecnicoProfInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	@XSSValidation
	private String codImpresa;
	@XSSValidation
	private String descrizione;
	private Date dataUni;
	@XSSValidation
	private String nonIdoneitaVerificaIscrizione;
	@XSSValidation
	private String nonIndicatiContratti;
	@XSSValidation
	private String mancataNominaResp;
	@XSSValidation
	private String mancataNominaMedico;
	@XSSValidation
	private String mancatoDocValutazioneRischi;
	@XSSValidation
	private String inadeguataFormazioneSicurezza;
	@XSSValidation
	private String altreCause;
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
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Date getDataUni() {
		return dataUni;
	}
	public void setDataUni(Date dataUni) {
		this.dataUni = dataUni;
	}
	public String getNonIdoneitaVerificaIscrizione() {
		return nonIdoneitaVerificaIscrizione;
	}
	public void setNonIdoneitaVerificaIscrizione(String nonIdoneitaVerificaIscrizione) {
		this.nonIdoneitaVerificaIscrizione = nonIdoneitaVerificaIscrizione;
	}
	public String getNonIndicatiContratti() {
		return nonIndicatiContratti;
	}
	public void setNonIndicatiContratti(String nonIndicatiContratti) {
		this.nonIndicatiContratti = nonIndicatiContratti;
	}
	public String getMancataNominaResp() {
		return mancataNominaResp;
	}
	public void setMancataNominaResp(String mancataNominaResp) {
		this.mancataNominaResp = mancataNominaResp;
	}
	public String getMancataNominaMedico() {
		return mancataNominaMedico;
	}
	public void setMancataNominaMedico(String mancataNominaMedico) {
		this.mancataNominaMedico = mancataNominaMedico;
	}
	public String getMancatoDocValutazioneRischi() {
		return mancatoDocValutazioneRischi;
	}
	public void setMancatoDocValutazioneRischi(String mancatoDocValutazioneRischi) {
		this.mancatoDocValutazioneRischi = mancatoDocValutazioneRischi;
	}
	public String getInadeguataFormazioneSicurezza() {
		return inadeguataFormazioneSicurezza;
	}
	public void setInadeguataFormazioneSicurezza(String inadeguataFormazioneSicurezza) {
		this.inadeguataFormazioneSicurezza = inadeguataFormazioneSicurezza;
	}
	public String getAltreCause() {
		return altreCause;
	}
	public void setAltreCause(String altreCause) {
		this.altreCause = altreCause;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	
}
