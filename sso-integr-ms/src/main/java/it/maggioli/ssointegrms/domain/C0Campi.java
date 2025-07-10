package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "c0campi")
public class C0Campi implements Serializable {

	private static final long serialVersionUID = -7008122722110947414L;

	@Id
	@NotBlank
	@Size(min = 1, max = 30)
	@Column(name = "c0c_mne_ber")
	private String c0c_mne_ber;

	@NotNull
	@Column(name = "coc_conta")
	private Long coc_conta;

	@Size(min = 0, max = 1)
	@Column(name = "c0c_tip")
	private String c0c_tip;

	@Size(min = 0, max = 1)
	@Column(name = "c0c_chi")
	private String c0c_chi;

	@Size(min = 0, max = 65)
	@Column(name = "coc_mne_uni")
	private String coc_mne_uni;

	@Size(min = 0, max = 60)
	@Column(name = "coc_des")
	private String coc_des;

	@Size(min = 0, max = 20)
	@Column(name = "coc_des_frm")
	private String coc_des_frm;

	@Size(min = 0, max = 10)
	@Column(name = "c0c_fs")
	private String c0c_fs;

	@Size(min = 0, max = 5)
	@Column(name = "c0c_tab1")
	private String c0c_tab1;

	@Size(min = 0, max = 20)
	@Column(name = "coc_dom")
	private String coc_dom;

	@Size(min = 0, max = 100)
	@Column(name = "coc_des_web")
	private String coc_des_web;

	public String getC0c_mne_ber() {
		return c0c_mne_ber;
	}

	public void setC0c_mne_ber(String c0c_mne_ber) {
		this.c0c_mne_ber = c0c_mne_ber;
	}

	public Long getCoc_conta() {
		return coc_conta;
	}

	public void setCoc_conta(Long coc_conta) {
		this.coc_conta = coc_conta;
	}

	public String getC0c_tip() {
		return c0c_tip;
	}

	public void setC0c_tip(String c0c_tip) {
		this.c0c_tip = c0c_tip;
	}

	public String getC0c_chi() {
		return c0c_chi;
	}

	public void setC0c_chi(String c0c_chi) {
		this.c0c_chi = c0c_chi;
	}

	public String getCoc_mne_uni() {
		return coc_mne_uni;
	}

	public void setCoc_mne_uni(String coc_mne_uni) {
		this.coc_mne_uni = coc_mne_uni;
	}

	public String getCoc_des() {
		return coc_des;
	}

	public void setCoc_des(String coc_des) {
		this.coc_des = coc_des;
	}

	public String getCoc_des_frm() {
		return coc_des_frm;
	}

	public void setCoc_des_frm(String coc_des_frm) {
		this.coc_des_frm = coc_des_frm;
	}

	public String getC0c_fs() {
		return c0c_fs;
	}

	public void setC0c_fs(String c0c_fs) {
		this.c0c_fs = c0c_fs;
	}

	public String getC0c_tab1() {
		return c0c_tab1;
	}

	public void setC0c_tab1(String c0c_tab1) {
		this.c0c_tab1 = c0c_tab1;
	}

	public String getCoc_dom() {
		return coc_dom;
	}

	public void setCoc_dom(String coc_dom) {
		this.coc_dom = coc_dom;
	}

	public String getCoc_des_web() {
		return coc_des_web;
	}

	public void setCoc_des_web(String coc_des_web) {
		this.coc_des_web = coc_des_web;
	}

}
