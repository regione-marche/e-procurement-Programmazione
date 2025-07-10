package it.appaltiecontratti.gestionereportsms.utils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class with utilities methods.
 *
 * @author andrea.chinellato
 *
 * */

public class UtilsMethods {

    /**
     * Maps a given value to one of the specified return values based on string comparison.
     *
     * @param <T> The type of the value to be compared
     * @param value The value to be compared
     * @param compareValue1 The first value to be compared
     * @param returnValue1 The value to be returned if the value is equal to compareValue1
     * @param compareValue2 The second value to be compared
     * @param returnValue2 The value to be returned if the value is equal to compareValue2
     * @return Value if the value matches one of the compare values, otherwise an empty string
     * */
    public static <T> String mapFieldTwoValues(T value, T compareValue1, T returnValue1, T compareValue2, T returnValue2){
        //In caso di valore nullo torno sempre il primo risultato che rappresenta il "No".
        if (value == null) {
            return returnValue1.toString();
        }

        if (value.equals(compareValue1)) {

            return returnValue1.toString();
        }
        else if (value.equals(compareValue2)) {

            return returnValue2.toString();
        }

        return "";
    }

    /**
    * Pattern: Pattern per parole chiave SQL sospette
    * Scopo: La regex riconosce parole chiave potenzialmente dannose.
    * Utilità: Aiuta ad identificare tutte le possibili parole chiave che possono modificare, alterare, eseguire, creare o manipolare il database.
    */
    private static final Pattern MALICIOUS_KEYWORDS_PATTERN = Pattern.compile(
            "\\b(drop|insert|delete|update|upsert|constraint|truncate|rename|alter|exec|execute|sleep|benchmark|xp_cmdshell|load_file)\\b",
            Pattern.CASE_INSENSITIVE
    );

    /**
    * Pattern: Pattern per codifica e offuscamento.
    * Scopo: La regex identifica le parole char, ascii, hex, unhex seguite da 0 o più spazi bianchi e da una parentesi (.
    * Utilità: Riconoscere chiamate a funzioni che iniziano con queste parole chiave.
    */
    private static final Pattern ENCODING_PATTERN = Pattern.compile(
            "\\b(char|ascii|hex|unhex|chr|concat)\\s*\\(",
            Pattern.CASE_INSENSITIVE
    );

    /**
    * Pattern: Pattern per manipolazione di tipi di dati.
    * Scopo: La regex identifica le parole cast e convert seguite da 0 o più spazi bianchi e da un'apertura di parentesi (.
    * Utilità: Riconoscere chiamate a funzioni di cast o convert all'interno di una stringa.
    */
    private static final Pattern DATA_TYPE_MANIPULATION_PATTERN = Pattern.compile(
            "\\b(convert)\\s*\\(",
            Pattern.CASE_INSENSITIVE
    );

    /** Separatori sospetti. Non sono ammessi separatori di query ad esempio ";".
     * Spiegazione:
     * 1) ;+     ------> Si controlla che sia presente un simbolo ; in query.
     */
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile(
            ";+",
            Pattern.CASE_INSENSITIVE
    );

    //Controllo di tutti i Pattern definiti sopra. Nel caso in cui si trovi una corrispondenza, si scarta la query.
    /**
     * Check if the given query is malicious. See all Patterns defined.
     *
     * @param query The query to be checked.
     * @return true if query is safe, false otherwise.
     * */
    public static boolean checkMaliciousInputQuery(String query){

        //Query vuota o nulla
        if(query == null || StringUtils.isEmpty(query)){
            return false;
        }

        String normalizedQuery = query.trim().toLowerCase();

        return !MALICIOUS_KEYWORDS_PATTERN.matcher(normalizedQuery).find() &&
               !ENCODING_PATTERN.matcher(normalizedQuery).find() &&
               !DATA_TYPE_MANIPULATION_PATTERN.matcher(normalizedQuery).find();
    }

    /**
     * Formati di tipo 'Date' accettati.
     * */
    private static final List<String> FORMATS = Arrays.asList(
            "yyyy/MM/dd",
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "yyyy-MM-dd",
            "dd-MM-yyyy"
    );

    /**
     * Format a date as String passed by parameter in dd/MM/yyyy.
     *
     * @param dateStr The date to be formatted.
     * @return The date formatted as String.
     * */
    public static Timestamp parseAndFormatDate(String dateStr) {
        for (String format : FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                //Uso lo strict parsing. In questo modo la data arrivata in input deve matchare per forza con uno dei formati presenti in FORMATS (vedere sopra).
                sdf.setLenient(false);
                // Parsing della data
                Date date = sdf.parse(dateStr);
                // Ritorno un Timestamp in quanto dev'essere accettato dal DB che esegue la query.
                return new Timestamp(date.getTime());
            } catch (ParseException e) {
                // Ignora e prova il prossimo formato
            }
        }
        //Non dovrei mai tornare null. Pensare ad un modo diverso di ritornare in caso di mancanza di match.
        // Se nessun formato corrisponde
        return null;
    }

    /**
     * Remove all comments and separators within the defined query.
     *
     * @param query to be manipulated
     * @return the string without any comment or separator.
     * */
    public static String removeCommentsAndSeparators(String query) {

        if(StringUtils.isEmpty(query)){
            return query;
        }
        // Rimuove commenti multi-linea
        String result = query.replaceAll("/\\*[\\s\\S]*?\\*/", "");

        // Divide la stringa in righe
        String[] lines = result.split("\n");
        StringBuilder sb = new StringBuilder();
        boolean inString = false;
        int parenthesesCount = 0;

        for (String line : lines) {
            // Rimuove commenti in linea
            line = line.replaceAll("--.*$", "");

            // Rimuove tutto ciò che segue il punto e virgola
            //line = line.replaceAll(";.*$", ";");
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '\'') {
                    inString = !inString;
                } else if (!inString) {
                    if (c == '(') {
                        parenthesesCount++;
                    } else if (c == ')') {
                        parenthesesCount--;
                    } else if (c == ';' && parenthesesCount == 0) {
                        // Punto e virgola trovato fuori da stringhe e parentesi
                        // Lo considero un separatore e non lo aggiungo alla stringa
                        break;
                    }
                }

                sb.append(c);
            }

            // Aggiungo la riga modificata al risultato
            sb.append("\n");
        }

        // Rimuovo l'ultimo carattere newline e restituisco il risultato
        return !sb.isEmpty() ? sb.substring(0, sb.length() - 1) : "";
    }

    /**
     * Extracts all substrings enclosed within '#' characters from a given input string.
     *
     * @param input to be searched within
     * @return the string array with all substrings found.
     * */
    public static List<String> extractSubstrings(String input) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }
}
