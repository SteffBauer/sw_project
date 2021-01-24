package de.othr.sw.bank.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static boolean isBefore(Date firstDate, Date secondDate) {
        try {
            firstDate = dateFormatter.parse(dateFormatter.format(firstDate));
            secondDate = dateFormatter.parse(dateFormatter.format(secondDate));

            return firstDate.before(secondDate);
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date formatDate(Date date){
        try {
            return dateFormatter.parse(dateFormatter.format(date));
        } catch (ParseException e) {
            return date;
        }
    }

    public static boolean isOldEnough(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -12);
        Date dateBefore = formatDate(cal.getTime());
       return isBefore(date,dateBefore);
    }
}
