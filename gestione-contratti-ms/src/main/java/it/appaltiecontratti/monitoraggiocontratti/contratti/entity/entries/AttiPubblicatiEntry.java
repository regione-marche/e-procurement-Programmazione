package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class AttiPubblicatiEntry {

	private String label;
	private String nome;
	private Long tipDoc;
	private Long numPub;
	private Boolean validato;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getTipDoc() {
		return tipDoc;
	}
	public void setTipDoc(Long tipDoc) {
		this.tipDoc = tipDoc;
	}
	public Long getNumPub() {
		return numPub;
	}
	public void setNumPub(Long numPub) {
		this.numPub = numPub;
	}
	public Boolean getValidato() {
		return validato;
	}
	public void setValidato(Boolean validato) {
		this.validato = validato;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
