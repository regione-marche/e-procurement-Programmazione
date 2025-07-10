package it.appaltiecontratti.inbox.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utility {

	private static final String XML_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static XMLGregorianCalendar convertTodayToCalendar() {
		return convertDateToCalendar(new Date());
	}

	public static XMLGregorianCalendar convertDateToCalendar(final Date date) {
		GregorianCalendar calendar = new GregorianCalendar(Locale.ITALY);
		calendar.setTime(date);
		XMLGregorianCalendar xmlDate;
		try {
			xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		return xmlDate;
	}

	public static String convertDateToXMLString(final Date date) {
		DateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT);
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
}
