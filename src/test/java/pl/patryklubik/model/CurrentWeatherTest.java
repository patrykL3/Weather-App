package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.Rain;
import com.github.prominence.openweathermap.api.model.Snow;
import com.github.prominence.openweathermap.api.model.response.Weather;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Create by Patryk Łubik on 06.03.2021.
 */

class CurrentWeatherTest {

    @Test
    void rainingShouldBeDetectedWhenRainVoleIsBiggerThanZero() {

        //given
        Rain rain = new Rain();
        rain.setRainVolumeLast3Hrs((byte) 1);
        Weather weather = new Weather();
        weather.setRain(rain);

        //when
        CurrentWeather currentWeather = new CurrentWeather(weather);

        //then
        assertThat(currentWeather.isItRainingNow(), is(true));
    }

    @Test
    void rainingShouldNotBeDetectedWhenIsNotRaining() {

        //given
        Weather weather = new Weather();
        Rain rain = new Rain();
        rain.setRainVolumeLast3Hrs((byte) 0);
        weather.setRain(rain);

        //when
        CurrentWeather currentWeather = new CurrentWeather(weather);

        //then
        assertThat(currentWeather.isItRainingNow(), is(false));
    }

    @Test
    void snowingShouldBeDetectedWhenSnowVoleIsBiggerThanZero() {

        //given
        Snow snow = new Snow();
        snow.setSnowVolumeLast3Hrs((byte) 1);
        Weather weather = new Weather();
        weather.setSnow(snow);

        //when
        CurrentWeather currentWeather = new CurrentWeather(weather);

        //then
        assertThat(currentWeather.isItSnowingNow(), is(true));
    }

    @Test
    void snowingShouldNotBeDetectedWhenIsNotSnowing() {

        //given
        Weather weather = new Weather();
        Snow snow = new Snow();
        snow.setSnowVolumeLast3Hrs((byte) 0);
        weather.setSnow(snow);

        //when
        CurrentWeather currentWeather = new CurrentWeather(weather);

        //then
        assertThat(currentWeather.isItSnowingNow(), is(false));
    }
}