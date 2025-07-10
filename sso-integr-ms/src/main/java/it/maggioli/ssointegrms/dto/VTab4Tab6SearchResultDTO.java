package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

import it.maggioli.ssointegrms.domain.VTab4Tab6;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class VTab4Tab6SearchResultDTO implements Serializable {

	private static final long serialVersionUID = 550328712729230362L;

	private List<VTab4Tab6> tabellatiList;

	private Long totalCount;

	public List<VTab4Tab6> getTabellatiList() {
		return tabellatiList;
	}

	public void setTabellatiList(List<VTab4Tab6> tabellatiList) {
		this.tabellatiList = tabellatiList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
