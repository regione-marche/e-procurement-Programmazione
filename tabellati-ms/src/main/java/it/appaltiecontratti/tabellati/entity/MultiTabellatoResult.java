package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class MultiTabellatoResult extends BaseResponse implements Serializable{

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
	  @ApiModelProperty(value="Mappa dei tabellati.")
	  private Map<String,List<TabellatoIstatEntry>> data = new HashMap<String,List<TabellatoIstatEntry>>();

	  


	  public Map<String, List<TabellatoIstatEntry>> getData() {
		return data;
	}

	public void setData(Map<String, List<TabellatoIstatEntry>> data) {
		this.data = data;
	}

	

	
}
