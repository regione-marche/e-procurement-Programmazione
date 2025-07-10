package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class BrowserInfoDTO implements Serializable {

	private static final long serialVersionUID = 5167364373955560321L;

	private String browserName;
	private String fullVersion;
	private String majorVersion;
	private String navigatorAppName;
	private String navigatorUserAgent;

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getFullVersion() {
		return fullVersion;
	}

	public void setFullVersion(String fullVersion) {
		this.fullVersion = fullVersion;
	}

	public String getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getNavigatorAppName() {
		return navigatorAppName;
	}

	public void setNavigatorAppName(String navigatorAppName) {
		this.navigatorAppName = navigatorAppName;
	}

	public String getNavigatorUserAgent() {
		return navigatorUserAgent;
	}

	public void setNavigatorUserAgent(String navigatorUserAgent) {
		this.navigatorUserAgent = navigatorUserAgent;
	}

	@Override
	public String toString() {
		return "BrowserInfoDTO [browserName=" + browserName + ", fullVersion=" + fullVersion + ", majorVersion="
				+ majorVersion + ", navigatorAppName=" + navigatorAppName + ", navigatorUserAgent=" + navigatorUserAgent
				+ "]";
	}

}
