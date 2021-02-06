package pl.patryklubik.controller;


import pl.patryklubik.WeatherAppManager;
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

    @FXML
    private TextField defaultCityField;

    @FXML
    private Label errorLabel;

    @FXML
    void DefaultCitySelectButtonAction() {
        defaultCityField.setText("Londyn");
        if (fieldAreValid()) {
        weatherDataService.setCityName(defaultCityField.getText());
        weatherDataService.restart();

        weatherDataService.setOnSucceeded(event -> {
                ResultDownloadWeatherData resultDownloadWeatherData = weatherDataService.getValue();
                reactToDownloadWeatherDataResult(resultDownloadWeatherData);
            });
        }
    }

    public StarterWindowController(WeatherAppManager weatherAppManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherAppManager, viewFactory, fxmlName);
        this.weatherDataService = new WeatherDataService(weatherAppManager, CityType.DEFAULT);
    }

    private boolean fieldAreValid() {

        if(defaultCityField.getText().isEmpty()) {
            errorLabel.setText("WPROWADŹ NAZWĘ MIASTA");
            return false;
        }

        return true;
    }

    private void reactToDownloadWeatherDataResult(ResultDownloadWeatherData resultDownloadWeatherData) {
        switch (resultDownloadWeatherData) {
            case SUCCESS:
                if(!viewFactory.isMainViewInitialized()){
                    viewFactory.showMainWindow();
                }
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                viewFactory.closeStage(stage);
                return;
            case FAILED_BY_INVALID_CITY_NAME_ERROR:
                errorLabel.setText("NIEPOPRAWNA NAZWA MIEJSCOWOŚCI");
                return;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText("NIESPODZIEWANY BŁĄD");
                return;
        }
    }
}
