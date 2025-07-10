package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

public class FaseCantieriImpEntry {

	private Long codGara;
	private Long codLotto;
	private Long numCant;
	private Long num;
	@XSSValidation
	private String codiceImpresa;
	@XSSValidation
	private String codiceDurc;
	@XSSValidation
	private String protocolloDurc;
	private Date dataDurc;
	private ImpresaEntry impresa;

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

	public Long getNumCant() {
		return numCant;
	}

	public void setNumCant(Long numCant) {
		this.numCant = numCant;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getCodiceImpresa() {
		return codiceImpresa;
	}

	public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

	public String getCodiceDurc() {
		return codiceDurc;
	}

	public void setCodiceDurc(String codiceDurc) {
		this.codiceDurc = codiceDurc;
	}

	public String getProtocolloDurc() {
		return protocolloDurc;
	}

	public void setProtocolloDurc(String protocolloDurc) {
		this.protocolloDurc = protocolloDurc;
	}

	public Date getDataDurc() {
		return dataDurc;
	}

	public void setDataDurc(Date dataDurc) {
		this.dataDurc = dataDurc;
	}

	public ImpresaEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}

}
