public enum ComputerDifficultyLevel {
    EASY,
    HARD;

    public static ComputerDifficultyLevel getByCode(String input) {
        return switch (input) {
            case "1" -> EASY;
            case "2" -> HARD;
            default -> null;
        };
    }

    public String getLabel() {
        return switch (this) {
            case EASY -> "Easy";
            case HARD -> "Hard";
        };
    }

    public String getCode() {
        return switch (this) {
            case EASY -> "1";
            case HARD -> "2";
        };
    }
}