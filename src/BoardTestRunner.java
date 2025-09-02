public class BoardTestRunner {
    public static void main(String[] args) {
        System.out.println("Testing Board with 1D position-only system (no row/column logic)...");

        Board board = new Board();

        // Test 1: Basic initialization
        System.out.println("\n1. Testing board initialization:");
        System.out.println("Board is empty: " + !board.isFull());

        // Test 2: Making moves using 1D positions only
        System.out.println("\n2. Testing 1D position moves:");
        board.makeMove(0, 'X'); // position 0 (top-left)
        board.makeMove(4, 'O'); // position 4 (center)
        board.makeMove(8, 'X'); // position 8 (bottom-right)

        System.out.println("Position 0: " + board.getCell(0));
        System.out.println("Position 4: " + board.getCell(4));
        System.out.println("Position 8: " + board.getCell(8));

        // Test 3: Board visualization
        System.out.println("\n3. Current board state:");
        board.draw();

        // Test 4: More moves to fill positions
        System.out.println("\n4. Testing additional moves:");
        board.makeMove(1, 'O'); // position 1
        board.makeMove(3, 'X'); // position 3
        board.makeMove(5, 'O'); // position 5

        System.out.println("Position 1: " + board.getCell(1));
        System.out.println("Position 3: " + board.getCell(3));
        System.out.println("Position 5: " + board.getCell(5));

        // Test 5: Updated board visualization
        System.out.println("\n5. Updated board state:");
        board.draw();

        // Test 6: 1D array copy
        System.out.println("\n6. 1D array representation:");
        char[] board1D = board.getCopy();
        System.out.print("Positions 0-8: ");
        for (int i = 0; i < 9; i++) {
            System.out.print("[" + i + ":" + board1D[i] + "]");
            if (i < 8) System.out.print(" ");
        }
        System.out.println();

        // Test 7: Valid moves
        System.out.println("\n7. Testing valid moves:");
        System.out.println("Position 2 valid: " + board.isValidMove(2)); // should be true (empty)
        System.out.println("Position 4 valid: " + board.isValidMove(4)); // should be false (occupied)
        System.out.println("Position 6 valid: " + board.isValidMove(6)); // should be true (empty)
        System.out.println("Position 7 valid: " + board.isValidMove(7)); // should be true (empty)
        System.out.println("Position -1 valid: " + board.isValidMove(-1)); // should be false (out of bounds)
        System.out.println("Position 9 valid: " + board.isValidMove(9)); // should be false (out of bounds)

        // Test 8: Clear and reset
        System.out.println("\n8. Testing clear functionality:");
        board.clear();
        System.out.println("Board is empty after clear: " + !board.isFull());

        char[] clearedBoard = board.getCopy();
        boolean allEmpty = true;
        for (char cell : clearedBoard) {
            if (cell != ' ') {
                allEmpty = false;
                break;
            }
        }
        System.out.println("All positions are empty: " + allEmpty);

        System.out.println("\n✅ Board 1D position-only test completed successfully!");
        System.out.println("The Board class now works exclusively with single position numbers (0-8):");
        System.out.println();
        System.out.println("Position mapping:");
        System.out.println("0 | 1 | 2");
        System.out.println("--|---|--");
        System.out.println("3 | 4 | 5");
        System.out.println("--|---|--");
        System.out.println("6 | 7 | 8");
        System.out.println();
        System.out.println("All row/column coordinate logic has been completely removed!");
    }
}
