package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dati di pubblicazione di un avviso
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description = "Contenitore per i dati di un avviso in fase di inserimento")
public class AvvisoInsertForm {

	@ApiModelProperty(value = "Stazione appaltante dell'avviso")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Numero dell'avviso")
	private Integer numeroAvviso;
	@ApiModelProperty(value = "Cod Sistema dell'avviso")
	private Integer codSistema;
	@NotNull
	@ApiModelProperty(value = "Codice RUP dell'avviso")
	private String rupCod;
	@ApiModelProperty(value = "Cig dell'avviso")
	private String cig;
	@ApiModelProperty(value = "Cup dell'avviso")
	private String cup;
	@ApiModelProperty(value = "Cui dell'avviso")
	private String cui;
	@NotNull
	@ApiModelProperty(value = "Codice tabellato TipoAvviso")
	private Integer tipologia;
	@NotNull
	@ApiModelProperty(value = "Data dell'avviso")
	private String dataAvviso;
	@ApiModelProperty(hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataAvvisoToInsert;
	@ApiModelProperty(hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataScadenzaToInsert;
	@NotNull
	@ApiModelProperty(value = "Descrizione dell'avviso")
	private String descrizione;
	@ApiModelProperty(value = "Data scadenza dell'avviso")
	private String dataScadenza;
	@ApiModelProperty(value = "Lista documentidell'avviso")
	private List<AvvisoDocument> newDocuments;
	@ApiModelProperty(value = "syscon dell'avviso")
	private Long syscon;
	@ApiModelProperty(value = "comune dell'avviso")
	private String comune;
	@ApiModelProperty(value = "provincia dell'avviso")
	private String provincia;
	@ApiModelProperty(value = "indirizzo dell'avviso")
	private String indirizzo;
	@ApiModelProperty(value = "Id ufficio")
	private Long idUfficio;

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public Integer getNumeroAvviso() {
		return numeroAvviso;
	}

	public void setNumeroAvviso(Integer numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}

	public Integer getCodSistema() {
		return codSistema;
	}

	public void setCodSistema(Integer codSistema) {
		this.codSistema = codSistema;
	}

	public String getRupCod() {
		return rupCod;
	}

	public void setRupCod(String rupCod) {
		this.rupCod = rupCod;
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

	public Integer getTipologia() {
		return tipologia;
	}

	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}

	public String getDataAvviso() {
		return dataAvviso;
	}

	public void setDataAvviso(String dataAvviso) {
		this.dataAvviso = dataAvviso;
	}

	public Date getDataAvvisoToInsert() {
		return dataAvvisoToInsert;
	}

	public void setDataAvvisoToInsert(Date dataAvvisoToInsert) {
		this.dataAvvisoToInsert = dataAvvisoToInsert;
	}

	public Date getDataScadenzaToInsert() {
		return dataScadenzaToInsert;
	}

	public void setDataScadenzaToInsert(Date dataScadenzaToInsert) {
		this.dataScadenzaToInsert = dataScadenzaToInsert;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public List<AvvisoDocument> getNewDocuments() {
		return newDocuments;
	}

	public void setNewDocuments(List<AvvisoDocument> newDocuments) {
		this.newDocuments = newDocuments;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
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

	public Long getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}
	
	

}
