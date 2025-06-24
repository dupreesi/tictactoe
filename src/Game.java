public class Game {

    private final Board board;
    private PlayerType currentPlayer;
    private GameMode gameMode;
    private ComputerDifficultyLevel difficulty;


    public Game() {
        this.board = new Board();
        this.currentPlayer = PlayerType.X;
    }

    public Board getBoard() {
        return board;
    }

    public char getCurrentPlayerSymbol() {
        return currentPlayer.getSymbol();
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.switchType();
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public boolean hasGameMode() {
        return getGameMode() != null;
    }

    public void setComputerDifficulty(ComputerDifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isGameOver() {
        return isWinningMove() || getBoard().isFull();
    }

    public boolean isComputerTurn() {
        return getGameMode() == GameMode.PLAYER_VS_COMPUTER && currentPlayer == PlayerType.O;
    }

    public int[] getComputerMove() {
        ComputerMoveHandler handler = ComputerMoveFactory.getComputerMoveHandler(difficulty, board);
        return handler.getNextMove();
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
        if (isWinningMove()) return String.format(Constants.MSG_PLAYER_WON, getCurrentPlayerSymbol());
        if (getBoard().isFull()) return Constants.MSG_DRAW;
        return "";
    }

    public boolean hasComputerDifficulty() {
        return difficulty != null;
    }

}





