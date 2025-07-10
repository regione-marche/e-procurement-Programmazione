package it.appaltiecontratti.monitoraggiocontratti.simog.form;

public class ImportaSmartCigInsertForm extends BaseConsultaAnacRequest{

	private String cig;
	
	private String codiceStazioneAppaltante;
	
	private boolean saveCredentials;
	
	private Long syscon;

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCodiceStazioneAppaltante() {
		return codiceStazioneAppaltante;
	}

	public void setCodiceStazioneAppaltante(String codiceStazioneAppaltante) {
		this.codiceStazioneAppaltante = codiceStazioneAppaltante;
	}
	
	public boolean isSaveCredentials() {
		return saveCredentials;
	}

	public void setSaveCredentials(boolean saveCredentials) {
		this.saveCredentials = saveCredentials;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	
	
	
}
