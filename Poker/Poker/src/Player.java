//Kunj Patel
//Poker
//handles all player methods, e.g. evaluates hand strength

import java.util.ArrayList;
import java.util.Collections;


public class Player {
	private int valueA, valueB;
	private int money, bet;
	private String handStrength;
	private ArrayList <Integer> pool;
	private boolean notFold;
	private boolean alive; 
	
	public Player(){
		bet = 0;
		handStrength = "0";
		money = 100;
		pool = new ArrayList<Integer>();
		notFold = true;
		alive = true;
	}
	
	//enters the bet from money
	public void enterBet(){
		money -=bet;
	}
	
	//evaluates hand strength
	public void evaluateHand(int[] community, int extra) {
		pool.add(community[0]);
		pool.add(community[1]);
		pool.add(community[2]);
		if(extra>0)
			pool.add(community[3]);
		if(extra>1)
			pool.add(community[4]);
		pool.add(valueA);
		pool.add(valueB);
		
		Collections.sort(pool);
			checkRoyalFlush();
		if(handStrength == "0")
			checkStraightFlush(extra);
		if(handStrength == "0")
			checkFour(extra);
		if(handStrength == "0")
			checkFullHouse(extra);
		if(handStrength == "0")
			checkFlush(extra);
		if(handStrength == "0")
			checkStraight(extra);
		if(handStrength == "0")
			checkTriple(extra);
		if(handStrength == "0")
			checkTwoPair(extra);
		if(handStrength == "0")
			checkDouble(extra);
		if(handStrength == "0")
			checkHigh(extra);
	}

	//checks to see if cards are in a royal flush
	private void checkRoyalFlush() {
		for(int i = 0; i < 3; i++){
			if(pool.contains(13*i)){
				int count = 1;
				for(int j = 0; j < 4 ;j++){
					if(pool.contains((13*i+9)+j)){
						count++;
					}
					else{
						count = 1;
					}
				}
				if(count == 5){
					handStrength = "10";
					addToHS(0);
					addToHS(12);
					addToHS(11);
					addToHS(10);
					addToHS(9);
				}
			}
		}
	}

	//checks to see if cards are in a straight flush
	private void checkStraightFlush(int extra) {
		for(int i = 0; i < (extra+1); i++){
			int suit = pool.get(i)/13;
			int rank = pool.get(i)%13;
			int count = 1;
			for(int j = 0; j < 4; j++){
				int nextCard = pool.get(i+j);
				if(nextCard%4 == suit){
					if(nextCard/4 == rank+(j+1)){
						count++;
					}
					else
						count = 1;
				}
				else count = 1;
			}
			if(count == 5){
				handStrength = "09";
				addToHS(0);
				addToHS(12);
				addToHS(11);
				addToHS(10);
				addToHS(9);
			}
		}
	}

	//checks to see if cards are four of a kind
	private void checkFour(int extra) {
		ArrayList <Integer> F =new ArrayList <Integer>();
		for(int i = 0; i < (extra+5); i++){
			F.add(pool.get(i)%13);
		}
		Collections.sort(F);
		for(int i = (extra+4); i > 2; i--){
				if(F.get(i-1) == F.get(i) && F.get(i-2) == F.get(i) && F.get(i-3) == F.get(i)){
					handStrength = "08";
					addToHS(F.get(i));
					addToHS(F.get(i-1));
					addToHS(F.get(i-2));
					addToHS(F.get(i-3));
					F.set(i, -1);
					F.set(i-1, -1);
					F.set(i-2, -1);
					F.set(i-3, -1);
					addFiller(F,1);
				}
		}
	}
	
	//checks to see if cards are full house (triple and pair)
	private void checkFullHouse(int extra) {
		ArrayList <Integer> FH = new ArrayList <Integer>();
		boolean trip = false, pair = false;
		int rank = 0;
		for(int i = 0; i < (extra+5); i++){
			FH.add(pool.get(i)%13);
		}
		Collections.sort(FH);
		for(int i = (extra+4); i > 1; i--){
			rank = FH.get(i);
			int count = 1;
			for(int j = 1; j < 3; j++){
				if(FH.get(i-j) == rank)
					count++;
			}
			if(count == 3){
				trip = true;
				handStrength = "07";
				addToHS(FH.get(i));
				addToHS(FH.get(i-1));
				addToHS(FH.get(i-2));
				FH.set(i, -1);
				FH.set(i-1, -1);
				FH.set(i-2, -1);
			}
		}
		Collections.sort(FH);
		if(trip){
			for(int i = (extra+4); i > 2; i--){
				if(FH.get(i-1) == FH.get(i)){
					addToHS(FH.get(i));
					addToHS(FH.get(i-1));
					pair = true;
				}
			}
		}
		if(!pair)
			handStrength = "0";
	}
	
	//checks to see if cards are flush
	private void checkFlush(int extra) {
		int flushCount = 0;
		int[] flushCards = {-1,-1,-1,-1,-1};
		for(int i = (extra+4); i > 3; i--){
			for(int j = 0; j < 5; j++){
				if(pool.get(i)/13 == pool.get(i-j)/13){
					flushCount++;
					flushCards[j] = pool.get(i)%13;
				}
				else
					flushCount = 0;
			}	
		}
		if(flushCount>4){
			handStrength = "06";
			addToHS(flushCards[0]);
			addToHS(flushCards[1]);
			addToHS(flushCards[2]);
			addToHS(flushCards[3]);
			addToHS(flushCards[4]);
		}
	}

