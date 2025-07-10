package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati generali del soggetto")
public class DatiGeneraliTecnicoEntry implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice del tecnico", hidden = true)
	@Size(max = 10)
	private String codice;

	@ApiModelProperty(value = "Cognome del tecnico", required = true)
	@Size(max = 80, min = 1)
	private String cognome;

	@ApiModelProperty(value = "Nome del tecnico", required = true)
	@Size(max = 80, min = 1)
	private String nome;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Cognome e nome del tecnico")
	@Size(max = 161)
	private String nomeCognome;

	@ApiModelProperty(value = "Indirizzo (via/piazza/corso)")
	@Size(max = 60)
	private String indirizzo;

	@ApiModelProperty(value = "Numero civico")
	@Size(max = 10)
	private String civico;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Localita` di residenza")
	@Size(max = 32)
	private String localita;

	@ApiModelProperty(value = "Provincia di residenza")
	@Size(max = 2)
	private String provincia; // PROV. - Agx15

	@ApiModelProperty(value = "Codice di avviamento postale")
	@Size(max = 5)
	private String cap;

	@ApiModelProperty(value = "Codice ISTAT del Comune luogo di esecuzione del contratto")
	@Size(max = 9)
	private String luogoIstat;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Numero di telefono")
	@Size(max = 50)
	private String telefono;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*FAX")
	@Size(max = 20)
	private String fax;

	@ApiModelProperty(value = "Codice fiscale", required = true)
	@Size(max = 16, min = 11)
	private String cfPiva;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Indirizzo E-mail")
	@Size(max = 100)
	private String mail;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice Stazione appaltante di appartenenza", hidden = true)
	@Size(max = 16)
	private String codiceSA;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCognome() {
		return nomeCognome;
	}

	public void setNomeCognome(String nomeCognome) {
		this.nomeCognome = nomeCognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
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

	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setCodiceSA(String codiceSA) {
		this.codiceSA = codiceSA;
	}

	public String getCodiceSA() {
		return codiceSA;
	}

	public void setLuogoIstat(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}

	public String getLuogoIstat() {
		return luogoIstat;
	}

}
