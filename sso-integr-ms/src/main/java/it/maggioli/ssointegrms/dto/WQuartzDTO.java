package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WQuartzDTO implements Serializable {

	private static final long serialVersionUID = 634623444957918649L;

	private String codApp;
	private String beanId;
	private String expression;
	private String seconds;
	private String minutes;
	private String hours;
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;
	private String year;
	private String descrizione;

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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "WQuartzDTO [codApp=" + codApp + ", beanId=" + beanId + ", expression=" + expression + ", seconds="
				+ seconds + ", minutes=" + minutes + ", hours=" + hours + ", dayOfMonth=" + dayOfMonth + ", month="
				+ month + ", dayOfWeek=" + dayOfWeek + ", year=" + year + ", descrizione=" + descrizione + "]";
	}

}
