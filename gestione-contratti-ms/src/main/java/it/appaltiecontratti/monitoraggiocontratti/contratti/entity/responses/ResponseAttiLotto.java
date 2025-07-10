package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttoLottoEntry;

public class ResponseAttiLotto  extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista dei lotti")
    List<AttoLottoEntry> data;
	
	public List<AttoLottoEntry> getData() {
		return data;
	}

	public void setData(List<AttoLottoEntry> data) {
		this.data = data;
	}
	
}