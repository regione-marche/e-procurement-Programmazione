package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries;

import java.io.Serializable;

public class AllegatoMotivazioneEntry implements Serializable {

    private static final long serialVersionUID = 722044173727298295L;

    private Long idAllegato;
    private String key01;
    private String motivoCanc;

    public Long getIdAllegato() { return idAllegato; }
    public void setIdAllegato(Long idAllegato) { this.idAllegato = idAllegato; }

    public String getKey01() { return key01; }
    public void setKey01(String key01) { this.key01 = key01; }

    public String getMotivoCanc() { return motivoCanc; }
    public void setMotivoCanc(String motivoCanc) { this.motivoCanc = motivoCanc; }
}
