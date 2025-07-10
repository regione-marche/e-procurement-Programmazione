package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaMandatiRequest {

	@JsonProperty("tipoRisultato")
	private String tipoRisultato;

	@JsonProperty("listaCup")
	private List<Cup> listaCup;

	@JsonProperty("listaCig")
	private List<Cig> listaCig;

//  @JsonProperty("codLavoro")
//  private int codLavoro;

	public List<Cig> getListaCig() {
		return listaCig;
	}

	public void setListaCig(List<Cig> listaCig) {
		this.listaCig = listaCig;
	}


	public String getTipoRisultato() {
		return tipoRisultato;
	}

	public void setTipoRisultato(String tipoRisultato) {
		this.tipoRisultato = tipoRisultato;
	}

	public List<Cup> getListaCup() {
		return listaCup;
	}

	public void setListaCup(List<Cup> listaCup) {
		this.listaCup = listaCup;
	}

	public static class Cup {

		@JsonProperty("cup")
		private String cup;

		public String getCup() {
			return cup;
		}

		public void setCup(String cup) {
			this.cup = cup;
		}
	}

}
