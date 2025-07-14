public class HumanPlayer implements Player {
    private final char symbol;
    private final Console console;

    public HumanPlayer(char symbol, Console console) {
        this.symbol = symbol;
        this.console = console;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public int[] getMove() {
        console.displayMessage(GameServer.Messages.INPUT_PROMPT, symbol);
        while (true) {
            String input = console.getInputValue();
            if (input == null || input.equalsIgnoreCase("exit")) {
                return null;
            }

            int[] move = MoveParser.parse(input, console);
            if (move != null) return move;

            console.displayMessage(GameServer.Messages.INVALID_INPUT);
        }
    }
}
