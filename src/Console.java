import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    // todo: hide your internals
    public final Scanner input;
    private final PrintStream output;

    public Console() {
        this(System.in, System.out);
    }

    public Console(InputStream in, PrintStream out) {
        this.output = out;
        this.input = new Scanner(in);
    }

    // displayMessage(String message)
    // promptForInput(String message, somethingSomething)

    public void println(String msg) {
        output.println(msg);
    }

    public void printf(String format, Object... args) {
        output.printf(format, args);
    }

    // todo: does this belong here?
    public void printGameBoard(char[][] gameBoard) {
        println(Constants.MSG_GAMEBOARD_BORDER);
        for (int i = 0; i < 3; i++) {
            printf(Constants.MSG_GAMEBOARD_CONTENT, gameBoard[i][0], gameBoard[i][1], gameBoard[i][2]);
        }
        println(Constants.MSG_GAMEBOARD_BORDER);
    }

    public String nextLine() {
        return input.nextLine();
    }

    public boolean hasNextLine() {
        try {
            return input.hasNextLine();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public void close() {
        input.close();
    }
}
