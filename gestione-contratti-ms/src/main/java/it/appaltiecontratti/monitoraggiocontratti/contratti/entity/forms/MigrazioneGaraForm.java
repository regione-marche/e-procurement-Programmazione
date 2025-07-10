package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.io.Serializable;

public class MigrazioneGaraForm implements Serializable {

	private static final long serialVersionUID = 49157727931190911L;
	
	private Long codGara;
	private String stazioneAppaltante;
	private String codiceFiscaleRupGara;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getCodiceFiscaleRupGara() {
		return codiceFiscaleRupGara;
	}

	public void setCodiceFiscaleRupGara(String codiceFiscaleRupGara) {
		this.codiceFiscaleRupGara = codiceFiscaleRupGara;
	}

}
