package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;

public class FaseSubappaltoPubbEntry extends BaseFasePubblicazioneEntry {

	private Long num;
	private Date dataAuth;
	private String oggetto;
	private Double importoPresunto;
	private Double importoEffettivo;
	private String idCategoria;
	private String idCpv;
	private String codImpresa;
	private String codImpresaAgg;
	private ImpresaFasePubbEntry impresa;
	private ImpresaFasePubbEntry impresaAgg;
	private Long numAppa;
	private List<ImpresaFasePubbEntry> mandanti;
	
	public List<ImpresaFasePubbEntry> getMandanti() {
		return mandanti;
	}

	public void setMandanti(List<ImpresaFasePubbEntry> mandanti) {
		this.mandanti = mandanti;
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

	public ImpresaFasePubbEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaFasePubbEntry impresa) {
		this.impresa = impresa;
	}

	public ImpresaFasePubbEntry getImpresaAgg() {
		return impresaAgg;
	}

	public void setImpresaAgg(ImpresaFasePubbEntry impresaAgg) {
		this.impresaAgg = impresaAgg;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
