package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullFlussiFase;

public class ResponseListaInviiFasi extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private List<FullFlussiFase> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale degli interventi non riproposti estraibili dalla form di ricerca")
	private int totalCount;

	private Object moreInfoGara;

	public List<FullFlussiFase> getData() {
		return data;
	}

	public void setData(List<FullFlussiFase> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public Object getMoreInfoGara() { return moreInfoGara; }

	public void setMoreInfoGara(Object moreInfoGara) { this.moreInfoGara = moreInfoGara; }
	
}
