import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerMoveRandom implements ComputerMoveHandler {
    private char[][] gameBoard;

    public ComputerMoveRandom(char[][] gameBoard) {
        this.gameBoard = gameBoard;
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

    @Override
    public int[] getNextMove() {
        List<int[]> remainingMoves = getRemainingValidMoves();

        if (remainingMoves.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return remainingMoves.get(random.nextInt(remainingMoves.size()));
    }
}
