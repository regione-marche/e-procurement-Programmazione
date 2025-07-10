package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaEventiInizDTO implements Serializable {

	private static final long serialVersionUID = -1726696285690484617L;

	private List<String> listaCodiciEventi;

	public List<String> getListaCodiciEventi() {
		return listaCodiciEventi;
	}

	public void setListaCodiciEventi(List<String> listaCodiciEventi) {
		this.listaCodiciEventi = listaCodiciEventi;
	}

}
