package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;

public class FaseVariazioneAggiudicatariInsertForm {

    private Long codGara;
    private Long codLotto;
    private Long num;
    private String idPartecipante;
    private String codImpresa;
    private Long idRuolo;
    private Long tipoOe;
    private Long flagAvvalimento;
    private Long motivoVariazione;


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

    public String getCodImpresa() {
        return codImpresa;
    }

    public void setCodImpresa(String codImpresa) {
        this.codImpresa = codImpresa;
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
}
