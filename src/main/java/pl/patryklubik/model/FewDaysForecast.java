package pl.patryklubik.model;

import com.github.prominence.openweathermap.api.model.Rain;
import com.github.prominence.openweathermap.api.model.Snow;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import pl.patryklubik.DateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 31.01.2021.
 */

public class FewDaysForecast {

    HourlyForecast fullWeatherForecast;
    DateManager dateManager = new DateManager();

    public FewDaysForecast(HourlyForecast fullWeatherForecast) {
        this.fullWeatherForecast = fullWeatherForecast;
    }

    public List<DailyForecast> getNextFewDaysForecast () {
        List<DailyForecast> fewDaysForecastData = new ArrayList<DailyForecast>();
        String countryCode = fullWeatherForecast.getCountry();
        int startPointOfNextFewDaysForecast = getStartPointOfNextFewDaysForecast(countryCode);

        int dayStartPoint = startPointOfNextFewDaysForecast;
        int numberPointInOneDay = 8;

        while (isPointInForecastExist(dayStartPoint)) {
            fewDaysForecastData.add(getDayData(dayStartPoint));
            dayStartPoint += numberPointInOneDay;
        }

        return fewDaysForecastData;
    }

    private boolean isPointInForecastExist(int dayStartPoint) {
        boolean isPointExist = false;

        try {
            fullWeatherForecast.getForecasts().get(dayStartPoint);
            isPointExist = true;

        } catch (Exception ex) {}

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
        int numberPointInOneDay = 8;

        for(int i = dayStartPoint; i < dayStartPoint + numberPointInOneDay; i++) {
            try {
                float pointTemperature =
                        fullWeatherForecast.getForecasts().get(i).getWeatherInfo().getTemperature();

                if(i == dayStartPoint) {
                    maxDayTemperature = pointTemperature;
                } else if (pointTemperature > maxDayTemperature) {
                    maxDayTemperature = pointTemperature;
                }
            } catch (Exception ex) {}
        }

        return maxDayTemperature;
    }

    private boolean isItSnowingAtDay(int dayStartPoint) {
        boolean isItSnowing = false;
        int numberPointInOneDay = 8;

        for(int i = dayStartPoint; i < dayStartPoint + numberPointInOneDay; i++) {
            try {
                Snow snowInPoint = fullWeatherForecast.getForecasts().get(i).getSnow();
                if(snowInPoint != null && !String.valueOf(snowInPoint).equals("Snow(last 3 hrs): 0 mm")) {
                    isItSnowing = true;
                }
            } catch (Exception ex) {}
        }

        return isItSnowing;
    }

    private boolean isItRainingAtDay(int dayStartPoint) {
        boolean isItRaining = false;
        int numberPointInOneDay = 8;

        for(int i = dayStartPoint; i < dayStartPoint + numberPointInOneDay; i++) {
            try {
                Rain rainInPoint = fullWeatherForecast.getForecasts().get(i).getRain();
                if(rainInPoint != null && !String.valueOf(rainInPoint).equals("Rain(last 3 hrs): 0 mm")) {
                    isItRaining = true;
                }
            } catch (Exception ex) {}
        }

        return isItRaining;
    }

    private int getStartPointOfNextFewDaysForecast(String countryCode) {
        int startPoint = 0;
        String currentDayNumber = dateManager.getCurrentDayNumberInCountry(countryCode);
        long timeInPoint = fullWeatherForecast.getForecasts().get(startPoint).getDataCalculationDate().getTime();
        String numberDayInPoint = dateManager.getDayNumberInCountry(countryCode, timeInPoint);

        if(!currentDayNumber.equals(numberDayInPoint)) {
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

}
