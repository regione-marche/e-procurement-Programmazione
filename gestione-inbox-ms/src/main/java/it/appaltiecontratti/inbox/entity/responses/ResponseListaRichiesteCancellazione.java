package it.appaltiecontratti.inbox.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.RichiestaCancellazioneEntry;

public class ResponseListaRichiesteCancellazione extends BaseResponse {

	private static final long serialVersionUID = 1633841615211006964L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati base delle richieste di cancellazione")
	private List<RichiestaCancellazioneEntry> data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Numero totale delle richieste di cancellazione")
	private int totalCount;

	public List<RichiestaCancellazioneEntry> getData() {
		return data;
	}

	public void setData(List<RichiestaCancellazioneEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
