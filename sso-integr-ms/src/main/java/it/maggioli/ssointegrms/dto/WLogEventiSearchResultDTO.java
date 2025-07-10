package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

import it.maggioli.ssointegrms.domain.WLogEventi;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WLogEventiSearchResultDTO implements Serializable {

	private static final long serialVersionUID = -1523705913351866592L;

	private List<WLogEventi> eventsList;
	private Long totalCount;

	public List<WLogEventi> getEventsList() {
		return eventsList;
	}

	public void setEventsList(List<WLogEventi> eventsList) {
		this.eventsList = eventsList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
