//Kunj Patel
//Poker
//creates the deck handles deck related methods such as shuffle and deal

import java.util.LinkedList;
import java.util.Queue;


public class Deck {
	int[] cards = new int[52];
	private int[] community;
	Queue<Integer> deck = new LinkedList<Integer>();
	
	public Deck(){
		community = new int[5];
		for(int i=0;i<52;i++){
			cards[i] = i;
		}
		shuffle();
		
	}
	
	//rearranges array and puts it into the queue
	public void shuffle(){
		for(int i = 0; i < 52; i++) {
			int replace = (int)(Math.random() * 52);
			int curr = cards[i];
			cards[i] = cards[replace];
			cards[replace] = curr;
	    }
		for(int i = 0; i < 52; i++){
			deck.add(cards[i]);
		}
	}
	
	//deals one card
	public int dealCard(){
		return deck.poll();
	}
	
	//return the community cards
	public int[] getCommunity() {
		return community;
	}
	
	//returns the community card in the position given
	public int getCommunity(int pos) {
		return community[pos];
	}
	
	//sets the community card based on the inputs
	public void setCommunity(int pos, int value) {
		community[pos] = value;
	}
}
