package pl.patryklubik.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private CitiesManager citiesManager;
    private String nameFileWithData = "testData.ser";
    private File fileWithData = new File(nameFileWithData);


    @BeforeEach
    public void setUp() {

        citiesManager = new CitiesManager();
        applicationData = new ApplicationData(citiesManager);
        applicationData.setDataLocation(nameFileWithData);

    }

    @AfterEach
    public void clean() {
        fileWithData.delete();
    }

    @Test
    void whenFileWithDataExistsApplicationShouldLoadData() {

        //given
        List<City> cities = returnListOfCities();

        //when
        applicationData.saveData(cities);
        Map dataCitiesMapFromSavedData = applicationData.getCitiesDataMap();

        applicationData = new ApplicationData(new CitiesManager());
        applicationData.loadData();
        Map dataCitiesMapFromLoadedData = applicationData.getCitiesDataMap();

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