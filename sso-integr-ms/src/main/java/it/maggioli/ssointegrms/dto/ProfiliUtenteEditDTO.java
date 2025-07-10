package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ProfiliUtenteEditDTO implements Serializable {

	private static final long serialVersionUID = 2512561893904105714L;

	private List<String> listaProfili;

	public List<String> getListaProfili() {
		return listaProfili;
	}

	public void setListaProfili(List<String> listaProfili) {
		this.listaProfili = listaProfili;
	}

	@Override
	public String toString() {
		return "ProfiliUtenteEditDTO [listaProfili=" + listaProfili + "]";
	}

}
