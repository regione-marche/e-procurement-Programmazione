package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms;

import io.swagger.annotations.ApiModel;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoMotivazioneEntry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description = "Contenitore per i dati di un atto generale in fase di modifica")
public class AttoGeneraleUpdateForm implements Serializable {

    private static final long serialVersionUID = -1189179263379090741L;

    private Long idAtto;
    private String stazioneAppaltante;
    private String rup;
    private Long tipologia;
    private String descrizione;
    private Date dataAtto;
    private String numeroAtto;
    private String primaPubb;
    private Date dataPrimaPubb;
    private Date dataPubbSistema;
    private Date dataScadenza;
    private List<AllegatoEntry> documents;
    private List<AllegatoEntry> updateDocuments;
    private List<AllegatoMotivazioneEntry> allegatiDaAnnullare;

    public Long getIdAtto() { return idAtto; }
    public void setIdAtto(Long idAtto) { this.idAtto = idAtto; }

    public String getStazioneAppaltante() { return stazioneAppaltante; }
    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }

    public String getRup() { return this.rup; }
    public void setRup(String rup) { this.rup = rup; }

    public Long getTipologia() { return tipologia; }
    public void setTipologia(Long tipologia) { this.tipologia = tipologia; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Date getDataAtto() { return dataAtto; }
    public void setDataAtto(Date dataAtto) { this.dataAtto = dataAtto; }

    public String getNumeroAtto() { return numeroAtto; }
    public void setNumeroAtto(String numeroAtto) { this.numeroAtto = numeroAtto; }

    public String getPrimaPubb() { return primaPubb; }
    public void setPrimaPubb(String primaPubb) { this.primaPubb = primaPubb; }

    public Date getDataPrimaPubb() { return dataPrimaPubb; }
    public void setDataPrimaPubb(Date dataPrimaPubb) { this.dataPrimaPubb = dataPrimaPubb; }

    public Date getDataPubbSistema() { return dataPubbSistema; }
    public void setDataPubbSistema(Date dataPubbSistema) { this.dataPubbSistema = dataPubbSistema; }

    public Date getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(Date dataScadenza) { this.dataScadenza = dataScadenza; }

    public List<AllegatoEntry> getDocuments() { return documents; }
    public void setDocuments(List<AllegatoEntry> documents) { this.documents = documents; }

    public List<AllegatoEntry> getUpdateDocuments() { return updateDocuments; }
    public void setUpdateDocuments(List<AllegatoEntry> updateDocuments) { this.updateDocuments = updateDocuments; }

    public List<AllegatoMotivazioneEntry> getAllegatiDaAnnullare() { return allegatiDaAnnullare; }
    public void setAllegatiDaAnnullare(List<AllegatoMotivazioneEntry> allegatiDaAnnullare) { this.allegatiDaAnnullare = allegatiDaAnnullare; }
}
