import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.canvas.GraphicsContext;

public class Deck {

    public static final int MAX_SIZE = 35;
    private static final boolean PRINT_DECK_EMPTY = false;

    private Tsuro tsuro;
    private Card[] cards;
    private int deckPointer, curSize;

    public Deck(Tsuro myTsuro) {

        tsuro = myTsuro;
        cards = new Card[MAX_SIZE];
        deckPointer = 0;
        curSize = MAX_SIZE;

        for (int i = 0; i < curSize; i++) cards[i] = new Card(i);

        try {
            Scanner scan = new Scanner(new File("data/paths.txt"));
            while (scan.hasNext()) {
                Scanner lineScan = new Scanner(scan.nextLine());
                lineScan.useDelimiter(", ");
                int index = Integer.parseInt(lineScan.next());
                int[] paths = new int[Card.PATHS];
                for (int i = 0; i < Card.PATHS; i++) {
                    paths[i] = lineScan.nextInt();
                }
                cards[index].setPaths(paths);
                lineScan.close();
            }
            scan.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        shuffle();

    }

    public void resetToInclude(ArrayList<Card> leftoverCards) {
        for (int i = deckPointer; i < curSize; i++) leftoverCards.add(cards[i]);
        curSize = leftoverCards.size();
        cards = new Card[curSize];
        for (int i = 0; i < curSize; i++) cards[i] = leftoverCards.get(i);
        shuffle();
    }

    public void showSize(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.strokeText("Deck Size: " + (curSize - deckPointer), 825, 170);
    }

    public int getDeckPointer() { return deckPointer; }

    public void decrementPointer() { deckPointer--; }

    public Card draw() {
        if (deckPointer < curSize) {
            deckPointer++;
            return cards[deckPointer - 1];
        } else {
            if (PRINT_DECK_EMPTY) System.out.println("Deck is empty");
            return null;
        }
    }

    public void shuffle() {

        deckPointer = 0;

        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < curSize; i++) nums.add(i);

        ArrayList<Card> newCards = new ArrayList<Card>();
        for (int i = 0; i < curSize; i++) {
            int rand = (int) (Math.random() * (curSize - i));
            int randElement = nums.get(rand);
            newCards.add(cards[randElement]);
            nums.remove(rand);
        }

        for (int i = 0; i < curSize; i++) cards[i] = newCards.get(i);

    }

}
