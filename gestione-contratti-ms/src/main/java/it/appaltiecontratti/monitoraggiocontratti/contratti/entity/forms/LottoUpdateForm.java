package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CategoriaLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;

public class LottoUpdateForm {

	@NotNull
	private Long codGara;
	private Long codLotto;
	@XSSValidation
	private String cig;
	@XSSValidation
	private String oggetto;
	private Long situazione;
	@XSSValidation
	private String tipologia;
	private Double importoNetto;
	@XSSValidation
	private String cui;
	@XSSValidation
	private String urlEproc;
	private Long numLotto;

	@XSSValidation
	private String tipoSettore;
	@XSSValidation
	private String contrattoEscluso;
	private Long sceltaContraente;
	private Long sceltaContraenteLgs;
	private Long criteriAggiudicazione;
	@XSSValidation
	private String idSchedalocale;
	@XSSValidation
	private String rup;
	private Double importoTotale;
	private Double importoSicurezza;
	@XSSValidation
	private String esenteCup;
	@XSSValidation
	private String cup;
	@XSSValidation
	private String cpv;
	@XSSValidation
	private String manodopera;
	@XSSValidation
	private String categoriaPrev;
	@XSSValidation
	private String classeCategoriaPrev;
	@XSSValidation
	private String luogoIstat;
	@XSSValidation
	private String luogoNuts;
	private Long prestazioneComprese;
	private List<LottoDynamicValue> modalitaAcquisizione;
	private List<LottoDynamicValue> modalitaTipologiaLavoro;
	private List<LottoDynamicValue> condizioniRicorsoProcNeg;
	private List <CPVSecondario> cpvSecondari;
	private List <CategoriaLotto> ulterioriCategorie;
	@XSSValidation
	private String exsottosoglia;
	private Date dataConsultaGara;
	@XSSValidation
	private String lotIdentifier;
	@XSSValidation
	private String daExport;
	


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

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Long getSituazione() {
		return situazione;
	}

	public void setSituazione(Long situazione) {
		this.situazione = situazione;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Double getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(Double importoNetto) {
		this.importoNetto = importoNetto;
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

	public Date getDataConsultaGara() {
		return dataConsultaGara;
	}

	public void setDataConsultaGara(Date dataConsultaGara) {
		this.dataConsultaGara = dataConsultaGara;
	}

	public String getLotIdentifier() {
		return lotIdentifier;
	}

	public void setLotIdentifier(String lotIdentifier) {
		this.lotIdentifier = lotIdentifier;
	}

	public String getDaExport() {
		return daExport;
	}

	public void setDaExport(String daExport) {
		this.daExport = daExport;
	}
	
}
