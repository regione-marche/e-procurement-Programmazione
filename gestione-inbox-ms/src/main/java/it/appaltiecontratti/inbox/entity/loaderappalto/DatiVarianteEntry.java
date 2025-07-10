package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiVarianteEntry {

	private Date dataVerbAppr;
	private String altreMotivazioni;
	private Double impRidetLavori;
	private Double impRidetServizi;
	private Double impRidetFornit;
	private Double impSicurezza;
	private Double impProgettazione;
	private Double impDisposizione;
	private Double impNonAssog;
	private Date dataAttoAggiuntivo;
	private Long numGiorniProroga;
	private Long numVari;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private String cigNuovaProcedura;
	private String urlVariantiCo;
	private Long motivoRevPrezzi;
	
	
	public String getUrlVariantiCo() {
		return urlVariantiCo;
	}
	public void setUrlVariantiCo(String urlVariantiCo) {
		this.urlVariantiCo = urlVariantiCo;
	}
	public Long getMotivoRevPrezzi() {
		return motivoRevPrezzi;
	}
	public void setMotivoRevPrezzi(Long motivoRevPrezzi) {
		this.motivoRevPrezzi = motivoRevPrezzi;
	}

	public Date getDataVerbAppr() {
		return dataVerbAppr;
	}

	public void setDataVerbAppr(Date dataVerbAppr) {
		this.dataVerbAppr = dataVerbAppr;
	}

	public String getAltreMotivazioni() {
		return altreMotivazioni;
	}

	public void setAltreMotivazioni(String altreMotivazioni) {
		this.altreMotivazioni = altreMotivazioni;
	}

	public Double getImpRidetLavori() {
		return impRidetLavori;
	}

	public void setImpRidetLavori(Double impRidetLavori) {
		this.impRidetLavori = impRidetLavori;
	}

	public Double getImpRidetServizi() {
		return impRidetServizi;
	}

	public void setImpRidetServizi(Double impRidetServizi) {
		this.impRidetServizi = impRidetServizi;
	}

	public Double getImpRidetFornit() {
		return impRidetFornit;
	}

	public void setImpRidetFornit(Double impRidetFornit) {
		this.impRidetFornit = impRidetFornit;
	}

	public Double getImpSicurezza() {
		return impSicurezza;
	}

	public void setImpSicurezza(Double impSicurezza) {
		this.impSicurezza = impSicurezza;
	}

	public Double getImpProgettazione() {
		return impProgettazione;
	}

	public void setImpProgettazione(Double impProgettazione) {
		this.impProgettazione = impProgettazione;
	}

	public Double getImpDisposizione() {
		return impDisposizione;
	}

	public void setImpDisposizione(Double impDisposizione) {
		this.impDisposizione = impDisposizione;
	}

	public Double getImpNonAssog() {
		return impNonAssog;
	}

	public void setImpNonAssog(Double impNonAssog) {
		this.impNonAssog = impNonAssog;
	}

	public Date getDataAttoAggiuntivo() {
		return dataAttoAggiuntivo;
	}

	public void setDataAttoAggiuntivo(Date dataAttoAggiuntivo) {
		this.dataAttoAggiuntivo = dataAttoAggiuntivo;
	}

	public Long getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(Long numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
	}

	public Long getNumVari() {
		return numVari;
	}

	public void setNumVari(Long numVari) {
		this.numVari = numVari;
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

	public String getCigNuovaProcedura() {
		return cigNuovaProcedura;
	}

	public void setCigNuovaProcedura(String cigNuovaProcedura) {
		this.cigNuovaProcedura = cigNuovaProcedura;
	}
}
