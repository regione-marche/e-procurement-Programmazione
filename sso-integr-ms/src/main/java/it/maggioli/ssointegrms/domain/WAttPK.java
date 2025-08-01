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
public class WAttPK implements Serializable {

	private static final long serialVersionUID = 3426416114127802546L;

	@NotBlank
	@Size(min = 1, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "chiave")
	private String chiave;

	public WAttPK() {
	}

	public WAttPK(@NotBlank @Size(min = 1, max = 10) String codApp,
				  @NotBlank @Size(min = 1, max = 100) String chiave) {
		this.codApp = codApp;
		this.chiave = chiave;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chiave, codApp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WAttPK other = (WAttPK) obj;
		return Objects.equals(chiave, other.chiave) && Objects.equals(codApp, other.codApp);
	}

}
