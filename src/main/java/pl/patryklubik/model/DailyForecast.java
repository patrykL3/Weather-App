package pl.patryklubik.model;

/**
 * Create by Patryk Łubik on 31.01.2021.
 */

public class DailyForecast {
    float temperature;
    long time;
    boolean snow;
    boolean rain;

    public DailyForecast(float temperature, long time, boolean snow, boolean rain) {
        this.temperature = temperature;
        this.time = time;
        this.snow = snow;
        this.rain = rain;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSnow() {
        return snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return String.format("Snow: " + snow + ", Rain: " + rain + ", Temp.: " + temperature + ", Time: " + time);
    }
}
