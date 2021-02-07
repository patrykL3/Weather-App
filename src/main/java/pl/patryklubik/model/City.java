package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.response.Weather;

/**
 * Create by Patryk Łubik on 27.01.2021.
 */

public class City {

    private String cityName;
    private CityType cityType;
    private FewDaysForecast weatherFewDaysForecast;
    private CurrentWeather currentWeather;


    public City(CityType cityType) {
        this.cityType = cityType;
    }

    public City(String cityName, CityType cityType) {
        this.cityName = cityName;
        this.cityType = cityType;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public String getCountryCode() {
        return currentWeather.getCountry();
    }

    public String getCityName() {
        return cityName;
    }

    public CityType getCityType() {
        return cityType;
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

    public void setCurrentWeather(Weather currentWeatherData) {
        this.currentWeather = new CurrentWeather(currentWeatherData);
    }
}
