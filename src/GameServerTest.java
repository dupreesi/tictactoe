import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameServerTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }


    private String runGameWithInput(String input) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        Console console = new Console(testIn, printStream);
        GameServer.launchMainWithConsole(console);
        return outputStream.toString();
    }

    private void assertOutputContains(String output, String... messages) {
        for (String message : messages) {
            assertTrue(output.contains(message), "Expected output to contain: " + message);
        }
    }

    @Test
    void shouldRunGameServerViaMainLogic() {
        String output = runGameWithInput("1\n1 1\n1 2\nexit");
        assertOutputContains(output,
                Constants.MSG_GAME_MODE_PROMPT,
                "Player vs Player",
                Constants.MSG_GAME_START,
                "Player X: 1 1",
                "Player O: 1 2",
                Constants.MSG_SHUTDOWN);
    }

    @Test
    void shouldNotProcessPositionBiggerThree() {
        String output = runGameWithInput("1\n1 4");
        assertOutputContains(output, Constants.MSG_INVALID_INPUT);
    }

    @Test
    void shouldNotAllowDuplicatePositions() {
        String output = runGameWithInput("1\n1 1\n1 1");
        assertOutputContains(output,
                "Player X: 1 1",
                Constants.MSG_INVALID_INPUT);
    }

    @Test
    void shouldDrawGameBoard() {
        String input = "1\n1 1\n1 2\n1 3\n2 1\n2 3\n2 2\n3 1\n3 3\n3 2";
        String output = runGameWithInput(input);

        assertOutputContains(output,
                "Player X: 1 1", "Player O: 1 2", "Player X: 1 3",
                "Player O: 2 1", "Player X: 2 3", "Player O: 2 2",
                "Player X: 3 1", "Player O: 3 3", "Player X: 3 2",
                Constants.MSG_DRAW);

        String expectedBoard = " X | O | X \n O | O | X \n X | X | O ";
        assertTrue(output.contains(expectedBoard));
    }

    @Test
    void shouldHandleShutdownCommand() {
        String output = runGameWithInput("exit");
        assertOutputContains(output, Constants.MSG_SHUTDOWN);
    }

    @Test
    void shouldHandleComputerModeSelectEasy() {
        String output = runGameWithInput("2\n1 \n1");
        assertOutputContains(output, "Selected: Player vs Computer", "Select computer difficulty level:", "Computer difficulty level set to: Easy");
    }

    @Test
    void shouldHandlePlayerModeSelect() {
        String output = runGameWithInput("1");
        assertOutputContains(output, "Player vs Player");
    }

    @Test
    void shouldRetryGameModeSelectionOnInvalidInput() {
        String output = runGameWithInput("invalid\n1");
        assertOutputContains(output, 
            Constants.MSG_INVALID_CHOICE,
            "Player vs Player");
    }

    @Test
    void shouldRetryComputerDifficultySelectionOnInvalidInput() {
        String output = runGameWithInput("2\ninvalid\n1");
        assertOutputContains(output,
            "Select computer difficulty level:",
            Constants.MSG_INVALID_CHOICE,
            "Computer difficulty level set to: Easy");
    }
}

