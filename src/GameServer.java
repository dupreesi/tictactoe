public class GameServer {
    public final Console console;
    private boolean isRunning;
    private Game game;

    public GameServer(Console console) {
        this.console = console;
        this.game = new Game();
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


//    private void initialize() {
//        selectGameMode();
//        if (!isRunning) return;
//
//        if (!game.hasGameMode()) {
//            console.println("No game mode selected. Exiting...");
//            shutdown();
//            return;
//        }
//
//        if (game.getGameMode() == GameMode.PLAYER_VS_COMPUTER) {
//            selectComputerDifficultyLevel();
//            if (!isRunning) return;
//        }
//
//        console.println(Constants.MSG_GAME_START);
//
//        isRunning = true;
//    }

    private void initialize() {
        if (!selectGameMode()) return;

        if (!game.hasGameMode()) {
            console.println("No game mode selected. Exiting...");
            shutdown();
            return;
        }

        if (game.getGameMode() == GameMode.PLAYER_VS_COMPUTER) {
            if (!selectComputerDifficultyLevel()) return;

            if (!game.hasComputerDifficulty()) {
                console.println("No difficulty selected. Exiting...");
                shutdown();
                return;
            }
        }

        console.println(Constants.MSG_GAME_START);
        isRunning = true;
    }


    public void start() {
        if (!isRunning) return; // game wasn’t initialized properly

        if (game.isComputerTurn()) {
            handleComputerTurn();
        } else {
            promptForPlayerMove();
        }

        while (isRunning && console.hasNextLine()) {
            handlePlayerMove(console.nextLine());
        }
    }


    private void promptForPlayerMove() {
        console.printf(Constants.MSG_INPUT_PROMPT, game.getCurrentPlayerSymbol());
    }

    private void handleComputerTurn() {
        console.println(Constants.MSG_THINKING);

        int[] move = game.getComputerMove();
        game.processPlayerMove(move[0], move[1]);
        console.printf(Constants.MSG_PLAYER_MOVE, game.getCurrentPlayerSymbol(), move[0] + 1, move[1] + 1);
        console.printGameBoard(game.getBoard().getCopy());

        if (checkGameOver()) return;

        game.switchPlayer(); // switch to human
        promptForPlayerMove();
    }

    public void handlePlayerMove(String moveString) {
        if (listenOnExitCmdAndShutdown(moveString)) return;

        int[] move = MoveParser.parse(moveString, console);
        if (move == null || !game.processPlayerMove(move[0], move[1])) {
            console.println(Constants.MSG_INVALID_INPUT);
            return;
        }

        console.printf(Constants.MSG_PLAYER_MOVE, game.getCurrentPlayerSymbol(), move[0] + 1, move[1] + 1);
        console.printGameBoard(game.getBoard().getCopy());

        if (checkGameOver()) return;

        game.switchPlayer();

        if (game.isComputerTurn()) {
            handleComputerTurn();
        } else {
            promptForPlayerMove();
        }
    }

    public boolean checkGameOver() {
        if (game.isGameOver()) {
            console.println(game.getGameOverMessage());
            shutdown();
            return true;
        }
        return false;
    }

    private boolean selectGameMode() {
        console.println(Constants.MSG_GAME_MODE_PROMPT);
        String choice = console.input.nextLine();

        if (listenOnExitCmdAndShutdown(choice)) return false;

        GameMode selected = GameMode.getByCode(choice);
        if (selected == null) {
            console.println(Constants.MSG_INVALID_CHOICE);
            return selectGameMode(); // retry
        }

        game.setGameMode(selected);
        console.println("Selected: " + selected.getLabel());
        return true;
    }

    public boolean selectComputerDifficultyLevel() {
        console.println("Select computer difficulty level:");
        for (ComputerDifficultyLevel level : ComputerDifficultyLevel.values()) {
            console.printf("%s - %s%n", level.getCode(), level.getLabel());
        }

        String choice = console.input.nextLine();
        if (listenOnExitCmdAndShutdown(choice)) return false;

        ComputerDifficultyLevel level = ComputerDifficultyLevel.getByCode(choice);
        if (level == null) {
            console.println(Constants.MSG_INVALID_CHOICE);
            return selectComputerDifficultyLevel(); // retry
        }

        game.setComputerDifficulty(level);
        console.println("Computer difficulty level set to: " + level.getLabel());
        return true;
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
        console.println(Constants.MSG_SHUTDOWN);
        console.close();
    }
}
