package pl.patryklubik.controller.services;

import com.github.prominence.openweathermap.api.HourlyForecastRequester;
import com.github.prominence.openweathermap.api.WeatherRequester;
import com.github.prominence.openweathermap.api.constants.Accuracy;
import com.github.prominence.openweathermap.api.constants.Unit;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import com.github.prominence.openweathermap.api.model.response.Weather;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.patryklubik.WeatherManager;
import pl.patryklubik.controller.ResultDownloadWeatherData;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.Config;


/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class WeatherDataService extends Service<ResultDownloadWeatherData> {

    private WeatherManager weatherManager;
    private City city;
    private final String unit = Unit.METRIC_SYSTEM;
    private final String accuracy = Accuracy.ACCURATE;


    public WeatherDataService(WeatherManager weatherManager, CityType cityType) {
        this.weatherManager = weatherManager;
        this.city = weatherManager.getCityByType(cityType);
    }

    public void setCityName(String cityName) {
        city.setCityName(cityName);
    }

    @Override
    protected Task<ResultDownloadWeatherData> createTask() {
        return new Task<ResultDownloadWeatherData>() {
            @Override
            protected ResultDownloadWeatherData call() throws Exception {
                return downloadWeatherData();
            }
        };
    }

    private ResultDownloadWeatherData downloadWeatherData() {

        try {

            city.setCurrentDayWeather(this.getCurrentDayWeather());
            city.setWeatherForecast(this.getWeatherForecast());

        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return  ResultDownloadWeatherData.FAILED_BY_INVALID_CITY_NAME_ERROR;

        } catch (Exception e) {
            e.printStackTrace();
            return  ResultDownloadWeatherData.FAILED_BY_UNEXPECTED_ERROR;
        }

        return ResultDownloadWeatherData.SUCCESS;
    }

    private HourlyForecast getWeatherForecast() {
        HourlyForecastRequester forecastRequester = weatherManager.getHourlyForecastRequester();
        HourlyForecast forecastResponse = forecastRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());

        return forecastResponse;
    }

    private Weather getCurrentDayWeather(){
        WeatherRequester weatherRequester = weatherManager.getWeatherRequester();

        Weather weatherResponse = weatherRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());

        return weatherResponse;
    }


}
