package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Contenitore per i dati necessari ad aggiornare l'id di pubblicazione di un avviso")
public class AvvisoPubblicatoForm extends AvvisoBaseForm{

	@ApiModelProperty(value="Id generato in fase di pubblicazione")
    private Integer idRicevuto;
	@ApiModelProperty(value="Codice del tabellato TipoAvviso")
	private Integer tipologia;
	@ApiModelProperty(value="Syscon dell'autore della pubblicazione")
	private Long syscon;
	private String payload;
	



	public Integer getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Integer idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Integer getTipologia() {
		return tipologia;
	}

	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
	
}
