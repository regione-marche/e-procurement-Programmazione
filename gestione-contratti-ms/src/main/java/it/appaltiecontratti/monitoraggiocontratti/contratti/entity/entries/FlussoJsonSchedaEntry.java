package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class FlussoJsonSchedaEntry {

    private String xml;
    private String idScheda;
    private Long codGara;
    private Long codLotto;
    private Long num;
    private Long faseEsecuzione;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getIdScheda() {
        return idScheda;
    }

    public void setIdScheda(String idScheda) {
        this.idScheda = idScheda;
    }

    public Long getCodGara() {
        return codGara;
    }

    public void setCodGara(Long codGara) {
        this.codGara = codGara;
    }

    public Long getCodLotto() {
        return codLotto;
    }

    public void setCodLotto(Long codLotto) {
        this.codLotto = codLotto;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getFaseEsecuzione() {
        return faseEsecuzione;
    }

    public void setFaseEsecuzione(Long faseEsecuzione) {
        this.faseEsecuzione = faseEsecuzione;
    }
}
