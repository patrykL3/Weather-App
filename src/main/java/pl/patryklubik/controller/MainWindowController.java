package pl.patryklubik.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.patryklubik.WeatherManager;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {

    private WeatherManager weatherManager;

    @FXML
    Label dateLabel;

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
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
