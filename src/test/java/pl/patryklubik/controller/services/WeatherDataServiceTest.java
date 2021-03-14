package pl.patryklubik.controller.services;


import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import org.junit.jupiter.api.Test;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Create by Patryk Łubik on 04.03.2021.
 */

class WeatherDataServiceTest {

    @Test
    void settingTheCityShouldChangeTheFirstCityLetterToCapitalAndOtherToLowercase() {

        //given
        CitiesManager citiesManagerMock = mock(CitiesManager.class);
        OpenWeatherMapManager openWeatherMapManagerMock = mock(OpenWeatherMapManager.class);
        CityType cityType = CityType.DEFAULT;
        City city = new City(cityType);
        doReturn(city).when(citiesManagerMock).getCityByType(any());
        WeatherDataService weatherDataService = new WeatherDataService(citiesManagerMock, cityType,
                openWeatherMapManagerMock);

        //when
        weatherDataService.setCityName("cityName");

        //then
        assertThat(city.getCityName(), is("Cityname"));
    }
}