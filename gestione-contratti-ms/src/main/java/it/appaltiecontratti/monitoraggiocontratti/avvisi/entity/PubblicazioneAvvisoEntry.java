package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class PubblicazioneAvvisoEntry implements Serializable {

	private static final long serialVersionUID = -2030200161003310279L;

	private String codiceFiscaleSA;
	private Integer tipologia;
	private String data;
	private String descrizione;
	private String cig;
	private String cup;
	private String cui;
	private String scadenza;
	private PubblicazioneTecnicoEntry rup;
	private String clientId;
	private List<PubblicazioneDocumentoAvvisoEntry> documenti;
	private String idRicevuto;
	private String syscon;
	private String autore;
	private String comune;
	private String provincia;
	private String indirizzo;
	private String ufficio;
	
	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}

	public Integer getTipologia() {
		return tipologia;
	}

	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getScadenza() {
		return scadenza;
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

	public PubblicazioneTecnicoEntry getRup() {
		return rup;
	}

	public void setRup(PubblicazioneTecnicoEntry rup) {
		this.rup = rup;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<PubblicazioneDocumentoAvvisoEntry> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<PubblicazioneDocumentoAvvisoEntry> documenti) {
		this.documenti = documenti;
	}

	public String getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(String idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public String getSyscon() {
		return syscon;
	}

	public void setSyscon(String syscon) {
		this.syscon = syscon;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	
	

}
