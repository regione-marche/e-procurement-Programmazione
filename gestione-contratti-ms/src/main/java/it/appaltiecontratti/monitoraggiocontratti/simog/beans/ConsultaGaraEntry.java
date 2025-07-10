package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaGaraBean;

public class ConsultaGaraEntry {

	private String codGara;
	private List<ConsultaGaraBean> listaLotti;
	private String operation;
	private SABaseEntry origineSAInfo;
	private String cfRup;
	private boolean isCigInDb;
	private String idAvGara;
	private boolean perfezionata;
	
	private boolean errors;
	private List<String> internalErrors;
	private List<String> validationErrors;
	private List<String> anacErrors;
	private String govwayMessageId;
	private String govwayTransactionId;
	private boolean inserito;
	private boolean presaCarico;
	private boolean presaCaricoCompleted;
	
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

	public String getCodGara() {
		return codGara;
	}

	public void setCodGara(String codGara) {
		this.codGara = codGara;
	}

	public List<ConsultaGaraBean> getListaLotti() {
		return listaLotti;
	}

	public void setListaLotti(List<ConsultaGaraBean> listaLotti) {
		this.listaLotti = listaLotti;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public SABaseEntry getOrigineSAInfo() {
		return origineSAInfo;
	}

	public void setOrigineSAInfo(SABaseEntry origineSAInfo) {
		this.origineSAInfo = origineSAInfo;
	}

	public String getCfRup() {
		return cfRup;
	}

	public void setCfRup(String cfRup) {
		this.cfRup = cfRup;
	}

	public boolean isCigInDb() {
		return isCigInDb;
	}

	public void setCigInDb(boolean isCigInDb) {
		this.isCigInDb = isCigInDb;
	}

	public String getIdAvGara() {
		return idAvGara;
	}

	public void setIdAvGara(String idAvGara) {
		this.idAvGara = idAvGara;
	}

	public boolean isPerfezionata() {
		return perfezionata;
	}

	public void setPerfezionata(boolean perfezionata) {
		this.perfezionata = perfezionata;
	}

	public boolean isPresaCarico() {
		return presaCarico;
	}

	public void setPresaCarico(boolean presaCarico) {
		this.presaCarico = presaCarico;
	}

	public boolean isPresaCaricoCompleted() {
		return presaCaricoCompleted;
	}

	public void setPresaCaricoCompleted(boolean presaCaricoCompleted) {
		this.presaCaricoCompleted = presaCaricoCompleted;
	}

}
