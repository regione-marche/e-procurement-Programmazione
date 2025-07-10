package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WQuartzEditDTO implements Serializable {

	private static final long serialVersionUID = 4929664156714108932L;

	private String codApp;
	private String beanId;
	private String seconds;
	private String minutes;
	private String hours;
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;
	private String year;

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "WQuartzEditDTO [codApp=" + codApp + ", beanId=" + beanId + ", seconds=" + seconds + ", minutes="
				+ minutes + ", hours=" + hours + ", dayOfMonth=" + dayOfMonth + ", month=" + month + ", dayOfWeek="
				+ dayOfWeek + ", year=" + year + "]";
	}

}
