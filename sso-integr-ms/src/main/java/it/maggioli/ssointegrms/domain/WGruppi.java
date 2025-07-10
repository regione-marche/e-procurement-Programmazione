package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_gruppi")
public class WGruppi implements Serializable {

	private static final long serialVersionUID = -6037726945465799269L;

	@Id
	@NotNull
	@Column(name = "id_gruppo")
	private Long id;

	@Size(min = 0, max = 100)
	@Column(name = "nome")
	private String nome;

	@Size(min = 0, max = 200)
	@Column(name = "descr")
	private String descrizione;

	@Size(min = 0, max = 20)
	@Column(name = "cod_profilo")
	private String codiceProfilo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCodiceProfilo() {
		return codiceProfilo;
	}

	public void setCodiceProfilo(String codiceProfilo) {
		this.codiceProfilo = codiceProfilo;
	}

	@Override
	public String toString() {
		return "WGruppi [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", codiceProfilo="
				+ codiceProfilo + "]";
	}

}
