import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach
    void setup() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    @Test
    void shouldDisplaySimpleMessage() {
        Console console = new Console(System.in, printStream);
        console.displayMessage("Hello World");
        assertTrue(outputStream.toString().contains("Hello World"));
    }

    @Test
    void shouldDisplayFormattedMessage() {
        Console console = new Console(System.in, printStream);
        console.displayMessage("Score: %d - %s", 42, "OK");
        assertTrue(outputStream.toString().contains("Score: 42 - OK"));
    }

    @Test
    void shouldGetInputValue() {
        String userInput = "Player1\n";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Console console = new Console(inputStream, printStream);

        String input = console.getInputValue();
        assertEquals("Player1", input);
    }

    @Test
    void shouldReturnTrueWhenPromptingInput() {
        String input = "next line\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Console console = new Console(inputStream, printStream);

        assertTrue(console.promptingForInput());
    }

    @Test
    void shouldReturnFalseWhenInputClosed() {
        String input = "next line\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Console console = new Console(inputStream, printStream);
        console.close();

        assertFalse(console.promptingForInput());
    }

    @Test
    void shouldCloseInputWithoutError() {
        Console console = new Console(System.in, printStream);
        assertDoesNotThrow(console::close);
    }
}
