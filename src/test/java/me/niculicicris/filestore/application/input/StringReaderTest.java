package me.niculicicris.filestore.application.input;

import me.niculicicris.filestore.application.console.io.input.StringReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StringReaderTest {
    @Mock
    public Scanner scannerMock;

    @InjectMocks
    private StringReader stringReader;

    @Test
    public void readString_shouldReturnInput() {
        var input = "testInput";

        when(scannerMock.next()).thenReturn(input);
        var readInput = stringReader.readString("Test: ");

        assertEquals(input, readInput);
    }
}
