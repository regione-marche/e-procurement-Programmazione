package it.appaltiecontratti.programmi.rl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DatiTrasmissioneDto implements Serializable {
    private String numeroAdozione;
    private String urlAdozione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataAdozione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataPubblicazioneAdozione;
    private String numeroApprovazione;
    private String urlApprovazione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataApprovazione;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataPubblicazioneApprovazione;

    public String getNumeroAdozione() {
        return numeroAdozione;
    }

    public void setNumeroAdozione(String numeroAdozione) {
        this.numeroAdozione = numeroAdozione;
    }

    public String getUrlAdozione() {
        return urlAdozione;
    }

    public void setUrlAdozione(String urlAdozione) {
        this.urlAdozione = urlAdozione;
    }

    public Date getDataAdozione() {
        return dataAdozione;
    }

    public void setDataAdozione(Date dataAdozione) {
        this.dataAdozione = dataAdozione;
    }

    public Date getDataPubblicazioneAdozione() {
        return dataPubblicazioneAdozione;
    }

    public void setDataPubblicazioneAdozione(Date dataPubblicazioneAdozione) {
        this.dataPubblicazioneAdozione = dataPubblicazioneAdozione;
    }

    public String getNumeroApprovazione() {
        return numeroApprovazione;
    }

    public void setNumeroApprovazione(String numeroApprovazione) {
        this.numeroApprovazione = numeroApprovazione;
    }

    public String getUrlApprovazione() {
        return urlApprovazione;
    }

    public void setUrlApprovazione(String urlApprovazione) {
        this.urlApprovazione = urlApprovazione;
    }

    public Date getDataApprovazione() {
        return dataApprovazione;
    }

    public void setDataApprovazione(Date dataApprovazione) {
        this.dataApprovazione = dataApprovazione;
    }

    public Date getDataPubblicazioneApprovazione() {
        return dataPubblicazioneApprovazione;
    }

    public void setDataPubblicazioneApprovazione(Date dataPubblicazioneApprovazione) {
        this.dataPubblicazioneApprovazione = dataPubblicazioneApprovazione;
    }

    @Override
    public String toString() {
        return "DatiTrasmissioneDto{" +
                "numeroAdozione='" + numeroAdozione + '\'' +
                ", urlAdozione='" + urlAdozione + '\'' +
                ", dataAdozione=" + dataAdozione +
                ", dataPubblicazioneAdozione=" + dataPubblicazioneAdozione +
                ", numeroApprovazione='" + numeroApprovazione + '\'' +
                ", urlApprovazione='" + urlApprovazione + '\'' +
                ", dataApprovazione=" + dataApprovazione +
                ", dataPubblicazioneApprovazione=" + dataPubblicazioneApprovazione +
                '}';
    }
}