import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

    public static final int DIM = 6;
    public static final int SIZE = 802;
    public static final int MARGIN = 50;

    private Tile[][] tiles;

    public Board() {

        tiles = new Tile[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

    }

    public void show(GraphicsContext gc) {

        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, SIZE, SIZE);

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j].show(gc);
            }
        }

        gc.setLineWidth(3);
        for (int i = 0; i < DIM + 1; i++) {
            gc.strokeLine(MARGIN, Tile.SIZE*i + MARGIN, SIZE-MARGIN, Tile.SIZE*i + MARGIN);
            gc.strokeLine(Tile.SIZE*i + MARGIN, MARGIN, Tile.SIZE*i + MARGIN, SIZE-MARGIN);
        }

    }

    public void playerFollowTile(Player player) {

        int nsi = player.getNextSpotIndex(); // nextSpotIndex
        int x = player.getTileX();
        int y = player.getTileY();

        Tile nextTile = null;

        if (nsi == 0 || nsi == 7) {
            if (x == DIM - 1) player.wentOffBoard();
            else nextTile = tiles[y][x+1];
        } else if (nsi == 1 || nsi == 2) {
            if (y == 0) player.wentOffBoard();
            else nextTile = tiles[y-1][x];
        } else if (nsi == 3 || nsi == 4) {
            if (x == 0) player.wentOffBoard();
            else nextTile = tiles[y][x-1];
        } else if (nsi == 5 || nsi == 6) {
            if (y == DIM - 1) player.wentOffBoard();
            else nextTile = tiles[y+1][x];
        }

        if (player.hasGoneOffBoard()) {
            player.setSpotIndex(nsi);
        } else {
            player.setTile(nextTile);
            player.setSpotIndex(Tile.getAdjSpot(nsi));
        }

    }

    // TESTING
    public void applyCardsToTiles(Deck deck) {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j].setCard(deck.draw());
            }
        }
    }

    public Tile getTile(int i, int j) { return tiles[i][j]; }

}
