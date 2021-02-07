package pl.patryklubik;

import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class CitiesManager {

    private List<City> cities = new ArrayList<City>();

    public CitiesManager() {
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
