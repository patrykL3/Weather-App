package pl.patryklubik.controller;

import pl.patryklubik.WeatherManager;
import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private String fxmlName;
    private WeatherManager weatherManager;

    public BaseController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherManager = weatherManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
