package pl.patryklubik;

import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class CitiesManager {

    private List<City> cities = new ArrayList<>();

    public CitiesManager() {
        cities.add(new City(CityType.DEFAULT));
        cities.add(new City(CityType.ADDITIONAL));
    }

    public List<City> getCities() {
        return cities;
    }

    public City getCityByType(CityType cityType) {
        return cities.stream()
            .filter(city -> city.getCityType() == cityType)
            .findFirst()
            .orElseGet(() -> new City(cityType));
    }
}
