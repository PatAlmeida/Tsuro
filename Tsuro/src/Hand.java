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

    private Player player;
    private Tsuro tsuro;
    private ArrayList<Card> cards;
    private ArrayList<Color> borders;

    public Hand(Player myPlayer, Tsuro myTsuro) {
        player = myPlayer;
        tsuro = myTsuro;
        cards = new ArrayList<Card>();
        borders = new ArrayList<Color>();
        for (int i = 0; i < MAX_SIZE; i++) {
            cards.add(tsuro.drawCard());
            borders.add(INIT_COL);
        }
    }

    private Color getBorderCol(int i) { return borders.get(i); }

    public Card getCard(int i) { return cards.get(i); }
    public int getCurSize() { return cards.size(); }

    public void setCard(int i, Card c) { cards.set(i, c); }
    public void empty() { cards.clear(); }

    public void show(GraphicsContext gc) {
        int y = STARTY;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null) {
                cards.get(i).show(gc, STARTX, y);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(3);
                gc.setStroke(borders.get(i));
                gc.strokeRect(STARTX, y, Tile.SIZE, Tile.SIZE);
                y += SB + Tile.SIZE;
            }
        }
    }

    public void addDummyCard(int i) {
        cards.add(i, null);
    }

    public void rotate() {
        for (int i = 0; i < cards.size(); i++) {
            if (borders.get(i) == HOVER_COL) cards.get(i).incRot();
        }
    }

    public void addCardsToList(ArrayList<Card> leftoverCards) {
        for (Card card : cards) leftoverCards.add(card);
    }

    public void playCard(int num) {
        player.getTile().setCard(cards.get(num));
        Card newCard = tsuro.drawCard();
        if (newCard == null) cards.remove(num);
        else cards.set(num, newCard);
        tsuro.playersFollowPath();
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

    public void checkClick(double mx, double my) {
        int removeIndex = -1;
        for (int i = 0; i < cards.size(); i++) {
            if (borders.get(i) == HOVER_COL && !player.hasGoneOffBoard() && tsuro.isHumanTurn()) {
                player.getTile().setCard(cards.get(i));
                Card newCard = tsuro.drawCard();
                if (newCard == null) removeIndex = i;
                else cards.set(i, newCard);
                tsuro.playersFollowPath();
                tsuro.humanTurnFinish();
            }
        }
        if (removeIndex != -1 && cards.size() != 0) cards.remove(removeIndex);
    }

}
