package pl.patryklubik.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import pl.patryklubik.DateManager;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.model.*;
import pl.patryklubik.view.IconProvider;
import pl.patryklubik.view.ViewFactory;

import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {

    private final DateManager dateManager;
    private final static String INFO_PAGE_PATH = "https://patryklubik.pl/weather-app/";
    private final String PART_OF_DEFAULT_CITY_TIME_ZONE_LABEL_TEXT = "Strefa czasowa: ";
    private final String PRESSURES_LABEL_TEXT = "Ciśnienie: ";
    private final String HUMIDITY_LABEL_TEXT = "Wilgotność: ";
    private final String PRESSURE_UNIT_IN_LABEL_TEXT = " hPa";
    private final String TEMPERATURE_UNIT_IN_LABEL_TEXT = " °C";
    private final String HUMIDITY_UNIT_IN_LABEL_TEXT = "%";

    @FXML
    private Button addSecondLocationButton;

    @FXML
    private VBox weatherForecastForAdditionalCityVBox;

    @FXML
    private VBox todayWeatherForAdditionalCityVBox;

    @FXML
    private Label currentDayOfWeekLabel;
    @FXML
    private Label defaultCityDateLabel;
    @FXML
    private Label defaultCityTimeZone;

    @FXML
    private Label defaultCityName;
    @FXML
    private Label defaultCityPressure;
    @FXML
    private Label defaultCityHumidity;
    @FXML
    private Label defaultCityCurrentTemperature;
    @FXML
    private ImageView defaultCityCurrentDayImage;

    @FXML
    private Label additionalCityName;
    @FXML
    private Label additionalCityPressure;
    @FXML
    private Label additionalCityHumidity;
    @FXML
    private Label additionalCityCurrentTemperature;
    @FXML
    private ImageView additionalCityCurrentDayImage;

    @FXML
    private Label dayOfWeekLabelD0;
    @FXML
    private Label dayOfWeekLabelD1;
    @FXML
    private Label dayOfWeekLabelD2;
    @FXML
    private Label dayOfWeekLabelD3;

    @FXML
    private Label temperatureLabelD0;
    @FXML
    private Label temperatureLabelD1;
    @FXML
    private Label temperatureLabelD2;
    @FXML
    private Label temperatureLabelD3;

    @FXML
    private ImageView weatherImageD0;
    @FXML
    private ImageView weatherImageD1;
    @FXML
    private ImageView weatherImageD2;
    @FXML
    private ImageView weatherImageD3;

    @FXML
    private Label dayOfWeekLabelA0;
    @FXML
    private Label dayOfWeekLabelA1;
    @FXML
    private Label dayOfWeekLabelA2;
    @FXML
    private Label dayOfWeekLabelA3;

    @FXML
    private Label temperatureLabelA0;
    @FXML
    private Label temperatureLabelA1;
    @FXML
    private Label temperatureLabelA2;
    @FXML
    private Label temperatureLabelA3;

    @FXML
    private ImageView weatherImageA0;
    @FXML
    private ImageView weatherImageA1;
    @FXML
    private ImageView weatherImageA2;
    @FXML
    private ImageView weatherImageA3;

    public MainWindowController(CitiesManager citiesManager, ViewFactory viewFactory, String fxmlName) {
        super(citiesManager, viewFactory, fxmlName);
        dateManager = new DateManager();
    }

    @FXML
    void openInfoPageAction(ActionEvent event) {
        try {
            URI uri= new URI(INFO_PAGE_PATH);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setHomeLocationAction(ActionEvent event) {
        viewFactory.showCitySelectionWindow(CityType.DEFAULT);
    }

    @FXML
    void setSecondLocationAction(ActionEvent event) {
        viewFactory.showCitySelectionWindow(CityType.ADDITIONAL);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderTimeData();
        setTodayCityData(CityType.DEFAULT);
        setWeatherDailyForecasts(CityType.DEFAULT);

        if (citiesManager.getCityByType(CityType.ADDITIONAL).getCityName() != null) {
            setTodayCityData(CityType.ADDITIONAL);
            setWeatherDailyForecasts(CityType.ADDITIONAL);
            weatherForecastForAdditionalCityVBox.setVisible(true);
            todayWeatherForAdditionalCityVBox.setVisible(true);
            addSecondLocationButton.setVisible(false);
        }
    }

    private void setHeaderTimeData() {
        CurrentWeather currentWeather = citiesManager.getCityByType(CityType.DEFAULT).getCurrentWeather();
        String countryCode = currentWeather.getCountry();
        String currentDayOfWeek = dateManager.getCurrentDayOfWeek(countryCode).toUpperCase();
        String currentDate = dateManager.getCurrentDate(countryCode);

        defaultCityDateLabel.setText(currentDayOfWeek + "    " + currentDate);
        defaultCityTimeZone.setText(PART_OF_DEFAULT_CITY_TIME_ZONE_LABEL_TEXT + dateManager.getTimeZoneName(countryCode));
    }

    private void setTodayCityData(CityType cityType) {
        CurrentWeather currentWeather = citiesManager.getCityByType(cityType).getCurrentWeather();

        if (cityType == CityType.DEFAULT) {
            fillDefaultTodaysLabels(currentWeather);
        } else if (cityType == CityType.ADDITIONAL) {
            fillAdditionalsTodaysLabels(currentWeather);
        }

        setCurrentWeatherPicture(currentWeather, cityType);
    }

    private void setCurrentWeatherPicture(CurrentWeather currentWeather, CityType cityType) {
        Image weatherPicture = new Image(IconProvider.getDefaultWeatherPictureFilePath());

        if (currentWeather.isItSnowingNow()) {
            weatherPicture = new Image(IconProvider.getSnowWeatherPictureFilePath());
        } else if (currentWeather.isItRainingNow()) {
            weatherPicture = new Image(IconProvider.getRainWeatherPictureFilePath());
        }

        if (cityType == CityType.DEFAULT) {
            defaultCityCurrentDayImage.setImage(weatherPicture);
        } else if (cityType == CityType.ADDITIONAL) {
            additionalCityCurrentDayImage.setImage(weatherPicture);
        }
    }

    private void setWeatherDailyForecasts(CityType cityType) {
        FewDaysForecast fewDaysForecast = citiesManager.getCityByType(cityType).getWeatherFewDaysForecast();
        List<DailyForecast> weatherDailyForecasts = fewDaysForecast.getNextFewDaysForecast();

        if (cityType == CityType.DEFAULT) {
            setWeekdayLabelsForDefaultCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInDefaultCity(weatherDailyForecasts);
            setWeatherImagesForDefaultCity(weatherDailyForecasts);
        } else if (cityType == CityType.ADDITIONAL) {
            setWeekdayLabelsForAdditionalCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInAdditionalCity(weatherDailyForecasts);
            setWeatherImagesForAdditionalCity(weatherDailyForecasts);
        }
    }

    private void fillAdditionalsTodaysLabels(CurrentWeather currentWeather) {
        additionalCityName.setText(citiesManager.getCityByType(CityType.ADDITIONAL).getCityName().toUpperCase());
        additionalCityPressure.setText(PRESSURES_LABEL_TEXT + String.valueOf(currentWeather.getPressure()) + PRESSURE_UNIT_IN_LABEL_TEXT);
        additionalCityHumidity.setText(HUMIDITY_LABEL_TEXT + String.valueOf(currentWeather.getHumidityPercentage()) + HUMIDITY_UNIT_IN_LABEL_TEXT);
        additionalCityCurrentTemperature.setText(Math.round(currentWeather.getTemperature()) + TEMPERATURE_UNIT_IN_LABEL_TEXT);
    }

    private void fillDefaultTodaysLabels(CurrentWeather currentWeather) {
        defaultCityName.setText(citiesManager.getCityByType(CityType.DEFAULT).getCityName().toUpperCase());
        defaultCityPressure.setText(PRESSURES_LABEL_TEXT + String.valueOf(currentWeather.getPressure()) + PRESSURE_UNIT_IN_LABEL_TEXT);
        defaultCityHumidity.setText(HUMIDITY_LABEL_TEXT + String.valueOf(currentWeather.getHumidityPercentage()) + HUMIDITY_UNIT_IN_LABEL_TEXT);
        defaultCityCurrentTemperature.setText(Math.round(currentWeather.getTemperature()) + TEMPERATURE_UNIT_IN_LABEL_TEXT);
    }

    private Image getWeatherPictureForDay(int dayNumber, List<DailyForecast> weatherDailyForecasts) {
        Image weatherPicture = new Image(IconProvider.getDefaultWeatherPictureFilePath());
        DailyForecast dailyForecast = weatherDailyForecasts.get(dayNumber);

        if (dailyForecast.isSnow()) {
            weatherPicture = new Image(IconProvider.getSnowWeatherPictureFilePath());
        } else if (dailyForecast.isRain()) {
            weatherPicture = new Image(IconProvider.getRainWeatherPictureFilePath());
        }

        return weatherPicture;
    }

    private void setWeatherImagesForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        weatherImageA0.setImage(getWeatherPictureForDay(0, weatherDailyForecasts));
        weatherImageA1.setImage(getWeatherPictureForDay(1, weatherDailyForecasts));
        weatherImageA2.setImage(getWeatherPictureForDay(2, weatherDailyForecasts));
        weatherImageA3.setImage(getWeatherPictureForDay(3, weatherDailyForecasts));
    }


    private void setWeatherImagesForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        weatherImageD0.setImage(getWeatherPictureForDay(0, weatherDailyForecasts));
        weatherImageD1.setImage(getWeatherPictureForDay(1, weatherDailyForecasts));
        weatherImageD2.setImage(getWeatherPictureForDay(2, weatherDailyForecasts));
        weatherImageD3.setImage(getWeatherPictureForDay(3, weatherDailyForecasts));
    }

    private void setWeekdayLabelsForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        City additionalCity= citiesManager.getCityByType(CityType.ADDITIONAL);

        dayOfWeekLabelA0.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(0).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(1).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(2).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(3).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
    }

    private void setWeekdayLabelsForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        City defaultCity= citiesManager.getCityByType(CityType.DEFAULT);

        dayOfWeekLabelD0.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(0).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(1).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(2).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(3).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
    }


    private void setForecastTemperatureLabelsInAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        temperatureLabelA0.setText(Math.round(weatherDailyForecasts.get(0).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelA1.setText(Math.round(weatherDailyForecasts.get(1).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelA2.setText(Math.round(weatherDailyForecasts.get(2).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelA3.setText(Math.round(weatherDailyForecasts.get(3).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
    }

    private void setForecastTemperatureLabelsInDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        temperatureLabelD0.setText(Math.round(weatherDailyForecasts.get(0).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelD1.setText(Math.round(weatherDailyForecasts.get(1).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelD2.setText(Math.round(weatherDailyForecasts.get(2).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
        temperatureLabelD3.setText(Math.round(weatherDailyForecasts.get(3).getTemperature()) +
                TEMPERATURE_UNIT_IN_LABEL_TEXT);
    }
}