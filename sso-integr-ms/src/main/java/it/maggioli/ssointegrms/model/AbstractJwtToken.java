package it.maggioli.ssointegrms.model;

/**
 * Classe astratta per la definizione del body del token JWT
 * 
 * @author Cristiano Perin
 *
 */
public abstract class AbstractJwtToken {

	private String uuid;
	private Long timestamp;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
