import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck {

    public static final int SIZE = 35;
    private static final boolean PRINT_DECK_EMPTY = false;

    private Tsuro tsuro;
    private Card[] cards;
    private int deckPointer;

    public Deck(Tsuro myTsuro) {

        tsuro = myTsuro;
        cards = new Card[SIZE];
        deckPointer = 0;

        for (int i = 0; i < SIZE; i++) {
            cards[i] = new Card(i);
        }

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

    private Card getCard(int i) { return cards[i]; }

    public int getDeckPointer() { return deckPointer; }

    public void decrementPointer() { deckPointer--; }

    public Card draw() {
        if (deckPointer < SIZE) {
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
        for (int i = 0; i < SIZE; i++) nums.add(i);

        ArrayList<Card> newCards = new ArrayList<Card>();
        for (int i = 0; i < SIZE; i++) {
            int rand = (int) (Math.random() * (SIZE - i));
            int randElement = nums.get(rand);
            newCards.add(cards[randElement]);
            nums.remove(rand);
        }

        for (int i = 0; i < SIZE; i++) cards[i] = newCards.get(i);

    }

    // TESTING
    public void printCardPaths() {
        for (Card card : cards) card.printPaths();
    }

}
