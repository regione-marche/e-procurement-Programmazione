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
package it.appaltiecontratti.pubblprogrammi.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Dati allegato.
 *
 * @author Mirco.Franzoni
 */

@ApiModel(description="Nome e numero allegato")
public class AllegatoEntry implements Serializable {
  /**
   * UID.
   */
  private static final long serialVersionUID = -4433185026855332865L;
  
  @ApiModelProperty(value="Numero progressivo documento", hidden = true)  
  private Long nrDoc;
  @ApiModelProperty(value="Titolo del documento", required = true)  
  @Size(max=250, min=1)
  private String titolo;
  @ApiModelProperty(value="URL documentazione")  
  @Size(max=2000)
  private String url;
  @ApiModelProperty(value="Contenuto file allegato", hidden = true)  
  private byte[] file;

    
public void setNrDoc(Long nrDoc) {
	this.nrDoc = nrDoc;
}
public Long getNrDoc() {
	return nrDoc;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}
public String getTitolo() {
	return titolo;
}
public void setUrl(String url) {
	this.url = url;
}
public String getUrl() {
	return url;
}
public void setFile(byte[] allegato) {
	this.file = allegato;
}
public byte[] getFile() {
	return file;
}

}
