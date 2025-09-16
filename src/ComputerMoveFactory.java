public class ComputerMoveFactory {
    public static ComputerMoveHandler getComputerMoveHandler(ComputerDifficultyLevel difficultyLevel, Board gameBoard) {
        return switch (difficultyLevel) {
            case HARD -> new ComputerMoveMinimax(gameBoard);
            case EASY -> new ComputerMoveRandom(gameBoard);
            default -> throw new IllegalArgumentException("Unknown difficulty level: " + difficultyLevel);
        };
    }
}
