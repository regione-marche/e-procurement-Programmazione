package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaBudgetImpegniResponse {

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

	@JsonProperty("lista_budget")
	private List<ListaBudget> listaBudget;

	@JsonProperty("risultato_base64")
	private String risultatoBase64;

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

	public List<ListaBudget> getListaBudget() {
		return listaBudget;
	}

	public void setListaBudget(List<ListaBudget> listaBudget) {
		this.listaBudget = listaBudget;
	}

	public String getRisultatoBase64() {
		return risultatoBase64;
	}

	public void setRisultatoBase64(String risultatoBase64) {
		this.risultatoBase64 = risultatoBase64;
	}

	// Inner Classes

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

	public static class ListaBudget {

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

		@JsonProperty("parte")
		private String parte;

		@JsonProperty("idimpegno")
		private Integer idimpegno;

		@JsonProperty("codimpegno")
		private Integer codimpegno;

		@JsonProperty("codannuale")
		private Integer codannuale;

		@JsonProperty("anno")
		private Integer anno;

		@JsonProperty("idimpegnopadre")
		private Integer idimpegnopadre;

		@JsonProperty("codimpegnopadre")
		private String codimpegnopadre;

		@JsonProperty("datavaluta")
		private Long datavaluta;

		@JsonProperty("descrizione")
		private String descrizione;

		@JsonProperty("idcapitolo")
		private Integer idcapitolo;

		@JsonProperty("numcapitolo")
		private Integer numcapitolo;

		@JsonProperty("codificacapitolo")
		private String codificacapitolo;

		@JsonProperty("descapitolo")
		private String descapitolo;

		@JsonProperty("annocompetenza")
		private Integer annocompetenza;

		@JsonProperty("idcentrocosto")
		private String idcentrocosto;

		@JsonProperty("codcentrocosto")
		private String codcentrocosto;

		@JsonProperty("descentrocosto")
		private String descentrocosto;

		@JsonProperty("idlavoro")
		private String idlavoro;

		@JsonProperty("codlavoro")
		private String codlavoro;

		@JsonProperty("deslavoro")
		private String deslavoro;

		@JsonProperty("idtipospesa")
		private String idtipospesa;

		@JsonProperty("codtipospesa")
		private String codtipospesa;

		@JsonProperty("destipospesa")
		private String destipospesa;

		@JsonProperty("idtipofinanz")
		private String idtipofinanz;

		@JsonProperty("codtipofinanz")
		private String codtipofinanz;

		@JsonProperty("destipofinanz")
		private String destipofinanz;

		@JsonProperty("idprogetto")
		private String idprogetto;

		@JsonProperty("codprogetto")
		private String codprogetto;

		@JsonProperty("desprogetto")
		private String desprogetto;

		@JsonProperty("idcpt")
		private String idcpt;

		@JsonProperty("sigcpt")
		private String sigcpt;

		@JsonProperty("descpt")
		private String descpt;

		@JsonProperty("idpianofin")
		private String idpianofin;

		@JsonProperty("sigpianofin")
		private String sigpianofin;

		@JsonProperty("despianofin")
		private String despianofin;

		@JsonProperty("cup")
		private String cup;

		@JsonProperty("cig")
		private String cig;

		@JsonProperty("idanagrafica")
		private String idanagrafica;

		@JsonProperty("ragionesocialeanagrafica")
		private String ragionesocialeanagrafica;

		@JsonProperty("codiceriferimento")
		private String codiceriferimento;

		@JsonProperty("copertofpv")
		private String copertofpv;

		@JsonProperty("impegnato")
		private Double impegnato;

		@JsonProperty("disponibile")
		private Double disponibile;

		@JsonProperty("mandatiemessi")
		private Double mandatiemessi;

		@JsonProperty("documentiaperti")
		private Double documentiaperti;

		@JsonProperty("prenotatodisponibile")
		private Integer prenotatodisponibile;

		@JsonProperty("dataatto")
		private Date dataatto;

		@JsonProperty("liquidato")
		private Double liquidato;

		@JsonProperty("flgautoincr")
		private Boolean flgautoincr;

		@JsonProperty("ordinato")
		private Integer ordinato;

		@JsonProperty("destipovoce")
		private String destipovoce;

		@JsonProperty("identipovoce")
		private String identipovoce;

		@JsonProperty("numAtto")
		private Integer numAtto;

		@JsonProperty("annoAtto")
		private Integer annoAtto;

		@JsonProperty("codSettore")
		private String codSettore;

		@JsonProperty("siglaTipoAtto")
		private String siglaTipoAtto;

		@JsonProperty("siglaTipoSpesa")
		private String siglaTipoSpesa;

		@JsonProperty("lista_riclassificazioni")
		private Object listaRiclassificazioni; // Adjust type if needed

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

		public String getParte() {
			return parte;
		}

		public void setParte(String parte) {
			this.parte = parte;
		}

		public Integer getIdimpegno() {
			return idimpegno;
		}

		public void setIdimpegno(Integer idimpegno) {
			this.idimpegno = idimpegno;
		}

		public Integer getCodimpegno() {
			return codimpegno;
		}

		public void setCodimpegno(Integer codimpegno) {
			this.codimpegno = codimpegno;
		}

		public Integer getCodannuale() {
			return codannuale;
		}

		public void setCodannuale(Integer codannuale) {
			this.codannuale = codannuale;
		}

		public Integer getAnno() {
			return anno;
		}

		public void setAnno(Integer anno) {
			this.anno = anno;
		}

		public Integer getIdimpegnopadre() {
			return idimpegnopadre;
		}

		public void setIdimpegnopadre(Integer idimpegnopadre) {
			this.idimpegnopadre = idimpegnopadre;
		}

		public String getCodimpegnopadre() {
			return codimpegnopadre;
		}

		public void setCodimpegnopadre(String codimpegnopadre) {
			this.codimpegnopadre = codimpegnopadre;
		}

		public Long getDatavaluta() {
			return datavaluta;
		}

		public void setDatavaluta(Long datavaluta) {
			this.datavaluta = datavaluta;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public Integer getIdcapitolo() {
			return idcapitolo;
		}

		public void setIdcapitolo(Integer idcapitolo) {
			this.idcapitolo = idcapitolo;
		}

		public Integer getNumcapitolo() {
			return numcapitolo;
		}

		public void setNumcapitolo(Integer numcapitolo) {
			this.numcapitolo = numcapitolo;
		}

		public String getCodificacapitolo() {
			return codificacapitolo;
		}

		public void setCodificacapitolo(String codificacapitolo) {
			this.codificacapitolo = codificacapitolo;
		}

		public String getDescapitolo() {
			return descapitolo;
		}

		public void setDescapitolo(String descapitolo) {
			this.descapitolo = descapitolo;
		}

		public Integer getAnnocompetenza() {
			return annocompetenza;
		}

		public void setAnnocompetenza(Integer annocompetenza) {
			this.annocompetenza = annocompetenza;
		}

		public String getIdcentrocosto() {
			return idcentrocosto;
		}

		public void setIdcentrocosto(String idcentrocosto) {
			this.idcentrocosto = idcentrocosto;
		}

		public String getCodcentrocosto() {
			return codcentrocosto;
		}

		public void setCodcentrocosto(String codcentrocosto) {
			this.codcentrocosto = codcentrocosto;
		}

		public String getDescentrocosto() {
			return descentrocosto;
		}

		public void setDescentrocosto(String descentrocosto) {
			this.descentrocosto = descentrocosto;
		}

		public String getIdlavoro() {
			return idlavoro;
		}

		public void setIdlavoro(String idlavoro) {
			this.idlavoro = idlavoro;
		}

		public String getCodlavoro() {
			return codlavoro;
		}

		public void setCodlavoro(String codlavoro) {
			this.codlavoro = codlavoro;
		}

		public String getDeslavoro() {
			return deslavoro;
		}

		public void setDeslavoro(String deslavoro) {
			this.deslavoro = deslavoro;
		}

		public String getIdtipospesa() {
			return idtipospesa;
		}

		public void setIdtipospesa(String idtipospesa) {
			this.idtipospesa = idtipospesa;
		}

		public String getCodtipospesa() {
			return codtipospesa;
		}

		public void setCodtipospesa(String codtipospesa) {
			this.codtipospesa = codtipospesa;
		}

		public String getDestipospesa() {
			return destipospesa;
		}

		public void setDestipospesa(String destipospesa) {
			this.destipospesa = destipospesa;
		}

		public String getIdtipofinanz() {
			return idtipofinanz;
		}

		public void setIdtipofinanz(String idtipofinanz) {
			this.idtipofinanz = idtipofinanz;
		}

		public String getCodtipofinanz() {
			return codtipofinanz;
		}

		public void setCodtipofinanz(String codtipofinanz) {
			this.codtipofinanz = codtipofinanz;
		}

		public String getDestipofinanz() {
			return destipofinanz;
		}

		public void setDestipofinanz(String destipofinanz) {
			this.destipofinanz = destipofinanz;
		}

		public String getIdprogetto() {
			return idprogetto;
		}

		public void setIdprogetto(String idprogetto) {
			this.idprogetto = idprogetto;
		}

		public String getCodprogetto() {
			return codprogetto;
		}

		public void setCodprogetto(String codprogetto) {
			this.codprogetto = codprogetto;
		}

		public String getDesprogetto() {
			return desprogetto;
		}

		public void setDesprogetto(String desprogetto) {
			this.desprogetto = desprogetto;
		}

		public String getIdcpt() {
			return idcpt;
		}

		public void setIdcpt(String idcpt) {
			this.idcpt = idcpt;
		}

		public String getSigcpt() {
			return sigcpt;
		}

		public void setSigcpt(String sigcpt) {
			this.sigcpt = sigcpt;
		}

		public String getDescpt() {
			return descpt;
		}

		public void setDescpt(String descpt) {
			this.descpt = descpt;
		}

		public String getIdpianofin() {
			return idpianofin;
		}

		public void setIdpianofin(String idpianofin) {
			this.idpianofin = idpianofin;
		}

		public String getSigpianofin() {
			return sigpianofin;
		}

		public void setSigpianofin(String sigpianofin) {
			this.sigpianofin = sigpianofin;
		}

		public String getDespianofin() {
			return despianofin;
		}

		public void setDespianofin(String despianofin) {
			this.despianofin = despianofin;
		}

		public String getCup() {
			return cup;
		}

		public void setCup(String cup) {
			this.cup = cup;
		}

		public String getCig() {
			return cig;
		}

		public void setCig(String cig) {
			this.cig = cig;
		}

		public String getIdanagrafica() {
			return idanagrafica;
		}

		public void setIdanagrafica(String idanagrafica) {
			this.idanagrafica = idanagrafica;
		}

		public String getRagionesocialeanagrafica() {
			return ragionesocialeanagrafica;
		}

		public void setRagionesocialeanagrafica(String ragionesocialeanagrafica) {
			this.ragionesocialeanagrafica = ragionesocialeanagrafica;
		}

		public String getCodiceriferimento() {
			return codiceriferimento;
		}

		public void setCodiceriferimento(String codiceriferimento) {
			this.codiceriferimento = codiceriferimento;
		}

		public String getCopertofpv() {
			return copertofpv;
		}

		public void setCopertofpv(String copertofpv) {
			this.copertofpv = copertofpv;
		}

		public Double getImpegnato() {
			return impegnato;
		}

		public void setImpegnato(Double impegnato) {
			this.impegnato = impegnato;
		}

		public Double getDisponibile() {
			return disponibile;
		}

		public void setDisponibile(Double disponibile) {
			this.disponibile = disponibile;
		}

		public Double getMandatiemessi() {
			return mandatiemessi;
		}

		public void setMandatiemessi(Double mandatiemessi) {
			this.mandatiemessi = mandatiemessi;
		}

		public Double getDocumentiaperti() {
			return documentiaperti;
		}

		public void setDocumentiaperti(Double documentiaperti) {
			this.documentiaperti = documentiaperti;
		}

		public Integer getPrenotatodisponibile() {
			return prenotatodisponibile;
		}

		public void setPrenotatodisponibile(Integer prenotatodisponibile) {
			this.prenotatodisponibile = prenotatodisponibile;
		}

		public Date getDataatto() {
			return dataatto;
		}

		public void setDataatto(Date dataatto) {
			this.dataatto = dataatto;
		}

		public Double getLiquidato() {
			return liquidato;
		}

		public void setLiquidato(Double liquidato) {
			this.liquidato = liquidato;
		}

		public Boolean getFlgautoincr() {
			return flgautoincr;
		}

		public void setFlgautoincr(Boolean flgautoincr) {
			this.flgautoincr = flgautoincr;
		}

		public Integer getOrdinato() {
			return ordinato;
		}

		public void setOrdinato(Integer ordinato) {
			this.ordinato = ordinato;
		}

		public String getDestipovoce() {
			return destipovoce;
		}

		public void setDestipovoce(String destipovoce) {
			this.destipovoce = destipovoce;
		}

		public String getIdentipovoce() {
			return identipovoce;
		}

		public void setIdentipovoce(String identipovoce) {
			this.identipovoce = identipovoce;
		}

		public Integer getNumAtto() {
			return numAtto;
		}

		public void setNumAtto(Integer numAtto) {
			this.numAtto = numAtto;
		}

		public Integer getAnnoAtto() {
			return annoAtto;
		}

		public void setAnnoAtto(Integer annoAtto) {
			this.annoAtto = annoAtto;
		}

		public String getCodSettore() {
			return codSettore;
		}

		public void setCodSettore(String codSettore) {
			this.codSettore = codSettore;
		}

		public String getSiglaTipoAtto() {
			return siglaTipoAtto;
		}

		public void setSiglaTipoAtto(String siglaTipoAtto) {
			this.siglaTipoAtto = siglaTipoAtto;
		}

		public String getSiglaTipoSpesa() {
			return siglaTipoSpesa;
		}

		public void setSiglaTipoSpesa(String siglaTipoSpesa) {
			this.siglaTipoSpesa = siglaTipoSpesa;
		}

		public Object getListaRiclassificazioni() {
			return listaRiclassificazioni;
		}

		public void setListaRiclassificazioni(Object listaRiclassificazioni) {
			this.listaRiclassificazioni = listaRiclassificazioni;
		}

	}
}
