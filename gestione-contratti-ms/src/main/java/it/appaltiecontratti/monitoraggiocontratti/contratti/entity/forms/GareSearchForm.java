package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

public class GareSearchForm extends BaseSearchForm {
	
	private String oggetto;
	private Long  modalitaRealizzazione;	
	private String numGara;	
	private Long situazioneGara;	
	private boolean archiviate;
	private String cigLotto;
	private String oggettoLotto;	
	private Long proceduraContraenteLotto;	
	private String tipoAppaltoLotto;	
	private String cupLotto;
	private String stazioneAppaltante;
	private Long syscon;
	private String cfTecnico;
	private String searchString;
	private List<String> rup;
	private Date dataDa;
	private Date dataA;
	private Double importoGaraDa;
	private Double importoGaraA;
	private Double importoLottoDa;
	private Double importoLottoA;
	private boolean cfNull;
	
	
	
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Long getModalitaRealizzazione() {
		return modalitaRealizzazione;
	}
	public void setModalitaRealizzazione(Long modalitaRealizzazione) { this.modalitaRealizzazione = modalitaRealizzazione; }

	public String getNumGara() {
		return numGara;
	}
	public void setNumGara(String numGara) {
		this.numGara = numGara;
	}

	public Long getSituazioneGara() {
		return situazioneGara;
	}
	public void setSituazioneGara(Long situazioneGara) {
		this.situazioneGara = situazioneGara;
	}

	public boolean isArchiviate() {
		return archiviate;
	}
	public void setArchiviate(boolean archiviate) {
		this.archiviate = archiviate;
	}

	public String getCigLotto() {
		return cigLotto;
	}
	public void setCigLotto(String cigLotto) {
		this.cigLotto = cigLotto;
	}

	public String getOggettoLotto() {
		return oggettoLotto;
	}
	public void setOggettoLotto(String oggettoLotto) {
		this.oggettoLotto = oggettoLotto;
	}

	public Long getProceduraContraenteLotto() {
		return proceduraContraenteLotto;
	}
	public void setProceduraContraenteLotto(Long proceduraContraenteLotto) { this.proceduraContraenteLotto = proceduraContraenteLotto; }

	public String getTipoAppaltoLotto() {
		return tipoAppaltoLotto;
	}
	public void setTipoAppaltoLotto(String tipoAppaltoLotto) {
		this.tipoAppaltoLotto = tipoAppaltoLotto;
	}

	public String getCupLotto() {
		return cupLotto;
	}
	public void setCupLotto(String cupLotto) {
		this.cupLotto = cupLotto;
	}

	public String getStazioneAppaltante() { return stazioneAppaltante; }
	public void setStazioneAppaltante(String stazioneAppaltante) { this.stazioneAppaltante = stazioneAppaltante; }

	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<String> getRup() {
		return rup;
	}
	public void setRup(List<String> rup) {
		this.rup = rup;
	}

	public Date getDataDa() {
		return dataDa;
	}
	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}
	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Double getImportoGaraDa() {
		return importoGaraDa;
	}
	public void setImportoGaraDa(Double importoGaraDa) {
		this.importoGaraDa = importoGaraDa;
	}

	public Double getImportoGaraA() {
		return importoGaraA;
	}
	public void setImportoGaraA(Double importoGaraA) {
		this.importoGaraA = importoGaraA;
	}

	public Double getImportoLottoDa() {
		return importoLottoDa;
	}
	public void setImportoLottoDa(Double importoLottoDa) {
		this.importoLottoDa = importoLottoDa;
	}

	public Double getImportoLottoA() {
		return importoLottoA;
	}
	public void setImportoLottoA(Double importoLottoA) {
		this.importoLottoA = importoLottoA;
	}

	public String getCfTecnico() {
		return cfTecnico;
	}
	public void setCfTecnico(String cfTecnico) {
		this.cfTecnico = cfTecnico;
	}

	public boolean isCfNull() {
		return cfNull;
	}
	public void setCfNull(boolean cfNull) {
		this.cfNull = cfNull;
	}
}
