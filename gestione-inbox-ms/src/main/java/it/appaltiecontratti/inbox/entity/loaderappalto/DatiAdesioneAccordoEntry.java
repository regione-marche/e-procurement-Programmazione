package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiAdesioneAccordoEntry {
	
	private String luogoIstat;
	private String luogoNuts;
	private String codStrumento;
	private Double importoLavori;
	private Double importoServizi;
	private Double importoForniture;
	private Double percRibasso;
	private Double percAumento;
	private Double importoAgg;
	private Date dataVerbAgg;
	private String richSubappalto;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private Double impNonAssog;
	private Double importoAttuazioneSicurezza;

	public Double getImpNonAssog() {
		return impNonAssog;
	}
	public void setImpNonAssog(Double impNonAssog) {
		this.impNonAssog = impNonAssog;
	}
	public Double getImportoAttuazioneSicurezza() {
		return importoAttuazioneSicurezza;
	}
	public void setImportoAttuazioneSicurezza(Double importoAttuazioneSicurezza) {
		this.importoAttuazioneSicurezza = importoAttuazioneSicurezza;
	}
	public String getLuogoIstat() {
		return luogoIstat;
	}
	public void setLuogoIstat(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}
	public String getLuogoNuts() {
		return luogoNuts;
	}
	public void setLuogoNuts(String logoNuts) {
		this.luogoNuts = logoNuts;
	}
	public String getCodStrumento() {
		return codStrumento;
	}
	public void setCodStrumento(String codStrumento) {
		this.codStrumento = codStrumento;
	}
	public Double getImportoLavori() {
		return importoLavori;
	}
	public void setImportoLavori(Double importoLavori) {
		this.importoLavori = importoLavori;
	}
	public Double getImportoServizi() {
		return importoServizi;
	}
	public void setImportoServizi(Double importoServizi) {
		this.importoServizi = importoServizi;
	}
	public Double getImportoForniture() {
		return importoForniture;
	}
	public void setImportoForniture(Double importoForniture) {
		this.importoForniture = importoForniture;
	}
	public Double getPercRibasso() {
		return percRibasso;
	}
	public void setPercRibasso(Double percRibasso) {
		this.percRibasso = percRibasso;
	}
	public Double getPercAumento() {
		return percAumento;
	}
	public void setPercAumento(Double percAumento) {
		this.percAumento = percAumento;
	}
	public Double getImportoAgg() {
		return importoAgg;
	}
	public void setImportoAgg(Double immportoAgg) {
		this.importoAgg = immportoAgg;
	}
	public Date getDataVerbAgg() {
		return dataVerbAgg;
	}
	public void setDataVerbAgg(Date dataVerbAgg) {
		this.dataVerbAgg = dataVerbAgg;
	}
	public String getRichSubappalto() {
		return richSubappalto;
	}
	public void setRichSubappalto(String richSubappalto) {
		this.richSubappalto = richSubappalto;
	}
	public String getIdSchedaLocale() {
		return idSchedaLocale;
	}
	public void setIdSchedaLocale(String idSchedaLocale) {
		this.idSchedaLocale = idSchedaLocale;
	}
	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}
	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

}