package it.appaltiecontratti.inbox.entity.contratti;

import java.util.List;

public class LottoEntry extends LottoBaseEntry {

	private String cui;
	private String urlEproc;
	private Long numLotto;
	private String tipoSettore;
	private String contrattoEscluso;
	private Long sceltaContraente;
	private Long sceltaContraenteLgs;
	private Long criteriAggiudicazione;
	private String idSchedalocale;
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
	private List <CPVSecondario> cpvSecondari;
	private List <CategoriaLotto> ulterioriCategorie;
	private String exsottosoglia;
	private String artE1;
	private String contrattoEscluso161718;

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

	public Long getNumLotto() {
		return numLotto;
	}

	public void setNumLotto(Long numLotto) {
		this.numLotto = numLotto;
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
	
	
}
