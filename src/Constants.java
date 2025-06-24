public class Constants {
    public static final String MSG_INVALID_CHOICE = "Select valid game mode";
    public static final String MSG_SHUTDOWN = "Game server shutting down...";
    public static final String MSG_THINKING = "Thinking...";
    public static final String MSG_INVALID_INPUT = "Invalid input! Enter row and column (e.g., '1 2').";
    public static final String MSG_INVALID_NUMBERS = "Invalid input! Enter numbers only.";
    public static final String MSG_GAME_MODE_PROMPT =
            """
                    Select game mode:
                    1 - Player vs Player
                    2 - Player vs Computer
                    Enter your choice (1 or 2) or type 'exit' to quit:\s""";
    public static final String MSG_DRAW = "The game is a draw!";
    public static final String MSG_PLAYER_WON = "Player %c won!%n";
    public static final String MSG_INPUT_PROMPT = "Player %c, enter numbers for row and column and press Enter (type 'exit' to quit):%n";
    public static final String MSG_GAME_START = "Game server is running...";
    public static final String MSG_PLAYER_MOVE = "Player %c: %d %d%n";
    public static final String MSG_GAMEBOARD_BORDER = "-----------";
    public static final String MSG_GAMEBOARD_CONTENT = " %c | %c | %c %n";
}
