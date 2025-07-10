package it.appaltiecontratti.monitoraggiocontratti.simog.form;

public class ListaCollaborazioniRequest extends BaseConsultaAnacRequest {

	private String data;
	private String usernameSimog;
	private String passwordSimog;
	
	public String getUsernameSimog() {
		return usernameSimog;
	}

	public void setUsernameSimog(String usernameSimog) {
		this.usernameSimog = usernameSimog;
	}

	public String getPasswordSimog() {
		return passwordSimog;
	}

	public void setPasswordSimog(String passwordSimog) {
		this.passwordSimog = passwordSimog;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
