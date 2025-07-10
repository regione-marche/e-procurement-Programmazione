package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

public class ProfiloInfo {
	@ApiModelProperty(name = "codProfilo", value = "Codice univoco profilo")
	private String codProfilo;
	@ApiModelProperty(name = "nome", value = "Nome profilo")
	private String nome;
	@ApiModelProperty(name = "descrizione", value = "Descrizione profilo")
	private String descrizione;
	@ApiModelProperty(name = "codapp", value = "Codice applicazione profilo")
	private String codapp;
	@ApiModelProperty(name = "flagInterno", value = "Determina se il profilo e' proprietario o custom")
	private Long flagInterno;
	@ApiModelProperty(name = "codCliente", value = "Codice cliente profilo")
	private Long codCliente;
	@ApiModelProperty(name = "crc", value = "Codice calcolato per determinare se il profilo e' corrotto")
	private Long crc;
	@ApiModelProperty(name = "ok", value = "Determina se il profilo e' corrotto")
	private boolean ok;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodapp() {
		return codapp;
	}
	public void setCodapp(String codapp) {
		this.codapp = codapp;
	}
	public Long getCrc() {
		return crc;
	}
	public void setCrc(Long crc) {
		this.crc = crc;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getCodProfilo() {
		return codProfilo;
	}
	public void setCodProfilo(String codProfilo) {
		this.codProfilo = codProfilo;
	}
	public Long getFlagInterno() {
		return flagInterno;
	}
	public void setFlagInterno(Long flagInterno) {
		this.flagInterno = flagInterno;
	}
	public Long getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}
	
}
