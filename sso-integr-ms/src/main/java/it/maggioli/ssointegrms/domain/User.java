package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "usrsys")
public class User implements Serializable {

	private static final long serialVersionUID = -5945138874235891846L;

	@Id
	@NotNull
	@Column(name = "syscon")
	private Long syscon;

	@Size(min = 0, max = 161)
	@Column(name = "sysute")
	private String sysute;

	@Size(min = 0, max = 61)
	@Column(name = "sysnom")
	private String nomeUtente;

	@Size(min = 0, max = 31)
	@Column(name = "syspwd")
	private String password;

	@Size(min = 0, max = 500)
	@Column(name = "syspwbou")
	private String syspwbou;

	@Size(min = 0, max = 16)
	@Column(name = "syscf")
	private String codiceFiscale;

	@Size(min = 0, max = 60)
	@Column(name = "syslogin")
	private String login;

	@Size(min = 0, max = 1)
	@Column(name = "sysdisab")
	private String disabilitato;

	@Column(name = "flag_ldap")
	private Integer ldap;

	@Size(min = 0, max = 100)
	@Column(name = "email")
	private String email;

	@Column(name = "sysuffapp")
	private Integer ufficioAppartenenza;

	@Column(name = "syscateg")
	private Integer categoria;

	@Column(name = "sysscad")
	private Date dataScadenzaAccount;

	@Column(name = "sysultacc")
	private Date dataUltimoAccesso;

	@Size(min = 0, max = 6)
	@Column(name = "sysab3")
	private String sysab3;

	@Size(min = 0, max = 5)
	@Column(name = "sysabg")
	private String sysabg;

	@ManyToMany
	@JoinTable( //
			name = "usr_ein", //
			joinColumns = @JoinColumn(name = "syscon"), //
			inverseJoinColumns = @JoinColumn(name = "codein"))
	private Set<Uffint> uffints = new HashSet<Uffint>();

	@ManyToMany
	@JoinTable( //
			name = "w_accpro", //
			joinColumns = @JoinColumn(name = "id_account", referencedColumnName = "syscon"), //
			inverseJoinColumns = @JoinColumn(name = "cod_profilo"))
	private Set<Profilo> profili = new HashSet<Profilo>();

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getSysute() {
		return sysute;
	}

	public void setSysute(String sysute) {
		this.sysute = sysute;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSyspwbou() {
		return syspwbou;
	}

	public void setSyspwbou(String syspwbou) {
		this.syspwbou = syspwbou;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDisabilitato() {
		return disabilitato;
	}

	public void setDisabilitato(String disabilitato) {
		this.disabilitato = disabilitato;
	}

	public Integer getLdap() {
		return ldap;
	}

	public void setLdap(Integer ldap) {
		this.ldap = ldap;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<Uffint> getUffints() {
		return uffints;
	}

	public void setUffints(Set<Uffint> uffints) {
		this.uffints = uffints;
	}

	public Set<Profilo> getProfili() {
		return profili;
	}

	public void setProfili(Set<Profilo> profili) {
		this.profili = profili;
	}
}
