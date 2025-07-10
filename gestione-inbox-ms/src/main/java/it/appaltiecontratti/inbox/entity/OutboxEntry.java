package it.appaltiecontratti.inbox.entity;

public class OutboxEntry {

	private String idcomun; 
	private String area;
	private Long key01;
	private Long key02;
	private Long key03;
	private Long key04;
	private String cfsa;
	public String getIdcomun() {
		return idcomun;
	}
	public void setIdcomun(String idcomun) {
		this.idcomun = idcomun;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Long getKey01() {
		return key01;
	}
	public void setKey01(Long key01) {
		this.key01 = key01;
	}
	public Long getKey02() {
		return key02;
	}
	public void setKey02(Long key02) {
		this.key02 = key02;
	}
	public Long getKey03() {
		return key03;
	}
	public void setKey03(Long key03) {
		this.key03 = key03;
	}
	public Long getKey04() {
		return key04;
	}
	public void setKey04(Long key04) {
		this.key04 = key04;
	}
	public String getCfsa() {
		return cfsa;
	}
	public void setCfsa(String cfsa) {
		this.cfsa = cfsa;
	}
}
