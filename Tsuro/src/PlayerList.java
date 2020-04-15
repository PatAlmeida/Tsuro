import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerList {

    public static final int SIZE = 1;

    private Tsuro tsuro;
    private Player[] players;

    public PlayerList(Tsuro myTsuro) {
        tsuro = myTsuro;
        players = new Player[SIZE];
        if (Tsuro.TESTING) makeRandomPlayers();
        else makePlayers();
    }

    public void show(GraphicsContext gc) {
        for (Player player : players) player.show(gc);
    }

    // ONLY WORKS FOR 1 PLAYER RIGHT NOW
    private void makePlayers() {
        for (int i = 0; i < SIZE; i++) {
            Color col = Color.color(Math.random(), Math.random(), Math.random());
            players[i] = new Player(tsuro, tsuro.getBoardTile(5, 0), 4, col);
        }
    }

    // TESTING
    private void makeRandomPlayers() {
        for (int i = 0; i < SIZE; i++) {
            int rx = (int) (Math.random() * (Board.DIM - 1));
            int ry = (int) (Math.random() * (Board.DIM - 1));
            int rs = (int) (Math.random() * Card.PATHS);
            Color col = Color.color(Math.random(), Math.random(), Math.random());
            players[i] = new Player(tsuro, tsuro.getBoardTile(ry, rx), rs, col);
        }
    }

    public void showHand(GraphicsContext gc) { players[0].showHand(gc); }
    public void checkHandHover(double x, double y) { players[0].checkHandHover(x, y); }
    public void checkHandClick(double x, double y) { players[0].checkHandClick(x, y); }
    public void rotateHand() { players[0].rotateHand(); }

    public void testStuff(Board board) {
        for (Player player : players) {
            if (!player.hasGoneOffBoard()) board.playerFollowTile(player);
        }
    }

}
