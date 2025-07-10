package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati da filtrare nella ricerca delle imprese filtrate per CODIMP presente in W9AGGI o W9SUBA")
public class SingolaImpresaAggiSubaSearchForm {

	@ApiModelProperty(value = "Codice della gara")
	private long codGara;

	@ApiModelProperty(value = "Codice del lotto")
	private long codLotto;

	@ApiModelProperty(value = "Porzione di codice/ragione sociale dell'impresa da cercare")
	private String desc;

	private String stazioneAppaltante;
	
	public long getCodGara() {
		return codGara;
	}

	public void setCodGara(long codGara) {
		this.codGara = codGara;
	}

	public long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(long codLotto) {
		this.codLotto = codLotto;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	

}
