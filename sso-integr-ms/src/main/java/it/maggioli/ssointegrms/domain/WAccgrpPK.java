package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class WAccgrpPK implements Serializable {

	private static final long serialVersionUID = 396525796120229854L;

	@NotNull
	@Column(name = "id_account")
	private Long idAccount;

	@NotNull
	@Column(name = "id_gruppo")
	private Long idGruppo;

	public WAccgrpPK() {
	}

	public WAccgrpPK(@NotNull Long idAccount, @NotNull Long idGruppo) {
		super();
		this.idAccount = idAccount;
		this.idGruppo = idGruppo;
	}

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAccount, idGruppo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WAccgrpPK other = (WAccgrpPK) obj;
		return Objects.equals(idAccount, other.idAccount) && Objects.equals(idGruppo, other.idGruppo);
	}

	@Override
	public String toString() {
		return "WAccgrpPK{" +
				"idAccount=" + idAccount +
				", idGruppo=" + idGruppo +
				'}';
	}
}
