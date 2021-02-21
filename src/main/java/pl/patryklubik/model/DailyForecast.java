package pl.patryklubik.model;

/**
 * Create by Patryk Łubik on 31.01.2021.
 */

public class DailyForecast {

    private final float temperature;
    private final long time;
    private final boolean snow;
    private final boolean rain;

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
        return String.format("Snow: %s, Rain: %s, Temp.: %s, Time: %s", snow, rain, temperature, time);
    }
}
