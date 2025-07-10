package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpegnoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PagamentoEntry;

public class ResponseDatiContabilita extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati degli impegni")
	private List<ImpegnoEntry> impegni;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati dei pagamenti")
	private List<PagamentoEntry> pagamenti;

	public List<ImpegnoEntry> getImpegni() {
		return impegni;
	}

	public void setImpegni(List<ImpegnoEntry> impegni) {
		this.impegni = impegni;
	}

	public List<PagamentoEntry> getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(List<PagamentoEntry> pagamenti) {
		this.pagamenti = pagamenti;
	}

}
