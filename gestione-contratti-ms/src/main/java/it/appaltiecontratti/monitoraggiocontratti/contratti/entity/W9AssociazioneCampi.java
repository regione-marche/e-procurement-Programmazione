package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

public class W9AssociazioneCampi {

	private Long id;
	private Long codGara;
	private Long codLotto;
	private Long faseEsecuzione;
	private Long num;
	private String nomeCampo;
	private String idEsterno;
	private String appEsterno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}

	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getIdEsterno() {
		return idEsterno;
	}

	public void setIdEsterno(String idEsterno) {
		this.idEsterno = idEsterno;
	}

	public String getAppEsterno() {
		return appEsterno;
	}

	public void setAppEsterno(String appEsterno) {
		this.appEsterno = appEsterno;
	}

}
