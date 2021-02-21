package pl.patryklubik.model;

import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;

import java.io.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Patryk Łubik on 04.02.2021.
 */
public class ApplicationData {

    private final String DATA_LOCATION = System.getProperty("user.home") + File.separator + "weatherAppData.ser";
    private CitiesManager citiesManager;
    private Map<CityType,String> dataMap = new EnumMap<>(CityType.class);


    public ApplicationData(CitiesManager citiesManager) {
        this.citiesManager = citiesManager;
        loadData();
    }

    public void loadData() {

        try {
            File fileWithData = new File(DATA_LOCATION);

            if (fileWithData.exists()) {
                FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                EnumMap<CityType,String> cities = (EnumMap<CityType,String>) objectInputStream.readObject();
                dataMap = cities;

                loadCityData(cities, CityType.DEFAULT);
                loadCityData(cities, CityType.ADDITIONAL);
            }

        } catch ( Exception ignored) {}

    }

    private void loadCityData(EnumMap<CityType,String>  cities, CityType cityType) {
        WeatherDataService weatherDataService = new WeatherDataService(citiesManager, cityType);
        weatherDataService.setCityName(cities.get(cityType));
        weatherDataService.restart();
    }

    public void saveData(List<City> cities) {

        File file = new File(DATA_LOCATION);

        for (City city : cities) {
            if (city.getCityName() != null && !city.getCityName().equals("")) {
                dataMap.put(city.getCityType(),city.getCityName());
            }
        }

        if (!(dataMap.get(CityType.DEFAULT).isEmpty())) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

                objectOutputStream.writeObject(dataMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean cityExist(CityType cityType) {

        return dataMap.get(cityType) != null && !dataMap.get(cityType).equals("");
    }

    public String getCityName(CityType cityType) {

        return dataMap.get(cityType);
    }

}
