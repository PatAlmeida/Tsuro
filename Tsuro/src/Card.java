import javafx.scene.image.Image;

public class Card {

    public static final int PATHS = 8;

    private Image image;
    private int[] paths;

    public Card(int index) {
        image = new Image("file:images/tile" + index + ".png");
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

    public Image getImage() { return image; }

    public void setPaths(int[] pathsArr) { paths = pathsArr; }

}
