package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "uffint")
public class Uffint implements Serializable {

	private static final long serialVersionUID = 7266453888424580288L;

	@Id
	@NotBlank
	@Size(min = 1, max = 16)
	@Column(name = "codein")
	private String codice;

	@Size(min = 0, max = 254)
	@Column(name = "nomein")
	private String denominazione;

	@Size(min = 0, max = 16)
	@Column(name = "cfein")
	private String codiceFiscale;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@Override
	public String toString() {
		return "Uffint [codice=" + codice + ", denominazione=" + denominazione + ", codiceFiscale=" + codiceFiscale
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Uffint uffint = (Uffint) o;
		return Objects.equals(codice, uffint.codice) && Objects.equals(denominazione, uffint.denominazione) && Objects.equals(codiceFiscale, uffint.codiceFiscale);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, denominazione, codiceFiscale);
	}
}
