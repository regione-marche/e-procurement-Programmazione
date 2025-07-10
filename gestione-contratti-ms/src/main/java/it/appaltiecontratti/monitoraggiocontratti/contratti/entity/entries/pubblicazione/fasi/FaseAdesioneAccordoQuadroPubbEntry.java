package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;
import java.util.List;

public class FaseAdesioneAccordoQuadroPubbEntry extends BaseFasePubblicazioneEntry {

	private String codStrumento;
	private Double importoLavori;
	private Double importoServizi;
	private Double importoForniture;
	private Double importosubtotale;
	private Double importoSicurezza;
	private Double importoProgettazione;
	private Double impNonAssog;
	private Double importoComplAppalto;
	private Double importoComplIntervento;
	private Double percentRibassoAgg;
	private Double percOffAumento;
	private Double importoAggiudicazione;
	private Date dataVerbAggiudicazione;
	private Long tipoAtto;
	private Date dataAtto;
	private String numeroAtto;
	private String flagRichSubappalto;
	List<ImpresaAggiudicatariaPubbEntry> impreseAggiudicatarie;
	List<FinanziamentiPubbEntry> finanziamenti;
	List<IncarichiProfPubbEntry> incarichiProfessionali;
	private Long numAppa;

	public String getCodStrumento() {
		return codStrumento;
	}

	public void setCodStrumento(String codStrumento) {
		this.codStrumento = codStrumento;
	}

	public Double getImportoLavori() {
		return importoLavori;
	}

	public void setImportoLavori(Double importoLavori) {
		this.importoLavori = importoLavori;
	}

	public Double getImportoServizi() {
		return importoServizi;
	}

	public void setImportoServizi(Double importoServizi) {
		this.importoServizi = importoServizi;
	}

	public Double getImportoForniture() {
		return importoForniture;
	}

	public void setImportoForniture(Double importoForniture) {
		this.importoForniture = importoForniture;
	}

	public Double getImportosubtotale() {
		return importosubtotale;
	}

	public void setImportosubtotale(Double importosubtotale) {
		this.importosubtotale = importosubtotale;
	}

	public Double getImportoSicurezza() {
		return importoSicurezza;
	}

	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}

	public Double getImportoProgettazione() {
		return importoProgettazione;
	}

	public void setImportoProgettazione(Double importoProgettazione) {
		this.importoProgettazione = importoProgettazione;
	}

	public Double getImpNonAssog() {
		return impNonAssog;
	}

	public void setImpNonAssog(Double impNonAssog) {
		this.impNonAssog = impNonAssog;
	}

	public Double getImportoComplAppalto() {
		return importoComplAppalto;
	}

	public void setImportoComplAppalto(Double importoComplAppalto) {
		this.importoComplAppalto = importoComplAppalto;
	}

	public Double getImportoComplIntervento() {
		return importoComplIntervento;
	}

	public void setImportoComplIntervento(Double importoComplIntervento) {
		this.importoComplIntervento = importoComplIntervento;
	}

	public Double getPercentRibassoAgg() {
		return percentRibassoAgg;
	}

	public void setPercentRibassoAgg(Double percentRibassoAgg) {
		this.percentRibassoAgg = percentRibassoAgg;
	}

	public Double getPercOffAumento() {
		return percOffAumento;
	}

	public void setPercOffAumento(Double percOffAumento) {
		this.percOffAumento = percOffAumento;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Date getDataVerbAggiudicazione() {
		return dataVerbAggiudicazione;
	}

	public void setDataVerbAggiudicazione(Date dataVerbAggiudicazione) {
		this.dataVerbAggiudicazione = dataVerbAggiudicazione;
	}

	public Long getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(Long tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getFlagRichSubappalto() {
		return flagRichSubappalto;
	}

	public void setFlagRichSubappalto(String flagRichSubappalto) {
		this.flagRichSubappalto = flagRichSubappalto;
	}

	public List<ImpresaAggiudicatariaPubbEntry> getImpreseAggiudicatarie() {
		return impreseAggiudicatarie;
	}

	public void setImpreseAggiudicatarie(List<ImpresaAggiudicatariaPubbEntry> impreseAggiudicatarie) {
		this.impreseAggiudicatarie = impreseAggiudicatarie;
	}

	public List<FinanziamentiPubbEntry> getFinanziamenti() {
		return finanziamenti;
	}

	public void setFinanziamenti(List<FinanziamentiPubbEntry> finanziamenti) {
		this.finanziamenti = finanziamenti;
	}

	public List<IncarichiProfPubbEntry> getIncarichiProfessionali() {
		return incarichiProfessionali;
	}

	public void setIncarichiProfessionali(List<IncarichiProfPubbEntry> incarichiProfessionali) {
		this.incarichiProfessionali = incarichiProfessionali;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}