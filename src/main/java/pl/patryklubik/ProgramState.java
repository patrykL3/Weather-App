package pl.patryklubik;

import pl.patryklubik.view.ViewFactory;


/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ProgramState {

    private ViewFactory viewFactory;

    public ProgramState() {
        viewFactory = new ViewFactory();
    }

    public void init() {

        viewFactory.showWindow();
    }

}
