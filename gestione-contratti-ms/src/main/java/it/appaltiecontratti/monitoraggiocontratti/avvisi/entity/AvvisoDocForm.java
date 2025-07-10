package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;



/**
 * Dati di pubblicazione di un documento di un avviso
 *
 * @author Michele.DiNapoli
 */


public class AvvisoDocForm {

	private String stazioneAppaltante;
	private Integer numeroAvviso;
	private Integer numdoc;
	private String titolo;
	private byte[] file_allegato;
	private String url;
	private String tipoFile;
	
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public Integer getNumeroAvviso() {
		return numeroAvviso;
	}
	public void setNumeroAvviso(Integer numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}
	public Integer getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(Integer numdoc) {
		this.numdoc = numdoc;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public byte[] getFile_allegato() {
		return file_allegato;
	}
	public void setFile_allegato(byte[] file_allegato) {
		this.file_allegato = file_allegato;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTipoFile() {
		return tipoFile;
	}
	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}
	
	
}
