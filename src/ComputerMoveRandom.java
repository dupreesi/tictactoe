import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerMoveRandom implements ComputerMoveHandler {
    private final Board gameBoard;

    public ComputerMoveRandom(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public List<int[]> getRemainingValidMoves() {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {            // board size is 3x3, so loop from 0 to 2
            for (int col = 0; col < 3; col++) {
                if (gameBoard.getCell(row, col) == ' ') {   // use getCell() instead of array access
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
