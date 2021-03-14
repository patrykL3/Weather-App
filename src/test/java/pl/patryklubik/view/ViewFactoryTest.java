package pl.patryklubik.view;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.patryklubik.CitiesManager;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


/**
 * Create by Patryk Łubik on 06.03.2021.
 */

class ViewFactoryTest {

    @BeforeAll
    public static void setUpToolkit() {
        try {
            Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        } catch (Exception ignored) {}
    }

    @Test
    public void stageShouldCloseWhenViewFactoryRunCloseMethod(){

        //given
        Stage stageMock = mock(Stage.class);
        CitiesManager citiesManagerMock = mock(CitiesManager.class);
        openMocks(this);
        ViewFactory viewFactory = new ViewFactory(citiesManagerMock);

        //when
        viewFactory.closeStage(stageMock);

        //then
        verify(stageMock, times(1)).close();
    }

}