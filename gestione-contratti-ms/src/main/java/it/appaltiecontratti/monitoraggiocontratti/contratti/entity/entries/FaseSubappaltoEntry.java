package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

public class FaseSubappaltoEntry extends FaseBaseEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataAuth;
	private String oggetto;
	private Double importoPresunto;
	private Double importoEffettivo;
	private String idCategoria;
	private String idCpv;
	private String descCpv;
	private String codImpresa;
	private String codImpresaAgg;
	ImpresaEntry impresaAggiudicatrice;
	ImpresaEntry impresaSubappaltatrice;
	private boolean pubblicata;
	List<ImpresaEntry> mandanti;
	private String dgue;
	private Long motivoMancatoSub;
	private Long numAppa;
	private Date dataUltimazione;
	private Long motivoMancataEsec;
	
	
	public Date getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Long getMotivoMancataEsec() {
		return motivoMancataEsec;
	}
	public void setMotivoMancataEsec(Long motivoMancataEsec) {
		this.motivoMancataEsec = motivoMancataEsec;
	}
	public List<ImpresaEntry> getMandanti() {
		return mandanti;
	}
	public void setMandanti(List<ImpresaEntry> mandanti) {
		this.mandanti = mandanti;
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
	public Date getDataAuth() {
		return dataAuth;
	}
	public void setDataAuth(Date dataAuth) {
		this.dataAuth = dataAuth;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Double getImportoPresunto() {
		return importoPresunto;
	}
	public void setImportoPresunto(Double importoPresunto) {
		this.importoPresunto = importoPresunto;
	}
	public Double getImportoEffettivo() {
		return importoEffettivo;
	}
	public void setImportoEffettivo(Double importoEffettivo) {
		this.importoEffettivo = importoEffettivo;
	}
	public String getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getIdCpv() {
		return idCpv;
	}
	public void setIdCpv(String idCpv) {
		this.idCpv = idCpv;
	}
	public String getDescCpv() {
		return descCpv;
	}
	public void setDescCpv(String descCpv) {
		this.descCpv = descCpv;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public String getCodImpresaAgg() {
		return codImpresaAgg;
	}
	public void setCodImpresaAgg(String codImpresaAgg) {
		this.codImpresaAgg = codImpresaAgg;
	}
	public ImpresaEntry getImpresaAggiudicatrice() {
		return impresaAggiudicatrice;
	}
	public void setImpresaAggiudicatrice(ImpresaEntry impresaAggiudicatrice) {
		this.impresaAggiudicatrice = impresaAggiudicatrice;
	}
	public ImpresaEntry getImpresaSubappaltatrice() {
		return impresaSubappaltatrice;
	}
	public void setImpresaSubappaltatrice(ImpresaEntry impresaSubappaltatrice) {
		this.impresaSubappaltatrice = impresaSubappaltatrice;
	}
	public String getDgue() {
		return dgue;
	}
	public void setDgue(String dgue) {
		this.dgue = dgue;
	}
	public Long getMotivoMancatoSub() {
		return motivoMancatoSub;
	}
	public void setMotivoMancatoSub(Long motivoMancatoSub) {
		this.motivoMancatoSub = motivoMancatoSub;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
}
