package pl.patryklubik.controller.services;


import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    private WeatherDataService weatherDataService;

    @Mock
    CitiesManager citiesManagerMock;
    @Mock
    OpenWeatherMapManager openWeatherMapManagerMock;


    @Test
    void settingTheCityShouldChangeTheFirstCityLetterToCapitalAndOtherToLowercase() {

        //given
        CityType cityType = CityType.DEFAULT;
        City city = new City(cityType);
        citiesManagerMock = mock(CitiesManager.class);
        doReturn(city).when(citiesManagerMock).getCityByType(any());
        weatherDataService = new WeatherDataService(citiesManagerMock, cityType, openWeatherMapManagerMock);

        //when
        weatherDataService.setCityName("cityName");

        //then
        assertThat(city.getCityName(), is("Cityname"));
    }
}