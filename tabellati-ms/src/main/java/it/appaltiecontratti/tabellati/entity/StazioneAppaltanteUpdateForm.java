package it.appaltiecontratti.tabellati.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import javax.validation.constraints.NotNull;

public class StazioneAppaltanteUpdateForm {

	@NotNull
	@XSSValidation
	private String codein;
	@NotNull
	@XSSValidation
	private String denominazione;
	@XSSValidation
	private String codFisc;
	private Long tipologia;
	private Long idAmministrazione;
	@XSSValidation
	private String urlProfiloC;
	@XSSValidation
	private String codIstat;
	@XSSValidation
	private String indirizzo;
	@XSSValidation
	private String nCivico;
	@XSSValidation
	private String cap;
	@XSSValidation
	private String telefono;
	@XSSValidation
	private String fax;
	@XSSValidation
	private String email;
	@XSSValidation
	private String isCuc;
	@XSSValidation
	private String cfAnac;
	@XSSValidation
	private String provincia;
	@XSSValidation
	private String citta;
	@XSSValidation
	private String codausa;
	
	public String getCodein() {
		return codein;
	}
	public void setCodein(String codein) {
		this.codein = codein;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public Long getTipologia() {
		return tipologia;
	}
	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
	}
	public Long getIdAmministrazione() {
		return idAmministrazione;
	}
	public void setIdAmministrazione(Long idAmministrazione) {
		this.idAmministrazione = idAmministrazione;
	}
	public String getUrlProfiloC() {
		return urlProfiloC;
	}
	public void setUrlProfiloC(String urlProfiloC) {
		this.urlProfiloC = urlProfiloC;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getnCivico() {
		return nCivico;
	}
	public void setnCivico(String nCivico) {
		this.nCivico = nCivico;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsCuc() {
		return isCuc;
	}
	public void setIsCuc(String isCuc) {
		this.isCuc = isCuc;
	}
	public String getCfAnac() {
		return cfAnac;
	}
	public void setCfAnac(String cfAnac) {
		this.cfAnac = cfAnac;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getCodausa() {
		return codausa;
	}
	public void setCodausa(String codausa) {
		this.codausa = codausa;
	}
	
}
