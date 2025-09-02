import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testBoardInitialization() {
        // Test that all positions are empty after initialization
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', board.getCell(i));
        }
        assertFalse(board.isFull());
    }

    @Test
    void testClearBoard() {
        // Make some moves
        board.makeMove(0, 'X');
        board.makeMove(4, 'O');
        board.makeMove(8, 'X');

        // Clear the board
        board.clear();

        // Verify all positions are empty
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', board.getCell(i));
        }
    }

    @Test
    void test1DPositionMethods() {
        // Test making moves using 1D positions
        board.makeMove(0, 'X'); // Top-left
        board.makeMove(4, 'O'); // Center
        board.makeMove(8, 'X'); // Bottom-right

        assertEquals('X', board.getCell(0));
        assertEquals('O', board.getCell(4));
        assertEquals('X', board.getCell(8));
    }

    @Test
    void testValidMove1D() {
        // Test valid moves using 1D positions
        assertTrue(board.isValidMove(0));
        assertTrue(board.isValidMove(4));
        assertTrue(board.isValidMove(8));

        // Make a move and test it's no longer valid
        board.makeMove(4, 'X');
        assertFalse(board.isValidMove(4));

        // Test out of bounds
        assertFalse(board.isValidMove(-1));
        assertFalse(board.isValidMove(9));
    }

    @Test
    void testIsFull() {
        assertFalse(board.isFull());

        // Fill the board
        char[] players = {'X', 'O'};
        for (int i = 0; i < 9; i++) {
            board.makeMove(i, players[i % 2]);
        }

        assertTrue(board.isFull());
    }

    @Test
    void testGetCopy1D() {
        // Make some moves
        board.makeMove(0, 'X');
        board.makeMove(4, 'O');
        board.makeMove(8, 'X');

        char[] copy = board.getCopy();

        // Verify copy matches original
        assertEquals('X', copy[0]);
        assertEquals('O', copy[4]);
        assertEquals('X', copy[8]);
        assertEquals(' ', copy[1]);

        // Verify it's a copy (modifying copy shouldn't affect original)
        copy[1] = 'Z';
        assertEquals(' ', board.getCell(1));
    }

    @Test
    void testBoardPositionMapping() {
        // Test that the 1D array maps correctly to a 3x3 grid
        // Grid layout should be:
        // 0 1 2
        // 3 4 5
        // 6 7 8

        char[] expectedPositions = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        // Fill using 1D positions
        for (int i = 0; i < 9; i++) {
            board.makeMove(i, expectedPositions[i]);
        }

        // Verify all positions
        for (int i = 0; i < 9; i++) {
            assertEquals(expectedPositions[i], board.getCell(i));
        }
    }

    @Test
    void testMoveOnOccupiedPosition() {
        // Make a move at position 4
        board.makeMove(4, 'X');

        // Verify position is occupied
        assertFalse(board.isValidMove(4));
        assertEquals('X', board.getCell(4));
    }

    @Test
    void testOutOfBoundsPositions() {
        // Test negative positions
        assertFalse(board.isValidMove(-1));
        assertFalse(board.isValidMove(-5));

        // Test positions >= 9
        assertFalse(board.isValidMove(9));
        assertFalse(board.isValidMove(10));
        assertFalse(board.isValidMove(100));
    }

    @Test
    void testMixedOperations() {
        // Test mixing different position operations
        board.makeMove(0, 'X');           // position 0
        board.makeMove(4, 'O');           // position 4 (center)
        board.makeMove(8, 'X');           // position 8

        // Verify using getCell
        assertEquals('X', board.getCell(0));
        assertEquals('O', board.getCell(4));
        assertEquals('X', board.getCell(8));

        // Check valid moves
        assertTrue(board.isValidMove(1));
        assertTrue(board.isValidMove(2));
        assertTrue(board.isValidMove(3));
        assertFalse(board.isValidMove(0));  // occupied
        assertFalse(board.isValidMove(4));  // occupied
        assertFalse(board.isValidMove(8));  // occupied
    }
}
