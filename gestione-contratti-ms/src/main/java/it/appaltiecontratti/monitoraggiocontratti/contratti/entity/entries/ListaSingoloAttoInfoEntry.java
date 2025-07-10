package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class ListaSingoloAttoInfoEntry {
	private String descrizione;
	private Date dataPubblicazione;
	private String primaPubblicazione;
	private Date dataPubbSistema;
	private Long utenteCancellazione;
	private Date dataCancellazione;
	private String motivoCancellazione;

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

}
