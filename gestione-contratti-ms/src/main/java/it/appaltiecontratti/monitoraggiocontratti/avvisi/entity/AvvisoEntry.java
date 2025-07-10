package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.UffEntry;

@ApiModel(description = "Contenitore dei campi dell'avviso per le response")
public class AvvisoEntry implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	private String nominativoStazioneAppaltante;
	@ApiModelProperty(value = "stazioneAppaltante dell'avviso")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Id dell'avviso")
	private Integer numeroAvviso;
	@ApiModelProperty(value = "Codice sistema dell'avviso")
	private Integer codSistema;
	@ApiModelProperty(value = "Codice del tapellato dell'avviso")
	private Integer tipoAvviso;
	@ApiModelProperty(value = "Data dell'avviso")
	private String dataAvviso;
	@ApiModelProperty(value = "Descrizione dell'avviso")
	private String descrizione;
	@ApiModelProperty(value = "Cig dell'avviso")
	private String cig;
	@ApiModelProperty(value = "Data scadenza dell'avviso")
	private String dataScadenza;
	@ApiModelProperty(value = "Cup dell'avviso")
	private String cup;
	@ApiModelProperty(value = "Cui dell'avviso")
	private String cui;
	@ApiModelProperty(value = "Codice rup dell'avviso")
	private String rup;
	@ApiModelProperty(value = "Rup dell'avviso")
	private RupEntry rupEntry;
	@ApiModelProperty(value = "Id generato dell'avviso")
	private String idGenerato;
	@ApiModelProperty(value = "Syscon associato all'avviso")
	private Long syscon;
	@ApiModelProperty(value = "Lista documenti dell'avviso")
	private List<ExistingDocAvvisoEntry> existingDocuments;
	@ApiModelProperty(value = "Lista pubblicazioni dell'avviso")
	List<FlussiAvviso> pubblicazioni;
	@ApiModelProperty(value = "comune dell'avviso")
	private String comune;
	@ApiModelProperty(value = "provincia dell'avviso")
	private String provincia;
	@ApiModelProperty(value = "indirizzo dell'avviso")
	private String indirizzo;
	@ApiModelProperty(value = "Id ufficio dell'avviso")
	private Long idUfficio;
	@ApiModelProperty(value = "Id ricevuto dell'avviso in fase di pubblicazione")
	private Long idRicevuto;
	
	private String ufficioDesc;
	
	private UffEntry ufficio;

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

	public Integer getTipoAvviso() {
		return tipoAvviso;
	}

	public void setTipoAvviso(Integer tipoAvviso) {
		this.tipoAvviso = tipoAvviso;
	}

	public String getDataAvviso() {
		return dataAvviso;
	}

	public void setDataAvviso(String dataAvviso) {
		this.dataAvviso = dataAvviso;
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

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
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

	public String getIdGenerato() {
		return idGenerato;
	}

	public void setIdGenerato(String idGenerato) {
		this.idGenerato = idGenerato;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public List<ExistingDocAvvisoEntry> getExistingDocuments() {
		return existingDocuments;
	}

	public void setExistingDocuments(List<ExistingDocAvvisoEntry> existingDocuments) {
		this.existingDocuments = existingDocuments;
	}

	public List<FlussiAvviso> getPubblicazioni() {
		return pubblicazioni;
	}

	public void setPubblicazioni(List<FlussiAvviso> pubblicazioni) {
		this.pubblicazioni = pubblicazioni;
	}

	public String getNominativoStazioneAppaltante() {
		return nominativoStazioneAppaltante;
	}

	public void setNominativoStazioneAppaltante(String nominativoStazioneAppaltante) {
		this.nominativoStazioneAppaltante = nominativoStazioneAppaltante;
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

	public UffEntry getUfficio() {
		return ufficio;
	}

	public void setUfficio(UffEntry ufficio) {
		this.ufficio = ufficio;
	}

	public String getUfficioDesc() {
		return ufficioDesc;
	}

	public void setUfficioDesc(String ufficioDesc) {
		this.ufficioDesc = ufficioDesc;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}



}
