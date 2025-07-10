package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class RicercaSchedeTrasmessePcpEntry implements Serializable {

    private static final long serialVersionUID = 8487752512960847694L;

    @ApiModelProperty(value = "Codici stazioni appaltanti in filtro")
    private String uffInt;

    @ApiModelProperty(value = "CIG del lotto")
    private String cigLottoNumber;

    @ApiModelProperty(value = "Fase dell'esecuzione")
    private Long faseEsecuzione;

    @ApiModelProperty(value = "Numero progressivo scheda")
    private Long progressivoScheda;

    @ApiModelProperty(value = "Data invio della scheda")
    private Date datInv;

    @ApiModelProperty(value = "Descrizione del RUP")
    private String autore;

    @ApiModelProperty(value = "Codice gara")
    private String codGara;

    @ApiModelProperty(value = "Codice lotto")
    private String codLotto;

    @ApiModelProperty(value = "Codice stazione appaltante attiva")
    private String stazioneAppaltante;

    public void setUffInt(String uffInt) { this.uffInt = uffInt; }
    public String getUffInt() { return uffInt; }

    public void setCigLottoNumber(String cigLottoNumber) { this.cigLottoNumber = cigLottoNumber; }
    public String getCigLottoNumber() { return cigLottoNumber; }

    public void setFaseEsecuzione(Long faseEsecuzione) { this.faseEsecuzione = faseEsecuzione; }
    public Long getFaseEsecuzione() { return faseEsecuzione; }

    public void setProgressivoScheda(Long progressivoScheda) { this.progressivoScheda = progressivoScheda; }
    public Long getProgressivoScheda() { return progressivoScheda; }

    public void setDatInv(Date datInv) { this.datInv = datInv; }
    public Date getDatInv() { return datInv; }

    public void setAutore(String autore) { this.autore = autore; }
    public String getAutore() { return autore; }

    public void setCodGara(String codGara) { this.codGara = codGara; }
    public String getCodGara() { return codGara; }

    public void setCodLotto(String codLotto) { this.codLotto = codLotto; }
    public String getCodLotto() { return codLotto; }

    public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }
    public String getStazioneAppaltante() { return stazioneAppaltante; }
}
