package pl.patryklubik.model;

import javafx.collections.ObservableList;
import pl.patryklubik.WeatherAppManager;
import pl.patryklubik.controller.ResultDownloadWeatherData;
import pl.patryklubik.controller.services.WeatherDataService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by Patryk Łubik on 04.02.2021.
 */
public class ApplicationData {

    private final String DATA_LOCATION = System.getProperty("user.home") + File.separator + "weatherAppData.ser";
    private WeatherAppManager weatherAppManager;

    private HashMap<CityType,String> dataMap = new HashMap<CityType,String>();


    public ApplicationData(WeatherAppManager weatherAppManager) {
        this.weatherAppManager = weatherAppManager;
    }

    public void loadData() {

        try {
            File fileWithData = new File(DATA_LOCATION);

            if(fileWithData.exists()) {
                FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                HashMap<CityType,String>  cities = (HashMap<CityType,String> ) objectInputStream.readObject();
                dataMap = cities;

                WeatherDataService weatherDataService = new WeatherDataService(weatherAppManager, CityType.DEFAULT);
                weatherDataService.setCityName(cities.get(CityType.DEFAULT));
                weatherDataService.restart();

                WeatherDataService weatherDataServiceA = new WeatherDataService(weatherAppManager, CityType.ADDITIONAL);
                weatherDataServiceA.setCityName(cities.get(CityType.ADDITIONAL));
                weatherDataServiceA.restart();

            }

        } catch ( Exception e){
            e.printStackTrace();
        }

    }




    public void saveData(List<City> cities){

        try {

            for (City city : cities) {
                if(city.getCityType() == CityType.ADDITIONAL && city.getCityName() != null) {
                    dataMap.put(CityType.ADDITIONAL,city.getCityName());
                } else if(city.getCityType() == CityType.DEFAULT) {
                    dataMap.put(CityType.DEFAULT,city.getCityName());
                }
            }

            File file = new File(DATA_LOCATION);
            if(!dataMap.get(CityType.DEFAULT).isEmpty()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(dataMap);

                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cityExist(CityType cityType) {
        if (dataMap.get(cityType) != null && dataMap.get(cityType) != "") {
            return true;
        }

        return false;
    }

    public String getCityName(CityType cityType) {

        return dataMap.get(cityType);
    }

}
