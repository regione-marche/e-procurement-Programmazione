package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

public class SpidUrlData {
	@ApiModelProperty(name = "url", value = "Url login spid")
	private String url;
	@ApiModelProperty(name = "authId", value = "authId user")
	private String authId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}
}
