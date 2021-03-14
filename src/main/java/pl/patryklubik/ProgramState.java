package pl.patryklubik;

import pl.patryklubik.model.*;
import pl.patryklubik.view.ViewFactory;

import java.util.List;


/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ProgramState {

    private final ViewFactory viewFactory;
    private final ApplicationData applicationData;
    private final CitiesManager citiesManager;

    public ProgramState() {
        citiesManager = new CitiesManager();
        applicationData = new ApplicationData(citiesManager);
        viewFactory = new ViewFactory(citiesManager);
        LanguageData.init(Config.getAppLanguage());
    }

    public ProgramState(ViewFactory viewFactory, ApplicationData applicationData, CitiesManager citiesManager) {
        this.viewFactory = viewFactory;
        this.applicationData = applicationData;
        this.citiesManager = citiesManager;
    }

    public void init() {
        checkData();
    }

    private void checkData() {
        String savedDefaultCityName;
        String savedAdditionalCityName;

        applicationData.loadData();

        if (applicationData.cityExist(CityType.DEFAULT)) {
            savedDefaultCityName = applicationData.getCityName(CityType.DEFAULT);
            citiesManager.getCityByType(CityType.DEFAULT).setCityName(savedDefaultCityName);

            if (applicationData.cityExist(CityType.ADDITIONAL)) {
                savedAdditionalCityName = applicationData.getCityName(CityType.ADDITIONAL);
                citiesManager.getCityByType(CityType.ADDITIONAL).setCityName(savedAdditionalCityName);
            }
            viewFactory.showMainWindow();

        } else {
            viewFactory.showStarterWindow();
        }
    }

    public void saveData() {
        List<City> cities = citiesManager.getCities();
        applicationData.saveData(cities);
    }
}
