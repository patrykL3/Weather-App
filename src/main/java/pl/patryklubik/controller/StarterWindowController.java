package pl.patryklubik.controller;


import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.CityType;
import pl.patryklubik.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class StarterWindowController extends BaseController {

    private WeatherDataService weatherDataService;
    private final String ENTER_CITY_NAME_LABEL_TEXT = "WPROWADŹ NAZWĘ MIASTA";
    private final String INVALID_CITY_NAME_LABEL_TEXT = "NIEPOPRAWNA NAZWA MIEJSCOWOŚCI";
    private final String UNEXPECTED_ERROR_LABEL_TEXT = "NIESPODZIEWANY BŁĄD";

    @FXML
    private TextField defaultCityField;

    @FXML
    private Label errorLabel;

    public StarterWindowController(CitiesManager citiesManager, ViewFactory viewFactory, String fxmlName) {
        super(citiesManager, viewFactory, fxmlName);
        this.weatherDataService = new WeatherDataService(citiesManager, CityType.DEFAULT);
    }

    @FXML
    void defaultCitySelectButtonAction() {
        if (fieldAreValid()) {
        weatherDataService.setCityName(defaultCityField.getText());
        weatherDataService.restart();

        weatherDataService.setOnSucceeded(event -> {
                ResultDownloadWeatherData resultDownloadWeatherData = weatherDataService.getValue();
                reactToDownloadWeatherDataResult(resultDownloadWeatherData);
            });
        }
    }

    private boolean fieldAreValid() {

        if(defaultCityField.getText().isEmpty()) {
            errorLabel.setText(ENTER_CITY_NAME_LABEL_TEXT);
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private void reactToDownloadWeatherDataResult(ResultDownloadWeatherData resultDownloadWeatherData) {
        switch (resultDownloadWeatherData) {
            case SUCCESS:
                viewFactory.showMainWindow();
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                viewFactory.closeStage(stage);
                break;
            case FAILED_BY_INVALID_CITY_NAME_ERROR:
                errorLabel.setText(INVALID_CITY_NAME_LABEL_TEXT);
                errorLabel.setVisible(true);
                break;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText(UNEXPECTED_ERROR_LABEL_TEXT);
                errorLabel.setVisible(true);
                break;
        }
    }
}
