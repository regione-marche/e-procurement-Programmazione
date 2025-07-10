package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

public class ValoreDatoProfilo {

	@ApiModelProperty(name = "valore", value = "Valore di protezione da impostare")
	private boolean valore;
	@ApiModelProperty(name = "ok", value = "Determina se la configurazione e' corrotta")
	private boolean ok;
	@ApiModelProperty(name = "isdefault", value = "Determina se la configurazione e' di default")
	private boolean isdefault;
	@ApiModelProperty(name = "valDefault", value = "Valore di default della configurazione")
	private Long valDefault;
	@ApiModelProperty(name = "okDefault", value = "Determina se la configurazione di default e' corrotta")
	private boolean okDefault;
	@ApiModelProperty(name = "key", value = "Chiave univoca della configurazione")
	private String key;

	public ValoreDatoProfilo(boolean valore, boolean isDefault, boolean ok,
			Long valDefault) {
		this.valore = valore;
		this.ok = ok;
		if (isDefault)
			this.setOkDefault(ok);
		this.valDefault = valDefault;
		this.isdefault = isDefault;
	}

	/**
	 * @return the valore
	 */
	public boolean isValore() {
		return valore;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @return the valDefault
	 */
	public Long getValDefault() {
		return valDefault;
	}

	/**
	 * @return the isdefault
	 */
	public boolean isDefault() {
		return isdefault;
	}

	/**
	 * @param valDefault
	 *            the valDefault to set
	 */
	public void setValDefault(Long valDefault) {
		this.valDefault = valDefault;
	}

	/**
	 * @return the okDefault
	 */
	public boolean isOkDefault() {
		return okDefault;
	}

	/**
	 * @param okDefault
	 *            the okDefault to set
	 */
	public void setOkDefault(boolean okDefault) {
		this.okDefault = okDefault;
	}

	public String toString() {
		return this.isValore() ? "true" : "false";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
