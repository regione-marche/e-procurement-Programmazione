package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullDettaglioAttoEntry;

public class ResponseAttiAssociatiLotto extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	List<FullDettaglioAttoEntry> data;

	public List<FullDettaglioAttoEntry> getData() {
		return data;
	}

	public void setData(List<FullDettaglioAttoEntry> data) {
		this.data = data;
	}

}
