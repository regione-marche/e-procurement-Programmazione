package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RichiestaAssistenzaOggettoDTO implements Serializable {

	private static final long serialVersionUID = -335352691110054482L;

	private String key;
	private String value;

	public RichiestaAssistenzaOggettoDTO() {
	}

	public RichiestaAssistenzaOggettoDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
