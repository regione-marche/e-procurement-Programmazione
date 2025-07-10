package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;

public class FaseVarianteIniz {

	private List<LottoDynamicValue> motivazioni;
	private Long countW9moti;
	
	public List<LottoDynamicValue> getMotivazioni() {
		return motivazioni;
	}
	public void setMotivazioni(List<LottoDynamicValue> motivazioni) {
		this.motivazioni = motivazioni;
	}
	public Long getCountW9moti() {
		return countW9moti;
	}
	public void setCountW9moti(Long countW9moti) {
		this.countW9moti = countW9moti;
	}
	
	
	
}
