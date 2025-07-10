package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description = "Dati di pubblicazione di un atto generale")
public class AttoGeneraleDocumentInsertForm implements Serializable {

    private static final long serialVersionUID = 1072645652729922815L;

    private String stazioneAppaltante;
    private Integer numeroAtto;
    private Integer numDoc;
    private String titolo;
    private String binary;
    private byte[] fileAllegato;
    private String url;
    private String tipoFile;

    public String getStazioneAppaltante() { return stazioneAppaltante; }
    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }

    public Integer getNumeroAtto() { return numeroAtto; }
    public void setNumeroAtto(Integer numeroAtto) { this.numeroAtto = numeroAtto; }

    public Integer getNumDoc() { return numDoc; }
    public void setNumDoc(Integer numDoc) { this.numDoc = numDoc; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getBinary() { return binary; }
    public void setBinary(String binary) { this.binary = binary; }

    public byte[] getFileAllegato() { return fileAllegato; }
    public void setFileAllegato(byte[] fileAllegato) { this.fileAllegato = fileAllegato; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTipoFile() { return tipoFile; }
    public void setTipoFile(String tipoFile) { this.tipoFile = tipoFile; }
}
