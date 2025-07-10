package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoAccorpabileEntry;

public class ResponseLottoAccorpabile extends BaseResponse {

	private static final long serialVersionUID = 1547486693947955533L;

	private LottoAccorpabileEntry data;

	public LottoAccorpabileEntry getData() {
		return data;
	}

	public void setData(LottoAccorpabileEntry data) {
		this.data = data;
	}

}
