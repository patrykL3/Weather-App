package pl.patryklubik.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.patryklubik.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class MainWindowController extends BaseController implements Initializable {


//    @FXML
//    private TextField DefaultCityField;
//
//    @FXML
//    private Label errorLabel;
//
//    @FXML
//    void DefaultCitySelectButtonAction(ActionEvent event) {
//        viewFactory.showMainWindow();
//        Stage stage = (Stage) errorLabel.getScene().getWindow();
//        viewFactory.closeStage(stage);
//    }


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


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
