package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.List;

public class SABaseEntry {
	
	private String nome;
	
	private String codice;
	
	
	private List<BaseRupInfo> listaRup;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public List<BaseRupInfo> getListaRup() {
		return listaRup;
	}

	public void setListaRup(List<BaseRupInfo> listaRup) {
		this.listaRup = listaRup;
	}
	
	

}
