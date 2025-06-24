public enum PlayerType {
    X('X'), O('O');

    private final char symbol;

    PlayerType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public PlayerType switchType() {
        return this == X ? O : X;
    }
}
