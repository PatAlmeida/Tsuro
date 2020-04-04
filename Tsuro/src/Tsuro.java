import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Inspired from these pages
// https://bit.ly/1rf8S5n
// https://bit.ly/2tcwZ9L

public class Tsuro extends Application {

    public static final boolean TESTING = true;

    private Deck deck;
    private Board board;
    private GameWindow window;
    private PlayerList players;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        deck = new Deck();
        board = new Board();
        players = new PlayerList(board);

        board.applyCardsToTiles(deck);

        window = new GameWindow(this, stage);

    }

    public void showBoard(GraphicsContext gc) {
        board.show(gc);
    }

    public void showPlayers(GraphicsContext gc) {
        players.show(gc);
    }

    // TESTING
    public void testStuff() {
        players.testStuff(board);
    }

}
