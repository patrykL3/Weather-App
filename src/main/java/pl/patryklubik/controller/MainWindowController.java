package pl.patryklubik.controller;


import com.github.prominence.openweathermap.api.model.Snow;
import com.github.prominence.openweathermap.api.model.response.Weather;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.patryklubik.DateManager;
import pl.patryklubik.WeatherManager;
import pl.patryklubik.model.CityType;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.util.*;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {

    private WeatherManager weatherManager;
    private DateManager dateManager;


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
    private Image imageTry;

    @FXML
    void openInfoPageAction(ActionEvent event) {

    }

    @FXML
    void optionsAction(ActionEvent event) {

    }

    @FXML
    void setHomeLocationAction(ActionEvent event) {

    }

    @FXML
    void setSecondLocationAction(ActionEvent event) {

    }


    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
        this.weatherManager = weatherManager;
        dateManager = new DateManager();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderTimeData();
        setTodayCityData(CityType.DEFAULT);

        if(weatherManager.getCityByType(CityType.ADDITIONAL).getCityName() != null) {
            setTodayCityData(CityType.ADDITIONAL);
        }
    }

    private void setTodayCityData(CityType cityType) {
        Weather todayWeather = weatherManager.getCityByType(cityType).getCurrentDayWeather();

        if (cityType == CityType.DEFAULT) {
            fillDefaultTodaysLabels(todayWeather);
        } else if (cityType == CityType.ADDITIONAL) {
            fillAdditionalsTodaysLabels(todayWeather);
        }

        setWeatherPicture(todayWeather);
    }

    private void setWeatherPicture(Weather todayWeather) {
        Image weatherPicture = new Image("/default.png");

        if(isItSnowing(todayWeather)) {
            weatherPicture = new Image("/snow.png");
        } else if (isItRaining(todayWeather)) {
            weatherPicture = new Image("/rain.png");
        }

        defaultCityCurrentDayImage.setImage(weatherPicture);
    }

    private boolean isItSnowing(Weather todayWeather) {
        try {
            byte snowVolume = todayWeather.getSnow().getSnowVolumeLast3Hrs();
            if(snowVolume > 0) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private boolean isItRaining(Weather todayWeather) {
        try {
            byte rainVolume = todayWeather.getRain().getRainVolumeLast3Hrs();
            if(rainVolume > 0) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


    private void fillAdditionalsTodaysLabels(Weather todayWeather) {
        additionalCityName.setText(weatherManager.getCityByType(CityType.DEFAULT).getCityName());
        additionalCityPressure.setText("Ciśnienie: " + String.valueOf(todayWeather.getPressure()) + " hPa");
        additionalCityHumidity.setText("Wilgotność: " + String.valueOf(todayWeather.getHumidityPercentage()) + "%");
        additionalCityCurrentTemperature.setText(todayWeather.getTemperature() + " °C");
    }

    private void setHeaderTimeData() {
        Weather todayWeather = weatherManager.getCityByType(CityType.DEFAULT).getCurrentDayWeather();
        long time = todayWeather.getDataCalculationDate().getTime();
        String countryCode = todayWeather.getCountry();

        defaultCityDateLabel.setText(dateManager.convertTimeToDate(time, countryCode));
        currentDayOfWeekLabel.setText(dateManager.convertTimeToDayOfWeek(time, countryCode));
        defaultCityTimeZone.setText("Strefa czasowa: " + dateManager.getTimeZoneName(countryCode));

    }

    private void fillDefaultTodaysLabels(Weather todayWeather) {
        defaultCityName.setText(weatherManager.getCityByType(CityType.DEFAULT).getCityName());
        defaultCityPressure.setText("Ciśnienie: " + String.valueOf(todayWeather.getPressure()) + " hPa");
        defaultCityHumidity.setText("Wilgotność: " + String.valueOf(todayWeather.getHumidityPercentage()) + "%");
        defaultCityCurrentTemperature.setText(todayWeather.getTemperature() + " °C");


    }




}
