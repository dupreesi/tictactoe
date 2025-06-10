import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @Test
    void newBoardIsEmpty() {
        board = new Board();
        assertTrue(board.isEmpty());
    }

    @Test
    void boardWithMoveIsNotEmpty() {
        board = new Board();
        board.makeMove(1);
        assertFalse(board.isEmpty());
    }


}