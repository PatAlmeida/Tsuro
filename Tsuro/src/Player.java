import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

    public static final int SIZE = 20;

    private Tsuro tsuro;
    private Tile tile;
    private int id, spotIndex;
    private Color color;
    private boolean goneOffBoard;
    private Hand hand;

    public Player(Tsuro myTsuro, Tile myTile, int myID, int si, Color col) {
        tsuro = myTsuro;
        tile = myTile;
        id = myID;
        spotIndex = si;
        color = col;
        goneOffBoard = false;
        hand = new Hand(this, tsuro);
    }

    public Player(Tsuro myTsuro, Player player) {
        tsuro = myTsuro;
        tile = new Tile(player.getTile());
        id = player.getID();
        spotIndex = player.getSpotIndex();
        color = player.getColor();
        goneOffBoard = player.hasGoneOffBoard();
        hand = new Hand(this, tsuro, player.getHand());
    }

    public void show(GraphicsContext gc) {
        Offsets off = Tile.getOffsets(spotIndex);
        gc.setFill(Color.BLACK);
        gc.fillOval(tile.getRealX() + off.x - SIZE/2 - 2, tile.getRealY() + off.y - SIZE/2 - 2, SIZE + 4, SIZE + 4);
        gc.setFill(color);
        gc.fillOval(tile.getRealX() + off.x - SIZE/2, tile.getRealY() + off.y - SIZE/2, SIZE, SIZE);
    }

    public static Color getRandomColor() {
        return Color.color(Math.random(), Math.random(), Math.random());
    }

    public void playNonLosingMove() {

        if (hand.getCurSize() == 0 || goneOffBoard) return;

        int startingSize = hand.getCurSize();
        LocationInfo[] locationInfo = tsuro.getPlayersLocationInfo();
        ArrayList<HandIDRotPair> livingPlays = new ArrayList<HandIDRotPair>();

        for (int i = 0; i < startingSize; i++) {

            Card c = hand.getCard(i);
            CardInfoHelper cardInfo = new CardInfoHelper(c.getImage(), c.getID(), c.getRot(), c.getPaths());

            for (int j = 0; j < Card.ROTATIONS; j++) {

                for (int k = 0; k < j; k++) hand.getCard(i).incRot();
                hand.playCard(i);

                if (!tsuro.hasPlayerGoneOffBoard(id)) {
                    livingPlays.add(new HandIDRotPair(i, j));
                }

                tsuro.resetLocationInfo(locationInfo);
                if (!goneOffBoard) tile.removeCard();

                if (hand.getCurSize() != startingSize) hand.addDummyCard(i);
                else tsuro.decrementDeckPointer();

                hand.setCard(i, new Card(cardInfo));

            }
        }

        chooseAndPlayMove(livingPlays);

    }

    private void chooseAndPlayMove(ArrayList<HandIDRotPair> livingPlays) {

        HandIDRotPair play = null;
        boolean noGoodMove = false;
        if (livingPlays.size() > 0) {
            int rand = (int) (Math.random() * livingPlays.size());
            play = livingPlays.get(rand);
        } else {
            noGoodMove = true;
            play = new HandIDRotPair(0, 0);
        }

        if (!(noGoodMove && Tsuro.PATH_ANIM_TESTING)) {
            for (int i = 0; i < play.rotations; i++) hand.getCard(play.handID).incRot();
            hand.playCard(play.handID);
            tsuro.playersFollowPath();
        }

    }

    public void resetTo(LocationInfo locInfo) {
        tile = tsuro.getBoardTile(locInfo.tileY, locInfo.tileX);
        spotIndex = locInfo.spotIndex;
        goneOffBoard = locInfo.goneOffBoard;
    }

    private Hand getHand() { return hand; }
    private Color getColor() { return color; }

    public int getNextSpotIndex() { return tile.getNextSpotIndex(spotIndex); }
    public int getTileX() { return tile.getX(); }
    public int getTileY() { return tile.getY(); }
    public Tile getTile() { return tile; }
    public int getID() { return id; }
    public int getSpotIndex() { return spotIndex; }
    public boolean hasGoneOffBoard() { return goneOffBoard; }

    public void showHand(GraphicsContext gc) { hand.show(gc); }
    public void checkHandHover(double x, double y) { hand.checkHover(x, y); }
    public void checkHandClick(double x, double y) { hand.checkClick(x, y); }
    public void rotateHand() { hand.rotate(); }
    public void setTile(Tile newTile) { tile = newTile; }
    public void setSpotIndex(int newSI) { spotIndex = newSI; }
    public void wentOffBoard() { goneOffBoard = true; }
    public void resetOffBoard() { goneOffBoard = false; }

}
