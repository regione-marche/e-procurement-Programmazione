package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;

public class ResponseFaseIncarichiProfessionali extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista degli incarichi professionali per la fase")
	private List<IncarichiProfEntry> incarichi;
	private boolean pubblicata;
	public List<IncarichiProfEntry> getIncarichi() {
		return incarichi;
	}
	public void setIncarichi(List<IncarichiProfEntry> incarichi) {
		this.incarichi = incarichi;
	}
	public boolean getPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
}
