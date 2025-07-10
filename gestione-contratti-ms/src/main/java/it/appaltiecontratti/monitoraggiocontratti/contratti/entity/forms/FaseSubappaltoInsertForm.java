package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.MandanteEntry;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseSubappaltoInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataAuth;
	@XSSValidation
	private String oggetto;
	private Double importoPresunto;
	private Double importoEffettivo;
	@XSSValidation
	private String idCategoria;
	@XSSValidation
	private String idCpv;
	@XSSValidation
	private String codImpresa;
	@XSSValidation
	private String codImpresaAgg;
	@JsonIgnore
	private Long numAppa;	
	private List<MandanteEntry> mandanti;
	@XSSValidation
	private String dgue;
	private Long motivoMancatoSub;
	
		
	public List<MandanteEntry> getMandanti() {
		return mandanti;
	}
	public void setMandanti(List<MandanteEntry> mandanti) {
		this.mandanti = mandanti;
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
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
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

}
