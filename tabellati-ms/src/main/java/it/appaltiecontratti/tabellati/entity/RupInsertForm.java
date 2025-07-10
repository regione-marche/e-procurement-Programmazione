package it.appaltiecontratti.tabellati.entity;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

/**
 * Dati di pubblicazione di un avviso
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per i dati di un RUP")
public class RupInsertForm {

	@ApiModelProperty(value = "Codice del RUP")
	@XSSValidation
	private String codice; 
	@NotNull
	@ApiModelProperty(value = "Nome del RUP")
	@XSSValidation
	private String 	nome; 
	@NotNull
	@ApiModelProperty(value = "Cognome del RUP")
	@XSSValidation
	private String 	cognome;
	@NotNull
	@ApiModelProperty(value = "CF del RUP")
	@XSSValidation
	private String 	cf; 
	@ApiModelProperty(value = "Nominativo del RUP")
	@XSSValidation
	private String nominativo;
	@ApiModelProperty(value = "Codice stazione appaltante del RUP")
	@XSSValidation
	private String 	stazioneAppaltante; 
	@ApiModelProperty(value = "Indirizzo del RUP")
	@XSSValidation
	private String 	indirizzo; 
	@ApiModelProperty(value = "Numero civico del RUP")
	@XSSValidation
	private String 	numCivico;
	@ApiModelProperty(value = "Provincia del RUP")
	@XSSValidation
	private String 	provincia; 
	@ApiModelProperty(value = "CAP del RUP")
	@XSSValidation
	private String 	cap; 
	@ApiModelProperty(value = "Comune del RUP")
	@XSSValidation
	private String 	comune; 
	@ApiModelProperty(value = "Codice Istat comune del RUP")
	@XSSValidation
	private String 	codIstat; 
	@ApiModelProperty(value = "Telefono del RUP")
	@XSSValidation
	private String 	telefono; 
	@ApiModelProperty(value = "Fax del RUP")
	@XSSValidation
	private String 	fax; 
	@ApiModelProperty(value = "Email del RUP")
	@XSSValidation
	private String 	email;
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNumCivico() {
		return numCivico;
	}
	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
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
	
}
