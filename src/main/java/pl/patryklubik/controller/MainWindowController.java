package pl.patryklubik.controller;


import com.github.prominence.openweathermap.api.model.response.Weather;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    Label currentDayOfWeekLabel;

    @FXML
    Label defaultCityDateLabel;

    @FXML
    Label defaultCityTimeZone;

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
        setDefaultCityData();

    }

    private void setDefaultCityData() {
        Weather todayWeather = weatherManager.getCityByType(CityType.DEFAULT).getCurrentDayWeather();
        long time = todayWeather.getDataCalculationDate().getTime();
        String countryCode = todayWeather.getCountry();

        defaultCityDateLabel.setText(dateManager.convertTimeToDate(time, countryCode));
        currentDayOfWeekLabel.setText(dateManager.convertTimeToDayOfWeek(time, countryCode));
        defaultCityTimeZone.setText("Strefa czasowa: " + dateManager.getTimeZoneName(countryCode));




    }





}
