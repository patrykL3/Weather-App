package pl.patryklubik.controller;


import javafx.fxml.Initializable;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.LanguageData;
import pl.patryklubik.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class StarterWindowController extends BaseController implements Initializable {

    private WeatherDataService weatherDataService;

    private final String APPLICATION_NAME = LanguageData.getText("APPLICATION_NAME");
    private final String DEFAULT_CITY_HEADER_LABEL_TEXT = LanguageData.getText("DEFAULT_CITY_HEADER_LABEL_TEXT");
    private final String SUBMIT_BUTTON_TEXT = LanguageData.getText("SUBMIT_TEXT");

    private final String ENTER_CITY_NAME_LABEL_TEXT = LanguageData.getText("ENTER_CITY_NAME_LABEL_TEXT");
    private final String INVALID_CITY_NAME_LABEL_TEXT = LanguageData.getText("INVALID_CITY_NAME_LABEL_TEXT");
    private final String UNEXPECTED_ERROR_LABEL_TEXT = LanguageData.getText("UNEXPECTED_ERROR_LABEL_TEXT");


    @FXML
    private Label applicationNameLabel;

    @FXML
    private Label headerLabel;

    @FXML
    private TextField defaultCityField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button submitButton;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applicationNameLabel.setText(APPLICATION_NAME);
        headerLabel.setText(DEFAULT_CITY_HEADER_LABEL_TEXT);
        submitButton.setText(SUBMIT_BUTTON_TEXT);
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
