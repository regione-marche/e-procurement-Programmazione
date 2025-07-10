package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseImportaGara;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaComunicaAppaltoType;

public class ResponseImportaGaraPcp extends ResponseImportaGara{

	private static final long serialVersionUID = -8616663721395876079L;
		
	private SchedaComunicaAppaltoType scheda;
	private String idAppalto;
		
	public SchedaComunicaAppaltoType getScheda() {
		return scheda;
	}
	public void setScheda(SchedaComunicaAppaltoType scheda) {
		this.scheda = scheda;
	}
	public String getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(String idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	
}
