package it.maggioli.ssointegrms.model;

/**
 * @author Cristiano Perin
 */
public class PostFormObject {

	private String jwtToken;

	public PostFormObject(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
