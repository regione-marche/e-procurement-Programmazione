package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class AccountConfigurationDTO implements Serializable {

	private static final long serialVersionUID = -6386084784927154099L;
	
	private Integer durataAccount;
	private Integer intervalloMinimoCambioPassword;
	private Integer delaySecondi;
	private Integer durataBloccoMinuti;
	private Integer maxNumTentativi;
	private Integer durataPassword;

	public Integer getDurataAccount() {
		return durataAccount;
	}

	public void setDurataAccount(Integer durataAccount) {
		this.durataAccount = durataAccount;
	}

	public Integer getIntervalloMinimoCambioPassword() {
		return intervalloMinimoCambioPassword;
	}

	public void setIntervalloMinimoCambioPassword(Integer intervalloMinimoCambioPassword) {
		this.intervalloMinimoCambioPassword = intervalloMinimoCambioPassword;
	}

	public Integer getDelaySecondi() {
		return delaySecondi;
	}

	public void setDelaySecondi(Integer delaySecondi) {
		this.delaySecondi = delaySecondi;
	}

	public Integer getDurataBloccoMinuti() {
		return durataBloccoMinuti;
	}

	public void setDurataBloccoMinuti(Integer durataBloccoMinuti) {
		this.durataBloccoMinuti = durataBloccoMinuti;
	}

	public Integer getMaxNumTentativi() {
		return maxNumTentativi;
	}

	public void setMaxNumTentativi(Integer maxNumTentativi) {
		this.maxNumTentativi = maxNumTentativi;
	}

	public Integer getDurataPassword() {
		return durataPassword;
	}

	public void setDurataPassword(Integer durataPassword) {
		this.durataPassword = durataPassword;
	}

}
