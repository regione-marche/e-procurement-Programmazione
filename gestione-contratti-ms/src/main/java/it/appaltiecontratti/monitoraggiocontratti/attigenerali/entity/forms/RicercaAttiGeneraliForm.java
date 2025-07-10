package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.BaseSearchForm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Form di ricerca per atti generali
 *
 * @author andrea.chinellato
 * */
public class RicercaAttiGeneraliForm extends BaseSearchForm implements Serializable {

    private static final long serialVersionUID = 5913906583791943861L;

    private String stazioneAppaltante;
    private Long idAtto;
    private Long tipologia;
    private String descrizione;
    private List<String> rup;
    private List<String> rupData;
    private Date dataPubbSistema;
    private Date dataPubbSistemaDa;
    private Date dataPubbSistemaA;
    private Long syscon;
    private String cfTecnico;
    private Boolean cfNull;
    private List<String> stazioneAppaltanteData;

    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }
    public String getStazioneAppaltante() { return stazioneAppaltante; }

    public void setIdAtto(Long idAtto) { this.idAtto = idAtto; }
    public Long getIdAtto() { return idAtto; }

    public void setTipologia(Long tipologia) { this.tipologia = tipologia; }
    public Long getTipologia() { return tipologia; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getDescrizione() { return descrizione; }

    public void setRup(List<String> rup) { this.rup = rup; }
    public List<String> getRup() { return rup; }

    public void setRupData(List<String> rupData) { this.rupData = rupData; }
    public List<String> getRupData() { return rupData; }

    public void setDataPubbSistema(Date dataPubbSistema) { this.dataPubbSistema = dataPubbSistema; }
    public Date getDataPubbSistema() { return dataPubbSistema; }

    public void setDataPubbSistemaDa(Date dataPubbSistemaDa) { this.dataPubbSistemaDa = dataPubbSistemaDa; }
    public Date getDataPubbSistemaDa() { return dataPubbSistemaDa; }

    public void setDataPubbSistemaA(Date dataPubbSistemaA) { this.dataPubbSistemaA = dataPubbSistemaA; }
    public Date getDataPubbSistemaA() { return dataPubbSistemaA; }

    public void setSyscon(Long syscon) { this.syscon = syscon; }
    public Long getSyscon() { return syscon; }

    public void setCfTecnico(String cfTecnico) { this.cfTecnico = cfTecnico; }
    public String getCfTecnico() { return cfTecnico; }

    public void setCfNull(Boolean cfNull) { this.cfNull = cfNull; }
    public Boolean getCfNull() { return cfNull; }

    public void setStazioneAppaltanteData(List<String> stazioneAppaltanteData) { this.stazioneAppaltanteData = stazioneAppaltanteData; }
    public List<String> getStazioneAppaltanteData() { return stazioneAppaltanteData; }
}
