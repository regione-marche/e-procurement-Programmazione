package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AttoGeneraleEntry implements Serializable {

    private static final long serialVersionUID = 5184811388841462021L;

    @ApiModelProperty(value = "id dell'atto")
    private Long idAtto;

    @ApiModelProperty(value = "stazione appaltante")
    private String stazioneAppaltante;

    @ApiModelProperty(value = "Tipologia tabellato W9032")
    private Long tipologia;

    @ApiModelProperty(value = "Data atto")
    private Date dataAtto;

    @ApiModelProperty(value = "Numero/Identificativo dell'atto")
    private String numeroAtto;

    @ApiModelProperty(value = "Descrizione dell'atto")
    private String descrizione;

    @ApiModelProperty(value = "Prima pubblicazione?")
    private String primaPubb;

    @ApiModelProperty(value = "Data prima pubblicazione")
    private Date dataPrimaPubb;

    @ApiModelProperty(value = "Data pubblicazione sul sistema")
    private Date dataPubbSistema;

    @ApiModelProperty(value = "Booleano per indicare se l'atto è stato pubblicato")
    private Boolean pubblicato;

    @ApiModelProperty(value = "Booleano per indicare se l'atto è stato annullato")
    private Boolean annullato;

    @ApiModelProperty(value = "Data di scadenza")
    private Date dataScadenza;

    @ApiModelProperty(value = "Rup dell'atto")
    private String rup;

    @ApiModelProperty(value = "Tecnico dell'atto")
    private RupEntry tecnico;

    @ApiModelProperty(value = "Identificativo utente")
    private Long utenteProp;

    @ApiModelProperty(value = "Id ufficio")
    private Long idUfficio;

    @ApiModelProperty(value = "Utente che effettua/ha effettuato la cancellazione dell'atto")
    private String utenteCanc;

    @ApiModelProperty(value = "Data della cancellazione dell'atto")
    private Date dataCanc;

    @ApiModelProperty(value = "Motivo della cancellazione dell'atto")
    private String motivoCanc;

    @ApiModelProperty(value = "Documenti esistenti modificati dall'utente")
    private List<AllegatoEntry> existingDocuments;

    @ApiModelProperty(value = "Documenti del singolo atto")
    private List<AllegatoEntry> documents;

    /**
     * GETTERS/SETTERS
     * */

    public Long getIdAtto() { return idAtto; }
    public void setIdAtto(Long idAtto) { this.idAtto = idAtto; }

    public String getStazioneAppaltante() { return stazioneAppaltante; }
    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }

    public Long getTipologia() { return tipologia; }
    public void setTipologia(Long tipologia) { this.tipologia = tipologia; }

    public Date getDataAtto() { return dataAtto; }
    public void setDataAtto(Date dataAtto) { this.dataAtto = dataAtto; }

    public String getNumeroAtto() { return numeroAtto; }
    public void setNumeroAtto(String numeroAtto) { this.numeroAtto = numeroAtto; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getPrimaPubb() { return primaPubb; }
    public void setPrimaPubb(String primaPubb) { this.primaPubb = primaPubb; }

    public Date getDataPrimaPubb() { return dataPrimaPubb; }
    public void setDataPrimaPubb(Date dataPrimaPubb) { this.dataPrimaPubb = dataPrimaPubb; }

    public Date getDataPubbSistema() { return dataPubbSistema; }
    public void setDataPubbSistema(Date dataPubbSistema) { this.dataPubbSistema = dataPubbSistema; }

    public Boolean getPubblicato() { return pubblicato; }
    public void setPubblicato(Boolean pubblicato) { this.pubblicato = pubblicato; }

    public Boolean getAnnullato() { return annullato; }
    public void setAnnullato(Boolean annullato) { this.annullato = annullato; }

    public Date getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(Date dataScadenza) { this.dataScadenza = dataScadenza; }

    public String getRup() { return rup; }
    public void setRup(String rup) { this.rup = rup; }

    public RupEntry getTecnico() { return tecnico; }
    public void setTecnico(RupEntry tecnico) { this.tecnico = tecnico; }

    public Long getUtenteProp() { return utenteProp; }
    public void setUtenteProp(Long utenteProp) { this.utenteProp = utenteProp; }

    public Long getIdUfficio() { return idUfficio; }
    public void setIdUfficio(Long idUfficio) { this.idUfficio = idUfficio; }

    public String getUtenteCanc() { return utenteCanc; }
    public void setUtenteCanc(String utenteCanc) { this.utenteCanc = utenteCanc; }

    public Date getDataCanc() { return dataCanc; }
    public void setDataCanc(Date dataCanc) { this.dataCanc = dataCanc; }

    public String getMotivoCanc() { return motivoCanc; }
    public void setMotivoCanc(String motivoCanc) { this.motivoCanc = motivoCanc; }

    public List<AllegatoEntry> getExistingDocuments() { return existingDocuments; }
    public void setExistingDocuments(List<AllegatoEntry> existingDocuments) { this.existingDocuments = existingDocuments; }

    public List<AllegatoEntry> getDocuments() { return documents; }
    public void setDocuments(List<AllegatoEntry> documents) { this.documents = documents; }
}
