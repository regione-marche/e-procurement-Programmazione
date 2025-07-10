package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 02, 2023
 */
public class WSsoJwtToken implements Serializable {

    private static final long serialVersionUID = -3381709906617856390L;

    private String syslogin;
    private String authorizationCode;
    private String jwtToken;
    private String refreshToken;
    private Date dtCreazione;
    private String levelOfAccess;

    public String getSyslogin() {
        return syslogin;
    }

    public void setSyslogin(String syslogin) {
        this.syslogin = syslogin;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
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

    public String getLevelOfAccess() {
		return levelOfAccess;
	}

	public void setLevelOfAccess(String levelOfAccess) {
		this.levelOfAccess = levelOfAccess;
	}

    @Override
    public String toString() {
        return "WSsoJwtToken{" +
                "syslogin='" + syslogin + '\'' +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", dtCreazione=" + dtCreazione +
                ", levelOfAccess='" + levelOfAccess + '\'' +
                '}';
    }
}
