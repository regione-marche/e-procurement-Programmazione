package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
public class UserConnectedDTO implements Serializable {

    private static final long serialVersionUID = -5875208292683737815L;

    private Long syscon;

    private String username;

    private String denominazione;

    private String email;

    public Long getSyscon() {
        return syscon;
    }

    public void setSyscon(Long syscon) {
        this.syscon = syscon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
