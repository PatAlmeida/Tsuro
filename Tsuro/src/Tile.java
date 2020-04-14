import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {

    public static final int SIZE = 117;
    public static final int OFF1 = SIZE / 3;
    public static final int OFF2 = SIZE * 2 / 3;

    private int x, y;
    private boolean hasCard;
    private Card card;

    public Tile(int yPos, int xPos) {
        x = xPos; y = yPos;
        hasCard = false;
    }

    public static Offsets getOffsets(int spotIndex) {
        int xOff = 0; int yOff = 0;
        if (spotIndex == 0) { xOff = SIZE; yOff = OFF1; }
        else if (spotIndex == 1) { xOff = OFF2; yOff = 0; }
        else if (spotIndex == 2) { xOff = OFF1; yOff = 0; }
        else if (spotIndex == 3) { xOff = 0; yOff = OFF1; }
        else if (spotIndex == 4) { xOff = 0; yOff = OFF2; }
        else if (spotIndex == 5) { xOff = OFF1; yOff = SIZE; }
        else if (spotIndex == 6) { xOff = OFF2; yOff = SIZE; }
        else if (spotIndex == 7) { xOff = SIZE; yOff = OFF2; }
        return new Offsets(xOff, yOff);
    }

    public static int getAdjSpot(int spotIndex) {
        if (spotIndex == 0) return 3;
        else if (spotIndex == 1) return 6;
        else if (spotIndex == 2) return 5;
        else if (spotIndex == 3) return 0;
        else if (spotIndex == 4) return 7;
        else if (spotIndex == 5) return 2;
        else if (spotIndex == 6) return 1;
        else if (spotIndex == 7) return 4;
        return -1;
    }

    public void show(GraphicsContext gc) {
        if (hasCard) { card.show(gc, getRealX(), getRealY()); }
    }

    public int getNextSpotIndex(int prevSpot) {
        if (hasCard) {
            return card.getNextSpotFor(prevSpot);
        } else {
            System.out.println("How'd you end up here ...");
            return -1;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getRealX() { return x * SIZE + Board.MARGIN; }
    public int getRealY() { return y * SIZE + Board.MARGIN; }

    public void setCard(Card c) {
        if (c != null) {
            card = c;
            hasCard = true;
        }
    }

    public void removeCard() {
        card = null;
        hasCard = false;
    }

}
