package pl.patryklubik.controller;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public abstract class BaseController {

    protected ViewFactory viewFactory;
    protected final CitiesManager citiesManager;
    private String fxmlName;

    public BaseController(CitiesManager citiesManager, ViewFactory viewFactory, String fxmlName) {
        this.citiesManager = citiesManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
