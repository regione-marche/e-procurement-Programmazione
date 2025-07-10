package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;

@ApiModel(description = "Contenitore per il risultato dell'operazione di reperimento lista imprese")
public class ResponseListaImpreseOptions extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 3175826211387242319L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati dell'impresa")
	private List<ImpresaEntry> data;

	public List<ImpresaEntry> getData() {
		return data;
	}

	public void setData(List<ImpresaEntry> data) {
		this.data = data;
	}

}
