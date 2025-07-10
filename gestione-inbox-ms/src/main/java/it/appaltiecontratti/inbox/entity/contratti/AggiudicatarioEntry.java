package it.appaltiecontratti.inbox.entity.contratti;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Dati relativi ad un aggiudicatario")
public class AggiudicatarioEntry implements Serializable {

	private static final long serialVersionUID = -4433185026855332865L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice della gara", hidden = true)
	private Long idGara;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Numero progressivo pubblicazione", hidden = true)
	private Long numeroPubblicazione;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Numero progressivo aggiudicatario", hidden = true)
	private Long numeroAggiudicatario;

	@ApiModelProperty(value = "Tipologia del soggetto aggiudicatario", required = true)
	private Long tipoAggiudicatario;
	@ApiModelProperty(value = "Ruolo nell'associazione")
	private Long ruolo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Collegamento all'archivio delle imprese", hidden = true)
	@Size(max = 10)
	private String codiceImpresa;

	@ApiModelProperty(value = "Dati dell'Operatore economico", required = true)
	private ImpresaPubbEntry impresa;

	@ApiModelProperty(value = "Numero raggruppamento")
	private Long idGruppo;

	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	public Long getIdGara() {
		return idGara;
	}

	public void setNumeroPubblicazione(Long numeroPubblicazione) {
		this.numeroPubblicazione = numeroPubblicazione;
	}

	public Long getNumeroPubblicazione() {
		return numeroPubblicazione;
	}

	public void setNumeroAggiudicatario(Long numeroAggiudicatario) {
		this.numeroAggiudicatario = numeroAggiudicatario;
	}

	public Long getNumeroAggiudicatario() {
		return numeroAggiudicatario;
	}

	public void setTipoAggiudicatario(Long tipoAggiudicatario) {
		this.tipoAggiudicatario = tipoAggiudicatario;
	}

	public Long getTipoAggiudicatario() {
		return tipoAggiudicatario;
	}

	public void setRuolo(Long ruolo) {
		this.ruolo = ruolo;
	}

	public Long getRuolo() {
		return ruolo;
	}

	public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

	public String getCodiceImpresa() {
		return codiceImpresa;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setImpresa(ImpresaPubbEntry impresa) {
		this.impresa = impresa;
	}

	public ImpresaPubbEntry getImpresa() {
		return impresa;
	}
}
