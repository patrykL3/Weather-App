package pl.patryklubik;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Create by Patryk Łubik on 07.03.2021.
 */
class DateManagerTest {

    private DateManager dateManager = new DateManager();

    @Test
    void convertingLongFormatTimeToDayOfWeekShouldReturnCorrectValue() {

        //when
        String dayFromLongFormat = dateManager.convertTimeToDayOfWeek(1615140000000L, "pl");

        //then
        assertThat(dayFromLongFormat, equalTo("niedziela"));
    }

    @Test
    void getCurrentDayOfWeekShouldReturnCorrectValue() {

        //given
        String currentDay = getCurrentDateData("Europe/Warsaw", "EEEE");

        //when
        String currentDayFromApplicationMethod = dateManager.getCurrentDayOfWeek("pl");

        //then
        assertThat(currentDayFromApplicationMethod, equalTo(currentDay));
    }

    @Test
    void getCurrentDateShouldReturnCorrectValue() {

        //given
        String currentDate = getCurrentDateData("Europe/Warsaw", "dd-MM-yyyy");

        //when
        String currentDateFromApplicationMethod = dateManager.getCurrentDate("pl");

        //then
        assertThat(currentDateFromApplicationMethod, equalTo(currentDate));
    }

    @Test
    void getCurrentDayNumberInCountryShouldReturnCorrectValue() {

        //given
        String currentDayNumber = getCurrentDateData("Europe/Warsaw", "dd");

        //when
        String currentDayNumberFromApplicationMethod = dateManager.getCurrentDayNumberInCountry("pl");

        //then
        assertThat(currentDayNumberFromApplicationMethod, equalTo(currentDayNumber));


    }

    @Test
    void convertingLongFormatTimeToDayNumberInCountryShouldReturnCorrectValue() {

        //given
        Long timeInLong = 1615140000000L;
        String polishDayNumberForTimeInLong = "07";

        //when
        String dayFromLongFormat = dateManager.getDayNumberInCountry("pl", timeInLong);

        //then
        assertThat(dayFromLongFormat, equalTo(polishDayNumberForTimeInLong));
    }

    @Test
    void getTimeZoneNameShouldReturnCorrectValue() {

        //given
        ZoneId countryZoneId = ZoneId.of("Europe/Warsaw");
        String correctTimeZoneName = countryZoneId.getDisplayName(TextStyle.SHORT, new Locale("pl"));

        //when
        String timeZoneNameFromAppMethod = dateManager.getTimeZoneName("pl");

        //then
        assertThat(correctTimeZoneName, equalTo(timeZoneNameFromAppMethod));
    }


    private String getCurrentDateData(String timeZone, String pattern) {

        ZonedDateTime zonedDateTimeInCountry = ZonedDateTime.now(ZoneId.of(timeZone));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        return zonedDateTimeInCountry.format(dateTimeFormatter);
    }
}