package proxy.after;

public class GameServiceProxy implements GameService {

    private GameService gameService;

    @Override
    public void startGame() throws InterruptedException {
        long before = System.currentTimeMillis();
        if (this.gameService == null) {
            this.gameService = new DefaultGameService();
        }

        gameService.startGame();
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }
}
