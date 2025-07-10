package it.appaltiecontratti.tabellati.entity;

public class AppInfoEntry {
    private String nomeComponente;
    private String versioneComponente;
    private String nomePiattaforma;
    private String versionePiattaforma;

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public String getVersioneComponente() {
        return versioneComponente;
    }

    public void setVersioneComponente(String versioneComponente) {
        this.versioneComponente = versioneComponente;
    }

    public String getNomePiattaforma() {
        return nomePiattaforma;
    }

    public void setNomePiattaforma(String nomePiattaforma) {
        this.nomePiattaforma = nomePiattaforma;
    }

    public String getVersionePiattaforma() {
        return versionePiattaforma;
    }

    public void setVersionePiattaforma(String versionePiattaforma) {
        this.versionePiattaforma = versionePiattaforma;
    }

    public AppInfoEntry(String nomeComponente, String versioneComponente, String nomePiattaforma, String versionePiattaforma) {
        this.nomeComponente = nomeComponente;
        this.versioneComponente = versioneComponente;
        this.nomePiattaforma = nomePiattaforma;
        this.versionePiattaforma = versionePiattaforma;
    }
}
