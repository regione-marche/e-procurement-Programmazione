package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class C0CampiDTO implements Serializable {

	private static final long serialVersionUID = 7452028495840672497L;

	@NotBlank
	private String c0c_mne_ber;
	@NotNull
	private Long coc_conta;
	private String c0c_tip;
	private String c0c_chi;
	private String coc_mne_uni;
	private String coc_des;
	private String coc_des_frm;
	private String c0c_fs;
	private String c0c_tab1;
	private String coc_dom;
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

	@Override
	public String toString() {
		return "C0CampiDTO [c0c_mne_ber=" + c0c_mne_ber + ", coc_conta=" + coc_conta + ", c0c_tip=" + c0c_tip
				+ ", c0c_chi=" + c0c_chi + ", coc_mne_uni=" + coc_mne_uni + ", coc_des=" + coc_des + ", coc_des_frm="
				+ coc_des_frm + ", c0c_fs=" + c0c_fs + ", c0c_tab1=" + c0c_tab1 + ", coc_dom=" + coc_dom
				+ ", coc_des_web=" + coc_des_web + "]";
	}

}
