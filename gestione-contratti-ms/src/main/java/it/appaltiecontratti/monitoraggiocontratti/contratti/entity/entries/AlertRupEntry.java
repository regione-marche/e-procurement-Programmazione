package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class AlertRupEntry {
    private String idAppalto;
    private String cig;
    private Long syscon;
    private Long codGara;
    private Long codLott;

    public String getIdAppalto() {
        return idAppalto;
    }

    public void setIdAppalto(String idAppalto) {
        this.idAppalto = idAppalto;
    }

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public Long getSyscon() {
        return syscon;
    }

    public void setSyscon(Long syscon) {
        this.syscon = syscon;
    }

    public Long getCodGara() {
        return codGara;
    }

    public void setCodGara(Long codGara) {
        this.codGara = codGara;
    }

    public Long getCodLott() {
        return codLott;
    }

    public void setCodLott(Long codLott) {
        this.codLott = codLott;
    }
}
