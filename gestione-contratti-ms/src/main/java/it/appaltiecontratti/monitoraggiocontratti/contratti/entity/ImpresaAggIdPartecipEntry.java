package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

public class ImpresaAggIdPartecipEntry {
    private String idPartecipante;
    private String ragioneSociale;
    private Long tipoAgg;

    public String getIdPartecipante() {
        return idPartecipante;
    }

    public void setIdPartecipante(String idPartecipante) {
        this.idPartecipante = idPartecipante;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public Long getTipoAgg() {
        return tipoAgg;
    }

    public void setTipoAgg(Long tipoAgg) {
        this.tipoAgg = tipoAgg;
    }
}
