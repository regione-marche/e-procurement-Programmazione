package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SchedeTrasmessePcpForm extends BaseSearchForm implements Serializable {

    private static final long serialVersionUID = 2515263175494071928L;

    private String stazioneAppaltante;
    private String autore;
    private Date dataTrasmissioneDa;
    private Date dataTrasmissioneA;
    private String idAppalto;
    private String cigLottoNumber;
    private Long tipoScheda;
    private Long progressivoScheda;
    private Long syscon;
    private String cfTecnico;
    private Boolean cfNull;
    private List<String> uffInt;
    private List<String> rup;

    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }
    public String getStazioneAppaltante() { return stazioneAppaltante; }

    public void setAutore(String autore) { this.autore = autore; }
    public String getAutore() { return autore; }

    public void setDataTrasmissioneDa(Date dataTrasmissioneDa) { this.dataTrasmissioneDa = dataTrasmissioneDa; }
    public Date getDataTrasmissioneDa() { return dataTrasmissioneDa; }

    public void setDataTrasmissioneA(Date dataTrasmissioneA) { this.dataTrasmissioneA = dataTrasmissioneA; }
    public Date getDataTrasmissioneA() { return dataTrasmissioneA; }

    public void setIdAppalto(String idAppalto) { this.idAppalto = idAppalto; }
    public String getIdAppalto() { return idAppalto; }

    public void setCigLottoNumber(String cigLottoNumber) { this.cigLottoNumber = cigLottoNumber; }
    public String getCigLottoNumber() { return cigLottoNumber; }

    public void setTipoScheda(Long tipoScheda) { this.tipoScheda = tipoScheda; }
    public Long getTipoScheda() { return tipoScheda; }

    public void setProgressivoScheda(Long progressivoScheda) { this.progressivoScheda = progressivoScheda; }
    public Long getProgressivoScheda() { return progressivoScheda; }

    public void setSyscon(Long syscon) { this.syscon = syscon; }
    public Long getSyscon() { return syscon; }

    public void setCfTecnico(String cfTecnico) { this.cfTecnico = cfTecnico; }
    public String getCfTecnico() { return cfTecnico; }

    public void setCfNull(Boolean cfNull) { this.cfNull = cfNull; }
    public Boolean getCfNull() { return cfNull; }

    public void setUffInt(List<String> uffInt) { this.uffInt = uffInt; }
    public List<String> getUffInt() { return uffInt; }

    public void setRup(List<String> rup) { this.rup = rup; }
    public List<String> getRup() { return rup; }
}
