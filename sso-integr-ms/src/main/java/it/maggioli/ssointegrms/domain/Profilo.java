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
@Table(name = "w_profili")
public class Profilo implements Serializable {

	private static final long serialVersionUID = -284969260980549222L;

	@Id
	@NotBlank
	@Size(min = 1, max = 20)
	@Column(name = "cod_profilo")
	private String codice;

	@Size(min = 0, max = 10)
	@Column(name = "codapp")
	private String codiceApplicazione;

	@Size(min = 0, max = 100)
	@Column(name = "nome")
	private String nome;

	@Size(min = 0, max = 500)
	@Column(name = "descrizione")
	private String descrizione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Profilo [codice=" + codice + ", codiceApplicazione=" + codiceApplicazione + ", nome=" + nome
				+ ", descrizione=" + descrizione + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profilo profilo = (Profilo) o;
		return Objects.equals(codice, profilo.codice) && Objects.equals(codiceApplicazione, profilo.codiceApplicazione) && Objects.equals(nome, profilo.nome) && Objects.equals(descrizione, profilo.descrizione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, codiceApplicazione, nome, descrizione);
	}
}
