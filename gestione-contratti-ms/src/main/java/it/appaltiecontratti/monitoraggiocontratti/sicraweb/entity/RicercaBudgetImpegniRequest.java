package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaBudgetImpegniRequest {

	private String parte;
	@JsonProperty("tipo_risultato")
	private String tiporisultato;
	// private int annocompetenza;
	private Date datavaluta;
//	private String codufficio;
//	private String codresponsabile;
//	private int codmissione;
//	private int codprogramma;
//	private int codtitolo;
//	private int codmacroaggregato;
//	private int codtipologia;
//	private int codcategoria;
//	private int codcentrocosto;
//	private String siglacpt;
//	private String siglapianofinanziario;
//	private int copfpv;
//	private int codlavoro;
//	private int codtipospesa;
//	private int codtipofinanz;
//	private int codprogetto;
//	private boolean aclbilancio;
//	private int idimpegno;
//	private int codimpegno;
//	private int codannuale;
//	private int anno;
//	private String des;
	private String cup;

//	private String siglastruttura;
//	private String desstruttura;
//	private String siglariclass;
//	private String desriclass;
//	private String destipovoce;
//	private String identipovoce;
//	private String siglatipospesa;
//	private String destipospesa;
//	private Anagrafica anagrafica;
//	private boolean includisub;
//	private boolean prenotazione;
//	private boolean flraggruppacentrocosto;
//	private boolean flraggruppacpt;
//	private boolean flraggruppapianofin;
//	private boolean flraggruppalavoro;
//	private boolean flraggruppacup;
	private boolean flraggruppacig;
	private boolean flraggruppaanagrafica;
//	private boolean flraggruppafpv;
//	private boolean flraggruppatipospesa;
	private boolean flraggruppatipofinanz;

//	private boolean flraggruppaprogetto;
//	private boolean flraggruppacodiceriferimento;
//	private String codiceriferimento;
//	private String siglaTipoAtto;
//	private String codSettore;
//	private int numatto;
//	private int annoatto;
//	private LocalDateTime dataatto;

	@JsonProperty("listaCig")
	private List<Cig> listaCig;

	public List<Cig> getListaCig() {
		return listaCig;
	}

	public void setListaCig(List<Cig> listaCig) {
		this.listaCig = listaCig;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getTiporisultato() {
		return tiporisultato;
	}

	public void setTiporisultato(String tiporisultato) {
		this.tiporisultato = tiporisultato;
	}

	public Date getDatavaluta() {
		return datavaluta;
	}

	public void setDatavaluta(Date datavaluta) {
		this.datavaluta = datavaluta;
	}

	public boolean isFlraggruppacig() {
		return flraggruppacig;
	}

	public void setFlraggruppacig(boolean flraggruppacig) {
		this.flraggruppacig = flraggruppacig;
	}

	public boolean isFlraggruppaanagrafica() {
		return flraggruppaanagrafica;
	}

	public void setFlraggruppaanagrafica(boolean flraggruppaanagrafica) {
		this.flraggruppaanagrafica = flraggruppaanagrafica;
	}

	public boolean isFlraggruppatipofinanz() {
		return flraggruppatipofinanz;
	}

	public void setFlraggruppatipofinanz(boolean flraggruppatipofinanz) {
		this.flraggruppatipofinanz = flraggruppatipofinanz;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

}
