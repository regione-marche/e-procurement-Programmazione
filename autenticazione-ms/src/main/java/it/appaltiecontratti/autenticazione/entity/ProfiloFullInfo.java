package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ProfiloFullInfo {

	@ApiModelProperty(name = "idProfilo", value = "Codice univoco profilo")
	private String            idProfilo;
	@ApiModelProperty(name = "idProfilo", value = "Determina se il profilo � corrotto")
	private boolean           ok;
	@ApiModelProperty(name = "nome", value = "Nome profilo")
	private String            nome;
	@ApiModelProperty(name = "configurazioni", value = "Arrai di configurazioni del profilo")
	List<ValoreDatoProfilo>   configurazioni;
	@ApiModelProperty(name = "campiSolaLettura", value = "Array di dei campi di sola lettura del profilo")
	List<String> campiSolaLettura;
	private Boolean documentiAssociatiDB;
	private List<String> woggetti;
	
	
	public String getIdProfilo() {
		return idProfilo;
	}
	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<ValoreDatoProfilo> getConfigurazioni() {
		return configurazioni;
	}
	public void setConfigurazioni(List<ValoreDatoProfilo> configurazioni) {
		this.configurazioni = configurazioni;
	}
	public List<String> getCampiSolaLettura() {
		return campiSolaLettura;
	}
	public void setCampiSolaLettura(List<String> campiSolaLettura) {
		this.campiSolaLettura = campiSolaLettura;
	}


	public Boolean getDocumentiAssociatiDB() {
		return documentiAssociatiDB;
	}

	public void setDocumentiAssociatiDB(Boolean documentiAssociatiDB) {
		this.documentiAssociatiDB = documentiAssociatiDB;
	}


	public List<String> getWoggetti() {
		return woggetti;
	}

	public void setWoggetti(List<String> woggetti) {
		this.woggetti = woggetti;
	}
}
