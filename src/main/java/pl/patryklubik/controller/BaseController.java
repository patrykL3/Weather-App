package pl.patryklubik.controller;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private String fxmlName;
    private CitiesManager citiesManager;

    public BaseController(CitiesManager citiesManager, ViewFactory viewFactory, String fxmlName) {
        this.citiesManager = citiesManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
