package pl.patryklubik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.patryklubik.CitiesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Create by Patryk Łubik on 06.03.2021.
 */
class ApplicationDataTest {

    private ApplicationData applicationData;
    private static final String NAME_FILE_WITH_DATA = "testData.ser";
    @TempDir
    public File fileWithData;


    @BeforeEach
    public void setUp() {

        CitiesManager citiesManager = new CitiesManager();
        applicationData = new ApplicationData(citiesManager);
        ApplicationData.setDataLocation(fileWithData.getAbsolutePath() + NAME_FILE_WITH_DATA);

    }

    @Test
    void whenFileWithDataExistsApplicationShouldLoadData() {

        //given
        List<City> cities = returnListOfCities();

        //when
        applicationData.saveData(cities);
        Map<CityType, String> dataCitiesMapFromSavedData = applicationData.getCitiesDataMap();

        applicationData = new ApplicationData(new CitiesManager());
        applicationData.loadData();
        Map<CityType, String> dataCitiesMapFromLoadedData = applicationData.getCitiesDataMap();

        //then
        assertThat(dataCitiesMapFromLoadedData, equalTo(dataCitiesMapFromSavedData));
    }

    @Test
    void savingDataShouldCreateFile() {

        //given
        List<City> cities = returnListOfCities();

        //when
        applicationData.saveData(cities);

        //then
        assertThat(fileWithData.exists(), is(true));
    }


    @Test
    void citiesShouldBeFindWhenExistsInCitiesDataMap() {

        //given
        Map<CityType, String> citiesDataMap = returnFullCitiesDataMap();

        //when
        applicationData.setCitiesDataMap(citiesDataMap);

        //then
        assertThat(applicationData.cityExist(CityType.DEFAULT), is(true));
        assertThat(applicationData.cityExist(CityType.ADDITIONAL), is(true));
    }

    @Test
    void citiesShouldNotBeFindWhenNotExistsInCitiesDataMap() {

        //given
        Map<CityType, String> citiesDataMap = new EnumMap<>(CityType.class);

        //when
        applicationData.setCitiesDataMap(citiesDataMap);

        //then
        assertThat(applicationData.cityExist(CityType.DEFAULT), is(false));
        assertThat(applicationData.cityExist(CityType.ADDITIONAL), is(false));
    }


    private Map<CityType, String> returnFullCitiesDataMap() {

        Map<CityType, String> citiesDataMap = new EnumMap<>(CityType.class);
        citiesDataMap.put(CityType.DEFAULT, "DefaultCityName");
        citiesDataMap.put(CityType.ADDITIONAL, "AdditionalCityName");

        return citiesDataMap;
    }

    private List<City> returnListOfCities() {

        List<City> cities = new ArrayList<>();
        City defaultCity = new City(CityType.DEFAULT,"defaultCityName");
        City additionalCity = new City(CityType.ADDITIONAL,"additionalCityName");
        cities.add(defaultCity);
        cities.add(additionalCity);

        return cities;
    }

}