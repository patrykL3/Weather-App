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
    private WeatherAppManager weatherAppManager;

    public ProgramState() {
        weatherAppManager = new WeatherAppManager();
        applicationData = new ApplicationData(weatherAppManager);
        viewFactory = new ViewFactory(weatherAppManager);
    }


    public void init() {
        this.checkData();
    }

    private void checkData() {
        String savedDefaultCityName;
        String savedAdditionalCityName;

        applicationData.loadData();

        if (applicationData.cityExist(CityType.DEFAULT)) {

            savedDefaultCityName = applicationData.getCityName(CityType.DEFAULT);
            weatherAppManager.getCityByType(CityType.DEFAULT).setCityName(savedDefaultCityName);

            if(applicationData.cityExist(CityType.ADDITIONAL)) {
                savedAdditionalCityName = applicationData.getCityName(CityType.ADDITIONAL);
                weatherAppManager.getCityByType(CityType.ADDITIONAL).setCityName(savedAdditionalCityName);
            }
            viewFactory.showMainWindow();

        } else {
            viewFactory.showStarterWindow();
        }
    }

    public void saveData() {
        List<City> cities = weatherAppManager.getCities();
        applicationData.saveData(cities);
    }



}
