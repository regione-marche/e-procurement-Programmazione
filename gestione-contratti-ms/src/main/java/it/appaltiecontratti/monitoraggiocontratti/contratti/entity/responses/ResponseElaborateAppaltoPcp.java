package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.Map;

public class ResponseElaborateAppaltoPcp {

	private Long codGara;
	private Map<String,Long> cigCodLotMap;
	private Map<String,String> cigLotIdMap;
		
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Map<String, Long> getCigCodLotMap() {
		return cigCodLotMap;
	}
	public void setCigCodLotMap(Map<String, Long> cigCodLotMap) {
		this.cigCodLotMap = cigCodLotMap;
	}
	public Map<String,String> getCigLotIdMap() {
		return cigLotIdMap;
	}
	public void setCigLotIdMap(Map<String,String> cigLotIdMap) {
		this.cigLotIdMap = cigLotIdMap;
	}
	
	
	
}
