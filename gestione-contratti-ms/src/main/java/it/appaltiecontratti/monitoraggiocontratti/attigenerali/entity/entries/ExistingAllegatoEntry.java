package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description = "Contenitore per i dati di un documento dell'atto generale preesistente (in fase di modifica)")
public class ExistingAllegatoEntry implements Serializable {

    private static final long serialVersionUID = -5140699885997175508L;

    private Long idAtto;
    private Long numDoc;
    private String url;
    private String tipoFile;

    public Long getIdAtto() { return idAtto; }
    public  void setIdAtto(Long idAtto) { this.idAtto = idAtto; }

    public Long getNumDoc() {
        return numDoc;
    }
    public void setNumDoc(Long numDoc) {
        this.numDoc = numDoc;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTipoFile() {
        return tipoFile;
    }
    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }
}
