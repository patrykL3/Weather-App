package pl.patryklubik;

import com.github.prominence.openweathermap.api.HourlyForecastRequester;
import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import com.github.prominence.openweathermap.api.WeatherRequester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class WeatherAppManager {


    private List<City> cities = new ArrayList<City>();

    public WeatherAppManager() {
        cities.add(new City(CityType.DEFAULT));
        cities.add(new City(CityType.ADDITIONAL));
    }

    public List<City> getCities() {
        return cities;
    }

    public City getCityByType(CityType cityType) {
        City searchedCity = new City(cityType);

        for (City city : cities) {
            if(city.getCityType() == cityType) {
                searchedCity = city;
            }
        }
        return searchedCity;
    }
}
