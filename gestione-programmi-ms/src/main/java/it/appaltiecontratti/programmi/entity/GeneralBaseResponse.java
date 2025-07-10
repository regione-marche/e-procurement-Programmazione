package it.appaltiecontratti.programmi.entity;

public class GeneralBaseResponse<T> extends BaseResponse {

	private static final long serialVersionUID = 1709128588508308127L;
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
