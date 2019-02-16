package moranjani.nanitbday.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.databinding.InverseMethod;

public class DateConverter {

    private static final String DATE_DOTS_FORMAT = "dd.MM.yyyy";

    @InverseMethod("stringToDate")
    public static String dateToString(Long value) {
        if (value == null || value == -1L) {
            return null;
        }
        return getMillisAsString(value);

    }

    public static Long stringToDate(String value) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_DOTS_FORMAT);
        if (value == null) {
            return -1L;
        }
        return stringToMilliseconds(value, format);
    }





    public static String getMillisAsString(long milliSeconds) {
        return geMillisAsStringIWithFormat(milliSeconds, DATE_DOTS_FORMAT);
    }

    public static String geMillisAsStringIWithFormat(long milliSeconds, String dateFormat) {

        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());


    }




    public static long stringToMilliseconds(String formattedDate, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(formattedDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Calendar millisToCalendar(Long millis) {
        Calendar val = Calendar.getInstance();
        if (millis == null || millis < 0) {
            return val;
        }

        val.setTimeInMillis(millis);
        return val;
    }
}
