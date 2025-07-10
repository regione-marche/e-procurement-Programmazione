package it.maggioli.ssointegrms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
public class AuthenticationDTO implements Serializable {

    private static final long serialVersionUID = 7513917721661934372L;

    @NotBlank(message = "Username obbligatorio")
    private String username;
    @NotBlank(message = "Password obbligatoria")
    private String password;
    private String certificato;
    private String motivazione;
    @JsonIgnore
    private String ipAddress;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCertificato() {
        return certificato;
    }

    public void setCertificato(String certificato) {
        this.certificato = certificato;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
