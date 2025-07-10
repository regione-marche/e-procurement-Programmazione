package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AccorpaMultilottoForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLottoMaster;
	@NotNull
	List<Long> codLottoAccorpati;
	@NotNull
	private Long syscon;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLottoMaster() {
		return codLottoMaster;
	}

	public void setCodLottoMaster(Long codLottoMaster) {
		this.codLottoMaster = codLottoMaster;
	}

	public List<Long> getCodLottoAccorpati() {
		return codLottoAccorpati;
	}

	public void setCodLottoAccorpati(List<Long> codLottoAccorpati) {
		this.codLottoAccorpati = codLottoAccorpati;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

}
