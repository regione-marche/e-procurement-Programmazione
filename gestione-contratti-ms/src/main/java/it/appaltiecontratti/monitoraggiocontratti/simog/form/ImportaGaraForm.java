package it.appaltiecontratti.monitoraggiocontratti.simog.form;

public class ImportaGaraForm {

	private String cfRup;
	private String cfSA;
	private String cigIdAvGara;
	private Long syscon;
	private boolean sendMail;	
	private String cfImpersonato;
	private String loaImpersonato;
	private String idpImpersonato;
	private String codProfilo;
	private String codein;
	

	public String getCfImpersonato() {
		return cfImpersonato;
	}

	public void setCfImpersonato(String cfImpersonato) {
		this.cfImpersonato = cfImpersonato;
	}

	public String getLoaImpersonato() {
		return loaImpersonato;
	}

	public void setLoaImpersonato(String loaImpersonato) {
		this.loaImpersonato = loaImpersonato;
	}

	public String getIdpImpersonato() {
		return idpImpersonato;
	}

	public void setIdpImpersonato(String idpImpersonato) {
		this.idpImpersonato = idpImpersonato;
	}

	public String getCfRup() {
		return cfRup;
	}

	public void setCfRup(String cfRup) {
		this.cfRup = cfRup;
	}

	public String getCfSA() {
		return cfSA;
	}

	public void setCfSA(String cfSA) {
		this.cfSA = cfSA;
	}

	public String getCigIdAvGara() {
		return cigIdAvGara;
	}

	public void setCigIdAvGara(String cigIdAvGara) {
		this.cigIdAvGara = cigIdAvGara;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public boolean isSendMail() {
		return sendMail;
	}

	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	public String getCodProfilo() {
		return codProfilo;
	}

	public void setCodProfilo(String codProfilo) {
		this.codProfilo = codProfilo;
	}

	public String getCodein() {
		return codein;
	}

	public void setCodein(String codein) {
		this.codein = codein;
	}

}
