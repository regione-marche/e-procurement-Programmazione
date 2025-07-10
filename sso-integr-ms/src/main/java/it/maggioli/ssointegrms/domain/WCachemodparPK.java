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
public class WCachemodparPK implements Serializable {

	private static final long serialVersionUID = -6500210117540704520L;

	@Column(name = "id_account")
	private Long idAccount;

	@Column(name = "id_modello")
	private Long idModello;

	@NotBlank
	@Size(min = 1, max = 30)
	@Column(name = "codice")
	private String codice;

	public WCachemodparPK() {
	}

	public WCachemodparPK(Long idAccount, Long idModello, @NotBlank @Size(min = 1, max = 30) String codice) {
		this.idAccount = idAccount;
		this.idModello = idModello;
		this.codice = codice;
	}

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}

	public Long getIdModello() {
		return idModello;
	}

	public void setIdModello(Long idModello) {
		this.idModello = idModello;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, idAccount, idModello);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WCachemodparPK other = (WCachemodparPK) obj;
		return Objects.equals(codice, other.codice) && Objects.equals(idAccount, other.idAccount)
				&& Objects.equals(idModello, other.idModello);
	}

}
