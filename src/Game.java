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

    public int getComputerMove() {
        if (computerMoveHandler == null) {
            throw new IllegalStateException(Messages.COMPUTER_HANDLER_NOT_INITIALIZED);
        }
        return computerMoveHandler.getNextMove();
    }

    public boolean isWinningMove() {
        char symbol = currentPlayer.getSymbol();
        // Check rows: 0-2, 3-5, 6-8
        if ((board.getCell(0) == symbol && board.getCell(1) == symbol && board.getCell(2) == symbol) ||
            (board.getCell(3) == symbol && board.getCell(4) == symbol && board.getCell(5) == symbol) ||
            (board.getCell(6) == symbol && board.getCell(7) == symbol && board.getCell(8) == symbol)) {
            return true;
        }
        // Check columns: 0-3-6, 1-4-7, 2-5-8
        if ((board.getCell(0) == symbol && board.getCell(3) == symbol && board.getCell(6) == symbol) ||
            (board.getCell(1) == symbol && board.getCell(4) == symbol && board.getCell(7) == symbol) ||
            (board.getCell(2) == symbol && board.getCell(5) == symbol && board.getCell(8) == symbol)) {
            return true;
        }
        // Check diagonals: 0-4-8, 2-4-6
        return (board.getCell(0) == symbol && board.getCell(4) == symbol && board.getCell(8) == symbol) ||
               (board.getCell(2) == symbol && board.getCell(4) == symbol && board.getCell(6) == symbol);
    }

    public boolean processPlayerMove(int position) {
        if (!getBoard().isValidMove(position)) return false;
        getBoard().makeMove(position, getCurrentPlayerSymbol());
        return true;
    }

    public String getGameOverMessage() {
        if (isWinningMove()) return String.format(Messages.PLAYER_WON, getCurrentPlayerSymbol());
        if (getBoard().isFull()) return Messages.DRAW;
        return "";
    }

    public void play() {
        isRunning = true;
        while (isRunning) {
            Player currentPlayer = getCurrentPlayer();
            int move = currentPlayer.getMove();
            if (move == -1) {
                isRunning = false;
                break;
            }
            if (!processPlayerMove(move)) {
                console.displayMessage(GameServer.Messages.INVALID_INPUT);
                continue;
            }

            console.displayMessage(GameServer.Messages.PLAYER_MOVE, currentPlayer.getSymbol(), move + 1);
            board.draw();

            if (isGameOver()) {
                console.displayMessage(getGameOverMessage());
                break;
            } else {
                switchPlayer();
            }
        }
    }

    public boolean setup() {
        if (!selectGameMode()) {
            return false;
        }

        if (getGameMode() == GameMode.PLAYER_VS_COMPUTER) {
            if (!selectComputerDifficultyLevel()) {
                return false;
            }
        }

        selectPlayersByGameMode(getGameMode());
        return true;
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

    public void setComputerDifficulty(ComputerDifficultyLevel level) {
        this.computerMoveHandler = ComputerMoveFactory.getComputerMoveHandler(level, this.getBoard());
    }

    public static class Messages {
        public static final String DRAW = "The game is a draw!";
        public static final String PLAYER_WON = "Player %c won!%n";
        public static final String CURRENT_PLAYER_NOT_SET = "Current player is not set.";
        public static final String COMPUTER_HANDLER_NOT_INITIALIZED = "Computer move handler is not initialized";
        public static final String COMPUTER_HANDLER_NOT_SET_BEFORE_PLAYER_INIT = "Computer handler not set before player init";
    }
}
