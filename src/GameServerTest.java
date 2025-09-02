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
        Console console = consoleWithInput(input);
        GameServer.launchMainWithConsole(console);
        return outputStream.toString();
    }

    private Console consoleWithInput(String input) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        return new Console(testIn, printStream);
    }

    private void assertOutputContains(String output, String... messages) {
        for (String message : messages) {
            assertTrue(output.contains(message), "Expected output to contain: " + message);
        }
    }

    @Test
    void shouldRunGameServerViaMainLogic() {
        String output = runGameWithInput("1\n1\n2\nexit");
        assertOutputContains(output,
                GameServer.Messages.GAME_MODE_PROMPT,
                "Player vs Player",
                GameServer.Messages.GAME_START,
                "Player X: 1",
                "Player O: 2",
                GameServer.Messages.SHUTDOWN);
    }

    @Test
    void shouldNotProcessPositionBiggerThanNine() {
        String output = runGameWithInput("1\n10\nexit");
        assertOutputContains(output, GameServer.Messages.INVALID_INPUT);
    }

    @Test
    void shouldNotAllowDuplicatePositions() {
        String output = runGameWithInput("1\n1\n1\nexit");
        assertOutputContains(output,
                "Player X: 1",
                GameServer.Messages.INVALID_INPUT);
    }

    @Test
    void shouldDrawGameBoard() {
        // Fill the board completely for a draw: positions 1,2,3,4,5,6,7,8,9 (user input) = positions 0,1,2,3,4,5,6,7,8 (internal)
        String input = "1\n1\n2\n3\n4\n6\n5\n7\n9\n8\nexit";
        String output = runGameWithInput(input);

        assertOutputContains(output,
                "Player X: 1", "Player O: 2", "Player X: 3",
                "Player O: 4", "Player X: 6", "Player O: 5",
                "Player X: 7", "Player O: 9", "Player X: 8",
                Game.Messages.DRAW);
    }

    @Test
    void shouldHandleShutdownCommand() {
        String output = runGameWithInput("exit");
        assertOutputContains(output, GameServer.Messages.SHUTDOWN);
    }

    @Test
    void shouldHandleComputerModeSelectEasy() {
        String output = runGameWithInput("2\n1\n1\nexit");
        assertOutputContains(output,
                "Selected: Player vs Computer",
                "Select computer difficulty level:",
                "Computer difficulty level set to: Easy");
    }

    @Test
    void shouldHandlePlayerModeSelect() {
        String output = runGameWithInput("1\nexit");
        assertOutputContains(output, "Player vs Player");
    }

    @Test
    void shouldRetryGameModeSelectionOnInvalidInput() {
        String output = runGameWithInput("invalid\n1\nexit");
        assertOutputContains(output,
                GameServer.Messages.INVALID_CHOICE,
                "Player vs Player");
    }

    @Test
    void shouldRetryComputerDifficultySelectionOnInvalidInput() {
        String output = runGameWithInput("2\ninvalid\n1\nexit");
        assertOutputContains(output,
                "Select computer difficulty level:",
                GameServer.Messages.INVALID_CHOICE,
                "Computer difficulty level set to: Easy");
    }
}
