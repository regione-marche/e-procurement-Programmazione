package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaEntry;

public class ResponseImprAggiudicatarie  extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista delle imprese aggiudicatarie per la fase")
	List<ImpresaAggiudicatariaEntry> data;

	public List<ImpresaAggiudicatariaEntry> getData() {
		return data;
	}

	public void setData(List<ImpresaAggiudicatariaEntry> data) {
		this.data = data;
	}
	
}