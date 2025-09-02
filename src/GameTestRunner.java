public class GameTestRunner {
    public static void main(String[] args) {
        System.out.println("Testing Game functionality with 1D position-only Board...");

        boolean allTestsPassed = true;

        // Test 1: Valid Move
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            boolean result = game.getBoard().isValidMove(5); // Position 5 (center-right)
            System.out.println("✓ Test 1 - Valid Move: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 1 - Valid Move: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 2: Make Move
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(5, game.getCurrentPlayerSymbol());
            char result = game.getBoard().getCell(5);
            System.out.println("✓ Test 2 - Make Move: " + (result == 'X' ? "PASS" : "FAIL"));
            if (result != 'X') allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 2 - Make Move: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 3: Move Out of Bounds
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            boolean test1 = !game.getBoard().isValidMove(-1);
            boolean test2 = !game.getBoard().isValidMove(9);
            boolean test3 = !game.getBoard().isValidMove(10);
            boolean result = test1 && test2 && test3;
            System.out.println("✓ Test 3 - Move Out of Bounds: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 3 - Move Out of Bounds: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 4: Move on Occupied Cell
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(4, game.getCurrentPlayerSymbol()); // Position 4 (center)
            boolean result = !game.getBoard().isValidMove(4);
            System.out.println("✓ Test 4 - Move on Occupied Cell: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 4 - Move on Occupied Cell: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 5: Multiple Moves
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(0, game.getCurrentPlayerSymbol()); // X at position 0
            game.switchPlayer();
            game.getBoard().makeMove(4, game.getCurrentPlayerSymbol()); // O at position 4
            game.switchPlayer();
            game.getBoard().makeMove(8, game.getCurrentPlayerSymbol()); // X at position 8

            boolean test1 = game.getBoard().getCell(0) == 'X';
            boolean test2 = game.getBoard().getCell(4) == 'O';
            boolean test3 = game.getBoard().getCell(8) == 'X';
            boolean result = test1 && test2 && test3;
            System.out.println("✓ Test 5 - Multiple Moves: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 5 - Multiple Moves: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 6: Board Not Full Initially
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            boolean result = !game.getBoard().isFull();
            System.out.println("✓ Test 6 - Board Not Full Initially: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 6 - Board Not Full Initially: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 7: Detect Winner Horizontally (top row: positions 0,1,2)
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(1, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(2, game.getCurrentPlayerSymbol());
            boolean result = game.isWinningMove();
            System.out.println("✓ Test 7 - Detect Winner Horizontally: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 7 - Detect Winner Horizontally: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 8: Detect Winner Vertically (left column: positions 0,3,6)
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(3, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(6, game.getCurrentPlayerSymbol());
            boolean result = game.isWinningMove();
            System.out.println("✓ Test 8 - Detect Winner Vertically: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 8 - Detect Winner Vertically: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 9: Detect Winner Diagonally (positions 0,4,8)
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(0, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(4, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(8, game.getCurrentPlayerSymbol());
            boolean result = game.isWinningMove();
            System.out.println("✓ Test 9 - Detect Winner Diagonally (\\): " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 9 - Detect Winner Diagonally (\\): FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 10: Detect Winner Diagonally (positions 2,4,6)
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            game.getBoard().makeMove(2, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(4, game.getCurrentPlayerSymbol());
            game.getBoard().makeMove(6, game.getCurrentPlayerSymbol());
            boolean result = game.isWinningMove();
            System.out.println("✓ Test 10 - Detect Winner Diagonally (/): " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 10 - Detect Winner Diagonally (/): FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 11: Current Player and Switch
        try {
            Game game = new Game(new Console());
            Player playerX = new HumanPlayer('X', new Console());
            Player playerO = new HumanPlayer('O', new Console());
            game.setPlayers(playerX, playerO);

            boolean test1 = game.getCurrentPlayerSymbol() == 'X';
            game.switchPlayer();
            boolean test2 = game.getCurrentPlayerSymbol() == 'O';
            boolean result = test1 && test2;
            System.out.println("✓ Test 11 - Current Player and Switch: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 11 - Current Player and Switch: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        // Test 12: Random Computer Move
        try {
            Game game = new Game(new Console());
            ComputerMoveRandom computerMoveRandom = new ComputerMoveRandom(game.getBoard());

            int initialMoves = computerMoveRandom.getRemainingValidMoves().size();
            boolean test1 = initialMoves == 9;

            game.getBoard().makeMove(2, 'X'); // Position 2
            int movesAfterOne = computerMoveRandom.getRemainingValidMoves().size();
            boolean test2 = movesAfterOne == 8;

            int nextMove = computerMoveRandom.getNextMove();
            boolean test3 = nextMove >= 0 && nextMove <= 8 && nextMove != 2;

            boolean result = test1 && test2 && test3;
            System.out.println("✓ Test 12 - Random Computer Move: " + (result ? "PASS" : "FAIL"));
            if (!result) allTestsPassed = false;
        } catch (Exception e) {
            System.out.println("✗ Test 12 - Random Computer Move: FAIL - " + e.getMessage());
            allTestsPassed = false;
        }

        System.out.println("\n" + "=".repeat(50));
        if (allTestsPassed) {
            System.out.println("🎉 ALL TESTS PASSED! 1D position-only system working!");
            System.out.println("The Board class now uses only single position numbers (0-8)");
            System.out.println("with complete removal of row/column coordinate logic.");
            System.out.println("\nBoard positions are mapped as:");
            System.out.println("0 | 1 | 2");
            System.out.println("--|---|--");
            System.out.println("3 | 4 | 5");
            System.out.println("--|---|--");
            System.out.println("6 | 7 | 8");
        } else {
            System.out.println("❌ Some tests failed. Please check the implementation.");
        }
        System.out.println("=".repeat(50));
    }
}

