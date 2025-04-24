import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    public final Scanner input;
    private final PrintStream output;

    public Console() {
        this(System.in, System.out);
    }

    public Console(InputStream in, PrintStream out) {
        this.output = out;
        this.input = new Scanner(in);
    }

    public void println(String msg) {
        output.println(msg);
    }

    public void printf(String format, Object... args) {
        output.printf(format, args);
    }

    public void printGameBoard(char[][] gameBoard) {
        println(Messages.GAMEBOARD_BORDER);
        for (int i = 0; i < 3; i++) {
            printf(Messages.GAMEBOARD_CONTENT, gameBoard[i][0], gameBoard[i][1], gameBoard[i][2]);
        }
        println(Messages.GAMEBOARD_BORDER);
    }

    public String nextLine() {
        return input.nextLine();
    }

    public boolean hasNextLine() {
        return input.hasNextLine();
    }

    public void close() {
        input.close();
    }

    public static class Messages {
        public static final String GAMEBOARD_BORDER = "-----------";
        public static final String GAMEBOARD_CONTENT = " %c | %c | %c %n";
    }
}
