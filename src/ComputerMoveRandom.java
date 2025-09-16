import java.util.List;
import java.util.Random;

public class ComputerMoveRandom implements ComputerMoveHandler {
    private final Board gameBoard;

    public ComputerMoveRandom(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public int getNextMove() {
        List<Integer> remainingMoves = Utils.getRemainingValidMoves(gameBoard);

        if (remainingMoves.isEmpty()) {
            return -1; // No moves available
        }
        Random random = new Random();
        return remainingMoves.get(random.nextInt(remainingMoves.size()));
    }
}
