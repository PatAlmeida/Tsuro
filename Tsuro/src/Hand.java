import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Hand {

    public static final int MAX_SIZE = 3;
    public static final int SB = 20; // SPACE_BETWEEN
    public static final Color INIT_COL = Color.BLACK;
    public static final Color HOVER_COL = Color.YELLOW;

    private static final int STARTX = 2*Board.MARGIN + 6*Tile.SIZE;
    private static final int STARTY = (Board.SIZEY - (3*Tile.SIZE + 2*Hand.SB)) / 2;

    private Tsuro tsuro;
    private ArrayList<Card> cards;
    private ArrayList<Color> borders;

    public Hand(Tsuro myTsuro) {
        tsuro = myTsuro;
        cards = new ArrayList<Card>();
        borders = new ArrayList<Color>();
        for (int i = 0; i < MAX_SIZE; i++) cards.add(tsuro.drawCard());
        for (int i = 0; i < MAX_SIZE; i++) borders.add(INIT_COL);
    }

    public void show(GraphicsContext gc) {
        int y = STARTY;
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).show(gc, STARTX, y);
            gc.setStroke(borders.get(i));
            gc.strokeRect(STARTX, y, Tile.SIZE, Tile.SIZE);
            y += SB + Tile.SIZE;
        }
    }

    public void rotate() {
        for (int i = 0; i < cards.size(); i++) {
            if (borders.get(i) == HOVER_COL) cards.get(i).incRot();
        }
    }

    public void checkHover(double mx, double my) {
        int x = STARTX; int y = STARTY;
        for (int i = 0; i < cards.size(); i++) {
            if (x < mx && x + Tile.SIZE > mx && y < my && y + Tile.SIZE > my) {
                borders.set(i, HOVER_COL);
            } else borders.set(i, INIT_COL);
            y += SB + Tile.SIZE;
        }
    }

}
