package pl.patryklubik;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class Launcher extends Application {

    private final ProgramState programState = new ProgramState();

    @Override
    public void start(Stage stage) {
        programState.init();
    }

        @Override
    public void stop() {
        programState.saveData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}