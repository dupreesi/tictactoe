public class GameServer {
    public final Console console;
    private final Game game;
    private boolean isRunning;

    public GameServer(Console console) {
        this.game = new Game();
        this.console = console;
    }

    public static void main(String[] args) {
        runGameServer(new Console());
    }

    private static void runGameServer(Console console) {
        GameServer server = new GameServer(console);
        server.selectGameMode();
        if (server.game.hasGameMode()) {
            server.start();
        }
    }

    // For testing with mock console
    public static void launchMainWithConsole(Console console) {
        runGameServer(console);
    }

    public void selectGameMode() {
        console.println(Messages.GAME_MODE_PROMPT);

        String choice = console.input.nextLine();

        if (listenOnExitCmdAndShutdown(choice)) return;

        Game.GameMode selectedMode = Game.GameMode.getByCode(choice);

        if (selectedMode == null) {
            console.println(Messages.INVALID_CHOICE);
            selectGameMode();
        } else {
            game.setGameMode(selectedMode);
            console.println("Selected: " + selectedMode.getLabel());
        }
    }

    public void handlePlayerMove(String moveString) {
        if (listenOnExitCmdAndShutdown(moveString)) return;

        int[] move = parseMove(moveString);

        processMove(move);

        if (isComputerPlayersTurn()) {
            console.println(Messages.THINKING);
            int[] randomMove = game.pickRandomMoveArray();
            processMove(randomMove);
        }
    }

    private boolean listenOnExitCmdAndShutdown(String input) {
        if (input.equalsIgnoreCase("exit")) {
            shutdown();
            return true;
        }
        return false;
    }

    private void processMove(int[] move) {
        if (move == null) return;

        int row = move[0];
        int col = move[1];

        if (!game.isValidMove(row, col)) {
            console.println(Messages.INVALID_INPUT);
            return;
        }

        game.makeMove(row, col);
        console.printf(Messages.PLAYER_MOVE, game.getCurrentPlayer(), move[0] + 1, move[1] + 1);
        console.printGameBoard(game.getGameBoard());

        if (checkGameOver()) return;

        game.switchPlayer();
        console.printf(Messages.INPUT_PROMPT, game.getCurrentPlayer());
    }

    // extract class for validation and parsing
    private int[] parseMove(String moveString) {
        String[] parts = moveString.split(" ");

        if (parts.length != 2) {
            console.println(Messages.INVALID_NUMBERS);
            return null;
        }

        try {
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            return new int[]{row, col};
        } catch (NumberFormatException e) {
            console.println(Messages.INVALID_NUMBERS);
            return null;
        }
    }

    public boolean checkGameOver() {
        if (game.detectWinner()) {
            console.printf(Messages.PLAYER_WON, game.getCurrentPlayer());
            shutdown();
            return true;
        }

        if (game.boardIsFull()) {
            console.println(Messages.DRAW_MSG);
            shutdown();
            return true;
        }
        return false;
    }

    public void shutdown() {
        isRunning = false;
        console.println(Messages.SHUTDOWN_MSG);
        console.close();
    }

    public void start() {
        isRunning = true;
        console.println(Messages.GAME_START);
        console.printf(Messages.INPUT_PROMPT, game.getCurrentPlayer());
        while (isRunning && (console.hasNextLine() || isComputerPlayersTurn())) {
            handlePlayerMove(console.nextLine());
        }
    }

    private boolean isComputerPlayersTurn() {
        return game.getGameMode() == Game.GameMode.PLAYER_VS_COMPUTER && game.getCurrentPlayer() == 'O';
    }

    public static class Messages {
        public static final String INVALID_CHOICE = "Select valid game mode";
        public static final String SHUTDOWN_MSG = "Game server shutting down...";
        public static final String THINKING = "Thinking...";
        public static final String INVALID_INPUT = "Invalid input! Enter row and column (e.g., '1 2').";
        public static final String INVALID_NUMBERS = "Invalid input! Enter numbers only.";
        public static final String GAME_MODE_PROMPT =
                """
                        Select game mode:
                        1 - Player vs Player
                        2 - Player vs Computer
                        Enter your choice (1 or 2) or type 'exit' to quit:\s""";
        public static final String DRAW_MSG = "The game is a draw!";
        public static final String PLAYER_WON = "Player %c won!%n";
        public static final String INPUT_PROMPT = "Player %c, enter numbers for row and column and press Enter (type 'exit' to quit):%n";
        public static final String GAME_START = "Game server is running...";
        public static final String PLAYER_MOVE = "Player %c: %d %d%n";
    }
}
