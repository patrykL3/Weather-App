package pl.patryklubik;

import pl.patryklubik.model.ApplicationData;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.view.ViewFactory;

import java.util.List;


/**
 * Create by Patryk Łubik on 23.01.2021.
 */

public class ProgramState {

    private ViewFactory viewFactory;
    private ApplicationData applicationData;
    private CitiesManager citiesManager;

    public ProgramState() {
        citiesManager = new CitiesManager();
        applicationData = new ApplicationData(citiesManager);
        viewFactory = new ViewFactory(citiesManager);
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
