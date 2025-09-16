import org.junit.jupiter.api.*;
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
        assertTrue(game.getBoard().isValidMove(5)); // Position 5 (center-right)
    }

    @Test
    void testMakeMove() {
        game.getBoard().makeMove(5, game.getCurrentPlayerSymbol());
        assertEquals('X', game.getBoard().getCell(5));
    }

    @Test
    void testMoveOutOfBounds() {
        assertFalse(game.getBoard().isValidMove(-1)); // Negative position
        assertFalse(game.getBoard().isValidMove(9));  // Position too high
        assertFalse(game.getBoard().isValidMove(10)); // Position too high
    }

    @Test
    void testMoveOnOccupiedCell() {
        game.getBoard().makeMove(4, game.getCurrentPlayerSymbol()); // First move at position 4 (center)
        assertFalse(game.getBoard().isValidMove(4)); // Try to place another move at the same position
    }

    @Test
    void testMultipleMoves() {
        game.getBoard().makeMove(0, game.getCurrentPlayerSymbol()); // X at position 0
        game.switchPlayer();
        game.getBoard().makeMove(4, game.getCurrentPlayerSymbol()); // O at position 4
        game.switchPlayer();
        game.getBoard().makeMove(8, game.getCurrentPlayerSymbol()); // X at position 8

        assertEquals('X', game.getBoard().getCell(0));
        assertEquals('O', game.getBoard().getCell(4));
        assertEquals('X', game.getBoard().getCell(8));
    }

    @Test
    void shouldDetectIfEmptyCellsExist() {
        assertFalse(game.getBoard().isFull());
    }

    @Test
    void detectWinnerHorizontally() {
        // Top row: positions 0, 1, 2
        game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(1, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(2, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerVertically() {
        // Left column: positions 0, 3, 6
        game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(3, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(6, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerDiagonallyOne() {
        // Main diagonal: positions 0, 4, 8
        game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(4, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(8, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void detectWinnerDiagonallyTwo() {
        // Anti-diagonal: positions 2, 4, 6
        game.getBoard().makeMove(2, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(4, game.getCurrentPlayerSymbol());
        game.getBoard().makeMove(6, game.getCurrentPlayerSymbol());
        assertTrue(game.isWinningMove());
    }

    @Test
    void getCurrentPlayerAndSwitchType() {
        assertEquals('X', game.getCurrentPlayerSymbol());
        game.switchPlayer();
        assertEquals('O', game.getCurrentPlayerSymbol());
    }

    @Test
    void getRemainingValidMoves() {
        // Initially, all 9 positions should be available
        assertEquals(9, Utils.getRemainingValidMoves(game.getBoard()).size());

        // Make a move and verify one less position is available
        game.getBoard().makeMove(2, 'X'); // Position 2
        assertEquals(8, Utils.getRemainingValidMoves(game.getBoard()).size());

    }

    @Test
    void handleRandomComputerMove() {
        // Test computer move generation with 1D positions
        ComputerMoveRandom computerMoveRandom = new ComputerMoveRandom(game.getBoard());
        // Get next computer move and verify it's valid
        int nextMove = computerMoveRandom.getNextMove();
        assertNotEquals(-1, nextMove); // Should not be -1 (no move available)
        assertTrue(nextMove >= 0 && nextMove <= 8); // Should be valid position
        assertNotEquals(2, nextMove); // Should not be the occupied position

        // Verify the move is actually valid on the board
        assertTrue(game.getBoard().isValidMove(nextMove));
    }

    @Test
    void testProcessPlayerMove() {
        // Test processing a valid move
        boolean moveSuccess = game.processPlayerMove(3); // Position 3
        assertTrue(moveSuccess);
        assertEquals('X', game.getBoard().getCell(3));

        // Test processing an invalid move (same position)
        boolean secondMoveSuccess = game.processPlayerMove(3);
        assertFalse(secondMoveSuccess);
    }

    @Test
    void testGameOverDetection() {
        // Test game is not over initially
        assertFalse(game.isGameOver());

        // Create a winning condition (top row)
        game.getBoard().makeMove(0, 'X');
        game.getBoard().makeMove(1, 'X');
        game.getBoard().makeMove(2, 'X');

        assertTrue(game.isGameOver());
    }

    @Test
    void testBoardFullDetection() {
        // Fill board without winning
        createFullBoard();

        assertTrue(game.getBoard().isFull());
        assertTrue(game.isGameOver());
    }

    @Test
    void handleMinimaxComputerMove() {
        ComputerMoveMinimax computerMoveRandom = new ComputerMoveMinimax(game.getBoard());
        createFullBoard();

        assertEquals(0, computerMoveRandom.getNextMove());
    }

    // research convert board as a tree
    // < 300ms response time for minimax
    // test cases (test the board state not the algorithm)
        // full board
        // switch between min max
        // one move left
        // two moves left
        // prevent fork 1 and 2



    private void createFullBoard() {
        game.getBoard().makeMove(0, 'X');
        game.getBoard().makeMove(1, 'O');
        game.getBoard().makeMove(2, 'X');
        game.getBoard().makeMove(3, 'O');
        game.getBoard().makeMove(4, 'X');
        game.getBoard().makeMove(5, 'O');
        game.getBoard().makeMove(6, 'O');
        game.getBoard().makeMove(7, 'X');
        game.getBoard().makeMove(8, 'O');
    }
}
