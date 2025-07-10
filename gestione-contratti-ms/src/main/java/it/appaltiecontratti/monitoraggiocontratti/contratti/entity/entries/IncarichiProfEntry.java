package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class IncarichiProfEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Long numInca;
	private String sezione;
	private Long idRuolo;
	private String cigProgEsterna;
	private Date dataAffProgEsterna;
	private Date dataConsProgEsterna;
	private String codiceTecnico;
	private RupEntry tecnico;
	private Long tipoProgettazione;
	private boolean pcp;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getNumInca() {
		return numInca;
	}

	public void setNumInca(Long numInca) {
		this.numInca = numInca;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getCigProgEsterna() {
		return cigProgEsterna;
	}

	public void setCigProgEsterna(String cigProgEsterna) {
		this.cigProgEsterna = cigProgEsterna;
	}

	public Date getDataAffProgEsterna() {
		return dataAffProgEsterna;
	}

	public void setDataAffProgEsterna(Date dataAffProgEsterna) {
		this.dataAffProgEsterna = dataAffProgEsterna;
	}

	public Date getDataConsProgEsterna() {
		return dataConsProgEsterna;
	}

	public void setDataConsProgEsterna(Date dataConsProgEsterna) {
		this.dataConsProgEsterna = dataConsProgEsterna;
	}

	public String getCodiceTecnico() {
		return codiceTecnico;
	}

	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}

	public RupEntry getTecnico() {
		return tecnico;
	}

	public void setTecnico(RupEntry tecnico) {
		this.tecnico = tecnico;
	}

	public Long getTipoProgettazione() {
		return tipoProgettazione;
	}

	public void setTipoProgettazione(Long tipoProgettazione) {
		this.tipoProgettazione = tipoProgettazione;
	}

	public void setPcp(Boolean pcp) { this.pcp = pcp; }

	public Boolean getPcp() { return pcp; }

}
