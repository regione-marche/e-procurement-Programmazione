package it.appaltiecontratti.inbox.entity.contratti;

import io.swagger.annotations.ApiModelProperty;

public class CPVSecondario {
	
	@ApiModelProperty(value="codice univoco CPV")
	private String codCpv;
	@ApiModelProperty(value="descrizione CPV")
	private String descCpv;
	
	public String getCodCpv() {
		return codCpv;
	}
	public void setCodCpv(String codCpv) {
		this.codCpv = codCpv;
	}
	public String getDescCpv() {
		return descCpv;
	}
	public void setDescCpv(String descCpv) {
		this.descCpv = descCpv;
	}

}
