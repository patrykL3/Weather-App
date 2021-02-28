package pl.patryklubik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.LanguageData;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */
public class CitySelectionWindowController extends BaseController implements Initializable {

    private final WeatherDataService weatherDataService;
    private final CityType cityType;

    private final String SUBMIT_BUTTON_TEXT = LanguageData.getText("SUBMIT_TEXT");
    private final String CANCEL_BUTTON_TEXT = LanguageData.getText("CANCEL_TEXT");
    private final String INVALID_CITY_NAME_MESSAGE = LanguageData.getText("INVALID_CITY_NAME_LABEL_TEXT");
    private final String UNEXPECTED_ERROR_LABEL_TEXT = LanguageData.getText("UNEXPECTED_ERROR_LABEL_TEXT");
    private final String DEFAULT_CITY_HEADER_LABEL_TEXT = LanguageData.getText("DEFAULT_CITY_HEADER_LABEL_TEXT");
    private final String ADDITIONAL_CITY_HEADER_LABEL_TEXT = LanguageData.getText("ADDITIONAL_CITY_HEADER_LABEL_TEXT");

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label headerLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button submitButton;

    public CitySelectionWindowController(CitiesManager citiesManager, ViewFactory viewFactory,
                                         String fxmlName, CityType cityType) {
        super(citiesManager, viewFactory, fxmlName);
        this.cityType = cityType;
        this.weatherDataService = new WeatherDataService(citiesManager, cityType);
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(thisStage);
    }

    @FXML
    void citySelectButtonAction() {
        weatherDataService.setCityName(cityField.getText());
        weatherDataService.restart();

        weatherDataService.setOnSucceeded(event -> {
            ResultDownloadWeatherData resultDownloadWeatherData = weatherDataService.getValue();
            reactToDownloadWeatherDataResult(resultDownloadWeatherData);
        });
    }

    private void reactToDownloadWeatherDataResult(ResultDownloadWeatherData resultDownloadWeatherData) {
        switch (resultDownloadWeatherData) {
            case SUCCESS:
                viewFactory.closeAllStages();
                viewFactory.showMainWindow();
                return;
            case FAILED_BY_INVALID_CITY_NAME_ERROR:
                errorLabel.setText(INVALID_CITY_NAME_MESSAGE);
                errorLabel.setVisible(true);
                return;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText(UNEXPECTED_ERROR_LABEL_TEXT);
                errorLabel.setVisible(true);
                return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderLabel(cityType);
        cancelButton.setText(CANCEL_BUTTON_TEXT);
        submitButton.setText(SUBMIT_BUTTON_TEXT);
        if (citiesManager.getCityByType(cityType).getCityName() != null) {
            cityField.setText(citiesManager.getCityByType(cityType).getCityName());
        }
    }

    private void setHeaderLabel(CityType cityType) {
        if (cityType == CityType.DEFAULT) {
            headerLabel.setText(DEFAULT_CITY_HEADER_LABEL_TEXT);
        } else if (cityType == CityType.ADDITIONAL) {
            headerLabel.setText(ADDITIONAL_CITY_HEADER_LABEL_TEXT);
        }
    }
}
