package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseInidoneitaTecnicoProfEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private String codImpresa;
	private String descrizione;
	private Date dataUni;
	private String nonIdoneitaVerificaIscrizione;
	private String nonIndicatiContratti;
	private String mancataNominaResp;
	private String mancataNominaMedico;
	private String mancatoDocValutazioneRischi;
	private String inadeguataFormazioneSicurezza;
	private String altreCause;
	private ImpresaEntry impresa;
	private boolean pubblicata;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
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
	public ImpresaEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}
}
