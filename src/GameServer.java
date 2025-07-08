public class GameServer {
    private final Console console;
    private final Game game;
    private final Board board;
    private boolean isRunning;

    public GameServer(Console console) {
        this.console = console;
        this.game = new Game();
        this.board = new Board();
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

        if (!game.hasGameMode()) {
            console.displayMessage("No game mode selected. Exiting...");
            shutdown();
            return;
        }

        if (game.getGameMode() == GameMode.PLAYER_VS_COMPUTER) {
            if (!selectComputerDifficultyLevel()) return;

            if (!game.hasComputerDifficulty()) {
                console.displayMessage("No difficulty selected. Exiting...");
                shutdown();
                return;
            }
        }

        console.displayMessage(Constants.MSG_GAME_START);
        isRunning = true;
    }


    public void start() {
        if (!isRunning) return; // game wasn’t initialized properly

        if (game.isComputerTurn()) {
            handleComputerTurn();
        } else {
            promptForPlayerMove();
        }

        while (isRunning && console.promptingForInput()) {
            handlePlayerMove(console.getInputValue());
        }
    }


    private void promptForPlayerMove() {
        console.displayMessage(Constants.MSG_INPUT_PROMPT, game.getCurrentPlayerSymbol());
    }

    private void handleComputerTurn() {
        console.displayMessage(Constants.MSG_THINKING);

        int[] move = game.getComputerMove();
        game.processPlayerMove(move[0], move[1]);
        console.displayMessage(Constants.MSG_PLAYER_MOVE, game.getCurrentPlayerSymbol(), move[0] + 1, move[1] + 1);
        board.draw(game.getBoard().getCopy());

        if (checkGameOver()) return;

        game.switchPlayer(); // switch to human
        promptForPlayerMove();
    }

    public void handlePlayerMove(String moveString) {
        if (listenOnExitCmdAndShutdown(moveString)) return;

        int[] move = MoveParser.parse(moveString, console);
        if (move == null || !game.processPlayerMove(move[0], move[1])) {
            console.displayMessage(Constants.MSG_INVALID_INPUT);
            return;
        }

        console.displayMessage(Constants.MSG_PLAYER_MOVE, game.getCurrentPlayerSymbol(), move[0] + 1, move[1] + 1);
        board.draw(game.getBoard().getCopy());

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
            console.displayMessage(game.getGameOverMessage());
            shutdown();
            return true;
        }
        return false;
    }

    private boolean selectGameMode() {

//        while (console.promptMode()) {
//
//        }


        while (true) {
            console.displayMessage(Constants.MSG_GAME_MODE_PROMPT);
            // demeter violation, lack of encapsulation of public members (input, output)
            String choice = console.getInputValue();

            if (listenOnExitCmdAndShutdown(choice)) return false;

            GameMode selected = GameMode.getByCode(choice);
            if (selected == null) {
                console.displayMessage(Constants.MSG_INVALID_CHOICE);
                continue; // retry
            }

            game.setGameMode(selected);
            console.displayMessage("Selected: " + selected.getLabel());
            return true;
        }
    }

    public boolean selectComputerDifficultyLevel() {
        while (true) {
            console.displayMessage("Select computer difficulty level:");
            for (ComputerDifficultyLevel level : ComputerDifficultyLevel.values()) {
                console.displayMessage("%s - %s%n", level.getCode(), level.getLabel());
            }

            String choice = console.getInputValue();
            if (listenOnExitCmdAndShutdown(choice)) return false;

            ComputerDifficultyLevel level = ComputerDifficultyLevel.getByCode(choice);
            if (level == null) {
                console.displayMessage(Constants.MSG_INVALID_CHOICE);
                continue; // retry
            }

            game.setComputerDifficulty(level);
            console.displayMessage("Computer difficulty level set to: " + level.getLabel());
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
        console.displayMessage(Constants.MSG_SHUTDOWN);
        console.close();
    }
}
