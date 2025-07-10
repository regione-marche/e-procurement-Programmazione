package it.appaltiecontratti.autenticazione.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import javax.validation.constraints.Size;
import java.util.List;

public class UserAccountForm {

    @XSSValidation
    private String nome;
    @XSSValidation
    private String cognome;
    @XSSValidation
    private String telefono;
    @XSSValidation
    private String email;
    @XSSValidation
    private String codiceFiscale;
    private List<String> applicativiSelezionati;
    @XSSValidation
    private String messaggioAmministratore;
    @XSSValidation
    private String nomeCognomeDirigente;
    @XSSValidation
    private String servizioDirigente;
    @XSSValidation
    private String stazioneAppaltante;
    private byte[] documentoFirmato;
    @XSSValidation
    private String fileExt;
    @XSSValidation
    private String fileName;
    @Size(min = 0, max = 16)
    @XSSValidation
    private String codiceFiscaleLogin;
    @Size(min = 0, max = 30)
    private String password;
    @Size(min = 0, max = 30)
    private String confermaPassword;
    private String captchaSolution;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public List<String> getApplicativiSelezionati() {
        return applicativiSelezionati;
    }

    public void setApplicativiSelezionati(List<String> applicativiSelezionati) {
        this.applicativiSelezionati = applicativiSelezionati;
    }

    public String getMessaggioAmministratore() {
        return messaggioAmministratore;
    }

    public void setMessaggioAmministratore(String messaggioAmministratore) {
        this.messaggioAmministratore = messaggioAmministratore;
    }

    public String getNomeCognomeDirigente() {
        return nomeCognomeDirigente;
    }

    public void setNomeCognomeDirigente(String nomeCognomeDirigente) {
        this.nomeCognomeDirigente = nomeCognomeDirigente;
    }

    public String getServizioDirigente() {
        return servizioDirigente;
    }

    public void setServizioDirigente(String servizioDirigente) {
        this.servizioDirigente = servizioDirigente;
    }

    public String getStazioneAppaltante() {
        return stazioneAppaltante;
    }

    public void setStazioneAppaltante(String stazioneAppaltante) {
        this.stazioneAppaltante = stazioneAppaltante;
    }

    public byte[] getDocumentoFirmato() {
        return documentoFirmato;
    }

    public void setDocumentoFirmato(byte[] documentoFirmato) {
        this.documentoFirmato = documentoFirmato;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCodiceFiscaleLogin() {
        return codiceFiscaleLogin;
    }

    public void setCodiceFiscaleLogin(String codiceFiscaleLogin) {
        this.codiceFiscaleLogin = codiceFiscaleLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }

    public String getCaptchaSolution() {
        return captchaSolution;
    }

    public void setCaptchaSolution(String captchaSolution) {
        this.captchaSolution = captchaSolution;
    }
}
