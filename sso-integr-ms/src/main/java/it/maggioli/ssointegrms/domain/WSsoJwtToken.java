package it.maggioli.ssointegrms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 22, 2025
 */
@Entity
@Table(name = "w_ssojwttoken")
public class WSsoJwtToken implements Serializable {
    private static final long serialVersionUID = -975859680920060051L;

    @Id
    @NotBlank
    @Column(name = "syslogin")
    @Size(min = 1, max = 256)
    private String syslogin;

    @NotBlank
    @Column(name = "authcode")
    @Size(min = 1, max = 100)
    private String authCode;

    @NotBlank
    @Column(name = "jwttoken")
    @Size(min = 1, max = 1000)
    private String jwtToken;

    @NotBlank
    @Column(name = "refreshtoken")
    @Size(min = 1, max = 1000)
    private String refreshToken;

    @NotNull
    @Column(name = "dtcreazione")
    private Date dtCreazione;

    public String getSyslogin() {
        return syslogin;
    }

    public void setSyslogin(String syslogin) {
        this.syslogin = syslogin;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }
}
