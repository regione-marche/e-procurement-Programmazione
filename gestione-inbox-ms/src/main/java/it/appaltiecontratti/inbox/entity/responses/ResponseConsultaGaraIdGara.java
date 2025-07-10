package it.appaltiecontratti.inbox.entity.responses;

import java.io.Serializable;

import it.avlp.simog.massload.xmlbeans.SchedaType;




public class ResponseConsultaGaraIdGara extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private SchedaType data;

	public SchedaType getData() {
		return data;
	}

	public void setData(SchedaType data) {
		this.data = data;
	}
}