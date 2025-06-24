import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerMoveMinimaxTest {

    @Test
    public void testMiniMax() {
        ComputerMoveMinimax miniMax = new ComputerMoveMinimax();
        Board board = new Board("XOXOXOXX ");
        System.out.println(board);
        int[] nextMove = miniMax.getNextMove();
        assertEquals(new int[]{2, 2}, nextMove);
    }

}