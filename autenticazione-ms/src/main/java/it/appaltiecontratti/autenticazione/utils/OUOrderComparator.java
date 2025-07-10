package it.appaltiecontratti.autenticazione.utils;

import java.util.Comparator;

/**
 * 
 * @author Cristiano Perin
 *
 */
public final class OUOrderComparator {

	private static final String DIGIT_AND_DECIMAL_REGEX = "[^\\d.]";

	private OUOrderComparator() {
		throw new AssertionError("La classe deve rimanere statica");
	}

	public static Comparator<String> createOUOrderComparator() {
		return Comparator.comparingInt(OUOrderComparator::parseStringToNumber);
	}

	private static int parseStringToNumber(String input) {

		final String digitsOnly = input.replaceAll(DIGIT_AND_DECIMAL_REGEX, "");

		if ("".equals(digitsOnly))
			return 0;

		try {
			return Integer.parseInt(digitsOnly);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}
}
