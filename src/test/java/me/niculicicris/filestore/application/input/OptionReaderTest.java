package me.niculicicris.filestore.application.input;

import me.niculicicris.filestore.application.console.input.OptionReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OptionReaderTest {
    @Mock
    private Scanner scannerMock;

    @InjectMocks
    private OptionReader optionReader;

    @Test
    public void readOption_onFirstTry_shouldReturnOption() {
        var expectedOption = 1;
        var optionsNumber = 1;

        when(scannerMock.nextInt()).thenReturn(expectedOption);
        var option = optionReader.readOption(optionsNumber);

        assertEquals(expectedOption, option);
    }

    @Test
    public void readOption_onSecondTry_shouldReturnOption() {
        var firstOption = 2;
        var secondOption = 1;
        var optionsNumber = 1;

        when(scannerMock.nextInt()).thenReturn(firstOption).thenReturn(secondOption);
        var option = optionReader.readOption(optionsNumber);

        assertEquals(secondOption, option);
    }
}
