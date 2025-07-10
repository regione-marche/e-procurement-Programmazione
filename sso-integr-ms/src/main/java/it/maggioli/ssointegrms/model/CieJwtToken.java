package it.maggioli.ssointegrms.model;

/**
 * Contenuto del token jwt/jwe per l'idp CIE
 * 
 * @author Cristiano Perin
 *
 */
public class CieJwtToken extends AbstractJwtToken {

	private Integer attrcsidx;
	private String env;

	public Integer getAttrcsidx() {
		return attrcsidx;
	}

	public void setAttrcsidx(Integer attrcsidx) {
		this.attrcsidx = attrcsidx;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}
