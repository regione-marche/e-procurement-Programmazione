package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.StatiId;

public class AttoEntry {

	@ApiModelProperty(value = "id dell'atto")
	private Long id;
	@ApiModelProperty(value = "nome dell'atto")
	private String nome;
	@ApiModelProperty(value = "campi ulteriori per la creazione dell'atto")
	private String campiVisibili;
	@ApiModelProperty(value = "campi obbligatori per la creazione dell'atto")
	private String campiObbligatori;
	@ApiModelProperty(value = "numeri pubb degli atti associati alla tipologia")
	private StatiId statiId;
	@ApiModelProperty(value = "Tipologia della categoria di pubblicazione")
	private String tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCampiVisibili() {
		return campiVisibili;
	}

	public void setCampiVisibili(String campiVisibili) {
		this.campiVisibili = campiVisibili;
	}

	public String getCampiObbligatori() {
		return campiObbligatori;
	}

	public void setCampiObbligatori(String campiObbligatori) {
		this.campiObbligatori = campiObbligatori;
	}

	public StatiId getStatiId() {
		return statiId;
	}

	public void setStatiId(StatiId statiId) {
		this.statiId = statiId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
