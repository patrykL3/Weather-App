package pl.patryklubik;

import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import pl.patryklubik.view.ViewFactory;


/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ProgramState {

    private ViewFactory viewFactory;
//    private UserData;

    public ProgramState() {
        viewFactory = new ViewFactory(new WeatherManager());
    }

    public void init() {
        viewFactory.showStarterWindow();
    }

}
