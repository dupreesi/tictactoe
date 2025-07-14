public class GameServer {
    private final Console console;
    private final Game game;
    private final Board board;
    private boolean isRunning;

    public GameServer(Console console) {
        this.console = console;
        this.game = new Game(console);
        this.board = game.getBoard();
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
        if (!selectGameMode()) return;

        GameMode gameMode = game.getGameMode();

        if (gameMode == GameMode.PLAYER_VS_COMPUTER) {
            if (!selectComputerDifficultyLevel()) return;
        }

        game.setPlayers(gameMode);

        console.displayMessage(Messages.GAME_START);
        isRunning = true;
    }

    public void start() {
        while (isRunning) {
            Player currentPlayer = game.getCurrentPlayer();
            int[] move = currentPlayer.getMove();
            if (move == null) {
                shutdown();
                return;
            }
            if (!game.processPlayerMove(move[0], move[1])) {
                console.displayMessage(Messages.INVALID_INPUT);
                continue;
            }

            console.displayMessage(Messages.PLAYER_MOVE, currentPlayer.getSymbol(), move[0] + 1, move[1] + 1);
            board.draw(board.getCopy());

            if (game.isGameOver()) {
                console.displayMessage(game.getGameOverMessage());
                shutdown();
            } else {
                game.switchPlayer();
            }
        }
    }


    private boolean selectGameMode() {
        console.displayMessage(Messages.GAME_MODE_PROMPT);

        while (console.promptingForInput()) {
            String choice = console.getInputValue();

            if (listenOnExitCmdAndShutdown(choice)) return false;

            GameMode selected = GameMode.getByCode(choice);
            if (selected == null) {
                console.displayMessage(Messages.INVALID_CHOICE);
                console.displayMessage(Messages.GAME_MODE_PROMPT);
                continue;
            }
            game.setGameMode(selected);
            console.displayMessage(Messages.SELECTED_MODE, selected.getLabel());
            return true;
        }
        return false;
    }

    public boolean selectComputerDifficultyLevel() {
        while (true) {
            console.displayMessage(Messages.DIFFICULTY_PROMPT);
            for (ComputerDifficultyLevel level : ComputerDifficultyLevel.values()) {
                console.displayMessage(Messages.DIFFICULTY_OPTION_FORMAT, level.getCode(), level.getLabel());
            }

            String choice = console.getInputValue();
            if (listenOnExitCmdAndShutdown(choice)) return false;

            ComputerDifficultyLevel level = ComputerDifficultyLevel.getByCode(choice);
            if (level == null) {
                console.displayMessage(Messages.INVALID_CHOICE);
                continue; // retry
            }
            // Sets up the computerMoveHandler which is used to determine the next move depending on the difficulty
            game.setComputerDifficulty(level);
            console.displayMessage(Messages.DIFFICULTY_SELECTED, level.getLabel());
            return true;
        }
    }


    private boolean listenOnExitCmdAndShutdown(String input) {
        if (input.equalsIgnoreCase("exit")) {
            shutdown();
            return true;
        }
        return false;
    }

    public void shutdown() {
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
