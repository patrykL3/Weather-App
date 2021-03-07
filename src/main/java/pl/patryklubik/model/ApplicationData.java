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

    private static String DATA_LOCATION = System.getProperty("user.home") + File.separator + "weatherAppData.ser";
    private final CitiesManager citiesManager;
    private Map<CityType,String> citiesDataMap = new EnumMap<>(CityType.class);


    public ApplicationData(CitiesManager citiesManager) {
        this.citiesManager = citiesManager;
    }

    public void loadData() {

        try {
            File fileWithData = new File(DATA_LOCATION);

            if (fileWithData.exists()) {
                FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                citiesDataMap = (Map<CityType,String>) objectInputStream.readObject();
                fileInputStream.close();

                loadCityData(citiesDataMap, CityType.DEFAULT);
                loadCityData(citiesDataMap, CityType.ADDITIONAL);
            }

        } catch ( Exception ignored) {}

    }

    private void loadCityData(Map<CityType,String>  cities, CityType cityType) {
        WeatherDataService weatherDataService = new WeatherDataService(citiesManager, cityType);
        weatherDataService.setCityName(cities.get(cityType));
        weatherDataService.restart();
    }

    public void saveData(List<City> cities) {

        File file = new File(DATA_LOCATION);

        for (City city : cities) {
            if (city.getCityName() != null && !city.getCityName().equals("")) {
                citiesDataMap.put(city.getCityType(),city.getCityName());
            }
        }

        if (!(citiesDataMap.get(CityType.DEFAULT).isEmpty())) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

                objectOutputStream.writeObject(citiesDataMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean cityExist(CityType cityType) {

        return citiesDataMap.get(cityType) != null && !citiesDataMap.get(cityType).equals("");
    }

    public String getCityName(CityType cityType) {

        return citiesDataMap.get(cityType);
    }

    public static void setDataLocation(String dataLocation) {
        DATA_LOCATION = dataLocation;
    }

    public void setCitiesDataMap(Map<CityType, String> citiesDataMap) {
        this.citiesDataMap = citiesDataMap;
    }

    public Map<CityType, String> getCitiesDataMap() {
        return citiesDataMap;
    }
}
