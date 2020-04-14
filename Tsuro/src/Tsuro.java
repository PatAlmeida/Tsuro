import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Inspired from these pages
// https://bit.ly/1rf8S5n
// https://bit.ly/2tcwZ9L

public class Tsuro extends Application {

    public static final boolean TESTING = false;

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

        board.applyCardsToTiles();

        window = new GameWindow(this, stage);

    }

    public void showBoard(GraphicsContext gc) { board.show(gc); }
    public void showPlayers(GraphicsContext gc) { players.show(gc); }
    public void showHand(GraphicsContext gc) { players.showHand(gc); }
    public Tile getBoardTile(int y, int x) { return board.getTile(y, x); }
    public Card drawCard() { return deck.draw(); }
    public void checkHandHover(double x, double y) { players.checkHandHover(x, y); }
    public void rotateHand() { players.rotateHand(); }

    // TESTING
    public void testStuff() {
        players.testStuff(board);
    }

}
