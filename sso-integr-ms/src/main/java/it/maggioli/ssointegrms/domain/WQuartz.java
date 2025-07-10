package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_quartz")
public class WQuartz implements Serializable {

	private static final long serialVersionUID = -9124187612592246076L;

	@EmbeddedId
	private WQuartzPK id;

	@Size(min = 0, max = 300)
	@Column(name = "cron_expression")
	private String expression;

	@Size(min = 0, max = 30)
	@Column(name = "cron_seconds")
	private String seconds;

	@Size(min = 0, max = 30)
	@Column(name = "cron_minutes")
	private String minutes;

	@Size(min = 0, max = 30)
	@Column(name = "cron_hours")
	private String hours;

	@Size(min = 0, max = 30)
	@Column(name = "cron_day_of_month")
	private String dayOfMonth;

	@Size(min = 0, max = 30)
	@Column(name = "cron_month")
	private String month;

	@Size(min = 0, max = 30)
	@Column(name = "cron_day_of_week")
	private String dayOfWeek;

	@Size(min = 0, max = 30)
	@Column(name = "cron_year")
	private String year;

	@Size(min = 0, max = 2000)
	@Column(name = "descrizione")
	private String descrizione;

	public WQuartzPK getId() {
		return id;
	}

	public void setId(WQuartzPK id) {
		this.id = id;
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

}
