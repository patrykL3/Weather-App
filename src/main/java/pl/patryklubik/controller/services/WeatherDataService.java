package pl.patryklubik.controller.services;

import com.github.prominence.openweathermap.api.HourlyForecastRequester;
import com.github.prominence.openweathermap.api.OpenWeatherMapManager;
import com.github.prominence.openweathermap.api.WeatherRequester;
import com.github.prominence.openweathermap.api.constants.Accuracy;
import com.github.prominence.openweathermap.api.constants.Unit;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import com.github.prominence.openweathermap.api.model.response.Weather;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.ResultDownloadWeatherData;
import pl.patryklubik.model.*;


/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class WeatherDataService extends Service<ResultDownloadWeatherData> {

    private final OpenWeatherMapManager openWeatherManager;
    private final String unit = Unit.METRIC_SYSTEM;
    private final String accuracy = Accuracy.ACCURATE;
    private final City city;

    public WeatherDataService(CitiesManager citiesManager, CityType cityType, OpenWeatherMapManager openWeatherMapManager) {
        openWeatherManager = openWeatherMapManager;
        this.city = citiesManager.getCityByType(cityType);
    }

    public WeatherDataService(CitiesManager citiesManager, CityType cityType) {
        openWeatherManager = new OpenWeatherMapManager(Config.getToken());
        this.city = citiesManager.getCityByType(cityType);
    }

    public void setCityName(String cityName) {
        String correctedName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1).toLowerCase();
        city.setCityName(correctedName);
    }

    @Override
    protected Task<ResultDownloadWeatherData> createTask() {
        return new Task<>() {
            @Override
            protected ResultDownloadWeatherData call() {
                return downloadWeatherData();
            }
        };
    }

    private ResultDownloadWeatherData downloadWeatherData() {

        try {
            city.setCurrentWeather(new CurrentWeather(getCurrentDayWeather()));
            city.setWeatherDailyForecast( new FewDaysForecast(getWeatherForecast()));
            return ResultDownloadWeatherData.SUCCESS;

        } catch (DataNotFoundException e) {
            e.printStackTrace();
            city.setCityName("");
            return  ResultDownloadWeatherData.FAILED_BY_INVALID_CITY_NAME_ERROR;

        } catch (Exception e) {
            e.printStackTrace();
            city.setCityName("");
            return  ResultDownloadWeatherData.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    private HourlyForecast getWeatherForecast() {
        HourlyForecastRequester forecastRequester = openWeatherManager.getHourlyForecastRequester();

        return forecastRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());
    }

    private Weather getCurrentDayWeather() {
        WeatherRequester weatherRequester = openWeatherManager.getWeatherRequester();

        return weatherRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());
    }
}
