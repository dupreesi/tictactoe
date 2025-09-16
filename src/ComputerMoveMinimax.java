public class ComputerMoveMinimax implements ComputerMoveHandler {

  private final Board gameBoard;
  private final Game game;

  public ComputerMoveMinimax(Board gameBoard) {
    this.gameBoard = gameBoard;
    this.game = new Game(new Console());
  }

  public int evaluate() {
    if (game.isWinningMove()) {
      if (game.getCurrentPlayerSymbol() == 'O') {
        return 10; // Computer wins
      } else {
        return -10; // Human wins
      }
    }
    return 0;
  }

  public int minimax() {
    int score = evaluate();
    // Maximizing player (computer) won or minimizing player (human) won
    if (score == 10 || score == -10) {
      return score;
    }
    if(gameBoard.isFull()) {
      return 0; // Draw
    }
    // add minimax logic here
    // go through all available cells and call minimax recursively
    // return the best value
    return 0;
  }

  @Override
  public int getNextMove() {
    return minimax();
  }
}
