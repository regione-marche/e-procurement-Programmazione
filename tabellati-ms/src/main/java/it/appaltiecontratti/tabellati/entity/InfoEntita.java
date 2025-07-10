package it.appaltiecontratti.tabellati.entity;

import java.util.List;

public class InfoEntita {

	private String schema;
	private String descrizioneSchema;
	private String entita;
	private String descrizioneEntita;
	private List<String> chiaveEntita;
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getDescrizioneSchema() {
		return descrizioneSchema;
	}
	public void setDescrizioneSchema(String descrizioneSchema) {
		this.descrizioneSchema = descrizioneSchema;
	}
	public String getEntita() {
		return entita;
	}
	public void setEntita(String entita) {
		this.entita = entita;
	}
	public String getDescrizioneEntita() {
		return descrizioneEntita;
	}
	public void setDescrizioneEntita(String descrizioneEntita) {
		this.descrizioneEntita = descrizioneEntita;
	}
	public List<String> getChiaveEntita() {
		return chiaveEntita;
	}
	public void setChiaveEntita(List<String> chiaveEntita) {
		this.chiaveEntita = chiaveEntita;
	}
}
