public class ComputerPlayer implements Player {
    private final char symbol;
    private final ComputerMoveHandler handler;

    public ComputerPlayer(char symbol, ComputerMoveHandler handler) {
        this.symbol = symbol;
        this.handler = handler;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public int[] getMove() {
        return handler.getNextMove();
    }
}