package it.appaltiecontratti.programmi.entity.pubblicazioni;

import java.io.Serializable;

import it.appaltiecontratti.programmi.entity.BaseResponse;

public class ResponsePubblicaLavori   extends BaseResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PubblicaProgrammaLavoriEntry data;

	public PubblicaProgrammaLavoriEntry getData() {
		return data;
	}

	public void setData(PubblicaProgrammaLavoriEntry data) {
		this.data = data;
	}
	
}
