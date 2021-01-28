package pl.patryklubik.view;


import pl.patryklubik.WeatherManager;
import pl.patryklubik.controller.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ViewFactory {

    private final String STYLESHEET_PATH = "pl/patryklubik/view/Stylesheet.css";
    private final String MAIN_ICON_PATH = "pl/patryklubik/view/mainIcon.png";
    private WeatherManager weatherManager;

    private ArrayList<Stage> activeStages;

    public ViewFactory(WeatherManager weatherManager) {

        this.weatherManager = weatherManager;
        activeStages = new ArrayList<Stage>();
    }

    public void showStarterWindow(){

        BaseController controller = new StarterWindowController(weatherManager, this, "StarterWindow.fxml");
        initializeStage(controller,false);
    }

    public void showMainWindow(){

        BaseController controller = new MainWindowController(weatherManager,this, "MainWindow.fxml");
        initializeStage(controller,false);
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
//        stage.getIcons().add(new Image(MAIN_ICON_PATH));
        stage.setTitle(chooseWindowTitle(baseController.getFxmlName()));
        stage.show();
        stage.setResizable(resizable);
        activeStages.add(stage);
    }

    private String chooseWindowTitle(String fxmlName) {
        switch (fxmlName) {
            case "SettingsWindow.fxml":
                return "Ustawienia";
            default:
                return "Weather-App";
        }
    }

    public  void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public boolean isMainViewInitialized() {
        return false;
    }
}