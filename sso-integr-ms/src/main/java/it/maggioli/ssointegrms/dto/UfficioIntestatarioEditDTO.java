package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UfficioIntestatarioEditDTO implements Serializable {

	private static final long serialVersionUID = -8360446270333801999L;

	private List<String> listaUfficiIntestatari;

	public List<String> getListaUfficiIntestatari() {
		return listaUfficiIntestatari;
	}

	public void setListaUfficiIntestatari(List<String> listaUfficiIntestatari) {
		this.listaUfficiIntestatari = listaUfficiIntestatari;
	}

	@Override
	public String toString() {
		return "UfficioIntestatarioEditDTO [listaUfficiIntestatari=" + listaUfficiIntestatari + "]";
	}

}
