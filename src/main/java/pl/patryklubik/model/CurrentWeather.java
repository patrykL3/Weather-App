package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.response.Weather;

/**
 * Create by Patryk Łubik on 07.02.2021.
 */

public class CurrentWeather {

    private final Weather currentWeatherData;

    public CurrentWeather(Weather currentWeatherData) {
        this.currentWeatherData = currentWeatherData;
    }

    public short getPressure() {
        return currentWeatherData.getPressure();
    }

    public short getHumidityPercentage() {
        return currentWeatherData.getHumidityPercentage();
    }

    public float getTemperature() {
        return currentWeatherData.getTemperature();
    }

    public String getCountry() {
        return currentWeatherData.getCountry();
    }

    public boolean isItRainingNow() {

        if (currentWeatherData.getRain() != null) {
            byte rainVolume = currentWeatherData.getRain().getRainVolumeLast3Hrs();
            if (rainVolume > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean isItSnowingNow() {

        if (currentWeatherData.getSnow() != null) {
            byte snowVolume = currentWeatherData.getSnow().getSnowVolumeLast3Hrs();
            if (snowVolume > 0) {
                return true;
            }
        }

        return false;
    }
}
