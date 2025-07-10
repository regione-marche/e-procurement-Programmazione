package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description = "Entry per il dettaglio dell'allegato.")
public class AllegatoEntry implements Serializable {

    private static final long serialVersionUID = -5993511894265070921L;

    private Long idAllegato;
    private String key01;
    private String key02;
    private String key03;
    private String tabella;
    private String descrizione;
    private String binary;
    private byte[] fileAllegato;
    private String url;
    private String tipoFile;
    private Long numOrdine;
    private Long utenteCreatore;
    private Date dataAggiunta;
    private Long utenteCanc;
    private Date dataCanc;
    private String motivoCanc;
    private Long syscon;

    public Long getIdAllegato() { return idAllegato; }
    public void setIdAllegato(Long idAllegato) { this.idAllegato = idAllegato; }

    public String getKey01() { return key01; }
    public void setKey01(String key01) { this.key01 = key01; }

    public String getKey02() { return key02; }
    public void setKey02(String key02) { this.key02 = key02; }

    public String getKey03() { return key03; }
    public void setKey03(String key03) { this.key03 = key03; }

    public String getTabella() { return tabella; }
    public void setTabella(String tabella) { this.tabella = tabella; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getBinary() { return binary; }
    public void setBinary(String binary) { this.binary = binary; }

    public byte[] getFileAllegato() { return fileAllegato; }
    public void setFileAllegato(byte[] fileAllegato) { this.fileAllegato = fileAllegato; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTipoFile() { return tipoFile; }
    public void setTipoFile(String tipoFile) { this.tipoFile = tipoFile; }

    public Long getNumOrdine() { return numOrdine; }
    public void setNumOrdine(Long numOrdine) { this.numOrdine = numOrdine; }

    public Long getUtenteCreatore() { return utenteCreatore; }
    public void setUtenteCreatore(Long utenteCreatore) { this.utenteCreatore = utenteCreatore; }

    public Date getDataAggiunta() { return dataAggiunta; }
    public void setDataAggiunta(Date dataAggiunta) { this.dataAggiunta = dataAggiunta; }

    public Long getUtenteCanc() { return utenteCanc; }
    public void setUtenteCanc(Long utenteCanc) { this.utenteCanc = utenteCanc; }

    public Date getDataCanc() { return dataCanc; }
    public void setDataCanc(Date dataCanc) { this.dataCanc = dataCanc; }

    public String getMotivoCanc() { return motivoCanc; }
    public void setMotivoCanc(String motivoCanc) { this.motivoCanc = motivoCanc; }

    public Long getSyscon() { return syscon; }
    public void setSyscon(Long syscon) { this.syscon = syscon; }
}
