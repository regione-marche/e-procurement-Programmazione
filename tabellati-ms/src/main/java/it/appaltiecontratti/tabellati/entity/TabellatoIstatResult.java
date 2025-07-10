package it.appaltiecontratti.tabellati.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


	public class TabellatoIstatResult extends BaseResponse implements Serializable{

	  /**
	   * UID.
	   */
	  private static final long serialVersionUID = 1227874189423727845L;
	  
	  /** Codice di errore nel caso tabellato non trovato */
	  public static final String ERROR_VALIDATION = "errore validazione parametri ricerca";
	  /** Codice di errore nel caso tabellato non trovato */
	  public static final String ERROR_NOT_FOUND = "value not found";
	  /** Codice indicante un errore inaspettato. */
	  public static final String ERROR_UNEXPECTED = "unexpected-error";

	  private List<TabellatoIstatEntry> data = new ArrayList<TabellatoIstatEntry>();

	  /**
	   * @return Ritorna data.
	   */
	  public List<TabellatoIstatEntry> getData() {
	    return data;
	  }

	  /**
	   * @param data
	   *        data da settare internamente alla classe.
	   */
	  public void setData(List<TabellatoIstatEntry> data) {
	    this.data = data;
	  }

	 

	}
