package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Contenitore dei campi degli utenti per le ricezioni dei messaggi")
public class MessageReceiverEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nominativo;
	private Long syscon;
	
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	
}
