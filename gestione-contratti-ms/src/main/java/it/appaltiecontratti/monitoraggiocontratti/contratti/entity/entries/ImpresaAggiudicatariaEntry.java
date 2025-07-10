package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class ImpresaAggiudicatariaEntry {

	private Long codGara;
	private Long codLotto;
	private Long numAppa;
	private Long numAggi;
	private Long idTipoAgg;
	private Long ruolo;
	private String flagAvvallamento;
	private String codImpresa;
	private String codImpAus;
	private Long idGruppo;
	private ImpresaEntry impresa;
	private ImpresaEntry impresaAusiliaria;
	private Double importoAggiudicazione;
	private Double ribassoAggiudicazione;
	private Double offertaAumento;
	private String nomeLegRap;
	private String cognomeLegRap; 
	private String cfLegRap;
	private String idPartecipante;

	public String getNomeLegRap() {
		return nomeLegRap;
	}

	public void setNomeLegRap(String nomeLegRap) {
		this.nomeLegRap = nomeLegRap;
	}

	public String getCognomeLegRap() {
		return cognomeLegRap;
	}

	public void setCognomeLegRap(String cognomeLegRap) {
		this.cognomeLegRap = cognomeLegRap;
	}

	public String getCfLegRap() {
		return cfLegRap;
	}

	public void setCfLegRap(String cfLegRap) {
		this.cfLegRap = cfLegRap;
	}

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

	public Long getNumAggi() {
		return numAggi;
	}

	public void setNumAggi(Long numAggi) {
		this.numAggi = numAggi;
	}

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

	public String getFlagAvvallamento() {
		return flagAvvallamento;
	}

	public void setFlagAvvallamento(String flagAvvallamento) {
		this.flagAvvallamento = flagAvvallamento;
	}

	public String getCodImpresa() {
		return codImpresa;
	}

	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}

	public String getCodImpAus() {
		return codImpAus;
	}

	public void setCodImpAus(String codImpAus) {
		this.codImpAus = codImpAus;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public ImpresaEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}

	public ImpresaEntry getImpresaAusiliaria() {
		return impresaAusiliaria;
	}

	public void setImpresaAusiliaria(ImpresaEntry impresaAusiliaria) {
		this.impresaAusiliaria = impresaAusiliaria;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Double getRibassoAggiudicazione() {
		return ribassoAggiudicazione;
	}

	public void setRibassoAggiudicazione(Double ribassoAggiudicazione) {
		this.ribassoAggiudicazione = ribassoAggiudicazione;
	}

	public Double getOffertaAumento() {
		return offertaAumento;
	}

	public void setOffertaAumento(Double offertaAumento) {
		this.offertaAumento = offertaAumento;
	}

	public String getIdPartecipante() {
		return idPartecipante;
	}

	public void setIdPartecipante(String idPartecipante) {
		this.idPartecipante = idPartecipante;
	}
	
}
