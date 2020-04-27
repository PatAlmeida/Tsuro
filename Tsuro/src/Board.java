import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

    public static final int DIM = 6;
    public static final int SIZEX = 969;
    public static final int SIZEY = 802;
    public static final int MARGIN = 50;

    private Tsuro tsuro;
    private Tile[][] tiles;

    public Board(Tsuro myTsuro) {

        tsuro = myTsuro;

        tiles = new Tile[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

    }

    public void show(GraphicsContext gc) {

        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, SIZEX, SIZEY);

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j].show(gc);
            }
        }

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        for (int i = 0; i < DIM + 1; i++) {
            gc.strokeLine(MARGIN, Tile.SIZE*i + MARGIN, SIZEX-2*MARGIN-Tile.SIZE, Tile.SIZE*i + MARGIN);
            gc.strokeLine(Tile.SIZE*i + MARGIN, MARGIN, Tile.SIZE*i + MARGIN, SIZEY-MARGIN);
        }

        if (Tsuro.SHOW_DECK_SIZE) tsuro.showDeckSize(gc);

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

    public void playerFollowPath(Player player) {
        while (player.getTile().hasCard() && !player.hasGoneOffBoard()) {
            playerFollowTile(player);
        }
    }

    // TESTING
    public void applyCardsToTiles() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tiles[i][j].setCard(tsuro.drawCard());
            }
        }
    }

    public boolean cardAt50() { return tiles[5][0].hasCard(); }
    public Tile getTile(int i, int j) { return tiles[i][j]; }

}
