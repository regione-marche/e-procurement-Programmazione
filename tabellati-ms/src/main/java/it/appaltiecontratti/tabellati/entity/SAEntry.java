package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * Entry dei dati di una stazione appaltante
 * @author Michele.DiNapoli
 */
public class SAEntry {

	@ApiModelProperty(name = "codein", value="Codice stazione appaltante")
	private String codein;
	@ApiModelProperty(name = "nomein", value="Nome stazione appaltante")
	private String nomein;
	@ApiModelProperty(name = "cfein", value="CF stazione appaltante")
	private String cfein;
	@ApiModelProperty(name = "viaein", value="Indirizzo stazione appaltante")
	private String viaein;
	@ApiModelProperty(name = "nciein", value="Numero civico stazione appaltante")
	private String nciein;
	@ApiModelProperty(name = "proein", value="Provincia stazione appaltante")
	private String proein;
	@ApiModelProperty(name = "capein", value="CAP stazione appaltante")
	private String capein;
	@ApiModelProperty(name = "citein", value="Comune stazione appaltante")
	private String citein;
	@ApiModelProperty(name = "codcit", value="Cod istat comune stazione appaltante")
	private String codcit;
	@ApiModelProperty(name = "telein", value="Telefono stazione appaltante")
	private String telein;
	@ApiModelProperty(name = "faxein", value="Fax stazione appaltante")
	private String faxein;
	@ApiModelProperty(name = "emai2in", value="Email stazione appaltante")
	private String emai2in;

	public String getCodein() {
		return codein;
	}

	public void setCodein(String codein) {
		this.codein = codein;
	}

	public String getNomein() {
		return nomein;
	}

	public void setNomein(String nomein) {
		this.nomein = nomein;
	}

	public String getCfein() {
		return cfein;
	}

	public void setCfein(String cfein) {
		this.cfein = cfein;
	}

	public String getViaein() {
		return viaein;
	}

	public void setViaein(String viaein) {
		this.viaein = viaein;
	}

	public String getNciein() {
		return nciein;
	}

	public void setNciein(String nciein) {
		this.nciein = nciein;
	}

	public String getProein() {
		return proein;
	}

	public void setProein(String proein) {
		this.proein = proein;
	}

	public String getCapein() {
		return capein;
	}

	public void setCapein(String capein) {
		this.capein = capein;
	}

	public String getCitein() {
		return citein;
	}

	public void setCitein(String citein) {
		this.citein = citein;
	}

	public String getCodcit() {
		return codcit;
	}

	public void setCodcit(String codcit) {
		this.codcit = codcit;
	}

	public String getTelein() {
		return telein;
	}

	public void setTelein(String telein) {
		this.telein = telein;
	}

	public String getFaxein() {
		return faxein;
	}

	public void setFaxein(String faxein) {
		this.faxein = faxein;
	}

	public String getEmai2in() {
		return emai2in;
	}

	public void setEmai2in(String emai2in) {
		this.emai2in = emai2in;
	}

}