	//checks if cards are straight
	private void checkStraight(int extra) {
		ArrayList <Integer> S =new ArrayList <Integer>();
		for(int i = 0; i < (extra+5); i++){
			S.add(pool.get(i)&13);
		}
		Collections.sort(S);
		for(int i = (extra+4); i > 3; i--){
			if(S.get(i-1) == S.get(i)-1 && S.get(i-2) == S.get(i)-2 && S.get(i-3) == S.get(i)-3 && S.get(i-4) == S.get(i)-4){
				handStrength = "05";
				addToHS(S.get(i));
				addToHS(S.get(i-1));
				addToHS(S.get(i-2));
				addToHS(S.get(i-3));
				addToHS(S.get(i-4));
			}
		}
	}

	//checks if there is a triple
	private void checkTriple(int extra) {
		ArrayList <Integer> T =new ArrayList <Integer>();
		for(int i = 0; i < (extra+5); i++){
			T.add(pool.get(i)%13);
		}
		Collections.sort(T);
		for(int i =(extra+4); i > 1; i--){
			if(T.get(i-1) == T.get(i) && T.get(i-2) == T.get(i)){
				handStrength = "04";
				addToHS(T.get(i));
				addToHS(T.get(i-1));
				addToHS(T.get(i-2));
				T.set(i, -1);
				T.set(i-1, -1);
				T.set(i-2, -1);
				addFiller(T,2);
			}
		}
	}

	//checks if there are two pairs
	private void checkTwoPair(int extra) {
		ArrayList <Integer> TP = new ArrayList <Integer>();
		boolean pair = false;
		for(int i = 0; i < (extra+5); i++){
			TP.add(pool.get(i)%13);
		}
		Collections.sort(TP);
		for(int i = (extra+4); i > 1; i--){
			if(TP.get(i-1) == TP.get(i)){
				handStrength = "03";
				addToHS(TP.get(i));
				addToHS(TP.get(i-1));
				TP.set(i, -1);
				TP.set(i-1, -1);
				break;
			}
		}
		if(handStrength != "0"){
			for(int i = (extra+2); i > 0; i--){
				if(TP.get(i) != -1){
					if(TP.get(i-1) == TP.get(i)){
						addToHS(TP.get(i));
						addToHS(TP.get(i-1));
						TP.set(i, -1);
						TP.set(i-1, -1);
						addFiller(TP,1);
						pair = true;
					}
				}
			}
		}
		if(!pair)
			handStrength = "0";
	}

	//checks if there is one pair
	private void checkDouble(int extra) {
		ArrayList <Integer> P = new ArrayList <Integer>();
		for(int i = 0; i < (extra+5); i++){
			P.add(pool.get(i)%13);
		}
		Collections.sort(P);
		for(int i = (extra+4); i > 1; i--){
			if(P.get(i-1) == P.get(i)){
				handStrength = "02";
				addToHS(P.get(i));
				addToHS(P.get(i-1));
				P.set(i, -1);
				P.set(i-1, -1);
				addFiller(P,3);
			}
		}
	}

	//converts card into hex value and adds to handStrength
	private void addToHS(int value) {
		if(value == 0)
			handStrength+="E";
		else if(value == 9)
			handStrength+="A";
		else if(value == 10)
			handStrength+="B";
		else if(value == 11)
			handStrength+="C";
		else if(value == 12)
			handStrength+="D";
		else
			handStrength += value+1;
		
	}

	//if no other hand is possible, adds five high cards
	private void checkHigh(int extra) {
		handStrength = "01";
		ArrayList <Integer> H = new ArrayList<Integer>();
		for(int i = 0; i < (extra+5); i++){
			H.add(pool.get(i)%13);
		}
		Collections.sort(H);
		addFiller(H,5);
	}
	
	//adds high cards to complete five cards in hand strength
	private void addFiller(ArrayList<Integer> fill, int toAdd) {	
		Collections.sort(fill);
		for(int i = 0; i < 5; i++){
			if(fill.get(i) == 0){
				handStrength+="E";
				toAdd--;
			}
		}
		for(int i = 0; i < toAdd; i++){
			int pos = (fill.size()-i)-1;
			if(fill.get(pos) == 9)
				handStrength+="A";
			else if(fill.get(pos) == 10)
				handStrength+="B";
			else if(fill.get(pos) == 11)
				handStrength+="C";
			else if(fill.get(pos) == 12)
				handStrength+="D";
			else
				handStrength += fill.get(pos)+1;
		}
		
	}
	
	//getters and setters for variables
	
	public int getValueA() {
		return valueA;
	}

	public void setValueA(int valueA) {
		this.valueA = valueA;
	}

	public int getValueB() {
		return valueB;
	}

	public void setValueB(int valueB) {
		this.valueB = valueB;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		if(bet>money)
			bet = money;
		this.bet = bet;
	}

	public String getHandStrength() {
		return handStrength;
	}

	public void setHandStrength(String handStrength) {
		this.handStrength = handStrength;
	}

	public ArrayList<Integer> getPool() {
		return pool;
	}

	public void setPool(ArrayList<Integer> pool) {
		this.pool = pool;
	}

	public void clearPool() {
		pool.clear();
	}

	
	public boolean isNotFold() {
		return notFold;
	}


	public void setNotFold(boolean notFold) {
		this.notFold = notFold;
	}

	
	public boolean isAlive() {
		return alive;
	}
	

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
