package it.appaltiecontratti.monitoraggiocontratti.contratti.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Funzioni {

	/**
	 * Restituisce la validita' del CIG
	 *
	 * @param codiceCIG cig
	 * @return validita'(-1:cig non valido, 0:cig, 1:smartcig)
	 */
	public static boolean controlloCIG(final String codiceCIG) {
	  	boolean result = false;
	    
	  	if (StringUtils.isNotEmpty(codiceCIG)) {
	      
		    // Codice CIG trimmato e maiuscolo
				String codCig = new String(codiceCIG.trim().toUpperCase());
				if (codCig.length() == 10) {
					if (!"0000000000".equals(codCig)) {
						String primoCarattere = StringUtils.substring(codCig.trim(), 0, 1).toUpperCase();
						
						if (StringUtils.isNumeric(primoCarattere)) {  // se primo carattere e' tra 0 e 9
							String primi7CaratteriCig = StringUtils.substring(codCig.trim(), 0, 7);
							String ultimi3CaratteriCig = StringUtils.substring(codCig.trim(), 7);
						
							if (StringUtils.isNumeric(primi7CaratteriCig) && StringUtils.isAlphanumeric(ultimi3CaratteriCig)) {
								while (primi7CaratteriCig.indexOf('0') == 0) {
									primi7CaratteriCig = primi7CaratteriCig.substring(1);
								}
								Long numeroCIG =  Long.parseLong(primi7CaratteriCig);
								long oper1 = numeroCIG.longValue();
								long resto = (oper1 * 211) % 4091;
							  
								String strResto = StringUtils.leftPad(Long.toHexString(resto), 3, '0').toUpperCase();
							  
								if (ultimi3CaratteriCig.equalsIgnoreCase(strResto)) {
									result = true;
								}
							}
						} else if (StringUtils.isAlpha(primoCarattere)) {  // se primo carattere e' una lettera (maiuscola)
							Pattern p1 = Pattern.compile("[A-U]{1}");
							Matcher m1 = p1.matcher(primoCarattere);
							if (m1.matches()) {   // se il primo carattere e' una lettera maiuscola tra A e U
								String NNNNNN = StringUtils.substring(codCig.trim(), 1, 7);
								String KKK = StringUtils.substring(codCig.trim(), 7);
								
								if (!"000000".equals(NNNNNN)) {
									Pattern p2 = Pattern.compile("[0-9A-F]{6}"); // NNNNNN e' un numero in esadecimale
									Matcher m2 = p2.matcher(NNNNNN);
								
									Pattern p3 = Pattern.compile("[0-9A-F]{3}"); // KKK e' un numero in esadecimale
									Matcher m3 = p3.matcher(KKK);
								
									if (m2.matches() && m3.matches()) {
										while (NNNNNN.indexOf('0') == 0) {
											NNNNNN = NNNNNN.substring(1);
										}
									
										long oper1 = Long.parseLong(NNNNNN, 16) + (primoCarattere.charAt(0) - 'A' + 1);
										long resto = (oper1 * 211) % 4091;
									
										String strResto = StringUtils.leftPad(Long.toHexString(resto), 3, '0').toUpperCase();
									
										if (KKK.equalsIgnoreCase(strResto)) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
	    }
	    return result;
	  }
	  
}
