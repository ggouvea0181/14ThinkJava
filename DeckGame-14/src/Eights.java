import java.util.Scanner;

/**
 * Simulates a game of Crazy Eights.  See
 * https://en.wikipedia.org/wiki/Crazy_Eights
 * for basic play and scoring rules.
 */
public class Eights {

    private Player one;
    private Player two;
    private Hand drawPile;
    private Hand discardPile;
    private Scanner in;

    /**
     * Initializes the state of the game.
     */
    public Eights() {
        Deck deck = new Deck("Deck");
        deck.shuffle();

        // deal cards to each player
        int handSize = 5;
        one = new Player("Allen");
        deck.deal(one.getHand(), handSize);

        two = new Player("Chris");
        deck.deal(two.getHand(), handSize);

        // turn one card face up
        discardPile = new Hand("Discards");
        deck.deal(discardPile, 1);

        // put the rest of the deck face down
        drawPile = new Hand("Draw pile");
        deck.dealAll(drawPile);

        // create the scanner we'll use to wait for the user
        in = new Scanner(System.in);
    }

    /**
     * Returns true if either hand is empty.
     */
    public boolean isDone() {
        return one.getHand().empty() || two.getHand().empty();
    }

    /**
     * Moves cards from the discard pile to the draw pile and shuffles.
     */
    public void reshuffle() {
        // save the top card
        Card prev = discardPile.popCard();

        // move the rest of the cards
        discardPile.dealAll(drawPile);

        // put the top card back
        discardPile.addCard(prev);

        // shuffle the draw pile
        drawPile.shuffle();
    }

    /**
     * Returns a card from the draw pile.
     */
    public Card draw() {
        if (drawPile.empty()) {
            reshuffle();
        }
        return drawPile.popCard();
    }

    /**
     * Switches players.
     */
    public Player nextPlayer(Player current) {
        if (current == one) {
            return two;
        } else {
            return one;
        }
    }

    /**
     * Displays the state of the game.
     */
    public void displayState() {
        one.display();
        two.display();
        discardPile.display();
        System.out.print("Draw pile: ");
        System.out.println(drawPile.size() + " cards");
    }

    /**
     * Waits for the user to press enter.
     */
  /*  public void waitForUser() {
        in.nextLine();
    }*/

    /**
     * One player takes a turn.
     */
    public void takeTurn(Player player) {
        Card prev = discardPile.last();
        Card next = player.play(this, prev);
        discardPile.addCard(next);

        System.out.println(player.getName() + " plays " + next);
        System.out.println();
    }

    /**
     * Plays the game.
     */
    public int[] playGame(int s, int s2) {
        Player player = one;
        int[] score = new int[2];

        // keep playing until there's a winner
        while (!isDone()) {
            displayState();
            //waitForUser();
            takeTurn(player);
            player = nextPlayer(player);
        }

        // display the final score
        one.displayScore(s);
        two.displayScore(s2);
        
        score[0] = one.score(s);
        score[1] = two.score(s2);
        return score;
    }

    /**
     * Creates the game and runs it.
     */
    public static void main(String[] args) {
    	int[] score = new int[2];
    	for(int i=0;i<100;i++){
    		Eights game = new Eights();
    		if(i == 0){
    			score = game.playGame(0,0);
    		}else{
    			score = game.playGame(score[0], score[1]);
    		}
    	}
        
    }

}
