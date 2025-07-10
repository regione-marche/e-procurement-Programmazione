package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per il risultato dell'operazione di lista programmi non paginata")
public class ResponseListaProgrammiNonPaginata extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 5843832647523006176L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Lista programmi non paginata")
	private List<ProgrammaBaseEntry> data;

	public List<ProgrammaBaseEntry> getData() {
		return data;
	}

	public void setData(List<ProgrammaBaseEntry> entry) {
		this.data = entry;
	}

}