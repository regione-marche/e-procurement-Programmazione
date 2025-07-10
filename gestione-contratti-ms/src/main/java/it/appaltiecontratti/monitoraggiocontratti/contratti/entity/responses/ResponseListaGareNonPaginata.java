package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraBaseEntry;

public class ResponseListaGareNonPaginata extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Lista gare non paginata")
	List<GaraBaseEntry> data;

	public List<GaraBaseEntry> getData() {
		return data;
	}

	public void setData(List<GaraBaseEntry> data) {
		this.data = data;
	}

}
