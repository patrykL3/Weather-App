package pl.patryklubik.controller;

import com.github.prominence.openweathermap.api.constants.Language;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.controller.services.WeatherDataService;
import pl.patryklubik.model.*;
import pl.patryklubik.view.ViewFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Create by Patryk Łubik on 01.03.2021.
 */

@ExtendWith(MockitoExtension.class)
class StarterWindowControllerTest {

    private TextField defaultCityField;
    private Label errorLabel;
    private StarterWindowController starterWindowController;

    @Mock
    private ViewFactory viewFactoryMock;
    @Mock
    private CitiesManager citiesManagerMock;

    @Mock
    private WeatherDataService weatherDataServiceMock;


    @BeforeAll
    public static void setUpBeforeAll() {

        LanguageData.init(Language.POLISH);
        try {
            Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        } catch (Exception ignored) {}
    }

    @BeforeEach
    public void setUp() {

        //given
        defaultCityField = new TextField();
        errorLabel = new Label();
        errorLabel.setVisible(false);

        starterWindowController = new StarterWindowController (
                citiesManagerMock,
                viewFactoryMock,
                null,
                defaultCityField,
                errorLabel,
                weatherDataServiceMock
        );
    }

    @Test
    void errorLabelShouldBeVisibleWhenUserTrySubmitCityWithEmptyTextField() {

        //given
        defaultCityField.clear();

        //when
        starterWindowController.defaultCitySelectButtonAction();

        //then
        assertThat(errorLabel.isVisible(), is(true));
    }

    @Test
    void errorLabelShouldNotBeVisibleBecauseOfEmptyFieldWhenUserTrySubmitCityWithNotEmptyTextField() {

        //given
        defaultCityField.setText("cityName");

        //when
        starterWindowController.defaultCitySelectButtonAction();

        //then
        assertThat(errorLabel.isVisible(), is(false));
    }

    @Test
    public void submitCityShouldSetCityNameAndRestartService() {

        //given
        defaultCityField.setText("cityName");

        //when
        starterWindowController.defaultCitySelectButtonAction();

        //then
        InOrder inOrder = inOrder(weatherDataServiceMock);
        inOrder.verify(weatherDataServiceMock).setCityName(any());
        inOrder.verify(weatherDataServiceMock).restart();
    }
}