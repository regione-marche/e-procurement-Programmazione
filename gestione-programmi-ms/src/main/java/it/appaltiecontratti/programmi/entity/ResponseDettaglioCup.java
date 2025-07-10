package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.HashMap;

public class ResponseDettaglioCup extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> data;

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

}