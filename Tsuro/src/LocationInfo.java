public class LocationInfo {

    public int tileX, tileY, spotIndex;
    public boolean goneOffBoard;

    public LocationInfo(int tx, int ty, int si, boolean off) {
        tileX = tx; tileY = ty; spotIndex = si;
        goneOffBoard = off;
    }

}
