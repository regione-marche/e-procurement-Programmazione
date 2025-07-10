package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.math.BigDecimal;

public class ResponseInizFinanziamenti extends BaseResponse{
	
	private static final long serialVersionUID = -5804777992848156114L;

	private BigDecimal data;

	public BigDecimal getData() {
		return data;
	}

	public void setData(BigDecimal data) {
		this.data = data;
	}

	
}

