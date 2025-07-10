package it.appaltiecontratti.inbox.entity.loaderappalto;

public class DatiAggiudicatarioEntry {

	private Long idTipoAgg;
	private Long ruolo;
	private String flagAvvalimento;
	private String codiceFiscaleAggiudicatario;
	private String cfAusiliaria;
	private String pivImp;
	private String pivImpAusiliaria;
	private Long nazimp;
	private Long idGruppo;
	private Double importoAggiudicazione;
	private Double percRibassoAgg;
	private Double percOffAumento;

	public Long getIdTipoAgg() {
		return idTipoAgg;
	}

	public void setIdTipoAgg(Long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}

	public Long getRuolo() {
		return ruolo;
	}

	public void setRuolo(Long ruolo) {
		this.ruolo = ruolo;
	}

	public String getFlagAvvalimento() {
		return flagAvvalimento;
	}

	public void setFlagAvvalimento(String flagAvvalimento) {
		this.flagAvvalimento = flagAvvalimento;
	}

	public String getCodiceFiscaleAggiudicatario() {
		return codiceFiscaleAggiudicatario;
	}

	public void setCodiceFiscaleAggiudicatario(String codiceFiscaleAggiudicatario) {
		this.codiceFiscaleAggiudicatario = codiceFiscaleAggiudicatario;
	}

	public String getCfAusiliaria() {
		return cfAusiliaria;
	}

	public void setCfAusiliaria(String cfAusiliaria) {
		this.cfAusiliaria = cfAusiliaria;
	}

	public String getPivImp() {
		return pivImp;
	}

	public void setPivImp(String pivImp) {
		this.pivImp = pivImp;
	}

	public String getPivImpAusiliaria() {
		return pivImpAusiliaria;
	}

	public void setPivImpAusiliaria(String pivImpAusiliaria) {
		this.pivImpAusiliaria = pivImpAusiliaria;
	}

	public Long getNazimp() {
		return nazimp;
	}

	public void setNazimp(Long nazimp) {
		this.nazimp = nazimp;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Double getPercRibassoAgg() {
		return percRibassoAgg;
	}

	public void setPercRibassoAgg(Double percRibassoAgg) {
		this.percRibassoAgg = percRibassoAgg;
	}

	public Double getPercOffAumento() {
		return percOffAumento;
	}

	public void setPercOffAumento(Double percOffAumento) {
		this.percOffAumento = percOffAumento;
	}

}
