package it.appaltiecontratti.programmi.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class OperaIncompiutaInsertForm {

	@NotNull
	private Long idProgramma;
	private Long id;
	@XSSValidation
	private String cup;
	@XSSValidation
	private String descrizione;
	@XSSValidation
	private String determinazioni;
	@XSSValidation
	private String ambitoInt;
	private Long annoUltimoQe;
	private Double importoInt;
	private Double importoLav;
	private Double importoSal;
	private Double oneriUlt;
	private Double avanzamento;
	@XSSValidation
	private String causa;
	@XSSValidation
	private String statoReal;
	@XSSValidation
	private String infrastruttura;
	@XSSValidation
	private String fruibile;
	@XSSValidation
	private String utilizzoRid;
	@XSSValidation
	private String destinazioneUso;
	@XSSValidation
	private String cessione;
	@XSSValidation
	private String vendita;
	@XSSValidation
	private String demolizione;
	private Double oneriSito;
	private List<ImmobileInsertForm> immobili;
	@XSSValidation
	private String codIstat;
	@XSSValidation
	private String codNuts;
	@XSSValidation
	private String tipologiaIntervento;
	@XSSValidation
	private String categoriaIntervento;
	@XSSValidation
	private String requisitiCapitolato;
	@XSSValidation
	private String requisitiProgetto;
	@XSSValidation
	private String dimensionamentoUm;
	private Double dimensionamentoValore;
	@XSSValidation
	private String sponsorizzazione;
	@XSSValidation
	private String finanzaProgetto;
	private Double costo;
	private Double finanziamento;
	@XSSValidation
	private String copStatale;
	@XSSValidation
	private String copRegionale;
	@XSSValidation
	private String copProvinciale;
	@XSSValidation
	private String copComunale;
	@XSSValidation
	private String copAltraPubblica;
	@XSSValidation
	private String copComunitaria;
	@XSSValidation
	private String copPrivata;
	@XSSValidation
	private String discontinuitaRete;
	private Object additionalInfo;

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

	public String getDeterminazioni() {
		return determinazioni;
	}

	public void setDeterminazioni(String determinazioni) {
		this.determinazioni = determinazioni;
	}

	public String getAmbitoInt() {
		return ambitoInt;
	}

	public void setAmbitoInt(String ambitoInt) {
		this.ambitoInt = ambitoInt;
	}

	public Long getAnnoUltimoQe() {
		return annoUltimoQe;
	}

	public void setAnnoUltimoQe(Long annoUltimoQe) {
		this.annoUltimoQe = annoUltimoQe;
	}

	public Double getImportoInt() {
		return importoInt;
	}

	public void setImportoInt(Double importoInt) {
		this.importoInt = importoInt;
	}

	public Double getImportoLav() {
		return importoLav;
	}

	public void setImportoLav(Double importoLav) {
		this.importoLav = importoLav;
	}

	public Double getImportoSal() {
		return importoSal;
	}

	public void setImportoSal(Double importoSal) {
		this.importoSal = importoSal;
	}

	public Double getOneriUlt() {
		return oneriUlt;
	}

	public void setOneriUlt(Double oneriUlt) {
		this.oneriUlt = oneriUlt;
	}

	public Double getAvanzamento() {
		return avanzamento;
	}

	public void setAvanzamento(Double avanzamento) {
		this.avanzamento = avanzamento;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getStatoReal() {
		return statoReal;
	}

	public void setStatoReal(String statoReal) {
		this.statoReal = statoReal;
	}

	public String getInfrastruttura() {
		return infrastruttura;
	}

	public void setInfrastruttura(String infrastruttura) {
		this.infrastruttura = infrastruttura;
	}

	public String getFruibile() {
		return fruibile;
	}

	public void setFruibile(String fruibile) {
		this.fruibile = fruibile;
	}

	public String getUtilizzoRid() {
		return utilizzoRid;
	}

	public void setUtilizzoRid(String utilizzoRid) {
		this.utilizzoRid = utilizzoRid;
	}

	public String getDestinazioneUso() {
		return destinazioneUso;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getCessione() {
		return cessione;
	}

	public void setCessione(String cessione) {
		this.cessione = cessione;
	}

	public String getVendita() {
		return vendita;
	}

	public void setVendita(String vendita) {
		this.vendita = vendita;
	}

	public String getDemolizione() {
		return demolizione;
	}

	public void setDemolizione(String demolizione) {
		this.demolizione = demolizione;
	}

	public Double getOneriSito() {
		return oneriSito;
	}

	public void setOneriSito(Double oneriSito) {
		this.oneriSito = oneriSito;
	}

	public List<ImmobileInsertForm> getImmobili() {
		if (immobili == null) {
			return new ArrayList<ImmobileInsertForm>();
		} else {
			return immobili;
		}
	}

	public void setImmobili(List<ImmobileInsertForm> immobili) {
		this.immobili = immobili;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getCodNuts() {
		return codNuts;
	}

	public void setCodNuts(String codNuts) {
		this.codNuts = codNuts;
	}

	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public String getCategoriaIntervento() {
		return categoriaIntervento;
	}

	public void setCategoriaIntervento(String categoriaIntervento) {
		this.categoriaIntervento = categoriaIntervento;
	}

	public String getRequisitiCapitolato() {
		return requisitiCapitolato;
	}

	public void setRequisitiCapitolato(String requisitiCapitolato) {
		this.requisitiCapitolato = requisitiCapitolato;
	}

	public String getRequisitiProgetto() {
		return requisitiProgetto;
	}

	public void setRequisitiProgetto(String requisitiProgetto) {
		this.requisitiProgetto = requisitiProgetto;
	}

	public String getDimensionamentoUm() {
		return dimensionamentoUm;
	}

	public void setDimensionamentoUm(String dimensionamentoUm) {
		this.dimensionamentoUm = dimensionamentoUm;
	}

	public Double getDimensionamentoValore() {
		return dimensionamentoValore;
	}

	public void setDimensionamentoValore(Double dimensionamentoValore) {
		this.dimensionamentoValore = dimensionamentoValore;
	}

	public String getSponsorizzazione() {
		return sponsorizzazione;
	}

	public void setSponsorizzazione(String sponsorizzazione) {
		this.sponsorizzazione = sponsorizzazione;
	}

	public String getFinanzaProgetto() {
		return finanzaProgetto;
	}

	public void setFinanzaProgetto(String finanzaProgetto) {
		this.finanzaProgetto = finanzaProgetto;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getFinanziamento() {
		return finanziamento;
	}

	public void setFinanziamento(Double finanziamento) {
		this.finanziamento = finanziamento;
	}

	public String getCopStatale() {
		return copStatale;
	}

	public void setCopStatale(String copStatale) {
		this.copStatale = copStatale;
	}

	public String getCopRegionale() {
		return copRegionale;
	}

	public void setCopRegionale(String copRegionale) {
		this.copRegionale = copRegionale;
	}

	public String getCopProvinciale() {
		return copProvinciale;
	}

	public void setCopProvinciale(String copProvinciale) {
		this.copProvinciale = copProvinciale;
	}

	public String getCopComunale() {
		return copComunale;
	}

	public void setCopComunale(String copComunale) {
		this.copComunale = copComunale;
	}

	public String getCopAltraPubblica() {
		return copAltraPubblica;
	}

	public void setCopAltraPubblica(String copAltraPubblica) {
		this.copAltraPubblica = copAltraPubblica;
	}

	public String getCopComunitaria() {
		return copComunitaria;
	}

	public void setCopComunitaria(String copComunitaria) {
		this.copComunitaria = copComunitaria;
	}

	public String getCopPrivata() {
		return copPrivata;
	}

	public void setCopPrivata(String copPrivata) {
		this.copPrivata = copPrivata;
	}

	public String getDiscontinuitaRete() {
		return discontinuitaRete;
	}

	public void setDiscontinuitaRete(String discontinuitaRete) {
		this.discontinuitaRete = discontinuitaRete;
	}

	public Object getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Object additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
}
