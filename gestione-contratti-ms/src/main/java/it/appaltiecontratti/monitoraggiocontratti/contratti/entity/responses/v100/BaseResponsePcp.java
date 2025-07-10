package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaComunicaAppaltoType;

public class BaseResponsePcp extends BaseResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7829083881812375377L;
	private boolean errors;
	private List<String> internalErrors;
	private List<String> validationErrors;
	private List<String> anacErrors;
	private String govwayMessageId;
	private String govwayTransactionId;
	private boolean inserito;
	
	public static final String ERROR_SCHEDA_WAITING_PCP = "ERROR-SCHEDA-WAITING-PCP";
	
	public boolean isErrors() {
		return errors;
	}
	public void setErrors(boolean errors) {
		this.errors = errors;
	}
	public List<String> getInternalErrors() {
		return internalErrors;
	}
	public void setInternalErrors(List<String> internalErrors) {
		this.internalErrors = internalErrors;
	}
	public List<String> getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}
	public List<String> getAnacErrors() {
		return anacErrors;
	}
	public void setAnacErrors(List<String> anacErrors) {
		this.anacErrors = anacErrors;
	}
	public String getGovwayMessageId() {
		return govwayMessageId;
	}
	public void setGovwayMessageId(String govwayMessageId) {
		this.govwayMessageId = govwayMessageId;
	}
	public String getGovwayTransactionId() {
		return govwayTransactionId;
	}
	public void setGovwayTransactionId(String govwayTransactionId) {
		this.govwayTransactionId = govwayTransactionId;
	}
	public boolean isInserito() {
		return inserito;
	}
	public void setInserito(boolean inserito) {
		this.inserito = inserito;
	}
	
	
}