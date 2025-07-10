package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

/**
 * @author andrea.chinellato
 * Entry per registro trasmissioni di schede a PCP da inserire in W9RegInviiPcp
 * */

public class W9RegInviiPcpEntry {

    private Long id;
    private Date dataInvio;
    @XSSValidation
    private String autore;
    @XSSValidation
    private String idScheda;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDataInvio() { return dataInvio; }
    public void setDataInvio(Date dataInvio) { this.dataInvio = dataInvio; }
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }
    public String getIdScheda() { return idScheda; }
    public void setIdScheda(String idScheda) { this.idScheda = idScheda; }
}
