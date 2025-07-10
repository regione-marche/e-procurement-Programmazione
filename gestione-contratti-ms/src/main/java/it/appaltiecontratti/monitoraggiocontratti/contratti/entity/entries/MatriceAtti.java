package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;
import java.util.Map;

public class MatriceAtti {
	
	private List<String> columns;
	
	private List<Map<String, String>> rows;
	
	private Map<String, List<StatoCig>> attiLotti;
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<Map<String, String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}

	public Map<String, List<StatoCig>> getAttiLotti() {
		return attiLotti;
	}

	public void setAttiLotti(Map<String, List<StatoCig>> attiLotti) {
		this.attiLotti = attiLotti;
	}

}
