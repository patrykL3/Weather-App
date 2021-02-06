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
import pl.patryklubik.WeatherAppManager;
import pl.patryklubik.controller.ResultDownloadWeatherData;
import pl.patryklubik.model.City;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.Config;
import pl.patryklubik.model.FewDaysForecast;


/**
 * Create by Patryk Łubik on 26.01.2021.
 */

public class WeatherDataService extends Service<ResultDownloadWeatherData> {

//    private WeatherAppManager weatherAppManager;
private final OpenWeatherMapManager openWeatherManager;
    private City city;
    private final String unit = Unit.METRIC_SYSTEM;
    private final String accuracy = Accuracy.ACCURATE;


    public WeatherDataService(WeatherAppManager weatherAppManager, CityType cityType) {
//        this.weatherAppManager = weatherAppManager;
        openWeatherManager = new OpenWeatherMapManager(Config.getToken());
        this.city = weatherAppManager.getCityByType(cityType);
    }

    public void setCityName(String cityName) {
        String correctedName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1).toLowerCase();
        city.setCityName(correctedName);
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
//            city.setWeatherForecast(this.getWeatherForecast());
            city.setWeatherDailyForecast( new FewDaysForecast(getWeatherForecast()));

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
//        HourlyForecastRequester forecastRequester = weatherAppManager.getHourlyForecastRequester();
        HourlyForecastRequester forecastRequester = openWeatherManager.getHourlyForecastRequester();
        HourlyForecast forecastResponse = forecastRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());

        return forecastResponse;
    }

    private Weather getCurrentDayWeather(){
//        WeatherRequester weatherRequester = weatherAppManager.getWeatherRequester();
        WeatherRequester weatherRequester = openWeatherManager.getWeatherRequester();

        Weather weatherResponse = weatherRequester
                .setLanguage(Config.getAppLanguage())
                .setUnitSystem(unit)
                .setAccuracy(accuracy)
                .getByCityName(city.getCityName());

        return weatherResponse;
    }


}
