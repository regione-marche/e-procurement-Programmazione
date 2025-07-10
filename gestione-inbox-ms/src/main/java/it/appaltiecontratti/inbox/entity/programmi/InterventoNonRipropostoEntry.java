package it.appaltiecontratti.inbox.entity.programmi;

public class InterventoNonRipropostoEntry {
	
	private Long idProgramma;
	private Long idIntervento; 
	private String cui; 
	private String cup; 
	private String descrizione; 
	private Double importoComplessivo; 
	private Long priorita; 
	private String motivo;
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getIdIntervento() {
		return idIntervento;
	}
	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}
	public String getCui() {
		return cui;
	}
	public void setCui(String cui) {
		this.cui = cui;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Double getImportoComplessivo() {
		return importoComplessivo;
	}
	public void setImportoComplessivo(Double importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}
	public Long getPriorita() {
		return priorita;
	}
	public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
