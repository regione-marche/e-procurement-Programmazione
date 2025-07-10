/*
 * Created on 01/giu/2017
 *
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.inbox.entity.avvisi;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.contratti.AllegatoEntry;

/**
 * Allegato avvisi.
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description = "Nome e numero allegato")
public class AllegatoAvvisiEntry extends AllegatoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@ApiModelProperty(hidden = true)
	private String codiceSA;

	@ApiModelProperty(hidden = true)
	private Long id;

	@ApiModelProperty(hidden = true)
	private Long codiceSistema;

	@JsonIgnore
	@ApiModelProperty(value = "Contenuto file allegato")
	private String file_allegato;

	public void setCodiceSA(String codiceSA) {
		this.codiceSA = codiceSA;
	}

	@XmlTransient
	public String getCodiceSA() {
		return codiceSA;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setCodiceSistema(Long codiceSistema) {
		this.codiceSistema = codiceSistema;
	}

	@XmlTransient
	public Long getCodiceSistema() {
		return codiceSistema;
	}

	public String getFile_allegato() {
		return file_allegato;
	}

	public void setFile_allegato(String file_allegato) {
		this.file_allegato = file_allegato;
	}

}
