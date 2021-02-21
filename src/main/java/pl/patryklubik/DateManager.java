package pl.patryklubik;

import pl.patryklubik.model.Config;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by Patryk Łubik on 29.01.2021.
 */

public class DateManager {

    public static final String FULL_DAY_OF_WEEKDAY_PATTERN = "EEEEEEE";
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String DAY_NUMBER_PATTERN = "dd";

    public String convertTimeToDayOfWeek(long time, String countryCode) {
        return getDateFromPattern(FULL_DAY_OF_WEEKDAY_PATTERN, getTimeZone(countryCode), time);
    }

    public String getCurrentDayOfWeek(String countryCode) {

        return formatDateData(countryCode, FULL_DAY_OF_WEEKDAY_PATTERN);
    }

    public String getCurrentDate(String countryCode) {

        return formatDateData(countryCode, DATE_PATTERN);
    }

    private String formatDateData(String countryCode, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        GregorianCalendar calendar = new GregorianCalendar();

        simpleDateFormat.setTimeZone(getTimeZone(countryCode));

        return simpleDateFormat.format(calendar.getTime());
    }

    private String formatDateData(String countryCode, String pattern, long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        GregorianCalendar calendar = new GregorianCalendar();

        simpleDateFormat.setTimeZone(getTimeZone(countryCode));
        calendar.setTimeInMillis(time);

        return simpleDateFormat.format(calendar.getTime());
    }

    public String getTimeZoneName(String countryCode) {

        return getTimeZone(countryCode).getDisplayName(true, 0);
    }

    private String getDateFromPattern(String pattern, TimeZone timeZone, long time) {
        Locale appLocale = new Locale(Config.getAppLanguage());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,appLocale);
        GregorianCalendar calendar = new GregorianCalendar();

        simpleDateFormat.setTimeZone(timeZone);
        calendar.setTimeInMillis(time);

        return simpleDateFormat.format(calendar.getTime());
    }

    public String getCurrentDayNumberInCountry(String countryCode) {

        return formatDateData(countryCode, DAY_NUMBER_PATTERN);
    }

    public String getDayNumberInCountry(String countryCode, long time) {

        return formatDateData(countryCode, DAY_NUMBER_PATTERN, time);
    }

    private TimeZone getTimeZone(String countryCode) {
        String countryTimeZoneId = com.ibm.icu.util.TimeZone.getAvailableIDs(countryCode)[0];

        return TimeZone.getTimeZone(countryTimeZoneId);
    }
}
