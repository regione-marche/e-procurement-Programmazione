package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;

public class ResponseInizNuovoProgramma extends BaseResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InizNuovoProgramma data;

	public InizNuovoProgramma getData() {
		return data;
	}

	public void setData(InizNuovoProgramma data) {
		this.data = data;
	}
	
	
	
	

}
