package pl.patryklubik.controller;

import com.github.prominence.openweathermap.api.constants.Language;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.CityType;
import pl.patryklubik.model.LanguageData;
import pl.patryklubik.view.ViewFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Create by Patryk Łubik on 04.03.2021.
 */
class CitySelectionWindowControllerTest {

    private CitySelectionWindowController citySelectionWindowController;
    private TextField cityField;
    private CitiesManager citiesManager;
    private Label errorLabel;
    private LanguageData languageData;

    @Mock
    private WeatherDataService weatherDataServiceMock;
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

        cityField = new TextField();
        languageData = new LanguageData(Language.POLISH);
        citiesManager = new CitiesManager();
        errorLabel = new Label();
        errorLabel.setVisible(false);
        viewFactoryMock = mock(ViewFactory.class);

        weatherDataServiceMock = mock(WeatherDataService.class);
        citySelectionWindowController = new CitySelectionWindowController(
                citiesManager,
                viewFactoryMock,
                null,
                CityType.DEFAULT,
                cityField,
                errorLabel,
                weatherDataServiceMock
        );
    }

    @Test
    public void submitCityShouldSetCityAndRestartService() {

        //given
        cityField.setText("cityName");

        //when
        citySelectionWindowController.citySelectButtonAction();

        //then
        InOrder inOrder = inOrder(weatherDataServiceMock);
        inOrder.verify(weatherDataServiceMock).setCityName(cityField.getText());
        inOrder.verify(weatherDataServiceMock).restart();
    }


    @Test
    void errorLabelShouldNotBeVisibleBecauseOfEmptyFieldWhenUserTrySubmitCityWithNotEmptyTextField() {

        //given
        cityField.setText("cityName");

        //when
        citySelectionWindowController.citySelectButtonAction();

        //then
        assertThat(errorLabel.isVisible(), is(false));
    }
}