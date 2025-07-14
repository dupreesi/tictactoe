public class GameServer {
    private final Console console;
    private final Game game;
    private boolean isRunning;

    public GameServer(Console console) {
        this.console = console;
        this.game = new Game(console);
    }

    public static void main(String[] args) {
        launchMainWithConsole(new Console());
    }

    public static void launchMainWithConsole(Console console) {
        GameServer server = new GameServer(console);
        server.initialize();

        if (server.isRunning()) {
            server.start();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void initialize() {
        if (!game.selectGameMode()) {
            shutdown();
            return;
        }

        if (game.getGameMode() == GameMode.PLAYER_VS_COMPUTER) {
            if (!game.selectComputerDifficultyLevel()) {
                shutdown();
                return;
            }
        }

        game.selectPlayersByGameMode(game.getGameMode());
        console.displayMessage(Messages.GAME_START);
        isRunning = true;
    }


    public void start() {
        game.play();
        shutdown();
    }

    public void shutdown() {
        if (!isRunning) return;
        isRunning = false;
        console.displayMessage(Messages.SHUTDOWN);
        console.close();
    }

    public class Messages {
        public static final String INVALID_CHOICE = "Select valid game mode";
        public static final String SHUTDOWN = "Game server shutting down...";
        public static final String THINKING = "Thinking...";
        public static final String INVALID_INPUT = "Invalid input! Enter row and column (e.g., '1 2').";

        public static final String GAME_MODE_PROMPT =
                """
                        Select game mode:
                        1 - Player vs Player
                        2 - Player vs Computer
                        Enter your choice (1 or 2) or type 'exit' to quit:\s""";

        public static final String GAME_START = "Game server is running...";
        public static final String PLAYER_MOVE = "Player %c: %d %d%n";
        public static final String INPUT_PROMPT = "Player %c, enter numbers for row and column and press Enter (type 'exit' to quit):%n";

        public static final String DIFFICULTY_PROMPT = "Select computer difficulty level:";
        public static final String DIFFICULTY_OPTION_FORMAT = "%s - %s%n";
        public static final String DIFFICULTY_SELECTED = "Computer difficulty level set to: %s";
        public static final String SELECTED_MODE = "Selected: %s";
    }

}
