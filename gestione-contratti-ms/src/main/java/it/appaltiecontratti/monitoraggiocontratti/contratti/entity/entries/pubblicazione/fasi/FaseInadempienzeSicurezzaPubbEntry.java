package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class FaseInadempienzeSicurezzaPubbEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataInadempienza;
	private String comma3A;
	private String comma3B;
	private String comma45A;
	private String comma5;
	private String comma6;
	private String commaAltro;
	private String descrizione;
	private String codImpresa;
	private ImpresaFasePubbEntry impresa;
	private Long numAppa;
	
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
	public Date getDataInadempienza() {
		return dataInadempienza;
	}
	public void setDataInadempienza(Date dataInadempienza) {
		this.dataInadempienza = dataInadempienza;
	}
	public String getComma3A() {
		return comma3A;
	}
	public void setComma3A(String comma3a) {
		comma3A = comma3a;
	}
	public String getComma3B() {
		return comma3B;
	}
	public void setComma3B(String comma3b) {
		comma3B = comma3b;
	}
	public String getComma45A() {
		return comma45A;
	}
	public void setComma45A(String comma45a) {
		comma45A = comma45a;
	}
	public String getComma5() {
		return comma5;
	}
	public void setComma5(String comma5) {
		this.comma5 = comma5;
	}
	public String getComma6() {
		return comma6;
	}
	public void setComma6(String comma6) {
		this.comma6 = comma6;
	}
	public String getCommaAltro() {
		return commaAltro;
	}
	public void setCommaAltro(String commaAltro) {
		this.commaAltro = commaAltro;
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
	public ImpresaFasePubbEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaFasePubbEntry impresa) {
		this.impresa = impresa;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
