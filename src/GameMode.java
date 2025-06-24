public enum GameMode {
    PLAYER_VS_COMPUTER,
    PLAYER_VS_PLAYER;


    public static GameMode getByCode(String input) {
        return switch (input) {
            case "1" -> PLAYER_VS_PLAYER;
            case "2" -> PLAYER_VS_COMPUTER;
            default -> null;
        };
    }

    public String getLabel() {
        return switch (this) {
            case PLAYER_VS_PLAYER -> "Player vs Player";
            case PLAYER_VS_COMPUTER -> "Player vs Computer";
        };
    }
}