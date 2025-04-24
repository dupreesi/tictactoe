public class Player {
    private char type;

    public Player(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public void switchType() {
        this.type = (this.type == 'X') ? 'O' : 'X';
    }
}
