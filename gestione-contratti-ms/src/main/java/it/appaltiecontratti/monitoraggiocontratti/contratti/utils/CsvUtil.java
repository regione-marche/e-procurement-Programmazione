package it.appaltiecontratti.monitoraggiocontratti.contratti.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

public class CsvUtil {

	private static final char CSV_DELIMITER = ';';
	private static final char CSV_QUOTE = '"';
	private static final String CSV_QUOTE_STR = String.valueOf(CSV_QUOTE);
	private static final char[] CSV_SEARCH_CHARS = new char[] { CSV_DELIMITER,
			CSV_QUOTE, CharUtils.CR, CharUtils.LF };

	public static String escapeCsv(String str) throws Exception {
		if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
			return str;
		}
		try {
			StringWriter writer = new StringWriter();
			escapeCsv(writer, str);
			return writer.toString();
		} catch (IOException ioe) {
			// this should never ever happen while writing to a StringWriter
			throw new Exception(ioe);
		}
	}

	/**
	 * <p>
	 * Writes a <code>String</code> value for a CSV column enclosed in double
	 * quotes, if required.
	 * </p>
	 * 
	 * <p>
	 * If the value contains a comma, newline or double quote, then the String
	 * value is written enclosed in double quotes.
	 * </p>
	 * </p>
	 * 
	 * <p>
	 * Any double quote characters in the value are escaped with another double
	 * quote.
	 * </p>
	 * 
	 * <p>
	 * If the value does not contain a comma, newline or double quote, then the
	 * String value is written unchanged (null values are ignored).
	 * </p>
	 * </p>
	 * 
	 * see <a
	 * href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a>
	 * and <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
	 * 
	 * @param str
	 *            the input CSV column String, may be null
	 * @param out
	 *            Writer to write input string to, enclosed in double quotes if
	 *            it contains a comma, newline or double quote
	 * @throws IOException
	 *             if error occurs on underlying Writer
	 * @since 2.4
	 */
	public static void escapeCsv(Writer out, String str) throws IOException {
		if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
			if (str != null) {
				out.write(str);
			}
			return;
		}
		out.write(CSV_QUOTE);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == CSV_QUOTE) {
				out.write(CSV_QUOTE); // escape double quote
			}
			out.write(c);
		}
		out.write(CSV_QUOTE);
	}

	/**
	 * <p>
	 * Returns a <code>String</code> value for an unescaped CSV column.
	 * </p>
	 * 
	 * <p>
	 * If the value is enclosed in double quotes, and contains a comma, newline
	 * or double quote, then quotes are removed.
	 * </p>
	 * 
	 * <p>
	 * Any double quote escaped characters (a pair of double quotes) are
	 * unescaped to just one double quote.
	 * </p>
	 * 
	 * <p>
	 * If the value is not enclosed in double quotes, or is and does not contain
	 * a comma, newline or double quote, then the String value is returned
	 * unchanged.
	 * </p>
	 * </p>
	 * 
	 * see <a
	 * href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a>
	 * and <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
	 * 
	 * @param str
	 *            the input CSV column String, may be null
	 * @return the input String, with enclosing double quotes removed and
	 *         embedded double quotes unescaped, <code>null</code> if null
	 *         string input
	 * @throws Exception 
	 * @since 2.4
	 */
	public static String unescapeCsv(String str) throws Exception {
		if (str == null) {
			return null;
		}
		try {
			StringWriter writer = new StringWriter();
			unescapeCsv(writer, str);
			return writer.toString();
		} catch (IOException ioe) {
			// this should never ever happen while writing to a StringWriter
			throw new Exception(ioe);
		}
	}

	/**
	 * <p>
	 * Returns a <code>String</code> value for an unescaped CSV column.
	 * </p>
	 * 
	 * <p>
	 * If the value is enclosed in double quotes, and contains a comma, newline
	 * or double quote, then quotes are removed.
	 * </p>
	 * 
	 * <p>
	 * Any double quote escaped characters (a pair of double quotes) are
	 * unescaped to just one double quote.
	 * </p>
	 * 
	 * <p>
	 * If the value is not enclosed in double quotes, or is and does not contain
	 * a comma, newline or double quote, then the String value is returned
	 * unchanged.
	 * </p>
	 * </p>
	 * 
	 * see <a
	 * href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a>
	 * and <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
	 * 
	 * @param str
	 *            the input CSV column String, may be null
	 * @param out
	 *            Writer to write the input String to, with enclosing double
	 *            quotes removed and embedded double quotes unescaped,
	 *            <code>null</code> if null string input
	 * @throws IOException
	 *             if error occurs on underlying Writer
	 * @since 2.4
	 */
	public static void unescapeCsv(Writer out, String str) throws IOException {
		if (str == null) {
			return;
		}
		if (str.length() < 2) {
			out.write(str);
			return;
		}
		if (str.charAt(0) != CSV_QUOTE
				|| str.charAt(str.length() - 1) != CSV_QUOTE) {
			out.write(str);
			return;
		}

		// strip quotes
		String quoteless = str.substring(1, str.length() - 1);

		if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS)) {
			// deal with escaped quotes; ie) ""
			str = StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR,
					CSV_QUOTE_STR);
		}

		out.write(str);
	}

}
