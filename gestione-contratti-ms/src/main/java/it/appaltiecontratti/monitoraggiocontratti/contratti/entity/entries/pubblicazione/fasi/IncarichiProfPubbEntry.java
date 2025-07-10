package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class IncarichiProfPubbEntry {

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
	private RupPubbEntry tecnico;

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

	public RupPubbEntry getTecnico() {
		return tecnico;
	}

	public void setTecnico(RupPubbEntry tecnico) {
		this.tecnico = tecnico;
	}

}
