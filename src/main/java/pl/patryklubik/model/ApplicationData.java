package pl.patryklubik.model;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Create by Patryk Łubik on 04.02.2021.
 */
public class ApplicationData {

    private final String DATA_LOCATION = System.getProperty("user.home") + File.separator + "weatherAppData.ser";
    private CitiesManager citiesManager;
    private HashMap<CityType,String> dataMap = new HashMap<CityType,String>();


    public ApplicationData(CitiesManager citiesManager) {
        this.citiesManager = citiesManager;
    }

    public void loadData() {

        try {
            File fileWithData = new File(DATA_LOCATION);

            if(fileWithData.exists()) {
                FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                HashMap<CityType,String>  cities = (HashMap<CityType,String>) objectInputStream.readObject();
                dataMap = cities;

                loadCityData(cities, CityType.DEFAULT);
                loadCityData(cities, CityType.ADDITIONAL);
            }

        } catch ( Exception e){
            e.printStackTrace();
        }

    }

    private void loadCityData(HashMap<CityType,String>  cities, CityType cityType) {
        WeatherDataService weatherDataService = new WeatherDataService(citiesManager, cityType);
        weatherDataService.setCityName(cities.get(cityType));
        weatherDataService.restart();
    }

    public void saveData(List<City> cities){

        try {
            File file = new File(DATA_LOCATION);

            for (City city : cities) {
                if(city.getCityType() == CityType.ADDITIONAL && city.getCityName() != null && city.getCityName() != "") {
                    dataMap.put(CityType.ADDITIONAL,city.getCityName());
                } else if(city.getCityType() == CityType.DEFAULT) {
                    dataMap.put(CityType.DEFAULT,city.getCityName());
                }
            }

            if(!(dataMap.get(CityType.DEFAULT).isEmpty())) {
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
