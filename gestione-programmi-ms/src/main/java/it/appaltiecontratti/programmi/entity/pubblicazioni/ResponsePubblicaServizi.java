package it.appaltiecontratti.programmi.entity.pubblicazioni;

import java.io.Serializable;

import it.appaltiecontratti.programmi.entity.BaseResponse;

public class ResponsePubblicaServizi   extends BaseResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PubblicaProgrammaFornitureServiziEntry data;

	public PubblicaProgrammaFornitureServiziEntry getData() {
		return data;
	}

	public void setData(PubblicaProgrammaFornitureServiziEntry data) {
		this.data = data;
	}
	
	

}
