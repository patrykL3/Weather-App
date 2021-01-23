package pl.patryklubik.controller;

import pl.patryklubik.view.ViewFactory;

/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
