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
public class WMailId implements Serializable {

	private static final long serialVersionUID = -3571283261060290812L;

	@NotBlank
	@Size(min = 1, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@NotBlank
	@Size(min = 1, max = 16)
	@Column(name = "idcfg")
	private String idCfg;

	public WMailId() {
	}

	public WMailId(@NotBlank @Size(min = 1, max = 10) String codApp, @NotBlank @Size(min = 1, max = 16) String idCfg) {
		this.codApp = codApp;
		this.idCfg = idCfg;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getIdCfg() {
		return idCfg;
	}

	public void setIdCfg(String idCfg) {
		this.idCfg = idCfg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codApp, idCfg);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WMailId other = (WMailId) obj;
		return Objects.equals(codApp, other.codApp) && Objects.equals(idCfg, other.idCfg);
	}

}
