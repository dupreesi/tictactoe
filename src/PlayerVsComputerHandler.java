public class PlayerVsComputerHandler implements GameModeHandler {
    @Override
    public void handle(GameServer server) {
        server.selectComputerDifficultyLevel();
        server.start();
    }
}