package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import com.github.prominence.openweathermap.api.model.response.Weather;

/**
 * Create by Patryk Łubik on 27.01.2021.
 */
public class City {

    String cityName;
    CityType cityType;
    Weather currentDayWeather;
    HourlyForecast weatherForecast;
    FewDaysForecast weatherFewDaysForecast;



    public City(String cityName, CityType cityType) {
        this.cityName = cityName;
        this.cityType = cityType;
    }
    public City(CityType cityType) {
        this.cityType = cityType;
    }


    public String getCityName() {
        return cityName;
    }

    public CityType getCityType() {
        return cityType;
    }

    public Weather getCurrentDayWeather() {
        return currentDayWeather;
    }

    public FewDaysForecast getWeatherFewDaysForecast() {
        return weatherFewDaysForecast;
    }

    public void setWeatherDailyForecast(FewDaysForecast weatherFewDaysForecast) {
        this.weatherFewDaysForecast = weatherFewDaysForecast;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCurrentDayWeather(Weather currentDayWeather) {
        this.currentDayWeather = currentDayWeather;
    }

    public void setWeatherHourlyForecast(HourlyForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

}
