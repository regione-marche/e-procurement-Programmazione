package it.appaltiecontratti.tabellati.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description="Contenitore per il risultato dell'operazione di recupero ufficio")
public class ResponseUfficio extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 3289638049792513551L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dell'ufficio")
	private UffEntry data;

	public UffEntry getData() {
		return data;
	}

	public void setData(UffEntry entry) {
		this.data = entry;
	}
}

