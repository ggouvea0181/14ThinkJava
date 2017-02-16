/**
 * A player in a game of crazy eights.
 */
public class Player {

    private String name;
    private Hand hand;

    /**
     * Constructs a player with an empty hand.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand(name);
    }

    /**
     * Gets the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Removes and returns a legal card from the player's hand.
     */
    public Card play(Eights eights, Card prev) {
        Card card = searchForMatch(prev);
        if (card == null) {
            card = drawForMatch(eights, prev);
        }
        return card;
    }

    /**
     * Searches the player's hand for a matching card.
     */
    public Card searchForMatch(Card prev) {
    	
    	//IF THERE IS ANY 8 IN THE HAND, PLAY THE 8.
    	for(int i = 0; i< hand.size();i++){
    		Card card = hand.getCard(i);
            if(card.getRank() == 8){
            	return hand.popCard(i);
            }
    	}
    	// #########################################
    	//ELSE VERIFY IF CARDS MATCH BY RANK
    	int aux = 50;
    	boolean found = false;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (cardMatches(card, prev)) {
            	found = true;
            	if(aux == 50){
            		aux = i;
            	}else{
            		Card card2 = hand.getCard(aux);
            		if(card.getRank() > card2.getRank()){
            			aux = i;
            		}
            	}
            }
        }
        if(found){
        	return hand.popCard(aux);
        }
        //############################################
        return null;
    }

    /**
     * Draws cards until a match is found.
     */
    public Card drawForMatch(Eights eights, Card prev) {
        while (true) {
            Card card = eights.draw();
            System.out.println(name + " draws " + card);
            if (cardMatches(card, prev)) {
                return card;
            }
            hand.addCard(card);
        }
    }

    /**
     * Checks whether two cards match.
     */
    public static boolean cardMatches(Card card1, Card card2) {
        if (card1.getSuit() == card2.getSuit()) {
            return true;
        }
        if (card1.getRank() == card2.getRank()) {
            return true;
        }
        if (card1.getRank() == 8) {
            return true;
        }
        return false;
    }

    /**
     * Calculates the player's score (penalty points).
     */
    public int score(int s) {
        int sum = s;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            int rank = card.getRank();
            if (rank == 8) {
                sum -= 20;
            } else if (rank > 10) {
                sum -= 10;
            } else {
                sum -= rank;
            }
        }
        return sum;
    }

    /**
     * Displays the player's hand.
     */
    public void display() {
        hand.display();
    }

    /**
     * Displays the player's name and score.
     */
    public void displayScore(int s) {
        System.out.println(name + " has " + score(s) + " points");
    }

}
