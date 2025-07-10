package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author andrea.chinellato
 * */

public class MessageInForm {

	@NotNull
	private Long id;
	private Date dataMessaggio;
	private String oggetto;
	private String corpoMessaggio;
	private Long sysconSender;
	private Long sysconReceiver;


	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Date getDataMessaggio() { return dataMessaggio; }
	public void setDataMessaggio(Date dataMessaggio) { this.dataMessaggio = dataMessaggio; }

	public String getOggetto() { return oggetto; }
	public void setOggetto(String oggetto) { this.oggetto = oggetto; }

	public String getCorpoMessaggio() { return corpoMessaggio; }
	public void setCorpoMessaggio(String corpoMessaggio) { this.corpoMessaggio = corpoMessaggio; }

	public Long getSysconSender() { return sysconSender; }
	public void setSysconSender(Long sysconSender) { this.sysconSender = sysconSender; }

	public Long getSysconReceiver() { return sysconReceiver; }
	public void setSysconReceiver(Long sysconReceiver) { this.sysconReceiver = sysconReceiver; }

}
