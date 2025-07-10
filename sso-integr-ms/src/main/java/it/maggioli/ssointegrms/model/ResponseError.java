package it.maggioli.ssointegrms.model;

/**
 * Classe wrapper che rappresenta una richiesta che e' andata in errore
 * 
 * @author Cristiano Perin
 *
 */
public class ResponseError extends BaseResponse {

	private String errorCode;
	private String errorMessage;
	private String traceId;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	@Override
	public String toString() {
		return "ResponseError [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", traceId=" + traceId
				+ ", get_uuid_()=" + get_uuid_() + ", get_ts_()=" + get_ts_() + "]";
	}

}
