package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class StoUteSysDTO implements Serializable {

	private static final long serialVersionUID = 6715357746965509490L;

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

	@Override
	public String toString() {
		return "StoUteSysDTO [sysnom=" + sysnom + ", syspwd=" + syspwd + ", syscon=" + syscon + ", sysdat=" + sysdat
				+ ", syslogin=" + syslogin + "]";
	}

}
