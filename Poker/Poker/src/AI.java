//Kunj Patel
//Poker
//extends Player, handles AI methods such as betting algorithm

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class AI extends Player{
	int pairStrength;
	Scanner s;
	File fileName;
	int raise, fold;
	int betState;
	final String[] betStateString = {"Raise", "Check", "Fold"};
	private final int [] pairValue = {32,6,7,9,10,12,14,16,18,20,23,25,28};
	
	public AI(){
		super();
		pairStrength = 0;
	}
	
	//uses odds from site to decide AI action http://wizardofodds.com/games/texas-hold-em/4-player-game/
	public void preFlopBet(){
		pairStrength = twoCardStrength();
		getRatio();
		Random r = new Random();
		int random = r.nextInt(100);
		if(random>=raise)
			betState = 0;
		else if(random<raise && random>fold)
			betState = 1;
		else if(random<=fold)
			betState = 2;
	}
	
	//returns the pair that the AI has
	public int twoCardStrength(){
		int valueA = super.getValueA();
		int valueB = super.getValueB();
		if(valueA%13 == valueB%13)
			return pair(valueA%13);
		
		else if(valueA/13 == valueB/13)
			return suitedPair(valueA%13, valueB%13);
		
		else
			return unsuitedPair(valueA%13, valueB%13);
	}
	
	//returns hand strength of a pair
	private int pair(int cardA) {
		return pairValue[cardA];
	}

	//returns two card strength based on two cards of same suit
	private int suitedPair(int valueA, int valueB) {
		fileName = new File("res/suitedPair");
		try {
			s = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int combine = 0;
		if(valueA<valueB)
			combine += valueB + (valueA*100);
		else if(valueB<valueA)
			combine += valueA + (valueB*100);
		for(int i = 0; i < 78; i++){
			if(combine == s.nextInt())
				return s.nextInt();
			else
				s.nextInt();
		}
		return 0;
	}
	
	//returns two card strenth based on two cards of different suits
	private int unsuitedPair(int valueA, int valueB) {
		fileName = new File("res/unsuitedPair");
		try {
			s = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int combine = 0;
		if(valueA<valueB)
			combine += valueB + (valueA*100);
		else if(valueB<valueA)
			combine += valueA + (valueB*100);
		for(int i = 0; i < 78; i++){
			if(combine == s.nextInt())
				return s.nextInt();
			else
				s.nextInt();
		}
		return 0;
	}

	//gets ratio of odds, to decide whether to raise check or fold
	private void getRatio() {
		if(pairStrength < 3){
			raise = 100;
			fold = 60;
		}
		else if(pairStrength < 6){
			raise = 100;
			fold = 45;
		}
		else if(pairStrength < 10){
			raise = 99;
			fold = 20;
		}
		else if(pairStrength < 16){
			raise = 95;
			fold = 0;
		}
		else if(pairStrength < 21){
			raise = 65;
			fold = 0;
		}
		else if(pairStrength < 28){
			raise = 55;
			fold = 0;
		}
		else{
			raise = 45;
			fold = 0;
		}
	}

	//determines AI action using evaluateHand from Player class
	public void postFlopBet(int[]community, int extra){
		super.evaluateHand(community, extra);
		postRatio();
		Random r = new Random();
		int random = r.nextInt(100);
		if(random>=raise)
			betState = 0;
		else if(random<raise && random>fold)
			betState = 1;
		else if(random<=fold)
			betState = 2;
	}
	
	//gets ratio of odds, to decide whether to raise check or fold for post flop hand
	private void postRatio() {
		int hand = Integer.parseInt(super.getHandStrength().substring(0, 2));
		if(hand == 10){
			raise = 5;
			fold = 0;
		}
		else if(hand == 9){
			raise = 10;
			fold = 0;
		}
		else if(hand == 8){
			raise = 20;
			fold = 0;
		}
		else if(hand == 7){
			raise = 25;
			fold = 0;
		}
		else if(hand == 6){
			raise = 30;
			fold = 0;
		}
		else if(hand == 5){
			raise = 35;
			fold = 0;
		}
		else if(hand == 4){
			raise = 40;
			fold = 0;
		}
		else if(hand == 3){
			raise = 60;
			fold = 10;
		}
		else if(hand == 2){
			raise = 70;
			fold = 20;
		}
		else if(hand == 1){
			raise = 90;
			fold = 60;
		}
	}

}
