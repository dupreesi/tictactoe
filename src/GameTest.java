import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(new Console());
        Player playerX = new HumanPlayer('X', new Console());
        Player playerO = new HumanPlayer('O', new Console());
        game.setPlayers(playerX, playerO);
    }

    @Test
    void testValidMove() {
        assertTrue(game.getBoard().isValidMove(1, 2));
    }

    @Test
    void testMakeMove() {
        game.getBoard().makeMove(1, 2, game.getCurrentPlayerSymbol());
        assertEquals('X', game.getBoard().getCell(1, 2));
    }

    @Test
    void testMoveOutOfBounds() {
        assertFalse(game.getBoard().isValidMove(-1, 0)); // Negative row
        assertFalse(game.getBoard().isValidMove(0, -1)); // Negative column
        assertFalse(game.getBoard().isValidMove(3, 0));  // Row too high
        assertFalse(game.getBoard().isValidMove(0, 3));  // Column too high
    }

    @Test
    void testMoveOnOccupiedCell() {
        game.getBoard().makeMove(1, 1, game.getCurrentPlayerSymbol()); // First move (valid)
        assertFalse(game.getBoard().isValidMove(1, 1)); // Try to place another move at the same position
    }

    @Test
    void testMultipleMoves() {
        game.getBoard().makeMove(0, 0, game.getCurrentPlayerSymbol()); // X
        game.switchPlayer();
        game.getBoard().makeMove(1, 1, game.getCurrentPlayerSymbol()); // O
        game.switchPlayer();
        game.getBoard().makeMove(2, 2, game.getCurrentPlayerSymbol()); // X

        assertEquals('X', game.getBoard().getCell(0, 0));
        assertEquals('O', game.getBoard().getCell(1, 1));
        assertEquals('X', game.getBoard().getCell(2, 2));
    }

    @Test
    void shouldDetectIfEmptyCellsExist() {
        assertFalse(game.getBoard().isFull());
    }

    @Test
    void detectWinnerHorizontally() {
        game.getBoard().makeMove(0, 0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(0, 1, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(0, 2, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerVertically() {
        game.getBoard().makeMove(0, 0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(1, 0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(2, 0, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerDiagonallyOne() {
        game.getBoard().makeMove(0, 0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(1, 1, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(2, 2, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerDiagonallyTwo() {
        game.getBoard().makeMove(0, 2, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(1, 1, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(2, 0, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void getCurrentPlayerAndSwitchType() {
        assertEquals('X', game.getCurrentPlayerSymbol());
        game.switchPlayer();
        assertEquals('O', game.getCurrentPlayerSymbol());
    }

    @Test
    void handleRandomComputerMove() {
        game.setComputerDifficulty(ComputerDifficultyLevel.EASY);

        ComputerMoveRandom computerMoveRandom = new ComputerMoveRandom(game.getBoard());

        game.getBoard().makeMove(0, 2, game.getCurrentPlayerSymbol());
        game.switchPlayer();

        assertEquals(8, computerMoveRandom.getRemainingValidMoves().size());

        game.getBoard().makeMove(1, 2, game.getCurrentPlayerSymbol());
        assertEquals(7, computerMoveRandom.getRemainingValidMoves().size());

        for (int[] move : computerMoveRandom.getRemainingValidMoves()) {
            assertFalse(java.util.Arrays.equals(move, new int[]{0, 2}));
            assertFalse(java.util.Arrays.equals(move, new int[]{1, 2}));
        }

        int[] nextMove = game.getComputerMove();
        assertNotNull(nextMove);
        assertEquals(2, nextMove.length);

        List<int[]> remainingMoves = computerMoveRandom.getRemainingValidMoves();

        boolean moveIsValid = remainingMoves.stream()
                .anyMatch(move -> move[0] == nextMove[0] && move[1] == nextMove[1]);

        assertTrue(moveIsValid);
    }
}
