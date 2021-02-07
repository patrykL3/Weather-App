package pl.patryklubik.model;

/**
 * Create by Patryk Łubik on 31.01.2021.
 */

public class DailyForecast {

    private float temperature;
    private long time;
    private boolean snow;
    private boolean rain;

    public DailyForecast(float temperature, long time, boolean snow, boolean rain) {
        this.temperature = temperature;
        this.time = time;
        this.snow = snow;
        this.rain = rain;
    }

    public float getTemperature() {
        return temperature;
    }

    public long getTime() {
        return time;
    }

    public boolean isSnow() {
        return snow;
    }

    public boolean isRain() {
        return rain;
    }

    @Override
    public String toString() {
        return String.format("Snow: " + snow + ", Rain: " + rain + ", Temp.: " + temperature + ", Time: " + time);
    }
}
