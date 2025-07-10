package it.appaltiecontratti.tabellati.entity;

import java.util.List;

public class InfoCampo extends InfoEntita{
	
	private String campo;
	private String descrizione;
	private String formato;
	private String mnemonico;
	List <String> valoriTabellati;
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getMnemonico() {
		return mnemonico;
	}
	public void setMnemonico(String mnemonico) {
		this.mnemonico = mnemonico;
	}
	public List<String> getValoriTabellati() {
		return valoriTabellati;
	}
	public void setValoriTabellati(List<String> valoriTabellati) {
		this.valoriTabellati = valoriTabellati;
	}
	
}
