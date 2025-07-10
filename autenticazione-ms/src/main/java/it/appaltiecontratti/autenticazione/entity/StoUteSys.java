package it.appaltiecontratti.autenticazione.entity;

import java.util.Date;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since feb 14, 2024
 */
public class StoUteSys {

    private String sysnom;
    private String syspwd;
    private Long syscon;
    private Date sysdat;
    private String syslogin;

    public String getSysnom() {
        return sysnom;
    }

    public void setSysnom(String sysnom) {
        this.sysnom = sysnom;
    }

    public String getSyspwd() {
        return syspwd;
    }

    public void setSyspwd(String syspwd) {
        this.syspwd = syspwd;
    }

    public Long getSyscon() {
        return syscon;
    }

    public void setSyscon(Long syscon) {
        this.syscon = syscon;
    }

    public Date getSysdat() {
        return sysdat;
    }

    public void setSysdat(Date sysdat) {
        this.sysdat = sysdat;
    }

    public String getSyslogin() {
        return syslogin;
    }

    public void setSyslogin(String syslogin) {
        this.syslogin = syslogin;
    }
}
