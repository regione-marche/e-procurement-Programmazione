package it.appaltiecontratti.inbox.entity.programmi;

public class OperaIncompiutaBaseEntry {
	
	private Long idProgramma;
	private Long id; 
	private String cup; 
	private String descrizione; 
	private Long annoUltimoQe; 
	private Double avanzamento;
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getAnnoUltimoQe() {
		return annoUltimoQe;
	}
	public void setAnnoUltimoQe(Long annoUltimoQe) {
		this.annoUltimoQe = annoUltimoQe;
	}
	public Double getAvanzamento() {
		return avanzamento;
	}
	public void setAvanzamento(Double avanzamento) {
		this.avanzamento = avanzamento;
	}
	
}