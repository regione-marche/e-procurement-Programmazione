package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class CigIdAtto {

	private String cig;
	private String tipoAtto;
	private String nome;
	private Boolean pubblicato;
	private String oggetto;
	
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getTipoAtto() {
		return tipoAtto;
	}
	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getPubblicato() {
		return pubblicato;
	}
	public void setPubblicato(Boolean pubblicato) {
		this.pubblicato = pubblicato;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
}
