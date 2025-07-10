package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;

public class ResponseDettaglioLotto  extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio lotto")
	LottoEntry data;
	
	public LottoEntry getData() {
		return data;
	}

	public void setData(LottoEntry data) {
		this.data = data;
	}
}