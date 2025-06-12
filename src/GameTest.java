import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testValidMove() {
        assertTrue(game.isValidMove(1, 2));
    }

    @Test
    void testMakeMove() {
        game.makeMove(1, 2);
        assertEquals('X', game.getGameBoard()[1][2]);
    }

    @Test
    void testMoveOutOfBounds() {
        assertFalse(game.isValidMove(-1, 0)); // Negative row
        assertFalse(game.isValidMove(0, -1)); // Negative column
        assertFalse(game.isValidMove(3, 0));  // Row too high
        assertFalse(game.isValidMove(0, 3));  // Column too high
    }

    @Test
    void testMoveOnOccupiedCell() {
        game.makeMove(1, 1); // First move (valid)
        assertFalse(game.isValidMove(1, 1)); // Try to place another move at the same position
    }

    @Test
    void testMultipleMoves() {
        game.makeMove(0, 0); // Player 'X' moves
        game.switchPlayer();
        game.makeMove(1, 1); // Player 'O' moves
        game.switchPlayer();
        game.makeMove(2, 2); // Player 'X' moves again

        assertEquals('X', game.getGameBoard()[0][0]);
        assertEquals('O', game.getGameBoard()[1][1]);
        assertEquals('X', game.getGameBoard()[2][2]);
    }

    @Test
    void shouldDetectIfEmptyCellsExist() {
        assertFalse(game.boardIsFull());
    }

    @Test
    void detectWinnerHorizontally() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(2, 2);
        assertTrue(game.detectWinner());
    }

    @Test
    void detectWinnerVertically() {
        game.makeMove(0, 0);
        game.makeMove(1, 0);
        game.makeMove(2, 0);
        assertTrue(game.detectWinner());
    }

    @Test
    void detectWinnerDiagonallyOne() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(2, 2);
        assertTrue(game.detectWinner());
    }

    @Test
    void detectWinnerDiagonallyTwo() {
        game.makeMove(0, 2);
        game.makeMove(1, 1);
        game.makeMove(2, 0);
        assertTrue(game.detectWinner());
    }

    @Test
    void getCurrentPlayerAndSwitchType() {
        assertEquals(game.getCurrentPlayer(), 'X');
        game.switchPlayer();
        assertEquals(game.getCurrentPlayer(), 'O');
    }

//    TODO: Fix these tests for updated move logic
//    @Test
//    void getRemainingValidMoves() {
//        game.makeMove(0, 2);
//        game.switchPlayer();
//        assertEquals(8, game.getRemainingValidMoves().size());
//        game.makeMove(1, 2);
//        assertEquals(7, game.getRemainingValidMoves().size());
//        for (int[] move : game.getRemainingValidMoves()) {
//            assertFalse(Arrays.equals(move, new int[]{0, 2}));
//            assertFalse(Arrays.equals(move, new int[]{1, 2}));
//        }
//    }

//    @Test
//    void getRandomMove() {
//        game.makeMove(0, 2);
//        game.switchPlayer();
//        game.makeMove(1, 2);
//        int[] randomMove = game.pickNextComputerMove();
//        assertNotNull(randomMove);
//        assertEquals(2, randomMove.length);
//    }

}
