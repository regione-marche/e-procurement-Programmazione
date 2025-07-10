package it.appaltiecontratti.programmi.rl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public String convertDateToString(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

}
