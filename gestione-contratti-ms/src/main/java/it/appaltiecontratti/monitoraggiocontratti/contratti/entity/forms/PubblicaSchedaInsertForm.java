package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;

public class PubblicaSchedaInsertForm implements Serializable {

	private static final long serialVersionUID = 6959277999252906919L;
	
	@NotNull
	@ApiModelProperty(value = "Codice fase", required = true)
	private Long codFase;
	
	@NotNull
	@ApiModelProperty(value = "JSON dell'oggetto scheda", required = true)
	private String oggettoScheda;
	
	@NotNull
	@ApiModelProperty(value = "CIG della gara", required = true)
	private String cig;
	
	@NotNull
	@ApiModelProperty(value = "Tipo di invio", required = true)
	private Long tipoInvio;
	
	@NotNull
	@ApiModelProperty(value = "Modalita' di invio, 1 = verifica, 2 = pubblica", required = true)
	private Long modalitaInvio;
	
	@NotNull
	@ApiModelProperty(value = "Codice fiscale Stazione Appaltante", required = true)
	private String cfStazioneAppaltante;
	
	@ApiModelProperty(value = "Record di SA contenente i dati della gara", required = true)
	private GaraEntry gara;
	
	@ApiModelProperty(value = "Record di SA contenente i dati del lotto", required = true)
	private LottoEntry lotto;


	public Long getCodFase() {
		return codFase;
	}

	public void setCodFase(Long codFase) {
		this.codFase = codFase;
	}

	public String getOggettoScheda() {
		return oggettoScheda;
	}

	public void setOggettoScheda(String oggettoScheda) {
		this.oggettoScheda = oggettoScheda;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public Long getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(Long tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public Long getModalitaInvio() {
		return modalitaInvio;
	}

	public void setModalitaInvio(Long modalitaInvio) {
		this.modalitaInvio = modalitaInvio;
	}

	public String getCfStazioneAppaltante() {
		return cfStazioneAppaltante;
	}

	public void setCfStazioneAppaltante(String cfStazioneAppaltante) {
		this.cfStazioneAppaltante = cfStazioneAppaltante;
	}

	public GaraEntry getGara() {
		return gara;
	}

	public void setGara(GaraEntry gara) {
		this.gara = gara;
	}

	public LottoEntry getLotto() {
		return lotto;
	}

	public void setLotto(LottoEntry lotto) {
		this.lotto = lotto;
	}

	
}
