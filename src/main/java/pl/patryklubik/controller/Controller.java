package pl.patryklubik.controller;


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

public class Controller extends BaseController implements Initializable {

//    @FXML
//    components


    public Controller(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
