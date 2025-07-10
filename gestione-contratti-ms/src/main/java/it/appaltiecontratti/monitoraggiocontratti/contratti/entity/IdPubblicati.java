package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import java.util.Date;

public class IdPubblicati {

	private Long id;
	private Boolean pubblicato;
	private String descrizione;
	private Date dataPubblicazione;
	private Boolean parziale;
	private Long countParziale;
	private Long totalParziale;
	private String primaPubblicazione;
	private Date dataPubbSistema;
	private Long utenteCancellazione;
	private Date dataCancellazione;
	private String motivoCancellazione;
	private Boolean pubblicatoAtto;
	private Boolean annullato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getPubblicato() {
		return pubblicato;
	}

	public void setPubblicato(Boolean pubblicato) {
		this.pubblicato = pubblicato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Boolean isParziale() {
		return parziale;
	}

	public void setParziale(Boolean parziale) {
		this.parziale = parziale;
	}

	public Long getCountParziale() {
		return countParziale;
	}

	public void setCountParziale(Long countParziale) {
		this.countParziale = countParziale;
	}

	public Long getTotalParziale() {
		return totalParziale;
	}

	public void setTotalParziale(Long totalParziale) {
		this.totalParziale = totalParziale;
	}

	public String getPrimaPubblicazione() { return primaPubblicazione; }

	public void setPrimaPubblicazione(String primaPubblicazione) { this.primaPubblicazione = primaPubblicazione; }

	public Date getDataPubbSistema() { return dataPubbSistema; }

	public void setDataPubbSistema(Date dataPubbSistema) { this.dataPubbSistema = dataPubbSistema; }

	public Long getUtenteCancellazione() { return utenteCancellazione; }

	public void setUtenteCancellazione(Long utenteCancellazione) { this.utenteCancellazione = utenteCancellazione; }

	public Date getDataCancellazione() { return dataCancellazione; }

	public void setDataCancellazione(Date dataCancellazione) { this.dataCancellazione = dataCancellazione; }

	public String getMotivoCancellazione() { return motivoCancellazione; }

	public void setMotivoCancellazione(String motivoCancellazione) { this.motivoCancellazione = motivoCancellazione; }

	public Boolean getPubblicatoAtto() { return pubblicatoAtto; }

	public void setPubblicatoAtto(Boolean pubblicatoAtto) { this.pubblicatoAtto = pubblicatoAtto; }

	public Boolean getAnnullato() { return annullato; }

	public void setAnnullato(Boolean annullato) { this.annullato = annullato; }

}
