public class MoveParser {
    public static int[] parse(String input, Console console) {
        String[] parts = input.trim().split(" ");
        if (parts.length != 2) {
            console.println(Constants.MSG_INVALID_NUMBERS);
            return null;
        }
        try {
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            return new int[]{row, col};
        } catch (NumberFormatException e) {
            console.println(Constants.MSG_INVALID_NUMBERS);
            return null;
        }
    }
}