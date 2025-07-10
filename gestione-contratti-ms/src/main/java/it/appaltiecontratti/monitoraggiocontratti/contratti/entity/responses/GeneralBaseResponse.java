package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

public class GeneralBaseResponse<T> extends BaseResponse {

	private static final long serialVersionUID = -3942452252861760544L;

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
