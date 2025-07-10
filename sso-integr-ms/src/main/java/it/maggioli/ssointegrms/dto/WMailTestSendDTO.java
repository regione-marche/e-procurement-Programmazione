package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailTestSendDTO implements Serializable {

	private static final long serialVersionUID = 5445937565848301439L;

	@NotBlank
	@Size(min = 1, max = 16)
	private String idCfg;
	@NotBlank
	@Size(min = 1, max = 100)
	private String testEmail;

	public String getIdCfg() {
		return idCfg;
	}

	public void setIdCfg(String idCfg) {
		this.idCfg = idCfg;
	}

	public String getTestEmail() {
		return testEmail;
	}

	public void setTestEmail(String testEmail) {
		this.testEmail = testEmail;
	}

	@Override
	public String toString() {
		return "WMailTestSendDTO [idCfg=" + idCfg + ", testEmail=" + testEmail + "]";
	}

}
