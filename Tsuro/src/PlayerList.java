import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerList {

    public static final int SIZE = Tsuro.NUM_PLAYERS;

    private Tsuro tsuro;
    private Player[] players;

    public PlayerList(Tsuro myTsuro) {
        tsuro = myTsuro;
        players = new Player[SIZE];
        makePlayers();
    }

    public void show(GraphicsContext gc) {
        for (Player player : players) player.show(gc);
    }

    private void makePlayers() {
        if (SIZE == 1) {
            players[0] = new Player(tsuro, tsuro.getBoardTile(5, 0), 0, 4, Color.ORANGE);
        } else if (SIZE == 4) {
            if (Tsuro.USE_SET_COLORS) {
                players[0] = new Player(tsuro, tsuro.getBoardTile(5, 0), 0, 4, Color.ORANGE);
                players[1] = new Player(tsuro, tsuro.getBoardTile(0, 0), 1, 3, Color.RED);
                players[2] = new Player(tsuro, tsuro.getBoardTile(0, 5), 2, 0, Color.BLUE);
                players[3] = new Player(tsuro, tsuro.getBoardTile(5, 5), 3, 7, Color.GREEN);
            } else {
                players[0] = new Player(tsuro, tsuro.getBoardTile(5, 0), 0, 4, Player.getRandomColor());
                players[1] = new Player(tsuro, tsuro.getBoardTile(0, 0), 1, 3, Player.getRandomColor());
                players[2] = new Player(tsuro, tsuro.getBoardTile(0, 5), 2, 0, Player.getRandomColor());
                players[3] = new Player(tsuro, tsuro.getBoardTile(5, 5), 3, 7, Player.getRandomColor());
            }
        } else {
            System.out.println("ERROR - Bad number of players");
            System.exit(0);
        }
    }

    public ArrayList<Integer> getLivingIDs() {
        ArrayList<Integer> living = new ArrayList<Integer>();
        for (Player player : players) {
            if (!player.hasGoneOffBoard()) living.add(player.getID());
        }
        return living;
    }

    public void resetLocationInfo(LocationInfo[] locationInfo) {
        for (int i = 0; i < SIZE; i++) players[i].resetTo(locationInfo[i]);
    }

    public void followPath() {
        for (Player player : players) {
            tsuro.playerFollowPath(player);
        }
    }

    public LocationInfo[] getAllLocationInfo() {
        LocationInfo[] locs = new LocationInfo[SIZE];
        for (int i = 0; i < SIZE; i++) {
            Player p = players[i];
            locs[i] = new LocationInfo(p.getTileX(), p.getTileY(), p.getSpotIndex(), p.hasGoneOffBoard());
        }
        return locs;
    }

    public boolean hasPlayerGoneOffBoard(int i) { return players[i].hasGoneOffBoard(); }

    public void showHand(GraphicsContext gc) { players[0].showHand(gc); }
    public void checkHandHover(double x, double y) { players[0].checkHandHover(x, y); }
    public void checkHandClick(double x, double y) { players[0].checkHandClick(x, y); }
    public void rotateHand() { players[0].rotateHand(); }
    public void playNonLosingMoveFor(int i) { players[i].playNonLosingMove(); }

}
