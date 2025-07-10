package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoBaseEntry;

public class ResponseMultilotto extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -6098436809673389883L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati dei lotti")
	private List<LottoBaseEntry> data;

	public List<LottoBaseEntry> getData() {
		return data;
	}

	public void setData(List<LottoBaseEntry> data) {
		this.data = data;
	}

}
