public class MoveParser {
    public static int parse(String input, Console console) {
        try {
            int position = Integer.parseInt(input.trim()) - 1; // Convert 1-9 input to 0-8 index
            if (position < 0 || position > 8) {
                console.displayMessage(GameServer.Messages.INVALID_INPUT);
                return -1;
            }
            return position;
        } catch (NumberFormatException e) {
            console.displayMessage(GameServer.Messages.INVALID_INPUT);
            return -1;
        }
    }

    public class Messages {
        public static final String INVALID_NUMBERS = "Invalid input! Enter a number from 1 to 9.";
        public static final String INVALID_POSITION = "Invalid position! Enter a number from 1 to 9.";
    }
}