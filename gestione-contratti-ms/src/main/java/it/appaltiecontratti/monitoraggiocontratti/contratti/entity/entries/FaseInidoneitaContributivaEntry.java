package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseInidoneitaContributivaEntry {
	

	private Long codGara;
	private Long codLotto;
	private Long num;
	private String descrizione;
	private String codImpresa;
	private String estremiDurc;
	private Date dataDurc;
	private String cassaEdile;
	private Long riscontroIrregolare;
	private Date dataComunicazione;
	private ImpresaEntry impresa; 
	private boolean pubblicata;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
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
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public String getEstremiDurc() {
		return estremiDurc;
	}
	public void setEstremiDurc(String estremiDurc) {
		this.estremiDurc = estremiDurc;
	}
	public Date getDataDurc() {
		return dataDurc;
	}
	public void setDataDurc(Date dataDurc) {
		this.dataDurc = dataDurc;
	}
	public String getCassaEdile() {
		return cassaEdile;
	}
	public void setCassaEdile(String cassaEdile) {
		this.cassaEdile = cassaEdile;
	}
	public Long getRiscontroIrregolare() {
		return riscontroIrregolare;
	}
	public void setRiscontroIrregolare(Long riscontroIrregolare) {
		this.riscontroIrregolare = riscontroIrregolare;
	}
	public Date getDataComunicazione() {
		return dataComunicazione;
	}
	public void setDataComunicazione(Date dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}
	public ImpresaEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}
	
}
