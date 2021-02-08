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
import pl.patryklubik.view.ViewFactory;

import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {

    private CitiesManager citiesManager;
    private DateManager dateManager;
    private final String INFO_PAGE_PATH = "https://patryklubik.pl/weather-app/";

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
    private Label dayOfWeekLabelD1;
    @FXML
    private Label dayOfWeekLabelD2;
    @FXML
    private Label dayOfWeekLabelD3;
    @FXML
    private Label dayOfWeekLabelD4;

    @FXML
    private Label temperatureLabelD1;
    @FXML
    private Label temperatureLabelD2;
    @FXML
    private Label temperatureLabelD3;
    @FXML
    private Label temperatureLabelD4;

    @FXML
    private ImageView weatherImageD1;
    @FXML
    private ImageView weatherImageD2;
    @FXML
    private ImageView weatherImageD3;
    @FXML
    private ImageView weatherImageD4;

    @FXML
    private Label dayOfWeekLabelA1;
    @FXML
    private Label dayOfWeekLabelA2;
    @FXML
    private Label dayOfWeekLabelA3;
    @FXML
    private Label dayOfWeekLabelA4;

    @FXML
    private Label temperatureLabelA1;
    @FXML
    private Label temperatureLabelA2;
    @FXML
    private Label temperatureLabelA3;
    @FXML
    private Label temperatureLabelA4;

    @FXML
    private ImageView weatherImageA1;
    @FXML
    private ImageView weatherImageA2;
    @FXML
    private ImageView weatherImageA3;
    @FXML
    private ImageView weatherImageA4;


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


    public MainWindowController(CitiesManager citiesManager, ViewFactory viewFactory, String fxmlName) {
        super(citiesManager, viewFactory, fxmlName);
        this.citiesManager = citiesManager;
        dateManager = new DateManager();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderTimeData();
        setTodayCityData(CityType.DEFAULT);
        setWeatherDailyForecasts(CityType.DEFAULT);

        if(citiesManager.getCityByType(CityType.ADDITIONAL).getCityName() != null) {
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
        defaultCityTimeZone.setText("Strefa czasowa: " + dateManager.getTimeZoneName(countryCode));
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
        Image weatherPicture = new Image("/default.png");

        if(currentWeather.isItSnowingNow()) {
            weatherPicture = new Image("/snow.png");
        } else if (currentWeather.isItRainingNow()) {
            weatherPicture = new Image("/rain.png");
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

        if(cityType == CityType.DEFAULT) {
            setWeekdayLabelsForDefaultCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInDefaultCity(weatherDailyForecasts);
            setWeatherImagesForDefaultCity(weatherDailyForecasts);
        } else if(cityType == CityType.ADDITIONAL) {
            setWeekdayLabelsForAdditionalCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInAdditionalCity(weatherDailyForecasts);
            setWeatherImagesForAdditionalCity(weatherDailyForecasts);
        }
    }

    private void fillAdditionalsTodaysLabels(CurrentWeather currentWeather) {
        additionalCityName.setText(citiesManager.getCityByType(CityType.ADDITIONAL).getCityName().toUpperCase());
        additionalCityPressure.setText("Ciśnienie: " + String.valueOf(currentWeather.getPressure()) + " hPa");
        additionalCityHumidity.setText("Wilgotność: " + String.valueOf(currentWeather.getHumidityPercentage()) + "%");
        additionalCityCurrentTemperature.setText(Math.round(currentWeather.getTemperature()) + " °C");
    }

    private void fillDefaultTodaysLabels(CurrentWeather currentWeather) {
        defaultCityName.setText(citiesManager.getCityByType(CityType.DEFAULT).getCityName().toUpperCase());
        defaultCityPressure.setText("Ciśnienie: " + String.valueOf(currentWeather.getPressure()) + " hPa");
        defaultCityHumidity.setText("Wilgotność: " + String.valueOf(currentWeather.getHumidityPercentage()) + "%");
        defaultCityCurrentTemperature.setText(Math.round(currentWeather.getTemperature()) + " °C");
    }

    private Image getWeatherPictureForDay(int dayNumber, List<DailyForecast> weatherDailyForecasts) {
        Image weatherPicture = new Image("/default.png");

        if(weatherDailyForecasts.get(dayNumber).isSnow()) {
            weatherPicture = new Image("/snow.png");
        } else if (weatherDailyForecasts.get(dayNumber).isRain()) {
            weatherPicture = new Image("/rain.png");
        }

        return weatherPicture;
    }

    private void setWeatherImagesForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;

        weatherImageA1.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageA2.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageA3.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageA4.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
    }


    private void setWeatherImagesForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;

        weatherImageD1.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD2.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD3.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD4.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
    }

    private void setWeekdayLabelsForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;
        City additionalCity= citiesManager.getCityByType(CityType.ADDITIONAL);

        dayOfWeekLabelA1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelA4.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                additionalCity.getCountryCode()).toUpperCase());
    }

    private void setWeekdayLabelsForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;
        City defaultCity= citiesManager.getCityByType(CityType.DEFAULT);

        dayOfWeekLabelD1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
        dayOfWeekLabelD4.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()).toUpperCase());
    }


    private void setForecastTemperatureLabelsInAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;

        temperatureLabelA1.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelA2.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelA3.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelA4.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
    }

    private void setForecastTemperatureLabelsInDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;

        temperatureLabelD1.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelD2.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelD3.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
        temperatureLabelD4.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
    }
}