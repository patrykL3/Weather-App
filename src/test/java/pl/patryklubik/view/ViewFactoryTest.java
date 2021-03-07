package pl.patryklubik.view;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.patryklubik.CitiesManager;
import pl.patryklubik.model.LanguageData;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


/**
 * Create by Patryk Łubik on 06.03.2021.
 */

class ViewFactoryTest {

    private ViewFactory viewFactory;
    private LanguageData languageData;

    @Mock
    private CitiesManager citiesManagerMock;
    @Mock
    private Stage stageMock;

    @BeforeAll
    public static void setUpToolkit() {
        try {
            Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        } catch (Exception ignored) {}
    }

    @Test
    public void stageShouldCloseWhenViewFactoryRunCloseMethod(){

        //given
        openMocks(this);
        stageMock = mock(Stage.class);
        citiesManagerMock = mock(CitiesManager.class);
        viewFactory = new ViewFactory(citiesManagerMock);
        languageData = new LanguageData("pl");

        //when
        viewFactory.closeStage(stageMock);

        //then
        verify(stageMock, times(1)).close();
    }

}