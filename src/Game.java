public class Game {
    private final Board board = new Board();
    private final Console console;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameMode gameMode;
    private ComputerMoveHandler computerMoveHandler;
    private boolean isRunning;

    public Game(Console console) {
        this.console = console;
    }

    public void setPlayers(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
    }

    public void selectPlayersByGameMode(GameMode gameMode) {
        Player playerX = new HumanPlayer('X', console);
        Player playerO;
        if (gameMode == GameMode.PLAYER_VS_COMPUTER) {
            if (computerMoveHandler == null) {
                throw new IllegalStateException(Messages.COMPUTER_HANDLER_NOT_SET_BEFORE_PLAYER_INIT);
            }
            playerO = new ComputerPlayer('O', computerMoveHandler);
        } else {
            playerO = new HumanPlayer('O', console);
        }
        setPlayers(playerX, playerO);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public Board getBoard() {
        return board;
    }

    public char getCurrentPlayerSymbol() {
        if (currentPlayer == null) {
            throw new IllegalStateException(Messages.CURRENT_PLAYER_NOT_SET);
        }
        return currentPlayer.getSymbol();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public boolean isGameOver() {
        return isWinningMove() || getBoard().isFull();
    }

    public int[] getComputerMove() {
        if (computerMoveHandler == null) {
            throw new IllegalStateException(Messages.COMPUTER_HANDLER_NOT_INITIALIZED);
        }
        return computerMoveHandler.getNextMove();
    }

    public boolean isWinningMove() {
        char symbol = currentPlayer.getSymbol();
        for (int i = 0; i < 3; i++) {
            if ((board.getCell(i, 0) == symbol && board.getCell(i, 1) == symbol && board.getCell(i, 2) == symbol) ||
                    (board.getCell(0, i) == symbol && board.getCell(1, i) == symbol && board.getCell(2, i) == symbol)) {
                return true;
            }
        }
        return (board.getCell(0, 0) == symbol && board.getCell(1, 1) == symbol && board.getCell(2, 2) == symbol) ||
                (board.getCell(0, 2) == symbol && board.getCell(1, 1) == symbol && board.getCell(2, 0) == symbol);
    }

    public boolean processPlayerMove(int row, int col) {
        if (!getBoard().isValidMove(row, col)) return false;
        getBoard().makeMove(row, col, getCurrentPlayerSymbol());
        return true;
    }

    public String getGameOverMessage() {
        if (isWinningMove()) return String.format(Messages.PLAYER_WON, getCurrentPlayerSymbol());
        if (getBoard().isFull()) return Messages.DRAW;
        return "";
    }

    public void setComputerDifficulty(ComputerDifficultyLevel level) {
        this.computerMoveHandler = ComputerMoveFactory.getComputerMoveHandler(level, this.getBoard());
    }

    public void play() {
        isRunning = true;
        while (isRunning) {
            Player currentPlayer = getCurrentPlayer();
            int[] move = currentPlayer.getMove();
            if (move == null) {
                isRunning = false;
                break;
            }
            if (!processPlayerMove(move[0], move[1])) {
                console.displayMessage(GameServer.Messages.INVALID_INPUT);
                continue;
            }

            console.displayMessage(GameServer.Messages.PLAYER_MOVE, currentPlayer.getSymbol(), move[0] + 1, move[1] + 1);
            board.draw(board.getCopy());

            if (isGameOver()) {
                console.displayMessage(getGameOverMessage());
                break;
            } else {
                switchPlayer();
            }
        }
    }

    public boolean selectGameMode() {
        console.displayMessage(GameServer.Messages.GAME_MODE_PROMPT);

        while (console.promptingForInput()) {
            String choice = console.getInputValue();

            if ("exit".equalsIgnoreCase(choice)) {
                return false;
            }

            GameMode selected = GameMode.getByCode(choice);
            if (selected == null) {
                console.displayMessage(GameServer.Messages.INVALID_CHOICE);
                console.displayMessage(GameServer.Messages.GAME_MODE_PROMPT);
                continue;
            }
            setGameMode(selected);
            console.displayMessage(GameServer.Messages.SELECTED_MODE, selected.getLabel());
            return true;
        }
        return false;
    }

    public boolean selectComputerDifficultyLevel() {
        console.displayMessage(GameServer.Messages.DIFFICULTY_PROMPT);
        while (true) {
            for (ComputerDifficultyLevel level : ComputerDifficultyLevel.values()) {
                console.displayMessage(GameServer.Messages.DIFFICULTY_OPTION_FORMAT, level.getCode(), level.getLabel());
            }

            String choice = console.getInputValue();
            if ("exit".equalsIgnoreCase(choice)) {
                return false;
            }

            ComputerDifficultyLevel level = ComputerDifficultyLevel.getByCode(choice);
            if (level == null) {
                console.displayMessage(GameServer.Messages.INVALID_CHOICE);
                continue; // retry
            }
            setComputerDifficulty(level);
            console.displayMessage(GameServer.Messages.DIFFICULTY_SELECTED, level.getLabel());
            return true;
        }
    }


    public static class Messages {
        public static final String DRAW = "The game is a draw!";
        public static final String PLAYER_WON = "Player %c won!%n";
        public static final String CURRENT_PLAYER_NOT_SET = "Current player is not set.";
        public static final String COMPUTER_HANDLER_NOT_INITIALIZED = "Computer move handler is not initialized";
        public static final String COMPUTER_HANDLER_NOT_SET_BEFORE_PLAYER_INIT = "Computer handler not set before player init";
    }
}
