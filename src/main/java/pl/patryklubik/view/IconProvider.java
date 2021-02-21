package pl.patryklubik.view;

/**
 * Create by Patryk Łubik on 16.02.2021.
 */
public class IconProvider {

    private final static String MAIN_ICON_PATH = "pl/patryklubik/view/mainIcon.png";
    private final static String DEFAULT_WEATHER_PICTURE_FILE_PATH = "/default.png";
    private final static String SNOW_WEATHER_PICTURE_FILE_PATH = "/snow.png";
    private final static String RAIN_WEATHER_PICTURE_FILE_PATH = "/rain.png";

    public static String getMainIconPath() {
        return MAIN_ICON_PATH;
    }

    public static String getDefaultWeatherPictureFilePath() {
        return DEFAULT_WEATHER_PICTURE_FILE_PATH;
    }

    public static String getSnowWeatherPictureFilePath() {
        return SNOW_WEATHER_PICTURE_FILE_PATH;
    }

    public static String getRainWeatherPictureFilePath() {
        return RAIN_WEATHER_PICTURE_FILE_PATH;
    }
}
