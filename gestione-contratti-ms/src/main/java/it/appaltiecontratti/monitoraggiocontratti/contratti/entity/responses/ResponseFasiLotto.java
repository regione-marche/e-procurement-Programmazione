package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FasiInfo;

public class ResponseFasiLotto extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 3045093110230968396L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Info relative alle fasi associate al lotto")
	private FasiInfo data;

	public FasiInfo getData() {
		return data;
	}

	public void setData(FasiInfo data) {
		this.data = data;
	}

}
