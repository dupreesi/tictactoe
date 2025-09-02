import java.util.Arrays;

public class Board {
    private char[] board;
    private Console console;

    public Board() {
        board = new char[9];
        console = new Console();
        clear();
    }

    public void clear() {
        Arrays.fill(board, ' ');
    }

    public boolean isFull() {
        for (char cell : board) {
            if (cell == ' ') {
                return false;
            }
        }
        return true;
    }

    public boolean isValidMove(int position) {
        return position >= 0 && position < 9 && board[position] == ' ';
    }

    public void makeMove(int position, char player) {
        board[position] = player;
    }

    public char getCell(int position) {
        return board[position];
    }

    public char[] getCopy() {
        return Arrays.copyOf(board, board.length);
    }

    public void draw() {
        console.displayMessage(Messages.GAMEBOARD_BORDER);
        console.displayMessage(Messages.GAMEBOARD_CONTENT, board[0], board[1], board[2]);
        console.displayMessage(Messages.GAMEBOARD_BORDER_MID);
        console.displayMessage(Messages.GAMEBOARD_CONTENT, board[3], board[4], board[5]);
        console.displayMessage(Messages.GAMEBOARD_BORDER_MID);
        console.displayMessage(Messages.GAMEBOARD_CONTENT, board[6], board[7], board[8]);
        console.displayMessage(Messages.GAMEBOARD_BORDER);
    }

    private class Messages {
        public static final String GAMEBOARD_BORDER = "-----------";
        public static final String GAMEBOARD_BORDER_MID = "---|---|---";
        public static final String GAMEBOARD_CONTENT = " %c | %c | %c %n";
    }
}
