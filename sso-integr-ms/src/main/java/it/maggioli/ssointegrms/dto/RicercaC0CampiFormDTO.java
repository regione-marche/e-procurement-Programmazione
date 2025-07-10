package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaC0CampiFormDTO extends BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = 8725403343648925029L;

	private String searchData;

	public String getSearchData() {
		return searchData;
	}

	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}

}
