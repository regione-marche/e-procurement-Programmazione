package it.appaltiecontratti.inbox.entity.responses;

import java.io.Serializable;
import java.util.List;

public class FullResponseLoaderAppalto extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 6676925716962987922L;

	private String data;
	private List<String> validationErrors;
	private String xml;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
