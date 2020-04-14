import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Card {

    public static final int PATHS = 8;

    private Image image;
    private int[] paths;
    private int rot;

    public Card(int index) {
        image = new Image("file:images/tile" + index + ".png");
        rot = 0;
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
        int[] newPaths = new int[PATHS];
        for (int i = 0; i < PATHS; i++) {
            newPaths[i] = (paths[(i+2) % 8] + 6) % 8;
        }
        for (int i = 0; i < PATHS; i++) paths[i] = newPaths[i]; // Copy
    }

    public Image getImage() { return image; }
    public int getRot() { return rot; }

    public void setPaths(int[] pathsArr) { paths = pathsArr; }

}
