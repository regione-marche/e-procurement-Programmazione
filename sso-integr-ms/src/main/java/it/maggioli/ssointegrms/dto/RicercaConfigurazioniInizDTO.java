package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaConfigurazioniInizDTO implements Serializable {

	private static final long serialVersionUID = 5166611094730867948L;

	private List<String> listaSezioni;

	public List<String> getListaSezioni() {
		return listaSezioni;
	}

	public void setListaSezioni(List<String> listaSezioni) {
		this.listaSezioni = listaSezioni;
	}

}
