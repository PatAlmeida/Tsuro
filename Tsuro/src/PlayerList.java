import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerList {

    public static final int SIZE = 1;

    private Player[] players;

    public PlayerList(Board board) {
        players = new Player[SIZE];
        makeRandomPlayers(board);
    }

    // TESTING
    private void makeRandomPlayers(Board board) {
        for (int i = 0; i < SIZE; i++) {
            int rx = (int) (Math.random() * (Board.DIM - 1));
            int ry = (int) (Math.random() * (Board.DIM - 1));
            int rs = (int) (Math.random() * Card.PATHS);
            Color col = Color.color(Math.random(), Math.random(), Math.random());
            players[i] = new Player(board.getTile(ry, rx), rs, col);
        }
    }

    public void show(GraphicsContext gc) {
        for (Player player : players) player.show(gc);
    }

    public void testStuff(Board board) {
        for (Player player : players) {
            if (!player.hasGoneOffBoard()) board.playerFollowTile(player);
        }
    }

}
