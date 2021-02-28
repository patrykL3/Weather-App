package pl.patryklubik;

import pl.patryklubik.model.Config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Create by Patryk Łubik on 29.01.2021.
 */

public class DateManager {

    public static final String FULL_DAY_OF_WEEKDAY_PATTERN = "EEEE";
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String DAY_NUMBER_PATTERN = "dd";


    public String convertTimeToDayOfWeek(long time, String countryCode) {

        return formatDateData(countryCode, FULL_DAY_OF_WEEKDAY_PATTERN, time);
    }

    public String getCurrentDayOfWeek(String countryCode) {

        return formatDateData(countryCode, FULL_DAY_OF_WEEKDAY_PATTERN);
    }

    public String getCurrentDate(String countryCode) {

        return formatDateData(countryCode, DATE_PATTERN);
    }

    public String getCurrentDayNumberInCountry(String countryCode) {

        return formatDateData(countryCode, DAY_NUMBER_PATTERN);
    }

    public String getDayNumberInCountry(String countryCode, long time) {

        return formatDateData(countryCode, DAY_NUMBER_PATTERN, time);
    }

    private String formatDateData(String countryCode, String pattern, long time) {

        ZoneId countryZoneId = ZoneId.of(getTimeZone(countryCode));
        LocalDateTime setDateTimeInZone = LocalDateTime.ofInstant(Instant.ofEpochMilli (time), countryZoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        return dateTimeFormatter.format(setDateTimeInZone);
    }

    private String formatDateData(String countryCode, String pattern) {

        ZonedDateTime zonedDateTimeInCountry = ZonedDateTime.now(ZoneId.of(getTimeZone(countryCode)));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        return zonedDateTimeInCountry.format(dateTimeFormatter);
    }

    public String getTimeZoneName(String countryCode) {

        ZoneId countryZoneId = ZoneId.of(getTimeZone(countryCode));

        return countryZoneId.getDisplayName(TextStyle.SHORT, new Locale(Config.getAppLanguage()));
    }

    private String getTimeZone(String countryCode) {

        return com.ibm.icu.util.TimeZone.getAvailableIDs(countryCode)[0];
    }
}