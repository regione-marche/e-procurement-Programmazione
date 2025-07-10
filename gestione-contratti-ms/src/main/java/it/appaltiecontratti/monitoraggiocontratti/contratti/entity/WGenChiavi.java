package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

public class WGenChiavi {

	private String tabella;
	private Long chiave;

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public Long getChiave() {
		return chiave;
	}

	public void setChiave(Long chiave) {
		this.chiave = chiave;
	}

	@Override
	public String toString() {
		return "WgenChiavi [" + (tabella != null ? "tabella=" + tabella + ", " : "")
				+ (chiave != null ? "chiave=" + chiave : "") + "]";
	}

}
