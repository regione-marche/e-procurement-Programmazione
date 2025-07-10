package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RichiestaAssistenzaInitDTO implements Serializable {

	private static final long serialVersionUID = -931003690207594648L;

	private boolean assistenzaAttiva;
	private boolean assistenzaEmail;
	private boolean assistenzaTelefonica;
	private String allegatoFileSize;
	private List<RichiestaAssistenzaOggettoDTO> listaOggetti;

	public boolean isAssistenzaAttiva() {
		return assistenzaAttiva;
	}

	public void setAssistenzaAttiva(boolean assistenzaAttiva) {
		this.assistenzaAttiva = assistenzaAttiva;
	}

	public boolean isAssistenzaEmail() {
		return assistenzaEmail;
	}

	public void setAssistenzaEmail(boolean assistenzaEmail) {
		this.assistenzaEmail = assistenzaEmail;
	}

	public boolean isAssistenzaTelefonica() {
		return assistenzaTelefonica;
	}

	public void setAssistenzaTelefonica(boolean assistenzaTelefonica) {
		this.assistenzaTelefonica = assistenzaTelefonica;
	}

	public String getAllegatoFileSize() {
		return allegatoFileSize;
	}

	public void setAllegatoFileSize(String allegatoFileSize) {
		this.allegatoFileSize = allegatoFileSize;
	}

	public List<RichiestaAssistenzaOggettoDTO> getListaOggetti() {
		return listaOggetti;
	}

	public void setListaOggetti(List<RichiestaAssistenzaOggettoDTO> listaOggetti) {
		this.listaOggetti = listaOggetti;
	}

}
