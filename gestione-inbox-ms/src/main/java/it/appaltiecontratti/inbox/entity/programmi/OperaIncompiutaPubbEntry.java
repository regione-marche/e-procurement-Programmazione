/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.inbox.entity.programmi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dati di un'opera incompiuta.
 * 
 * @author Mirco.Franzoni
 */
@ApiModel(description = "Contenitore per i dati di un'opera incompiuta")
public class OperaIncompiutaPubbEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(hidden=true)
	private Long contri;
	
	@ApiModelProperty(hidden=true)
	private Long numoi;
	
	@ApiModelProperty(value = "Codice CUP", required=true)
	@Size(max=15)
	private String cup;
	
	@ApiModelProperty(value = "Descrizione opera", required=true)
	@Size(max=2000)
	private String descrizione;
	
	@ApiModelProperty(value="Determinazioni dell'amministrazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Determinazioni)", required=true)
	@Size(max=5)
	private String determinazioneAmministrazione;
	  
	@ApiModelProperty(value="Ambito di interesse dell'opera (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Ambito)", required=true)
	@Size(max=5)
	private String ambito;
	  
	@ApiModelProperty(value = "Anno ultimo q.e. approvato", required=true)
	private Long anno;

	@ApiModelProperty(value="Importo complessivo dell'intervento", required=true)
	private Double importoIntervento;
	
	@ApiModelProperty(value="Importo complessivo lavori", required=true)
	private Double importoLavori;
		
	@ApiModelProperty(value="Oneri necessari per l'ultimazione dei lavori", required=true)
	private Double oneri;
		
	@ApiModelProperty(value="Importo ultimo SAL", required=true)
	private Double importoAvanzamento;
		
	@ApiModelProperty(value="Percentuale avanzamento lavori %", required=true)
	private Double percentualeAvanzamento;
		
	@ApiModelProperty(value="Causa per la quale l'opera � incompiuta (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Causa)", required=true)
	@Size(max=5)
	private String causa;
		  
	@ApiModelProperty(value="Stato di realizzazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=StatoRealizzazione)", required=true)
	@Size(max=5)
	private String stato;
 
	@ApiModelProperty(value="Parte di infrastruttura di rete? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)")
	@Size(max=1)
	private String infrastruttura;
		  
	@ApiModelProperty(value="Opera fruibile parzialmente? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String fruibile;
		  
	@ApiModelProperty(value="Utilizzo ridimensionato? (OBBLIGATORIO se fruibile = Si)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)")
	@Size(max=1)
	private String ridimensionato;
		  
	@ApiModelProperty(value="Destinazione d'uso (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=DestinazioneUso)", required=true)
	@Size(max=5)
	private String destinazioneUso;

	@ApiModelProperty(value="Cessione per realizzazione di altra opera? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String cessione;
		  
	@ApiModelProperty(value="Prevista la vendita? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String previstaVendita;
		  
	@ApiModelProperty(value="Opera da demolire? (OBBLIGATORIO  se previstaVendita = No)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)")
	@Size(max=1)
	private String demolizione;
		  
	@ApiModelProperty(value="Oneri Sito")
	private Double oneriSito;

	@ApiModelProperty(value="Altri dati")
	private AltriDatiOperaIncompiutaPubbEntry altriDati;

	@ApiModelProperty(value="Immobili da trasferire")
	@Size(min=0)
    private List<ImmobilePubbEntry> immobili = new ArrayList<ImmobilePubbEntry>();
	
	private String discontinuitaRete;

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDeterminazioneAmministrazione(
			String determinazioneAmministrazione) {
		this.determinazioneAmministrazione = determinazioneAmministrazione;
	}

	public String getDeterminazioneAmministrazione() {
		return determinazioneAmministrazione;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getAnno() {
		return anno;
	}

	public void setImportoIntervento(Double importoIntervento) {
		this.importoIntervento = importoIntervento;
	}

	public Double getImportoIntervento() {
		return importoIntervento;
	}

	public void setImportoLavori(Double importoLavori) {
		this.importoLavori = importoLavori;
	}

	public Double getImportoLavori() {
		return importoLavori;
	}

	public void setOneri(Double oneri) {
		this.oneri = oneri;
	}

	public Double getOneri() {
		return oneri;
	}

	public void setImportoAvanzamento(Double importoAvanzamento) {
		this.importoAvanzamento = importoAvanzamento;
	}

	public Double getImportoAvanzamento() {
		return importoAvanzamento;
	}

	public void setPercentualeAvanzamento(Double percentualeAvanzamento) {
		this.percentualeAvanzamento = percentualeAvanzamento;
	}

	public Double getPercentualeAvanzamento() {
		return percentualeAvanzamento;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getCausa() {
		return causa;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStato() {
		return stato;
	}

	public void setInfrastruttura(String infrastruttura) {
		this.infrastruttura = infrastruttura;
	}

	public String getInfrastruttura() {
		return infrastruttura;
	}

	public void setFruibile(String fruibile) {
		this.fruibile = fruibile;
	}

	public String getFruibile() {
		return fruibile;
	}

	public void setRidimensionato(String ridimensionato) {
		this.ridimensionato = ridimensionato;
	}

	public String getRidimensionato() {
		return ridimensionato;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getDestinazioneUso() {
		return destinazioneUso;
	}

	public void setCessione(String cessione) {
		this.cessione = cessione;
	}

	public String getCessione() {
		return cessione;
	}

	public void setPrevistaVendita(String previstaVendita) {
		this.previstaVendita = previstaVendita;
	}

	public String getPrevistaVendita() {
		return previstaVendita;
	}

	public void setDemolizione(String demolizione) {
		this.demolizione = demolizione;
	}

	public String getDemolizione() {
		return demolizione;
	}

	public void setOneriSito(Double oneriSito) {
		this.oneriSito = oneriSito;
	}

	public Double getOneriSito() {
		return oneriSito;
	}

	public void setAltriDati(AltriDatiOperaIncompiutaPubbEntry altriDati) {
		this.altriDati = altriDati;
	}

	public AltriDatiOperaIncompiutaPubbEntry getAltriDati() {
		return altriDati;
	}

	public void setImmobili(List<ImmobilePubbEntry> immobili) {
		this.immobili = immobili;
	}

	public List<ImmobilePubbEntry> getImmobili() {
		return immobili;
	}

	public void setContri(Long contri) {
		this.contri = contri;
	}

	@XmlTransient
	public Long getContri() {
		return contri;
	}

	public void setNumoi(Long numoi) {
		this.numoi = numoi;
	}

	@XmlTransient
	public Long getNumoi() {
		return numoi;
	}

	public String getDiscontinuitaRete() {
		return discontinuitaRete;
	}

	public void setDiscontinuitaRete(String discontinuitaRete) {
		this.discontinuitaRete = discontinuitaRete;
	}
	
}
