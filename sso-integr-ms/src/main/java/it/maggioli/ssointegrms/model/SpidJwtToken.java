package it.maggioli.ssointegrms.model;

/**
 * Contenuto del token jwt/jwe per l'idp SPID
 * 
 * @author Cristiano Perin
 *
 */
public class SpidJwtToken extends AbstractJwtToken {

	private Integer attrcsidx;
	private String purpose;

	public Integer getAttrcsidx() {
		return attrcsidx;
	}

	public void setAttrcsidx(Integer attrcsidx) {
		this.attrcsidx = attrcsidx;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
