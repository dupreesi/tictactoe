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
            GameModeHandler processGameModeHandler = GameModeFactory.getGameModeHandler(server.game.getGameMode());
            processGameModeHandler.handle(server);
        } else {
            console.println("No game mode selected. Exiting...");
            server.shutdown();
        }
    }

    // For testing with mock console
    public static void launchMainWithConsole(Console console) {
        runGameServer(console);
    }

    public void selectGameMode() {
        console.println(Constants.MSG_GAME_MODE_PROMPT);

        String choice = console.input.nextLine();

        if (listenOnExitCmdAndShutdown(choice)) return;

        GameMode selectedMode = GameMode.getByCode(choice);

        if (selectedMode == null) {
            console.println(Constants.MSG_INVALID_CHOICE);
            selectGameMode();
        } else {
            game.setGameMode(selectedMode);
            console.println("Selected: " + selectedMode.getLabel());
        }
    }

    public void selectComputerDifficultyLevel() {
        console.println("Select computer difficulty level:");
        for (ComputerDifficultyLevel difficultyLevel : ComputerDifficultyLevel.values()) {
            console.printf("%s - %s%n", difficultyLevel.getCode(), difficultyLevel.getLabel());
        }
        String choice = console.input.nextLine();
        if (listenOnExitCmdAndShutdown(choice)) return;
        ComputerDifficultyLevel level = ComputerDifficultyLevel.getByCode(choice);
        if (level == null) {
            console.println(Constants.MSG_INVALID_CHOICE);
            selectComputerDifficultyLevel();
            return;
        }
        game.setComputerDifficultyLevel(level);
        console.println("Computer difficulty level set to: " + level.getLabel());
    }

    public void handlePlayerMove(String moveString) {
        if (listenOnExitCmdAndShutdown(moveString)) return;

        int[] move = parseMove(moveString);

        processMove(move);

        if (isComputerPlayersTurn()) {
            console.println(Constants.MSG_THINKING);
            int[] randomMove = game.pickNextComputerMove();
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
            console.println(Constants.MSG_INVALID_INPUT);
            return;
        }

        game.makeMove(row, col);
        console.printf(Constants.MSG_PLAYER_MOVE, game.getCurrentPlayer(), move[0] + 1, move[1] + 1);
        console.printGameBoard(game.getGameBoard());

        if (checkGameOver()) return;

        game.switchPlayer();
        console.printf(Constants.MSG_INPUT_PROMPT, game.getCurrentPlayer());
    }

    // extract class for validation and parsing
    private int[] parseMove(String moveString) {
        String[] parts = moveString.split(" ");

        if (parts.length != 2) {
            console.println(Constants.MSG_INVALID_NUMBERS);
            return null;
        }

        try {
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            return new int[]{row, col};
        } catch (NumberFormatException e) {
            console.println(Constants.MSG_INVALID_NUMBERS);
            return null;
        }
    }

    public boolean checkGameOver() {
        if (game.detectWinner()) {
            console.printf(Constants.MSG_PLAYER_WON, game.getCurrentPlayer());
            shutdown();
            return true;
        }

        if (game.boardIsFull()) {
            console.println(Constants.MSG_DRAW);
            shutdown();
            return true;
        }
        return false;
    }

    public void shutdown() {
        isRunning = false;
        console.println(Constants.MSG_SHUTDOWN);
        console.close();
    }

    public void start() {
        isRunning = true;
        console.println(Constants.MSG_GAME_START);
        console.printf(Constants.MSG_INPUT_PROMPT, game.getCurrentPlayer());
        while (isRunning && (console.hasNextLine() || isComputerPlayersTurn())) {
            handlePlayerMove(console.nextLine());
        }
    }

    private boolean isComputerPlayersTurn() {
        return game.getGameMode() == GameMode.PLAYER_VS_COMPUTER && game.getCurrentPlayer() == 'O';
    }
}
