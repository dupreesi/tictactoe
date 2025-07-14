import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    private final Scanner input;
    private final PrintStream output;

    public Console() {
        this(System.in, System.out);
    }

    public Console(InputStream in, PrintStream out) {
        this.output = out;
        this.input = new Scanner(in);
    }

    public boolean promptingForInput() {
        try {
            return input.hasNextLine();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public void displayMessage(String message) {
        output.println(message);
        output.flush();
    }

    public void displayMessage(String format, Object... args) {
        output.printf(format, args);
    }

    public String getInputValue() {
        return input.nextLine();
    }

    public void close() {
        input.close();
    }
}
