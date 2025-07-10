package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cig {

	@JsonProperty("cig")
	private String cig;

	public Cig(String cig) {
		this.cig = cig;
	}
	
}