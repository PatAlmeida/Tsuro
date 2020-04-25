import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Inspired from these pages
// https://bit.ly/1rf8S5n
// https://bit.ly/2tcwZ9L

public class Tsuro extends Application {

    public static final int NUM_PLAYERS = 4;
    public static final int TURN_DELAY_COUNT = 60;
    public static final boolean AI_ANIM = false;
    public static final boolean PLAY_GAME = true;
    public static final boolean HUMAN_PLAY = true;
    public static final boolean USE_SET_COLORS = true;
    public static final boolean PATH_ANIM_TESTING = false;

    private Deck deck;
    private Board board;
    private PlayerList players;
    private Game game;
    private GameWindow window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        deck = new Deck(this);
        board = new Board(this);
        players = new PlayerList(this);
        game = new Game(this);
        window = new GameWindow(this, stage);

    }

    public Tile getBoardTile(int y, int x) { return board.getTile(y, x); }
    public Card drawCard() { return deck.draw(); }
    public boolean hasPlayerGoneOffBoard(int pID) { return players.hasPlayerGoneOffBoard(pID); }
    public int getDeckPointer() { return deck.getDeckPointer(); }
    public LocationInfo[] getPlayersLocationInfo() { return players.getAllLocationInfo(); }
    public boolean isHumanTurn() { return game.isHumanTurn(); }
    public ArrayList<Integer> getLivingPlayerIDs() { return players.getLivingIDs(); }

    public void showBoard(GraphicsContext gc) { board.show(gc); }
    public void showPlayers(GraphicsContext gc) { players.show(gc); }
    public void showHand(GraphicsContext gc) { players.showHand(gc); }
    public void checkHandHover(double x, double y) { players.checkHandHover(x, y); }
    public void checkHandClick(double x, double y) { players.checkHandClick(x, y); }
    public void rotateHand() { players.rotateHand(); }
    public void playersFollowPath() { players.followPath(); }
    public void playerFollowPath(Player player) { board.playerFollowPath(player); }
    public void resetLocationInfo(LocationInfo[] locs) { players.resetLocationInfo(locs); }
    public void decrementDeckPointer() { deck.decrementPointer(); }
    public void updateGame(int count) { game.update(count); }
    public void humanTurnFinish() { game.humanTurnFinish(); }
    public void playNonLosingMoveFor(int i) { players.playNonLosingMoveFor(i); }

}
