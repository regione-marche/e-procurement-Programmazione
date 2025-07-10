package it.appaltiecontratti.programmi.entity;

import java.util.List;

public class ResponsePubblicazioni  extends BaseResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<FlussiProgrammi> data;

	public List<FlussiProgrammi> getData() {
		return data;
	}

	public void setData(List<FlussiProgrammi> data) {
		this.data = data;
	}
	
}
