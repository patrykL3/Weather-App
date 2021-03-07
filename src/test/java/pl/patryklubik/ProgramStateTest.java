package pl.patryklubik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.patryklubik.model.ApplicationData;
import pl.patryklubik.view.ViewFactory;

import static org.mockito.Mockito.*;

/**
 * Create by Patryk Łubik on 07.03.2021.
 */
class ProgramStateTest {

    private ProgramState programState;

    @Mock
    ViewFactory viewFactoryMock;
    @Mock
    ApplicationData applicationDataMock;

    @BeforeEach
    void setUp() {
        viewFactoryMock = mock(ViewFactory.class);
        applicationDataMock = mock(ApplicationData.class);
        programState = new ProgramState(viewFactoryMock, applicationDataMock, new CitiesManager());
    }


    @Test
    void programShouldShowStarterWindowWhenCitiesDataIsMissing() {

        //given
        doReturn(false).when(applicationDataMock).cityExist(any());

        //when
        programState.init();

        //then
        verify(viewFactoryMock, times(1)).showStarterWindow();
    }

    @Test
    void programShouldShowMainWindowWhenCitiesDataExist() {

        //given
        doReturn(true).when(applicationDataMock).cityExist(any());

        //when
        programState.init();

        //then
        verify(viewFactoryMock, times(1)).showMainWindow();
    }

    @Test
    void programShouldInvokedSaveMethodFromApplicationDataWhenSaveDataIsInvoked() {

        //when
        programState.saveData();

        //then
        verify(applicationDataMock, times(1)).saveData(any());
    }
}