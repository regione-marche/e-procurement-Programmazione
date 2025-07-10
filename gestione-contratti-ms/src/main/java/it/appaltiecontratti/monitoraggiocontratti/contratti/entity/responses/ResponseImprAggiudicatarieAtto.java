package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaAttoEntry;

public class ResponseImprAggiudicatarieAtto extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista delle imprese aggiudicatarie per gli atti")
	List<ImpresaAggiudicatariaAttoEntry> data;

	public List<ImpresaAggiudicatariaAttoEntry> getData() {
		return data;
	}

	public void setData(List<ImpresaAggiudicatariaAttoEntry> data) {
		this.data = data;
	}
	
}
