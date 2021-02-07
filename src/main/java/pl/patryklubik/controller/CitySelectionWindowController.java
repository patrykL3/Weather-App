package pl.patryklubik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.CityType;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */
public class CitySelectionWindowController extends BaseController implements Initializable {

    private WeatherDataService weatherDataService;
    private final CityType cityType;
    private CitiesManager citiesManager;


    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label headerLabel;

    @FXML
    void CancelButtonAction(ActionEvent event) {
        Stage thisStage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(thisStage);
    }

    @FXML
    void CitySelectButtonAction() {
        weatherDataService.setCityName(cityField.getText());
        weatherDataService.restart();

        weatherDataService.setOnSucceeded(event -> {
            ResultDownloadWeatherData resultDownloadWeatherData = weatherDataService.getValue();
            reactToDownloadWeatherDataResult(resultDownloadWeatherData);
        });
    }

    public CitySelectionWindowController(CitiesManager citiesManager, ViewFactory viewFactory,
                                         String fxmlName, CityType cityType) {
        super(citiesManager, viewFactory, fxmlName);
        this.cityType = cityType;
        this.citiesManager = citiesManager;
        this.weatherDataService = new WeatherDataService(citiesManager, cityType);
    }

    private void reactToDownloadWeatherDataResult(ResultDownloadWeatherData resultDownloadWeatherData) {
        switch (resultDownloadWeatherData) {
            case SUCCESS:
                viewFactory.closeAllStages();
                viewFactory.showMainWindow();
                return;
            case FAILED_BY_INVALID_CITY_NAME_ERROR:
                errorLabel.setText("NIEPOPRAWNA NAZWA MIEJSCOWOŚCI");
                errorLabel.setVisible(true);
                return;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText("NIESPODZIEWANY BŁĄD");
                errorLabel.setVisible(true);
                return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeaderLabel(cityType);
        if(citiesManager.getCityByType(cityType).getCityName() != null) {
            cityField.setText(citiesManager.getCityByType(cityType).getCityName());
        }
    }

    private void setHeaderLabel(CityType cityType) {
        if(cityType == CityType.DEFAULT) {
            headerLabel.setText("Wprowadź nazwę Twojego miasta:");
        } else if(cityType == CityType.ADDITIONAL) {
            headerLabel.setText("Wprowadź nazwę dodatkowego miasta:");
        }
    }
}
