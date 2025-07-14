public class Board {
    private char[][] board;
    private Console console;

    public Board() {
        board = new char[3][3];
        console = new Console();
        clear();
    }


    public void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean isFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    public void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public char[][] getCopy() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        return copy;
    }

    public void draw(char[][] gameBoard) {
        console.displayMessage(Messages.GAMEBOARD_BORDER);
        for (int i = 0; i < 3; i++) {
            console.displayMessage(Messages.GAMEBOARD_CONTENT, gameBoard[i][0], gameBoard[i][1], gameBoard[i][2]);
        }
        console.displayMessage(Messages.GAMEBOARD_BORDER);
    }

    private class Messages {
        public static final String GAMEBOARD_BORDER = "-----------";
        public static final String GAMEBOARD_CONTENT = " %c | %c | %c %n";
    }
}
