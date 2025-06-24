public class Board {
    private char[][] board;

    public Board() {
        board = new char[3][3];
        clear();
    }

    protected Board(String boardAsString) {
        this();
        if (boardAsString.length() != 9) {
            throw new RuntimeException("Incorrect number of chars in Board String: " + boardAsString.length());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = boardAsString.charAt(i * 3 + j);
            }
        }
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

    // take position and player
    // map position to coord.
    // set board
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


    @Override
    public String toString() {
        for (int i = 0; i < 3; i++) {
            System.out.printf(Constants.MSG_GAMEBOARD_CONTENT, board[i][0], board[i][1], board[i][2]);
        }
        return "";
    }
}
