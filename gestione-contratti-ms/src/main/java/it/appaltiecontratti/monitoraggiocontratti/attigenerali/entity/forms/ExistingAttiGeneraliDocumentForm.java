package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description = "Contenitore per i dati di un documento di un atto generale esistente.")
public class ExistingAttiGeneraliDocumentForm implements Serializable {

    private static final long serialVersionUID = 7274216738190060872L;

    private Long idAtto;
    private Integer numDoc;
    private String descri;
    private String titolo;
    private String tipoFile;
    private String url;

    public Long getIdAtto() { return idAtto; }
    public void setIdAtto(Long idAtto) { this.idAtto = idAtto; }

    public Integer getNumDoc() { return numDoc; }
    public void setNumDoc(Integer numDoc) { this.numDoc = numDoc; }

    public String getDescri() { return descri; }
    public void setDescri(String descri) { this.descri = descri; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getTipoFile() { return tipoFile; }
    public void setTipoFile(String tipoFile) { this.tipoFile = tipoFile; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

}
