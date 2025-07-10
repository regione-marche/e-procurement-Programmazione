package it.appaltiecontratti.tabellati.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;
import java.util.List;

public class MessageForm {

	private Long id;

	private Date dataMessaggio;
	@XSSValidation
	private String oggetto;
	@XSSValidation
	private String corpoMessaggio;
	private Long sysconSender;
	List<Long> sysconReceivers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataMessaggio() {
		return dataMessaggio;
	}
	public void setDataMessaggio(Date dataMessaggio) {
		this.dataMessaggio = dataMessaggio;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpoMessaggio() {
		return corpoMessaggio;
	}
	public void setCorpoMessaggio(String corpoMessaggio) {
		this.corpoMessaggio = corpoMessaggio;
	}
	public Long getSysconSender() {
		return sysconSender;
	}
	public void setSysconSender(Long sysconSender) {
		this.sysconSender = sysconSender;
	}
	public List<Long> getSysconReceivers() {
		return sysconReceivers;
	}
	public void setSysconReceivers(List<Long> sysconReceivers) {
		this.sysconReceivers = sysconReceivers;
	}
}
