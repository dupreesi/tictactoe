import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerMoveRandom implements ComputerMoveHandler {
    private final Board gameBoard;

    public ComputerMoveRandom(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public List<Integer> getRemainingValidMoves() {
        List<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (gameBoard.isValidMove(i)) {
                validMoves.add(i);
            }
        }
        return validMoves;
    }

    @Override
    public int getNextMove() {
        List<Integer> remainingMoves = getRemainingValidMoves();

        if (remainingMoves.isEmpty()) {
            return -1; // No moves available
        }
        Random random = new Random();
        return remainingMoves.get(random.nextInt(remainingMoves.size()));
    }
}
