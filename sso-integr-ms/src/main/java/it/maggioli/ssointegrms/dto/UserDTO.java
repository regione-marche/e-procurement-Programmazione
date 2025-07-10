package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 3496336115888190078L;

	private Long syscon;
	private String username;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String email;
	private boolean disabilitato;
	private boolean ldap;
	private Integer ufficioAppartenenza;
	private Integer categoria;
	private List<String> opzioniUtente;
	private Date dataScadenzaAccount;
	private Date dataUltimoAccesso;
	private String sysab3;
	private String sysabg;
	private boolean deletable;
	private List<String> ufficiIntestatari;

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDisabilitato() {
		return disabilitato;
	}

	public void setDisabilitato(boolean disabilitato) {
		this.disabilitato = disabilitato;
	}

	public boolean isLdap() {
		return ldap;
	}

	public void setLdap(boolean ldap) {
		this.ldap = ldap;
	}

	public Integer getUfficioAppartenenza() {
		return ufficioAppartenenza;
	}

	public void setUfficioAppartenenza(Integer ufficioAppartenenza) {
		this.ufficioAppartenenza = ufficioAppartenenza;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public List<String> getOpzioniUtente() {
		return opzioniUtente;
	}

	public void setOpzioniUtente(List<String> opzioniUtente) {
		this.opzioniUtente = opzioniUtente;
	}

	public Date getDataScadenzaAccount() {
		return dataScadenzaAccount;
	}

	public void setDataScadenzaAccount(Date dataScadenzaAccount) {
		this.dataScadenzaAccount = dataScadenzaAccount;
	}

	public Date getDataUltimoAccesso() {
		return dataUltimoAccesso;
	}

	public void setDataUltimoAccesso(Date dataUltimoAccesso) {
		this.dataUltimoAccesso = dataUltimoAccesso;
	}

	public String getSysab3() {
		return sysab3;
	}

	public void setSysab3(String sysab3) {
		this.sysab3 = sysab3;
	}

	public String getSysabg() {
		return sysabg;
	}

	public void setSysabg(String sysabg) {
		this.sysabg = sysabg;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public List<String> getUfficiIntestatari() {
		return ufficiIntestatari;
	}

	public void setUfficiIntestatari(List<String> ufficiIntestatari) {
		this.ufficiIntestatari = ufficiIntestatari;
	}

	@Override
	public String toString() {
		return "UserDTO [syscon=" + syscon + ", username=" + username + ", codiceFiscale=" + codiceFiscale + ", nome="
				+ nome + ", cognome=" + cognome + ", email=" + email + ", disabilitato=" + disabilitato + ", ldap="
				+ ldap + ", ufficioAppartenenza=" + ufficioAppartenenza + ", categoria=" + categoria
				+ ", opzioniUtente=" + opzioniUtente + ", dataScadenzaAccount=" + dataScadenzaAccount
				+ ", dataUltimoAccesso=" + dataUltimoAccesso + ", sysab3=" + sysab3 + ", sysabg=" + sysabg
				+ ", deletable=" + deletable + "]";
	}

}
