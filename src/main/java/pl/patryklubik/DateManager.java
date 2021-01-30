package pl.patryklubik;

import com.github.prominence.openweathermap.api.constants.Language;
import pl.patryklubik.model.Config;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by Patryk Łubik on 29.01.2021.
 */

public class DateManager {


    public String convertTimeToDate(long time, String countryCode) {
        String currentDatePattern = "dd-MM-yyyy";

        return getDateFromPattern(currentDatePattern, getTimeZone(countryCode), time);
    }

    public String convertTimeToDayOfWeek(long time, String countryCode) {
        String dayOfWeekPattern = "EEEEEEE";

        return getDateFromPattern(dayOfWeekPattern, getTimeZone(countryCode), time);
    }

    public String getTimeZoneName(String countryCode) {
        String timeZoneName = getTimeZone(countryCode).getDisplayName(true, 0);

        return timeZoneName;
    }

    private String getDateFromPattern(String pattern, TimeZone timeZone, long time) {
        Locale appLocale = new Locale(Config.getAppLanguage());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,appLocale);
        GregorianCalendar calendar = new GregorianCalendar();

        simpleDateFormat.setTimeZone(timeZone);
        calendar.setTimeInMillis(time);

        return simpleDateFormat.format(calendar.getTime());
    }


    private TimeZone getTimeZone(String countryCode) {
        String countryTimeZoneId = com.ibm.icu.util.TimeZone.getAvailableIDs(countryCode)[0];
        TimeZone countryTimeZone = TimeZone.getTimeZone(countryTimeZoneId);

        return countryTimeZone;
    }

}
