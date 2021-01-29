package pl.patryklubik;

import com.github.prominence.openweathermap.api.HourlyForecastRequester;
import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import com.github.prominence.openweathermap.api.WeatherRequester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.Config;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class WeatherManager {

    private ObservableList<City> cities = FXCollections.observableArrayList();

    private final OpenWeatherMapManager openWeatherManager;

    public WeatherManager() {
        openWeatherManager = new OpenWeatherMapManager(Config.getToken());
        cities.add(new City(CityType.DEFAULT));
        cities.add(new City(CityType.ADDITIONAL));
    }

    public WeatherRequester getWeatherRequester() {
        return openWeatherManager.getWeatherRequester();
    }

    public HourlyForecastRequester getHourlyForecastRequester(){
        return openWeatherManager.getHourlyForecastRequester();
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
