package pl.patryklubik.view;


import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.*;
import pl.patryklubik.model.CityType;

import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ViewFactory {

    private final String STYLESHEET_PATH = "pl/patryklubik/view/Stylesheet.css";
    private final String MAIN_ICON_PATH = "pl/patryklubik/view/mainIcon.png";
    private CitiesManager citiesManager;
    private ArrayList<Stage> activeStages;

    public ViewFactory(CitiesManager citiesManager) {

        this.citiesManager = citiesManager;
        activeStages = new ArrayList<Stage>();
    }

    public void showStarterWindow(){

        BaseController controller = new StarterWindowController(citiesManager, this, "StarterWindow.fxml");
        initializeStage(controller,false);
    }

    public void showMainWindow(){

        BaseController controller = new MainWindowController(citiesManager,this, "MainWindow.fxml");
        initializeStage(controller,false);
    }

    public void showCitySelectionWindow(CityType cityType){

        BaseController controller = new CitySelectionWindowController(citiesManager, this, "CitySelectionWindow" +
                ".fxml", cityType);

        initializeStage(controller,false);
    }

    public void closeAllStages() {
        for (Stage stage: activeStages) {
            stage.close();
        }
    }

    private void initializeStage(BaseController baseController, boolean resizable) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        scene.getStylesheets().add(STYLESHEET_PATH);
        stage.getIcons().add(new Image(MAIN_ICON_PATH));
        stage.setTitle("Weather-App");
        stage.show();
        stage.setResizable(resizable);
        activeStages.add(stage);
    }

    public  void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }
}