package it.appaltiecontratti.inbox.entity.avvisi;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.contratti.RupEntry;

@ApiModel(description = "Contenitore dei campi dell'avviso per le response")
public class AvvisoEntry implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(value = "stazioneAppaltante dell'avviso")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Id dell'avviso")
	private Long numeroAvviso;
	@ApiModelProperty(value = "Codice sistema dell'avviso")
	private Long codSistema;
	@ApiModelProperty(value = "Codice del tapellato dell'avviso")
	private Long tipoAvviso;
	@ApiModelProperty(value = "Data dell'avviso")
	private Date dataAvviso;
	@ApiModelProperty(value = "Descrizione dell'avviso")
	private String descrizione;
	@ApiModelProperty(value = "Cig dell'avviso")
	private String cig;
	@ApiModelProperty(value = "Data scadenza dell'avviso")
	private Date dataScadenza;
	@ApiModelProperty(value = "Cup dell'avviso")
	private String cup;
	@ApiModelProperty(value = "Cui dell'avviso")
	private String cui;
	@ApiModelProperty(value = "Codice rup dell'avviso")
	private String rup;
	@ApiModelProperty(value = "Rup dell'avviso")
	private RupEntry rupEntry;
	@ApiModelProperty(value = "Id generato dell'avviso")
	private Long idRicevuto;
	@ApiModelProperty(value = "Syscon associato all'avviso")
	private Long syscon;
	@ApiModelProperty(value = "Lista documenti dell'avviso")
	private List<DocAvvisoEntry> documents;
	@ApiModelProperty(value = "Lista pubblicazioni dell'avviso")

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public Long getNumeroAvviso() {
		return numeroAvviso;
	}

	public void setNumeroAvviso(Long numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}

	public Long getCodSistema() {
		return codSistema;
	}

	public void setCodSistema(Long codSistema) {
		this.codSistema = codSistema;
	}

	public Long getTipoAvviso() {
		return tipoAvviso;
	}

	public void setTipoAvviso(Long tipoAvviso) {
		this.tipoAvviso = tipoAvviso;
	}

	

	public Date getDataAvviso() {
		return dataAvviso;
	}

	public void setDataAvviso(Date dataAvviso) {
		this.dataAvviso = dataAvviso;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataScadenza() {
		return dataScadenza;
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

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public RupEntry getRupEntry() {
		return rupEntry;
	}

	public void setRupEntry(RupEntry rupEntry) {
		this.rupEntry = rupEntry;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public List<DocAvvisoEntry> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocAvvisoEntry> documents) {
		this.documents = documents;
	}


}
