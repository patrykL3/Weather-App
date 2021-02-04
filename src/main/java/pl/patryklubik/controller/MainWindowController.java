package pl.patryklubik.controller;


import com.github.prominence.openweathermap.api.model.response.Weather;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.fxd.Duplicator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.patryklubik.DateManager;
import pl.patryklubik.WeatherAppManager;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.DailyForecast;
import pl.patryklubik.model.FewDaysForecast;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.util.*;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {

    private WeatherAppManager weatherAppManager;
    private DateManager dateManager;

    @FXML
    private VBox myVBox;

    @FXML
    private AnchorPane anch;

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
    private Label additionalCityName;

    @FXML
    private Label additionalCityPressure;

    @FXML
    private Label additionalCityHumidity;

    @FXML
    private Label additionalCityCurrentTemperature;

    @FXML
    private ImageView defaultCityCurrentDayImage;

    @FXML
    private Label dayOfWeekLabelD1;
    @FXML
    private Label dayOfWeekLabelD2;
    @FXML
    private Label dayOfWeekLabelD3;
    @FXML
    private Label dayOfWeekLabelD4;
    @FXML
    private Label dayOfWeekLabelD5;

    @FXML
    private Label temperatureLabelD1;
    @FXML
    private Label temperatureLabelD2;
    @FXML
    private Label temperatureLabelD3;
    @FXML
    private Label temperatureLabelD4;
    @FXML
    private Label temperatureLabelD5;

    @FXML
    private ImageView weatherImageD1;
    @FXML
    private ImageView weatherImageD2;
    @FXML
    private ImageView weatherImageD3;
    @FXML
    private ImageView weatherImageD4;
    @FXML
    private ImageView weatherImageD5;



    @FXML
    void openInfoPageAction(ActionEvent event) {

    }

    @FXML
    void optionsAction(ActionEvent event) {

    }

    @FXML
    void setHomeLocationAction(ActionEvent event) {
        viewFactory.showCitySelectionWindow(CityType.DEFAULT);
    }

    @FXML
    void setSecondLocationAction(ActionEvent event) {

    }


    public MainWindowController(WeatherAppManager weatherAppManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherAppManager, viewFactory, fxmlName);
        this.weatherAppManager = weatherAppManager;
        dateManager = new DateManager();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderTimeData();
        setTodayCityData(CityType.DEFAULT);
        setWeatherDailyForecasts(CityType.DEFAULT);

        if(weatherAppManager.getCityByType(CityType.ADDITIONAL).getCityName() != null) {
            setTodayCityData(CityType.ADDITIONAL);
        }

    }

    private void setWeatherDailyForecasts(CityType cityType) {
        FewDaysForecast fewDaysForecast = weatherAppManager.getCityByType(cityType).getWeatherFewDaysForecast();
        List<DailyForecast> weatherDailyForecasts = fewDaysForecast.getNextFewDaysForecast();

        if(cityType == CityType.DEFAULT) {
            setWeekdayLabelsForDefaultCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInDefaultCity(weatherDailyForecasts);
            setWeatherImagesForDefaultCity(weatherDailyForecasts);
        } else if(cityType == CityType.ADDITIONAL) {
        } else if(cityType == CityType.ADDITIONAL) {
            setWeekdayLabelsForAdditionalCity(weatherDailyForecasts);
            setForecastTemperatureLabelsInAdditionalCity(weatherDailyForecasts);
            setWeatherImagesForAdditionalCity(weatherDailyForecasts);
        }
    }

    private void setWeatherImagesForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {

    }


    private void setWeatherImagesForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;

        weatherImageD1.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD2.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD3.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD4.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));
        weatherImageD5.setImage(getWeatherPictureForDay(dayNumber++, weatherDailyForecasts));

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


    private void setWeekdayLabelsForAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;
        City additionalCity= weatherAppManager.getCityByType(CityType.ADDITIONAL);
