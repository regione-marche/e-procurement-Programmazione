package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class FullFlussiFase extends FlussiFase {
	
	private Long idFlusso;

	private String tipoInvio;

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

}
