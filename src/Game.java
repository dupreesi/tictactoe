import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final Player currentPlayer;
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

    public void setComputerDifficultyLevel (ComputerDifficultyLevel level) {
        this.computerDifficultyLevel = level;
    }

    public boolean hasGameMode() {
        return getGameMode() != null;
    }

    public List<int[]> getRemainingValidMoves() {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                if (gameBoard[row][col] == ' ') {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }

    public int[] pickNextComputerMove() {
        // TODO: Implement a smarter AI for HARD difficulty
        List<int[]> remainingMoves = getRemainingValidMoves();

        if (remainingMoves.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return remainingMoves.get(random.nextInt(remainingMoves.size()));
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

    public enum ComputerDifficultyLevel {
        EASY,
        HARD;
        public static ComputerDifficultyLevel getByCode(String input) {
            return switch (input) {
                case "1" -> EASY;
                case "2" -> HARD;
                default -> null;
            };
        }
        public String getLabel() {
            return switch (this) {
                case EASY -> "Easy";
                case HARD -> "Hard";
            };
        }

        public String getCode() {
            return switch (this) {
                case EASY -> "1";
                case HARD -> "2";
            };
        }
    }

    public enum GameMode {
        PLAYER_VS_COMPUTER,
        PLAYER_VS_PLAYER;


        public static GameMode getByCode(String input) {
            return switch (input) {
                case "1" -> PLAYER_VS_PLAYER;
                case "2" -> PLAYER_VS_COMPUTER;
                default -> null;
            };
        }

        public String getLabel() {
            return switch (this) {
                case PLAYER_VS_PLAYER -> "Player vs Player";
                case PLAYER_VS_COMPUTER -> "Player vs Computer";
            };
        }
    }
}





