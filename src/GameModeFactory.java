public class GameModeFactory {
    public static GameModeHandler getGameModeHandler(GameMode gameMode) {
        return switch (gameMode) {
            case PLAYER_VS_PLAYER -> new PlayerVsPlayerHandler();
            case PLAYER_VS_COMPUTER -> new PlayerVsComputerHandler();
        };
    }
}
