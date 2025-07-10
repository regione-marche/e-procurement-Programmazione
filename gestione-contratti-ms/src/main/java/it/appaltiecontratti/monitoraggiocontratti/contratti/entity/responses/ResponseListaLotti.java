package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoBaseEntry;

public class ResponseListaLotti extends BaseResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base dei lotti")
	private List<LottoBaseEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale dei lotti estraibili dalla form di ricerca")
	private int totalCount;


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<LottoBaseEntry> getData() {
		return data;
	}

	public void setData(List<LottoBaseEntry> data) {
		this.data = data;
	}
}
