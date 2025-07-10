package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description = "Contenitore per il risultato dell'operazione di lista programmi")
public class ResponseListaProgrammi extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1271833422288720428L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Lista programmi")
	private List<ProgrammaBaseEntry> data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Numero totale dei programmi estraibili dalla form di ricerca")
	private int totalCount;

	public List<ProgrammaBaseEntry> getData() {
		return data;
	}

	public void setData(List<ProgrammaBaseEntry> entry) {
		this.data = entry;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}