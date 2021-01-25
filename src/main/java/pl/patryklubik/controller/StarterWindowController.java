package pl.patryklubik.controller;


import javafx.event.ActionEvent;
import pl.patryklubik.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class StarterWindowController extends BaseController implements Initializable {


    @FXML
    private TextField DefaultCityField;

    @FXML
    private Label errorLabel;

    @FXML
    void DefaultCitySelectButtonAction(ActionEvent event) {
        viewFactory.showMainWindow();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }


    public StarterWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
