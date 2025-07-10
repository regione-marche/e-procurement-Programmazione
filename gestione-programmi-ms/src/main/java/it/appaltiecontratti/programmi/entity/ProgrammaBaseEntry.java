package it.appaltiecontratti.programmi.entity;

public class ProgrammaBaseEntry {

	private Long id;
	private String idProgramma;
	private Long annoInizio;
	private String descrizioneBreve;
	private Long tipoProg;
	private Long idRicevuto;
	private Long syscon;
	private String referenteProgrammazione;
	private Long norma;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdProgramma() {
		return idProgramma;
	}

	public void setIdProgramma(String idProgramma) {
		this.idProgramma = idProgramma;
	}

	public Long getAnnoInizio() {
		return annoInizio;
	}

	public void setAnnoInizio(Long annoInizio) {
		this.annoInizio = annoInizio;
	}

	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public Long getTipoProg() {
		return tipoProg;
	}

	public void setTipoProg(Long tipoProg) {
		this.tipoProg = tipoProg;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getReferenteProgrammazione() {
		return referenteProgrammazione;
	}

	public void setReferenteProgrammazione(String referenteProgrammazione) {
		this.referenteProgrammazione = referenteProgrammazione;
	}

	public Long getNorma() {
		return norma;
	}

	public void setNorma(Long norma) {
		this.norma = norma;
	}

	
}
