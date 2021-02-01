package pl.patryklubik.controller;

import pl.patryklubik.WeatherAppManager;
import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private String fxmlName;
    private WeatherAppManager weatherAppManager;

    public BaseController(WeatherAppManager weatherAppManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherAppManager = weatherAppManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
