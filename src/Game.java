import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final Player currentPlayer;
    // todo add GameBoard class
    private char[][] gameBoard;
    private GameMode gameMode;
    private ComputerDifficultyLevel computerDifficultyLevel;


    public Game() {
        this.currentPlayer = new Player('X');
        this.gameMode = null;
        this.computerDifficultyLevel = null;
        initialiseBoard();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setComputerDifficultyLevel(ComputerDifficultyLevel level) {
        this.computerDifficultyLevel = level;
    }

    public boolean hasGameMode() {
        return getGameMode() != null;
    }

    public int[] pickNextComputerMove() {
        ComputerMoveHandler computerMoveHandler = ComputerMoveFactory.getComputerMoveHandler(computerDifficultyLevel, gameBoard);
        return computerMoveHandler.getNextMove();
    }

    public void initialiseBoard() {
        gameBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }

    public boolean detectWinner() {
        for (int i = 0; i < 3; i++) {
            if ((checkPlayerCell(i, 0) && checkPlayerCell(i, 1) && checkPlayerCell(i, 2)) ||
                    (checkPlayerCell(0, i) && checkPlayerCell(1, i) && checkPlayerCell(2, i))) {
                return true;
            }
        }
        return (checkPlayerCell(0, 0) && checkPlayerCell(1, 1) && checkPlayerCell(2, 2)) ||
                (checkPlayerCell(0, 2) && checkPlayerCell(1, 1) && checkPlayerCell(2, 0));
    }

    private boolean checkPlayerCell(int row, int col) {
        return gameBoard[row][col] == currentPlayer.getType();
    }

    public boolean boardIsFull() {
        for (char[] row : gameBoard) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(int row, int col) {
        gameBoard[row][col] = currentPlayer.getType();
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && gameBoard[row][col] == ' ';
    }

    public void switchPlayer() {
        currentPlayer.switchType();
    }


    public char[][] getGameBoard() {
        return gameBoard;
    }

    public char getCurrentPlayer() {
        return currentPlayer.getType();
    }
}





