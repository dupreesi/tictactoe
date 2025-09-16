import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static List<Integer> getRemainingValidMoves(Board gameBoard) {
    List<Integer> validMoves = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      if (gameBoard.isValidMove(i)) {
        validMoves.add(i);
      }
    }
    return validMoves;
  }

}
