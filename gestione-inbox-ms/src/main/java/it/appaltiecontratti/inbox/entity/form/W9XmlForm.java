package it.appaltiecontratti.inbox.entity.form;

import java.util.Date;

public class W9XmlForm {

	private Long codGara;
	private Long codLotto;
	private Long numXml;
	private Date dataExport;
	private Date dataInvio;
	private String xml;
	private Long idFlusso;
	private Long faseEsecuzione;
	private Long numProgressivoFase;

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

	public Long getNumXml() {
		return numXml;
	}

	public void setNumXml(Long numXml) {
		this.numXml = numXml;
	}

	public Date getDataExport() {
		return dataExport;
	}

	public void setDataExport(Date dataExport) {
		this.dataExport = dataExport;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}

	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}

	public Long getNumProgressivoFase() {
		return numProgressivoFase;
	}

	public void setNumProgressivoFase(Long numProgressivoFase) {
		this.numProgressivoFase = numProgressivoFase;
	}

}
