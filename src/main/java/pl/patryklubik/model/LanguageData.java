package pl.patryklubik.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Create by Patryk Łubik on 23.02.2021.
 */

public class LanguageData {
    private static String LANGUAGE_OF_LABELS;
    private static final Map<String, Map<String, String>> APPLICATION_LANGUAGES_MAP = new HashMap<>();

    private static final Map<String, String> UNITS = Map.of(

            "PRESSURE_UNIT_IN_LABEL_TEXT"," hPa",
            "TEMPERATURE_UNIT_IN_LABEL_TEXT"," °C",
            "HUMIDITY_UNIT_IN_LABEL_TEXT","%");


    private static final Map<String, String> polishPhrases = Stream.of(new String[][] {

        { "APPLICATION_NAME", "WEATHER-APP" },
        { "SUBMIT_TEXT","Zatwierdź" },
        { "CANCEL_TEXT","Anuluj" },
        { "SETTINGS_TEXT","Ustawienia" },

        { "DEFAULT_CITY_HEADER_LABEL_TEXT", "Wprowadź nazwę Twojego miasta:" },
        { "ADDITIONAL_CITY_HEADER_LABEL_TEXT", "Wprowadź nazwę dodatkowego miasta:" },
        { "HOME_LOCATION_TEXT", "Miejsce zamieszkania" },
        { "SECOND_LOCATION_TEXT", "Dodatkowa lokalizacja" },
        { "HELP_MENU_TEXT", "Pomoc" },
        { "ABOUT_APP_TEXT", "O aplikacji" },
        { "ADD_SECOND_LOCATION_BUTTON_TEXT", "DODAJ LOKALIZACJĘ" },

        { "PART_OF_DEFAULT_CITY_TIME_ZONE_LABEL_TEXT", "Strefa czasowa: " },
        { "PRESSURES_LABEL_TEXT", "Ciśnienie: " },
        { "HUMIDITY_LABEL_TEXT", "Wilgotność: " },

        { "ENTER_CITY_NAME_LABEL_TEXT", "WPROWADŹ NAZWĘ MIASTA" },
        { "INVALID_CITY_NAME_LABEL_TEXT", "NIEPOPRAWNA NAZWA MIEJSCOWOŚCI" },
        { "UNEXPECTED_ERROR_LABEL_TEXT", "NIESPODZIEWANY BŁĄD" },

    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private LanguageData(){}

    public static void init(String languageOfLabels) {
        LANGUAGE_OF_LABELS = languageOfLabels;
        APPLICATION_LANGUAGES_MAP.put("pl", polishPhrases);
    }

    public static String getText(String keyLabel) {

        return APPLICATION_LANGUAGES_MAP.get(LANGUAGE_OF_LABELS).get(keyLabel);
    }

    public static String getUnit(String keyLabel) {

        return UNITS.get(keyLabel);
    }
}
