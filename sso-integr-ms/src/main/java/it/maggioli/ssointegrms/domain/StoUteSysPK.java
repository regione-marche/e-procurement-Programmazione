package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class StoUteSysPK implements Serializable {

	private static final long serialVersionUID = -5299816667885880873L;

	@NotBlank
	@Size(min = 1, max = 61)
	@Column(name = "sysnom")
	private String sysnom;

	@NotBlank
	@Size(min = 1, max = 31)
	@Column(name = "syspwd")
	private String syspwd;

	public StoUteSysPK() {
	}

	public StoUteSysPK(@NotBlank @Size(min = 1, max = 61) String sysnom,
					   @NotBlank @Size(min = 1, max = 31) String syspwd) {
		this.sysnom = sysnom;
		this.syspwd = syspwd;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(sysnom, syspwd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoUteSysPK other = (StoUteSysPK) obj;
		return Objects.equals(sysnom, other.sysnom) && Objects.equals(syspwd, other.syspwd);
	}

}
