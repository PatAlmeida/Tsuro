import java.util.ArrayList;

public class Game {

    private Tsuro tsuro;
    private int turnIndex;
    private int waitCount;
    private ArrayList<Integer> livePlayerIDs;

    public Game(Tsuro myTsuro) {
        tsuro = myTsuro;
        turnIndex = 0;
        waitCount = 0;
        livePlayerIDs = new ArrayList<Integer>();
        for (int i = 0; i < Tsuro.NUM_PLAYERS; i++) livePlayerIDs.add(i);
    }

    public void update(int count) {

        if (livePlayerIDs.size() == 0) return;

        int nextToPlay = livePlayerIDs.get(turnIndex);
        if (nextToPlay != 0 || !Tsuro.HUMAN_PLAY) {
            waitCount++;
            if (waitCount == Tsuro.TURN_DELAY_COUNT) {
                tsuro.playNonLosingMoveFor(nextToPlay);
                advanceTurn(nextToPlay);
                waitCount = 0;
            }
        }

    }

    public void advanceTurn(int justPlayed) {

        livePlayerIDs = tsuro.getLivingPlayerIDs();
        if (livePlayerIDs.size() == 0) return;

        if (livePlayerIDs.contains(justPlayed)) {
            int index = livePlayerIDs.indexOf(justPlayed);
            turnIndex = (index + 1) % livePlayerIDs.size();
        } else {
            // Find the first ID bigger than justPlayed
            int i; boolean foundOne = false;
            for (i = 0; i < livePlayerIDs.size(); i++) {
                if (livePlayerIDs.get(i) > justPlayed) {
                    foundOne = true;
                    break;
                }
            }
            if (foundOne) turnIndex = i;
            else turnIndex = 0;
        }

    }

    public boolean isHumanTurn() { return turnIndex == 0; }

    public void humanTurnFinish() { advanceTurn(0); }

}
