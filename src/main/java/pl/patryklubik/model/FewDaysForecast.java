package pl.patryklubik.model;

import pl.patryklubik.DateManager;

import com.github.prominence.openweathermap.api.model.Rain;
import com.github.prominence.openweathermap.api.model.Snow;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 31.01.2021.
 */

public class FewDaysForecast {

    private final HourlyForecast fullWeatherForecast;
    private final DateManager dateManager = new DateManager();
    public static final int NUMBER_POINT_IN_ONE_DAY = 8;

    public FewDaysForecast(HourlyForecast fullWeatherForecast) {
        this.fullWeatherForecast = fullWeatherForecast;
    }

    public List<DailyForecast> getNextFewDaysForecast() {
        List<DailyForecast> fewDaysForecastData = new ArrayList<>();
        String countryCode = fullWeatherForecast.getCountry();
        int dayStartPoint = getStartPointOfNextFewDaysForecast(countryCode);

        while (isPointInForecastExist(dayStartPoint)) {
            fewDaysForecastData.add(getDayData(dayStartPoint));
            dayStartPoint += NUMBER_POINT_IN_ONE_DAY;
        }

        return fewDaysForecastData;
    }

    private int getStartPointOfNextFewDaysForecast(String countryCode) {
        int startPoint = 0;
        String currentDayNumber = dateManager.getCurrentDayNumberInCountry(countryCode);
        long timeInPoint = fullWeatherForecast.getForecasts().get(startPoint).getDataCalculationDate().getTime();
        String numberDayInPoint = dateManager.getDayNumberInCountry(countryCode, timeInPoint);

        if (!currentDayNumber.equals(numberDayInPoint)) {
            return startPoint;
        } else {
            while(currentDayNumber.equals(numberDayInPoint)) {
                startPoint++;
                timeInPoint = fullWeatherForecast.getForecasts().get(startPoint).getDataCalculationDate().getTime();
                numberDayInPoint = dateManager.getDayNumberInCountry(countryCode, timeInPoint);
            }
        }

        return startPoint;
    }

    private boolean isPointInForecastExist(int dayStartPoint) {
        boolean isPointExist = false;

        if (fullWeatherForecast.getForecasts().size() > dayStartPoint) {
            isPointExist = true;
        }

        return isPointExist;
    }

    private DailyForecast getDayData(int dayStartPoint) {
        float temperature = getMaxDayTemperature(dayStartPoint);
        long timeOfDay = fullWeatherForecast.getForecasts().get(dayStartPoint).getDataCalculationDate().getTime();
        boolean snowAtDay = isItSnowingAtDay(dayStartPoint);
        boolean rainAtDay = isItRainingAtDay(dayStartPoint);

        return new DailyForecast(temperature, timeOfDay, snowAtDay, rainAtDay);
    }

    private float getMaxDayTemperature(int dayStartPoint) {
        float maxDayTemperature = 0;

        for (int i = dayStartPoint; i < dayStartPoint + NUMBER_POINT_IN_ONE_DAY && fullWeatherForecast.getForecasts().size() > i; i++) {
            float pointTemperature = fullWeatherForecast.getForecasts().get(i).getWeatherInfo().getTemperature();

            if (i == dayStartPoint) {
                maxDayTemperature = pointTemperature;
            } else if (pointTemperature > maxDayTemperature) {
                maxDayTemperature = pointTemperature;
            }
        }

        return maxDayTemperature;
    }

    private boolean isItSnowingAtDay(int dayStartPoint) {
        boolean isItSnowing = false;

        for (int i = dayStartPoint; i < dayStartPoint + NUMBER_POINT_IN_ONE_DAY && fullWeatherForecast.getForecasts().size() > i; i++) {
            Snow snowInPoint = fullWeatherForecast.getForecasts().get(i).getSnow();
            if (snowInPoint != null && snowInPoint.getSnowVolumeLast3Hrs() != 0) {
                isItSnowing = true;
            }
        }

        return isItSnowing;
    }

    private boolean isItRainingAtDay(int dayStartPoint) {
        boolean isItRaining = false;

        for (int i = dayStartPoint; i < dayStartPoint + NUMBER_POINT_IN_ONE_DAY && fullWeatherForecast.getForecasts().size() > i; i++) {
            Rain rainInPoint = fullWeatherForecast.getForecasts().get(i).getRain();
            if (rainInPoint != null && rainInPoint.getRainVolumeLast3Hrs() != 0) {
                isItRaining = true;
            }
        }

        return isItRaining;
    }
}
