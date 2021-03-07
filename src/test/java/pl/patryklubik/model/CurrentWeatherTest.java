package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.Rain;
import com.github.prominence.openweathermap.api.model.Snow;
import com.github.prominence.openweathermap.api.model.response.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Create by Patryk Łubik on 06.03.2021.
 */

class CurrentWeatherTest {

    private CurrentWeather currentWeather;

    @Mock
    private Weather weatherMock;

    @BeforeEach
    void setUp() {
        weatherMock = mock(Weather.class);
    }

    @Test
    void rainingShouldBeDetectedWhenRainVoleIsBiggerThanZero() {

        //given
        Rain rainMock = mock(Rain.class);
        currentWeather = new CurrentWeather(weatherMock);
        given(weatherMock.getRain()).willReturn(rainMock);
        given(rainMock.getRainVolumeLast3Hrs()).willReturn((byte) 5);

        //when
        //then
        assertThat(currentWeather.isItRainingNow(), is(true));
    }

    @Test
    void rainingShouldNotBeDetectedWhenIsNotRaining() {

        //given
        currentWeather = new CurrentWeather(weatherMock);

        //when
        //then
        assertThat(currentWeather.isItRainingNow(), is(false));
    }

    @Test
    void snowingShouldBeDetectedWhenSnowVoleIsBiggerThanZero() {

        //given
        Snow snowMock = mock(Snow.class);
        currentWeather = new CurrentWeather(weatherMock);
        given(weatherMock.getSnow()).willReturn(snowMock);
        given(snowMock.getSnowVolumeLast3Hrs()).willReturn((byte) 5);

        //when
        //then
        assertThat(currentWeather.isItSnowingNow(), is(true));
    }

    @Test
    void snowingShouldNotBeDetectedWhenIsNotSnowing() {

        //given
        currentWeather = new CurrentWeather(weatherMock);

        //when
        //then
        assertThat(currentWeather.isItSnowingNow(), is(false));
    }
}