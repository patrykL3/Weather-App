package pl.patryklubik.controller;

import com.github.prominence.openweathermap.api.constants.Language;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.LanguageData;
import pl.patryklubik.view.ViewFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Create by Patryk Łubik on 05.03.2021.
 */
class MainWindowControllerTest {

    private MainWindowController mainWindowController;
    private CitiesManager citiesManager;
    private LanguageData languageData;

    @Mock
    private ViewFactory viewFactoryMock;

    @BeforeAll
    public static void setUpToolkit() {
        try {
            Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        } catch (Exception ignored) {}
    }

    @BeforeEach
    public void setUp() {

        viewFactoryMock = mock(ViewFactory.class);
        languageData = new LanguageData(Language.POLISH);
        citiesManager = new CitiesManager();

        mainWindowController = new MainWindowController(
                citiesManager,
                viewFactoryMock,
                null
        );
    }

    @Test
    void setHomeLocationActionShouldOpenCitySelectionWindowWithDefaultCity() {

        //given
        CityType cityType = CityType.DEFAULT;

        //when
        mainWindowController.setHomeLocationAction(any());

        //then
        verify(viewFactoryMock, Mockito.times(1)).showCitySelectionWindow(cityType);
    }

    @Test
    void setSecondLocationActionShouldOpenCitySelectionWindowWithSecondCity() {

        //given
        CityType cityType = CityType.ADDITIONAL;

        //when
        mainWindowController.setSecondLocationAction(any());

        //then
        verify(viewFactoryMock, Mockito.times(1)).showCitySelectionWindow(cityType);
    }
}