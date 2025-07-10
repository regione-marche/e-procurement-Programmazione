package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class PubblicaSmartcigResult {

	private boolean create;
	
	private boolean published;
	
	private Long codGara;

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	
	
}
