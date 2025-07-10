package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriImpEntry;

public class FaseCantieriPubbEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Long tipoOpera;
	private Long tipoIntervento;
	private Long tipologiaCostruttiva;
	private Date dataInizio;
	private Long durataPresunta;
	private String indirizzoCantiere;
	private String civico;
	private String codIstatComune;
	private String provincia;
	private Long coordX;
	private Long coordY;
	private Double latitudine;
	private Double longitudine;
	private Long numMaxLavoratori;
	private Long numeroImprese;
	private Long numLavoratoriAutonomi;
	private Long infrastrutturaReteDa;
	private Long infrastrutturaReteA;
	private String descInfrsatrutturaRete;
	private String ulterioreMail;
	private List<FaseCantieriImpEntry> imprese;
	private List<LottoDynamicValue> destinatariNotifica;
	private List<IncarichiProfPubbEntry> incarichiProfessionali;
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

	public Long getTipoOpera() {
		return tipoOpera;
	}

	public void setTipoOpera(Long tipoOpera) {
		this.tipoOpera = tipoOpera;
	}

	public Long getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(Long tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public Long getTipologiaCostruttiva() {
		return tipologiaCostruttiva;
	}

	public void setTipologiaCostruttiva(Long tipologiaCostruttiva) {
		this.tipologiaCostruttiva = tipologiaCostruttiva;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Long getDurataPresunta() {
		return durataPresunta;
	}

	public void setDurataPresunta(Long durataPresunta) {
		this.durataPresunta = durataPresunta;
	}

	public String getIndirizzoCantiere() {
		return indirizzoCantiere;
	}

	public void setIndirizzoCantiere(String indirizzoCantiere) {
		this.indirizzoCantiere = indirizzoCantiere;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Long getCoordX() {
		return coordX;
	}

	public void setCoordX(Long coordX) {
		this.coordX = coordX;
	}

	public Long getCoordY() {
		return coordY;
	}

	public void setCoordY(Long coordY) {
		this.coordY = coordY;
	}

	public Double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}

	public Double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(Double longitudine) {
		this.longitudine = longitudine;
	}

	public Long getNumMaxLavoratori() {
		return numMaxLavoratori;
	}

	public void setNumMaxLavoratori(Long numMaxLavoratori) {
		this.numMaxLavoratori = numMaxLavoratori;
	}

	public Long getNumeroImprese() {
		return numeroImprese;
	}

	public void setNumeroImprese(Long numeroImprese) {
		this.numeroImprese = numeroImprese;
	}

	public Long getNumLavoratoriAutonomi() {
		return numLavoratoriAutonomi;
	}

	public void setNumLavoratoriAutonomi(Long numLavoratoriAutonomi) {
		this.numLavoratoriAutonomi = numLavoratoriAutonomi;
	}

	public Long getInfrastrutturaReteDa() {
		return infrastrutturaReteDa;
	}

	public void setInfrastrutturaReteDa(Long infrastrutturaReteDa) {
		this.infrastrutturaReteDa = infrastrutturaReteDa;
	}

	public Long getInfrastrutturaReteA() {
		return infrastrutturaReteA;
	}

	public void setInfrastrutturaReteA(Long infrastrutturaReteA) {
		this.infrastrutturaReteA = infrastrutturaReteA;
	}

	public String getDescInfrsatrutturaRete() {
		return descInfrsatrutturaRete;
	}

	public void setDescInfrsatrutturaRete(String descInfrsatrutturaRete) {
		this.descInfrsatrutturaRete = descInfrsatrutturaRete;
	}

	public String getUlterioreMail() {
		return ulterioreMail;
	}

	public void setUlterioreMail(String ulterioreMail) {
		this.ulterioreMail = ulterioreMail;
	}

	public List<FaseCantieriImpEntry> getImprese() {
		return imprese;
	}

	public void setImprese(List<FaseCantieriImpEntry> imprese) {
		this.imprese = imprese;
	}

	public List<LottoDynamicValue> getDestinatariNotifica() {
		return destinatariNotifica;
	}

	public void setDestinatariNotifica(List<LottoDynamicValue> destinatariNotifica) {
		this.destinatariNotifica = destinatariNotifica;
	}

	public List<IncarichiProfPubbEntry> getIncarichiProfessionali() {
		return incarichiProfessionali;
	}

	public void setIncarichiProfessionali(List<IncarichiProfPubbEntry> incarichiProfessionali) {
		this.incarichiProfessionali = incarichiProfessionali;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
