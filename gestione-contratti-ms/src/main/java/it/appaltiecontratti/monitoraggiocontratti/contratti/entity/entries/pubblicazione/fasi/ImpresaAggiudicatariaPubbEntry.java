package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

public class ImpresaAggiudicatariaPubbEntry {

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
	private ImpresaFasePubbEntry impresa;
	private ImpresaFasePubbEntry impresaAusiliaria;
	private Long importoAggiudicazione;
	private Long ribassoAggiudicazione;
	private Long offertaAumento;
	private String nomeLegRap;
	private String cognomeLegRap; 
	private String cfLegRap;

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

	public ImpresaFasePubbEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaFasePubbEntry impresa) {
		this.impresa = impresa;
	}

	public ImpresaFasePubbEntry getImpresaAusiliaria() {
		return impresaAusiliaria;
	}

	public void setImpresaAusiliaria(ImpresaFasePubbEntry impresaAusiliaria) {
		this.impresaAusiliaria = impresaAusiliaria;
	}

	public Long getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Long importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Long getRibassoAggiudicazione() {
		return ribassoAggiudicazione;
	}

	public void setRibassoAggiudicazione(Long ribassoAggiudicazione) {
		this.ribassoAggiudicazione = ribassoAggiudicazione;
	}

	public Long getOffertaAumento() {
		return offertaAumento;
	}

	public void setOffertaAumento(Long offertaAumento) {
		this.offertaAumento = offertaAumento;
	}
	
}
