package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CategoriaLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;

public class LottoEntry extends LottoBaseEntry {

	private String cui;
	private String urlEproc;
	private String tipoSettore;
	private String contrattoEscluso;
	private Long sceltaContraente;
	private Long sceltaContraenteLgs;
	private Long criteriAggiudicazione;
	private String idSchedalocale;
	private String idSchedaSimog;
	private String rup;
	private Double importoTotale;
	private Double importoSicurezza;
	private String esenteCup;
	private String cup;
	private String cpv;
	private String descCpv;
	private String manodopera;
	private String categoriaPrev;
	private String classeCategoriaPrev;
	private String luogoIstat;
	private String luogoNuts;
	private Long prestazioneComprese;
	private List<LottoDynamicValue> modalitaAcquisizione;
	private List<LottoDynamicValue> modalitaTipologiaLavoro;
	private List<LottoDynamicValue> condizioniRicorsoProcNeg;
	private List<CPVSecondario> cpvSecondari;
	private List<CategoriaLotto> ulterioriCategorie;
	private String exsottosoglia;
	private String artE1;
	private String contrattoEscluso161718;
	private String contrattoEsclusoAlleggerito;
	private String esclusioneRegimeSpeciale;
	private Long qualificazioneSa;
	private Long categoriaMerceologica;
	private String flagPrevedeRip;
	private Long durataRinnovi;
	private Long motivoCollegamento;
	private String cigOrigineRip;
	private String flagPnrrPnc;
	private String flagPrevisioneQuota;
	private String flagMisurePreliminari;
	private String listaCup;
	private Date dataScadenzaPagamenti;
	private boolean sottoSoglia;
	private boolean hasAggiudicazione;
	
	

	public Long getQualificazioneSa() {
		return qualificazioneSa;
	}

	public void setQualificazioneSa(Long qualificazioneSa) {
		this.qualificazioneSa = qualificazioneSa;
	}

	public Long getCategoriaMerceologica() {
		return categoriaMerceologica;
	}

	public void setCategoriaMerceologica(Long categoriaMerceologica) {
		this.categoriaMerceologica = categoriaMerceologica;
	}

	public String getFlagPrevedeRip() {
		return flagPrevedeRip;
	}

	public void setFlagPrevedeRip(String flagPrevedeRip) {
		this.flagPrevedeRip = flagPrevedeRip;
	}

	public Long getDurataRinnovi() {
		return durataRinnovi;
	}

	public void setDurataRinnovi(Long durataRinnovi) {
		this.durataRinnovi = durataRinnovi;
	}

	public Long getMotivoCollegamento() {
		return motivoCollegamento;
	}

	public void setMotivoCollegamento(Long motivoCollegamento) {
		this.motivoCollegamento = motivoCollegamento;
	}

	public String getCigOrigineRip() {
		return cigOrigineRip;
	}

	public void setCigOrigineRip(String cigOrigineRip) {
		this.cigOrigineRip = cigOrigineRip;
	}

	public String getFlagPnrrPnc() {
		return flagPnrrPnc;
	}

	public void setFlagPnrrPnc(String flagPnrrPnc) {
		this.flagPnrrPnc = flagPnrrPnc;
	}

	public String getFlagPrevisioneQuota() {
		return flagPrevisioneQuota;
	}

	public void setFlagPrevisioneQuota(String flagPrevisioneQuota) {
		this.flagPrevisioneQuota = flagPrevisioneQuota;
	}

	public String getFlagMisurePreliminari() {
		return flagMisurePreliminari;
	}

