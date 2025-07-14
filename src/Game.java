public class Game {
    private final Board board = new Board();
    private final Console console;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameMode gameMode;
    private ComputerMoveHandler computerMoveHandler;

    public Game(Console console) {
        this.console = console;
    }

    public void setPlayers(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
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

    public void setPlayers(GameMode gameMode) {
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

    public static class Messages {
        public static final String DRAW = "The game is a draw!";
        public static final String PLAYER_WON = "Player %c won!%n";
        public static final String CURRENT_PLAYER_NOT_SET = "Current player is not set.";
        public static final String COMPUTER_HANDLER_NOT_INITIALIZED = "Computer move handler is not initialized";
        public static final String COMPUTER_HANDLER_NOT_SET_BEFORE_PLAYER_INIT = "Computer handler not set before player init";
    }
}
