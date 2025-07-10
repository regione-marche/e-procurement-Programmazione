package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;

public class FaseVariazioneAggiudicatariEntry extends FaseBaseEntry {
    private Long codGara;
    private Long codLotto;
    private Long num;
    private String idPartecipante;
    private String codImpresa;
    private ImpresaEntry impresa;
    private Long idRuolo;
    private Long tipoOe;
    private Long flagAvvalimento;
    private Long motivoVariazione;
    private Boolean pubblicata;
    private String partecipante;

    public String getIdPartecipante() {
        return idPartecipante;
    }

    public void setIdPartecipante(String idPartecipante) {
        this.idPartecipante = idPartecipante;
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

    public ImpresaEntry getImpresa() {
        return impresa;
    }

    public void setImpresa(ImpresaEntry impresa) {
        this.impresa = impresa;
    }

    public Long getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Long idRuolo) {
        this.idRuolo = idRuolo;
    }

    public Long getTipoOe() {
        return tipoOe;
    }

    public void setTipoOe(Long tipoOe) {
        this.tipoOe = tipoOe;
    }

    public Long getFlagAvvalimento() {
        return flagAvvalimento;
    }

    public void setFlagAvvalimento(Long flagAvvalimento) {
        this.flagAvvalimento = flagAvvalimento;
    }

    public Long getMotivoVariazione() {
        return motivoVariazione;
    }

    public void setMotivoVariazione(Long motivoVariazione) {
        this.motivoVariazione = motivoVariazione;
    }

    public Boolean getPubblicata() {
        return pubblicata;
    }

    public void setPubblicata(Boolean pubblicata) {
        this.pubblicata = pubblicata;
    }

    public String getCodImpresa() {
        return codImpresa;
    }

    public void setCodImpresa(String codImpresa) {
        this.codImpresa = codImpresa;
    }

    public String getPartecipante() {
        return partecipante;
    }

    public void setPartecipante(String partecipante) {
        this.partecipante = partecipante;
    }
}