	public void setFlagMisurePreliminari(String flagMisurePreliminari) {
		this.flagMisurePreliminari = flagMisurePreliminari;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getUrlEproc() {
		return urlEproc;
	}

	public void setUrlEproc(String urlEproc) {
		this.urlEproc = urlEproc;
	}

	public String getTipoSettore() {
		return tipoSettore;
	}

	public void setTipoSettore(String tipoSettore) {
		this.tipoSettore = tipoSettore;
	}

	public String getContrattoEscluso() {
		return contrattoEscluso;
	}

	public void setContrattoEscluso(String contrattoEscluso) {
		this.contrattoEscluso = contrattoEscluso;
	}

	public Long getSceltaContraente() {
		return sceltaContraente;
	}

	public void setSceltaContraente(Long sceltaContraente) {
		this.sceltaContraente = sceltaContraente;
	}

	public Long getSceltaContraenteLgs() {
		return sceltaContraenteLgs;
	}

	public void setSceltaContraenteLgs(Long sceltaContraenteLgs) {
		this.sceltaContraenteLgs = sceltaContraenteLgs;
	}

	public Long getCriteriAggiudicazione() {
		return criteriAggiudicazione;
	}

	public void setCriteriAggiudicazione(Long criteriAggiudicazione) {
		this.criteriAggiudicazione = criteriAggiudicazione;
	}

	public String getIdSchedalocale() {
		return idSchedalocale;
	}

	public void setIdSchedalocale(String idSchedalocale) {
		this.idSchedalocale = idSchedalocale;
	}

	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}

	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public Double getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}

	public Double getImportoSicurezza() {
		return importoSicurezza;
	}

	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}

	public String getEsenteCup() {
		return esenteCup;
	}

	public void setEsenteCup(String esenteCup) {
		this.esenteCup = esenteCup;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCpv() {
		return cpv;
	}

	public void setCpv(String cpv) {
		this.cpv = cpv;
	}

	public String getDescCpv() {
		return descCpv;
	}

	public void setDescCpv(String descCpv) {
		this.descCpv = descCpv;
	}

	public String getManodopera() {
		return manodopera;
	}

	public void setManodopera(String manodopera) {
		this.manodopera = manodopera;
	}

	public String getCategoriaPrev() {
		return categoriaPrev;
	}

	public void setCategoriaPrev(String categoriaPrev) {
		this.categoriaPrev = categoriaPrev;
	}

	public String getClasseCategoriaPrev() {
		return classeCategoriaPrev;
	}

	public void setClasseCategoriaPrev(String classeCategoriaPrev) {
		this.classeCategoriaPrev = classeCategoriaPrev;
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

	public void setLuogoNuts(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}

	public Long getPrestazioneComprese() {
		return prestazioneComprese;
	}

	public void setPrestazioneComprese(Long prestazioneComprese) {
		this.prestazioneComprese = prestazioneComprese;
	}

	public List<LottoDynamicValue> getModalitaAcquisizione() {
		return modalitaAcquisizione;
	}

	public void setModalitaAcquisizione(List<LottoDynamicValue> modalitaAcquisizione) {
		this.modalitaAcquisizione = modalitaAcquisizione;
	}

	public List<LottoDynamicValue> getModalitaTipologiaLavoro() {
		return modalitaTipologiaLavoro;
	}

	public void setModalitaTipologiaLavoro(List<LottoDynamicValue> modalitaTipologiaLavoro) {
		this.modalitaTipologiaLavoro = modalitaTipologiaLavoro;
	}

	public List<LottoDynamicValue> getCondizioniRicorsoProcNeg() {
		return condizioniRicorsoProcNeg;
	}

	public void setCondizioniRicorsoProcNeg(List<LottoDynamicValue> condizioniRicorsoProcNeg) {
		this.condizioniRicorsoProcNeg = condizioniRicorsoProcNeg;
	}

	public List<CPVSecondario> getCpvSecondari() {
		return cpvSecondari;
	}

	public void setCpvSecondari(List<CPVSecondario> cpvSecondari) {
		this.cpvSecondari = cpvSecondari;
	}

	public List<CategoriaLotto> getUlterioriCategorie() {
		return ulterioriCategorie;
	}

	public void setUlterioriCategorie(List<CategoriaLotto> ulterioriCategorie) {
		this.ulterioriCategorie = ulterioriCategorie;
	}

	public String getExsottosoglia() {
		return exsottosoglia;
	}

	public void setExsottosoglia(String exsottosoglia) {
		this.exsottosoglia = exsottosoglia;
	}

	public String getArtE1() {
		return artE1;
	}

	public void setArtE1(String artE1) {
		this.artE1 = artE1;
	}

	public String getContrattoEscluso161718() {
		return contrattoEscluso161718;
	}

	public void setContrattoEscluso161718(String contrattoEscluso161718) {
		this.contrattoEscluso161718 = contrattoEscluso161718;
	}

	public String getContrattoEsclusoAlleggerito() {
		return contrattoEsclusoAlleggerito;
	}

	public void setContrattoEsclusoAlleggerito(String contrattoEsclusoAlleggerito) {
		this.contrattoEsclusoAlleggerito = contrattoEsclusoAlleggerito;
	}

	public String getEsclusioneRegimeSpeciale() {
		return esclusioneRegimeSpeciale;
	}

	public void setEsclusioneRegimeSpeciale(String esclusioneRegimeSpeciale) {
		this.esclusioneRegimeSpeciale = esclusioneRegimeSpeciale;
	}

	public String getListaCup() {
		return listaCup;
	}

	public void setListaCup(String listaCup) {
		this.listaCup = listaCup;
	}

	public Date getDataScadenzaPagamenti() {
		return dataScadenzaPagamenti;
	}

	public void setDataScadenzaPagamenti(Date dataScadenzaPagamenti) {
		this.dataScadenzaPagamenti = dataScadenzaPagamenti;
	}

	public boolean isSottoSoglia() {
		return sottoSoglia;
	}

	public void setSottoSoglia(boolean sottoSoglia) {
		this.sottoSoglia = sottoSoglia;
	}

	public boolean isHasAggiudicazione() {
		return hasAggiudicazione;
	}

	public void setHasAggiudicazione(boolean hasAggiudicazione) {
		this.hasAggiudicazione = hasAggiudicazione;
	}

}
