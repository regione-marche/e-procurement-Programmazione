package it.appaltiecontratti.programmi.entity;

public class InterventoBaseEntry {
	private Long idProgramma;
	private Long id;
	private Long annoAvvio;
	private Long numProgetto;
	private String numeroCui;
	private String descrizione;
	private Double importoTotale;
	private String inPianoAnnuale ;
	
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long idIntervento) {
		this.id = idIntervento;
	}
	public Long getAnnoAvvio() {
		return annoAvvio;
	}
	public void setAnnoAvvio(Long annoAvvio) {
		this.annoAvvio = annoAvvio;
	}
	public Long getNumProgetto() {
		return numProgetto;
	}
	public void setNumProgetto(Long numProgetto) {
		this.numProgetto = numProgetto;
	}
	public String getNumeroCui() {
		return numeroCui;
	}
	public void setNumeroCui(String numeroCui) {
		this.numeroCui = numeroCui;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Double getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}
	public String getInPianoAnnuale() {
		return inPianoAnnuale;
	}
	public void setInPianoAnnuale(String inPianoAnnuale) {
		this.inPianoAnnuale = inPianoAnnuale;
	}
	

}
