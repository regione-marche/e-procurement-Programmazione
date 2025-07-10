package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaMandatiResponse {

	@JsonProperty("self")
	private String self;

	@JsonProperty("openApi")
	private String openApi;

	@JsonProperty("asString")
	private String asString;

	@JsonProperty("asString2")
	private String asString2;

	@JsonProperty("asString3")
	private String asString3;

	@JsonProperty("esito")
	private Esito esito;

	@JsonProperty("contenutoZip64")
	private String contenutoZip64;

	@JsonProperty("listaMandati")
	private List<Mandato> listaMandati;

	// Getters and Setters

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getOpenApi() {
		return openApi;
	}

	public void setOpenApi(String openApi) {
		this.openApi = openApi;
	}

	public String getAsString() {
		return asString;
	}

	public void setAsString(String asString) {
		this.asString = asString;
	}

	public String getAsString2() {
		return asString2;
	}

	public void setAsString2(String asString2) {
		this.asString2 = asString2;
	}

	public String getAsString3() {
		return asString3;
	}

	public void setAsString3(String asString3) {
		this.asString3 = asString3;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	public String getContenutoZip64() {
		return contenutoZip64;
	}

	public void setContenutoZip64(String contenutoZip64) {
		this.contenutoZip64 = contenutoZip64;
	}

	public List<Mandato> getListaMandati() {
		return listaMandati;
	}

	public void setListaMandati(List<Mandato> listaMandati) {
		this.listaMandati = listaMandati;
	}

	// Static Inner Classes

	public static class Esito {

		@JsonProperty("self")
		private String self;

		@JsonProperty("openApi")
		private String openApi;

		@JsonProperty("asString")
		private String asString;

		@JsonProperty("asString2")
		private String asString2;

		@JsonProperty("asString3")
		private String asString3;

		@JsonProperty("tipo")
		private String tipo;

		@JsonProperty("descrizione")
		private String descrizione;

		// Getters and Setters

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public String getOpenApi() {
			return openApi;
		}

		public void setOpenApi(String openApi) {
			this.openApi = openApi;
		}

		public String getAsString() {
			return asString;
		}

		public void setAsString(String asString) {
			this.asString = asString;
		}

		public String getAsString2() {
			return asString2;
		}

		public void setAsString2(String asString2) {
			this.asString2 = asString2;
		}

		public String getAsString3() {
			return asString3;
		}

		public void setAsString3(String asString3) {
			this.asString3 = asString3;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
	}

	public static class Mandato {

		@JsonProperty("self")
		private String self;

		@JsonProperty("openApi")
		private String openApi;

		@JsonProperty("asString")
		private String asString;

		@JsonProperty("asString2")
		private String asString2;

		@JsonProperty("asString3")
		private String asString3;

		@JsonProperty("nummandato")
		private int nummandato;

		@JsonProperty("datamandato")
		private Date datamandato;

		@JsonProperty("oggetto")
		private String oggetto;

		@JsonProperty("nominativo")
		private String nominativo;

		@JsonProperty("codicefiscale")
		private String codicefiscale;

		@JsonProperty("importolordo")
		private double importolordo;

		@JsonProperty("cig")
		private String cig;

		@JsonProperty("cup")
		private String cup;

		@JsonProperty("siglapianofin")
		private String siglapianofin;

		@JsonProperty("dataquietanza")
		private Date dataquietanza;

		@JsonProperty("importoquietanza")
		private double importoquietanza;

		@JsonProperty("numquietanza")
		private String numquietanza;

		@JsonProperty("imponibile")
		private double imponibile;

		// Getters and Setters

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public String getOpenApi() {
			return openApi;
		}

		public void setOpenApi(String openApi) {
			this.openApi = openApi;
		}

		public String getAsString() {
			return asString;
		}

		public void setAsString(String asString) {
			this.asString = asString;
		}

		public String getAsString2() {
			return asString2;
		}

		public void setAsString2(String asString2) {
			this.asString2 = asString2;
		}

		public String getAsString3() {
			return asString3;
		}

		public void setAsString3(String asString3) {
			this.asString3 = asString3;
		}

		public int getNummandato() {
			return nummandato;
		}

		public void setNummandato(int nummandato) {
			this.nummandato = nummandato;
		}

		public Date getDatamandato() {
			return datamandato;
		}

		public void setDatamandato(Date datamandato) {
			this.datamandato = datamandato;
		}

		public String getOggetto() {
			return oggetto;
		}

		public void setOggetto(String oggetto) {
			this.oggetto = oggetto;
		}

		public String getNominativo() {
			return nominativo;
		}

		public void setNominativo(String nominativo) {
			this.nominativo = nominativo;
		}

		public String getCodicefiscale() {
			return codicefiscale;
		}

		public void setCodicefiscale(String codicefiscale) {
			this.codicefiscale = codicefiscale;
		}

		public double getImportolordo() {
			return importolordo;
		}

		public void setImportolordo(double importolordo) {
			this.importolordo = importolordo;
		}

		public String getCig() {
			return cig;
		}

		public void setCig(String cig) {
			this.cig = cig;
		}

		public String getCup() {
			return cup;
		}

		public void setCup(String cup) {
			this.cup = cup;
		}

		public String getSiglapianofin() {
			return siglapianofin;
		}

		public void setSiglapianofin(String siglapianofin) {
			this.siglapianofin = siglapianofin;
		}

		public Date getDataquietanza() {
			return dataquietanza;
		}

		public void setDataquietanza(Date dataquietanza) {
			this.dataquietanza = dataquietanza;
		}

		public double getImportoquietanza() {
			return importoquietanza;
		}

		public void setImportoquietanza(double importoquietanza) {
			this.importoquietanza = importoquietanza;
		}

		public String getNumquietanza() {
			return numquietanza;
		}

		public void setNumquietanza(String numquietanza) {
			this.numquietanza = numquietanza;
		}

		public double getImponibile() {
			return imponibile;
		}

		public void setImponibile(double imponibile) {
			this.imponibile = imponibile;
		}
	}
}
