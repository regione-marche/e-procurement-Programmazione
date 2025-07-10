package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

import it.maggioli.ssointegrms.domain.C0Campi;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class C0CampiSearchResultDTO implements Serializable {

	private static final long serialVersionUID = 550328712729230362L;

	private List<C0Campi> c0CampiList;

	private Long totalCount;

	public List<C0Campi> getC0CampiList() {
		return c0CampiList;
	}

	public void setC0CampiList(List<C0Campi> c0CampiList) {
		this.c0CampiList = c0CampiList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
