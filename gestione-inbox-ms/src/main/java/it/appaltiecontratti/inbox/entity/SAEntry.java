package it.appaltiecontratti.inbox.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * Entry dei dati di una stazione appaltante
 * @author Michele.DiNapoli
 */
public class SAEntry extends SABaseEntry{

	@ApiModelProperty(name = "codFiscale", value="CF stazione appaltante")
	private String codFiscale;
	@ApiModelProperty(name = "indirizzo", value="Indirizzo stazione appaltante")
	private String indirizzo;
	@ApiModelProperty(name = "numCivico", value="Numero civico stazione appaltante")
	private String numCivico;
	@ApiModelProperty(name = "provincia", value="Provincia stazione appaltante")
	private String provincia;
	@ApiModelProperty(name = "cap", value="CAP stazione appaltante")
	private String cap;
	@ApiModelProperty(name = "comune", value="Comune stazione appaltante")
	private String comune;
	@ApiModelProperty(name = "codistat", value="Cod istat comune stazione appaltante")
	private String codistat;
	@ApiModelProperty(name = "telefono", value="Telefono stazione appaltante")
	private String telefono;
	@ApiModelProperty(name = "fax", value="Fax stazione appaltante")
	private String fax;
	@ApiModelProperty(name = "email", value="Email stazione appaltante")
	private String email;
	private String iscuc;
	private String pec;
	private String cfAnac;
	
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
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
	public String getCodistat() {
		return codistat;
	}
	public void setCodistat(String codistat) {
		this.codistat = codistat;
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
	public String getIscuc() {
		return iscuc;
	}
	public void setIscuc(String iscuc) {
		this.iscuc = iscuc;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getCfAnac() {
		return cfAnac;
	}
	public void setCfAnac(String cfAnac) {
		this.cfAnac = cfAnac;
	}
	
}
