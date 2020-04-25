import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Card {

    public static final int PATHS = 8;
    public static final int ROTATIONS = 4;
    public static final boolean SHOW_ID = true;

    private Image image;
    private int id, rot;
    private int[] paths;

    public Card(int index) {
        image = new Image("file:images/tile" + index + ".png");
        id = index;
        rot = 0;
    }

    public Card(CardInfoHelper info) {
        image = info.image;
        id = info.id; rot = info.rot;
        paths = new int[PATHS];
        for (int i = 0; i < PATHS; i++) paths[i] = info.paths[i];
    }

    public Card(Card card) {
        image = card.getImage();
        id = card.getID();
        rot = card.getRot();
        paths = new int[PATHS];
        for (int i = 0; i < PATHS; i++) paths[i] = card.getPathNum(i);
    }

    public void show(GraphicsContext gc, int x, int y) {
        int xOff = 0; int yOff = 0; int r = rot % 360;
        if (r == 90 || r == 180) xOff = Tile.SIZE;
        if (r == 180 || r == 270) yOff = Tile.SIZE;
        gc.save();
        gc.translate(x + xOff, y + yOff);
        gc.rotate(rot);
        gc.drawImage(image, 0, 0);
        gc.restore();
        if (SHOW_ID) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeText(id + "", x+5, y+15);
        }
    }

    public int getNextSpotFor(int prevSpot) {
        return paths[prevSpot];
    }

    // TESTING
    public void printPaths() {
        System.out.print("[");
        for (int i = 0; i < PATHS; i++) {
            System.out.print(paths[i]);
            if (i != PATHS - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public void incRot() {
        rot += 90;
        if (rot == 360) rot = 0;
        int[] newPaths = new int[PATHS];
        for (int i = 0; i < PATHS; i++) {
            newPaths[i] = (paths[(i+2) % 8] + 6) % 8;
        }
        for (int i = 0; i < PATHS; i++) paths[i] = newPaths[i]; // Copy
    }

    private int getPathNum(int i) { return paths[i]; }

    public Image getImage() { return image; }
    public int getID() { return id; }
    public int getRot() { return rot; }
    public int[] getPaths() { return paths; }

    public void setPaths(int[] pathsArr) { paths = pathsArr; }

}
