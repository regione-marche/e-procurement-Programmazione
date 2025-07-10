package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

public class BaseAzioneEntry {

	@ApiModelProperty(value = "tipologia azione")
	private String tipo;
	@ApiModelProperty(value = "Descrizione azione")
	private String azione; 
	@ApiModelProperty(value = "Oggetto azione")
	private String oggetto;
	@ApiModelProperty(value = "Valore azione")
	private Long valore; 
	@ApiModelProperty(value = "CRC azione")
	private Long crc;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Long getValore() {
		return valore;
	}
	public void setValore(Long valore) {
		this.valore = valore;
	}
	public Long getCrc() {
		return crc;
	}
	public void setCrc(Long crc) {
		this.crc = crc;
	}
	
}
