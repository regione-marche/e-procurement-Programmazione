package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

import it.maggioli.ssointegrms.domain.WConfig;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WConfigSearchResultDTO implements Serializable {

	private static final long serialVersionUID = -1523705913351866592L;

	private List<WConfig> configList;
	private Long totalCount;

	public List<WConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<WConfig> configList) {
		this.configList = configList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
