package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;

public class FaseVarianteEntry extends FaseBaseEntry{
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataVerbaleAppr;
	private String altreMotivazioni;
	private Date dataAttoAggiuntivo;
	private Long numGiorniProroga;
	private Double importoRideterminatoLavori;
	private Double importoRideterminatoServizi;
	private Double importoRideterminatoForniture;
	private Double importoSubtotale;
	private Double importoSicurezza;
	private Double importoProgettazione;
	private Double importoNonAssog;
	private Double importoComplAppalto;
	private Double importoDisposizione;
	private Double importoComplIntervento;
	private String flagVariante;
	private String quintoObbligo;
	private String flagImporti;
	private String flagSicurezza;
	private List<LottoDynamicValue> motivazioniVariante;
	private boolean pubblicata;
	private String cigNuovaProc;
	private Long countW9moti;
	private String urlVariantiCo;
	private Long motivoRevPrezzi;
	private Long numAppa;
	private Double impFinpa;
	private Double entrUtenza; 
	private Double intrAttivo; 
	private Double impOpzioni;
	private String eform;
	private Long motivazioniVariantePcp;
	private Double impModifica;
	
	
	public Double getImpFinpa() {
		return impFinpa;
	}
	public void setImpFinpa(Double impFinpa) {
		this.impFinpa = impFinpa;
	}
	public Double getEntrUtenza() {
		return entrUtenza;
	}
	public void setEntrUtenza(Double entrUtenza) {
		this.entrUtenza = entrUtenza;
	}
	public Double getIntrAttivo() {
		return intrAttivo;
	}
	public void setIntrAttivo(Double intrAttivo) {
		this.intrAttivo = intrAttivo;
	}
	public Double getImpOpzioni() {
		return impOpzioni;
	}
	public void setImpOpzioni(Double impOpzioni) {
		this.impOpzioni = impOpzioni;
	}
	public String getEform() {
		return eform;
	}
	public void setEform(String eform) {
		this.eform = eform;
	}
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
	public Long getCountW9moti() {
		return countW9moti;
	}
	public void setCountW9moti(Long countW9moti) {
		this.countW9moti = countW9moti;
	}
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
	public Date getDataVerbaleAppr() {
		return dataVerbaleAppr;
	}
	public void setDataVerbaleAppr(Date dataVerbaleAppr) {
		this.dataVerbaleAppr = dataVerbaleAppr;
	}
	public String getAltreMotivazioni() {
		return altreMotivazioni;
	}
	public void setAltreMotivazioni(String altreMotivazioni) {
		this.altreMotivazioni = altreMotivazioni;
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
	public Double getImportoRideterminatoLavori() {
		return importoRideterminatoLavori;
	}
	public void setImportoRideterminatoLavori(Double importoRideterminatoLavori) {
		this.importoRideterminatoLavori = importoRideterminatoLavori;
	}
	public Double getImportoRideterminatoServizi() {
		return importoRideterminatoServizi;
	}
	public void setImportoRideterminatoServizi(Double importoRideterminatoServizi) {
		this.importoRideterminatoServizi = importoRideterminatoServizi;
	}
	public Double getImportoRideterminatoForniture() {
		return importoRideterminatoForniture;
	}
	public void setImportoRideterminatoForniture(Double importoRideterminatoForniture) {
		this.importoRideterminatoForniture = importoRideterminatoForniture;
	}
	public Double getImportoSubtotale() {
		return importoSubtotale;
	}
	public void setImportoSubtotale(Double importoSubtotale) {
		this.importoSubtotale = importoSubtotale;
	}
	public Double getImportoSicurezza() {
		return importoSicurezza;
	}
	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}
	public Double getImportoProgettazione() {
		return importoProgettazione;
	}
	public void setImportoProgettazione(Double importoProgettazione) {
		this.importoProgettazione = importoProgettazione;
	}
	public Double getImportoNonAssog() {
		return importoNonAssog;
	}
	public void setImportoNonAssog(Double importoNonAssog) {
		this.importoNonAssog = importoNonAssog;
	}
	public Double getImportoComplAppalto() {
		return importoComplAppalto;
	}
	public void setImportoComplAppalto(Double importoComplAppalto) {
		this.importoComplAppalto = importoComplAppalto;
	}
	public Double getImportoDisposizione() {
		return importoDisposizione;
	}
	public void setImportoDisposizione(Double importoDisposizione) {
		this.importoDisposizione = importoDisposizione;
	}
	public Double getImportoComplIntervento() {
		return importoComplIntervento;
	}
	public void setImportoComplIntervento(Double importoComplIntervento) {
		this.importoComplIntervento = importoComplIntervento;
	}
	public String getFlagVariante() {
		return flagVariante;
	}
	public void setFlagVariante(String flagVariante) {
		this.flagVariante = flagVariante;
	}
	public String getQuintoObbligo() {
		return quintoObbligo;
	}
	public void setQuintoObbligo(String quintoObbligo) {
		this.quintoObbligo = quintoObbligo;
	}
	public String getFlagImporti() {
		return flagImporti;
	}
	public void setFlagImporti(String flagImporti) {
		this.flagImporti = flagImporti;
	}
	public String getFlagSicurezza() {
		return flagSicurezza;
	}
	public void setFlagSicurezza(String flagSicurezza) {
		this.flagSicurezza = flagSicurezza;
	}
	public List<LottoDynamicValue> getMotivazioniVariante() {
		return motivazioniVariante;
	}
	public void setMotivazioniVariante(List<LottoDynamicValue> motivazioniVariante) {
		this.motivazioniVariante = motivazioniVariante;
	}
	public String getCigNuovaProc() {
		return cigNuovaProc;
	}
	public void setCigNuovaProc(String cigNuovaProc) {
		this.cigNuovaProc = cigNuovaProc;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public Long getMotivazioniVariantePcp() {
		return motivazioniVariantePcp;
	}
	public void setMotivazioniVariantePcp(Long motivazioniVariantePcp) {
		this.motivazioniVariantePcp = motivazioniVariantePcp;
	}

	public Double getImpModifica() {
		return impModifica;
	}

	public void setImpModifica(Double impModifica) {
		this.impModifica = impModifica;
	}
}
