package it.appaltiecontratti.programmi.entity;

import java.math.BigDecimal;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since giu 16, 2023
 * Select
 *    coalesce(IR.CONTRI,0) as CONTRI,
 *    coalesce(IR.CUIINT,'') as CODICEUNICO,
 *    coalesce(IR.CUPPRG,'') as CUP,
 *    coalesce(IR.DESINT,'') as DESCRIZIONE,
 *    coalesce(IR.TOTINT,0.00) as IMPORTO,
 *    coalesce(IR.PRGINT,0) as PRIORITA,
 *    coalesce(IR.MOTIVO,'') as MOTIVO
 *    from PIATRI PI LEFT JOIN INRTRI IR ON PI.CONTRI = IR.CONTRI
 *    where PI.CONTRI = $P{PIATRI_CONTRI}
 */
public class ExportInterventiAcquistiNonRipropostiQueryResult {

    private Long contri;
    private String codiceUnico;
    private String cup;
    private String descrizione;
    private BigDecimal importo;
    private Long priorita;
    private String motivo;

    public Long getContri() {
        return contri;
    }

    public void setContri(Long contri) {
        this.contri = contri;
    }

    public String getCodiceUnico() {
        return codiceUnico;
    }

    public void setCodiceUnico(String codiceUnico) {
        this.codiceUnico = codiceUnico;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public Long getPriorita() {
        return priorita;
    }

    public void setPriorita(Long priorita) {
        this.priorita = priorita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "ExportInterventiNonRipropostiQueryResult{" +
                "contri=" + contri +
                ", codiceUnico='" + codiceUnico + '\'' +
                ", cup='" + cup + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", importo=" + importo +
                ", priorita=" + priorita +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
