import javafx.scene.image.Image;

public class CardInfoHelper {

    public static final int PATHS = 8;

    public Image image;
    public int id, rot;
    public int[] paths;

    public CardInfoHelper(Image im, int myID, int rotation, int[] pathsArr) {
        image = im;
        id = myID; rot = rotation;
        paths = new int[PATHS];
        for (int i = 0; i < PATHS; i++) paths[i] = pathsArr[i];
    }

}
