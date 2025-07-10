package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Categoria CPV del lotto")
public class CpvLottoEntry implements Serializable {

	private static final long serialVersionUID = -4433185026855332865L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice della gara", hidden = true)
	private Long idGara;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice del lotto", hidden = true)
	private Long idLotto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Numero progressivo", hidden = true)
	private String numCpv;
	@ApiModelProperty(value = "Codice CPV secondario", required = true)
	@Size(max = 12)
	private String cpv;

	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	public Long getIdGara() {
		return idGara;
	}

	public void setIdLotto(Long idLotto) {
		this.idLotto = idLotto;
	}

	public Long getIdLotto() {
		return idLotto;
	}

	public void setNumCpv(String numCpv) {
		this.numCpv = numCpv;
	}

	public String getNumCpv() {
		return numCpv;
	}

	public void setCpv(String cpv) {
		this.cpv = StringUtils.stripToNull(cpv);
	}

	public String getCpv() {
		return cpv;
	}

}
