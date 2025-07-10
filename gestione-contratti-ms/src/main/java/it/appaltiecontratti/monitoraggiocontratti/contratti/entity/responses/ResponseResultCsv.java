package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;

public class ResponseResultCsv extends ResponseResult{

	private Long rowNumber;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	
}
