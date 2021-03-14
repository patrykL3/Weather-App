package pl.patryklubik;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Create by Patryk Łubik on 07.03.2021.
 */
class DateManagerTest {

    private final String currentDateToClock = "2021-03-14T16:00:00.00Z";
    private final ZoneId zoneIdToClock = ZoneId.of("Europe/Warsaw");
    private final DateManager dateManager = new DateManager(Clock.fixed(Instant.parse(currentDateToClock), zoneIdToClock));


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
        String currentDay = "niedziela";

        //when
        String currentDayFromApplicationMethod = dateManager.getCurrentDayOfWeek("pl");

        //then
        assertThat(currentDayFromApplicationMethod, equalTo(currentDay));
    }

    @Test
    void getCurrentDateShouldReturnCorrectValue() {

        //given
        String currentDate = "14-03-2021";

        //when
        String currentDateFromApplicationMethod = dateManager.getCurrentDate("pl");

        //then
        assertThat(currentDateFromApplicationMethod, equalTo(currentDate));
    }

    @Test
    void getCurrentDayNumberInCountryShouldReturnCorrectValue() {

        //given
        String currentDayNumber = "14";

        //when
        String currentDayNumberFromApplicationMethod = dateManager.getCurrentDayNumberInCountry("pl");

        //then
        assertThat(currentDayNumberFromApplicationMethod, equalTo(currentDayNumber));


    }

    @Test
    void convertingLongFormatTimeToDayNumberInCountryShouldReturnCorrectValue() {

        //given
        long timeInLong = 1615140000000L;
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
}