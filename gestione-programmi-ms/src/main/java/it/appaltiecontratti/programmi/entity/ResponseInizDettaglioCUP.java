package it.appaltiecontratti.programmi.entity;


public class ResponseInizDettaglioCUP  extends BaseResponse{
	
	
	private static final long serialVersionUID = 1L;
	
	private CredenzialiSimog data;

	public CredenzialiSimog getData() {
		return data;
	}

	public void setData(CredenzialiSimog data) {
		this.data = data;
	}

}