//        dayOfWeekLabelA1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
//                additionalCity.getCountryCode()));
//        dayOfWeekLabelA2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
//                additionalCity.getCountryCode()));
//        dayOfWeekLabelA3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
//                additionalCity.getCountryCode()));
//        dayOfWeekLabelA4.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
//                additionalCity.getCountryCode()));
//        dayOfWeekLabelA5.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
//                additionalCity.getCountryCode()));
    }

    private void setWeekdayLabelsForDefaultCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;
        City defaultCity= weatherAppManager.getCityByType(CityType.DEFAULT);

        dayOfWeekLabelD1.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()));
        dayOfWeekLabelD2.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()));
        dayOfWeekLabelD3.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()));
        dayOfWeekLabelD4.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()));
        dayOfWeekLabelD5.setText(dateManager.convertTimeToDayOfWeek(weatherDailyForecasts.get(dayNumber++).getTime(),
                defaultCity.getCountryCode()));
    }


    private void setForecastTemperatureLabelsInAdditionalCity(List<DailyForecast> weatherDailyForecasts) {
        int dayNumber = 0;
//        temperatureLabelA1.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
//                " °C");
//        temperatureLabelA2.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
//                " °C");
//        temperatureLabelA3.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
//                " °C");
//        temperatureLabelA4.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
//                " °C");
//        temperatureLabelA5.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
//                " °C");
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
        temperatureLabelD5.setText(String.valueOf(Math.round(weatherDailyForecasts.get(dayNumber++).getTemperature())) +
                " °C");
    }

    private void setTodayCityData(CityType cityType) {
        Weather todayWeather = weatherAppManager.getCityByType(cityType).getCurrentDayWeather();

        if (cityType == CityType.DEFAULT) {
            fillDefaultTodaysLabels(todayWeather);
        } else if (cityType == CityType.ADDITIONAL) {
            fillAdditionalsTodaysLabels(todayWeather);
        }

        setCurrentWeatherPicture(todayWeather);
    }

    private void setCurrentWeatherPicture(Weather todayWeather) {
        Image weatherPicture = new Image("/default.png");

        if(isItSnowingNow(todayWeather)) {
            weatherPicture = new Image("/snow.png");
        } else if (isItRainingNow(todayWeather)) {
            weatherPicture = new Image("/rain.png");
        }

        defaultCityCurrentDayImage.setImage(weatherPicture);
    }

    private boolean isItSnowingNow(Weather todayWeather) {
        try {
            byte snowVolume = todayWeather.getSnow().getSnowVolumeLast3Hrs();
            if(snowVolume > 0) {
                return true;
            }
        } catch (Exception ex) {}

        return false;
    }

    private boolean isItRainingNow(Weather todayWeather) {
        try {
            byte rainVolume = todayWeather.getRain().getRainVolumeLast3Hrs();
            if(rainVolume > 0) {
                return true;
            }
        } catch (Exception ex) {}

        return false;
    }


    private void fillAdditionalsTodaysLabels(Weather todayWeather) {
        additionalCityName.setText(weatherAppManager.getCityByType(CityType.ADDITIONAL).getCityName());
        additionalCityPressure.setText("Ciśnienie: " + String.valueOf(todayWeather.getPressure()) + " hPa");
        additionalCityHumidity.setText("Wilgotność: " + String.valueOf(todayWeather.getHumidityPercentage()) + "%");
        additionalCityCurrentTemperature.setText(todayWeather.getTemperature() + " °C");
    }

    private void setHeaderTimeData() {
        Weather todayWeather = weatherAppManager.getCityByType(CityType.DEFAULT).getCurrentDayWeather();
        long time = todayWeather.getDataCalculationDate().getTime();
        String countryCode = todayWeather.getCountry();

        defaultCityDateLabel.setText(dateManager.convertTimeToDate(time, countryCode));
        currentDayOfWeekLabel.setText(dateManager.convertTimeToDayOfWeek(time, countryCode));
        defaultCityTimeZone.setText("Strefa czasowa: " + dateManager.getTimeZoneName(countryCode));

    }

    private void fillDefaultTodaysLabels(Weather todayWeather) {
        defaultCityName.setText(weatherAppManager.getCityByType(CityType.DEFAULT).getCityName());
        defaultCityPressure.setText("Ciśnienie: " + String.valueOf(todayWeather.getPressure()) + " hPa");
        defaultCityHumidity.setText("Wilgotność: " + String.valueOf(todayWeather.getHumidityPercentage()) + "%");
        defaultCityCurrentTemperature.setText(todayWeather.getTemperature() + " °C");


    }




}
