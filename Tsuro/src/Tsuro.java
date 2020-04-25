import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Inspired from these pages
// https://bit.ly/1rf8S5n
// https://bit.ly/2tcwZ9L

public class Tsuro extends Application implements Cloneable {

    public static final boolean TESTING = false;
    public static final boolean USE_SET_COLORS = true;
    public static final boolean PATH_ANIM_TESTING = false;

    private Deck deck;
    private Board board;
    private PlayerList players;
    private GameWindow window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        deck = new Deck(this);
        board = new Board(this);
        players = new PlayerList(this);
        window = new GameWindow(this, stage);

    }

    // Deep Copy --- Depreciated ? ---
    public Tsuro clone() throws CloneNotSupportedException {
        Tsuro tsuro = (Tsuro) super.clone();
        tsuro.deck = new Deck(tsuro, deck);
        tsuro.board = new Board(tsuro, board);
        tsuro.players = new PlayerList(tsuro, players);
        tsuro.updateWindowTsuro(tsuro);
        return tsuro;
    }

    public Tile getBoardTile(int y, int x) { return board.getTile(y, x); }
    public Card drawCard() { return deck.draw(); }
    public boolean hasPlayerGoneOffBoard(int pID) { return players.hasPlayerGoneOffBoard(pID); }
    public int getDeckPointer() { return deck.getDeckPointer(); }
    public LocationInfo[] getPlayersLocationInfo() { return players.getAllLocationInfo(); }

    public void showBoard(GraphicsContext gc) { board.show(gc); }
    public void showPlayers(GraphicsContext gc) { players.show(gc); }
    public void showHand(GraphicsContext gc) { players.showHand(gc); }
    public void checkHandHover(double x, double y) { players.checkHandHover(x, y); }
    public void checkHandClick(double x, double y) { players.checkHandClick(x, y); }
    public void rotateHand() { players.rotateHand(); }
    public void playersFollowPath() { players.followPath(); }
    public void playerFollowPath(Player player) { board.playerFollowPath(player); }
    public void updateWindowTsuro(Tsuro tsuro) { window.updateTsuro(tsuro); }
    public void resetLocationInfo(LocationInfo[] locs) { players.resetLocationInfo(locs); }
    public void decrementDeckPointer() { deck.decrementPointer(); }

    // TESTING
    public void testMakeMove() {
        players.testMakeMove();
    }

}
