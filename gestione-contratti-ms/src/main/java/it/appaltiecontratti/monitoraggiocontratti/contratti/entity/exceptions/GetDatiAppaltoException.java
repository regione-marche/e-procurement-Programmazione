package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseAppaltoPcp;

public class GetDatiAppaltoException extends RuntimeException {
	
	private ResponseAppaltoPcp additionalInfo;
	
	public GetDatiAppaltoException(String errorMessage, ResponseAppaltoPcp additionalInfo) {
		super(errorMessage);
		this.additionalInfo = additionalInfo;
	}
    
    public ResponseAppaltoPcp getAdditionalInfo() {
        return additionalInfo;
    }
}