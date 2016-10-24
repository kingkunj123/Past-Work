//Kunj Patel
//Poker Game
//Main class, draws everything, and calls all classes and methods from here

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	
	//screen dimensions and variables
	static final int WIDTH = 800;
	static final int HEIGHT = WIDTH / 4 * 3; //4:3 aspect ratio
	private JFrame frame;
	
	//holds position in switch case
	int playCounter;
	
	//how much each person bet
	int perPerson;
	
	int winner;
	
	int pot;
	
	int numAlive;
	
	//holds end condition, 0 = not done, 1 = lost, 2 = won
	int endCondition;
		
	//background images
	BufferedImage mainScreen;
	BufferedImage helpScreen;
	
	//card spritesheet
	SpriteSheet cards;
	
	//card sprites
	BufferedImage [] card;
	
	//mouse coordinates
	int x,y;
	
	//players
	Player player1;
	AI player2;
	AI player3;
	AI player4;
	
	//deck of cards
	static Deck deck;
	
	Mouse mouse = new Mouse();
	
	//game states
	int state;
	int menuState = 0, playState = 1, helpState = 2, endState = 3;
	
	//game updates per second
	static final int UPS = 60;
	
	//variables for the thread
	private Thread thread;
	private boolean running;
	
	//used for drawing items to the screen
	private Graphics2D graphics;
				
	//initialize game objects, load media(pics, music, etc)
	public void init() { 
		playCounter = 1;
		endCondition = 0;
		numAlive = 4;
		initCards();
		player1 = new Player();
		player2 = new AI();
		player3 = new AI();
		player4 = new AI();
		state = 0;
		makeMenuBackground();
		makeHelpBackground();
	}
	
	//initializes deck, and loads card sprites
	private void initCards() {
		deck = new Deck();
		cards = new SpriteSheet();
		cards.loadSpriteSheet("res/cards.jpg");
		card = new BufferedImage[53];
		for(int i = 0; i < 4; i++){
			for(int j = 0;j < 13; j++){
				card[i*13+j] = cards.getSprite(72*j, 100*i, 72, 100);
			}
		}
		card[52] = cards.getSprite(360, 400, 72, 100);
	}

	//creates the background used in most states
	private void makeMenuBackground() {
		try {
			mainScreen = ImageIO.read(new File("res/menuBackground.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//creates the background used in the help state
	private void makeHelpBackground() {
		try {
			helpScreen = ImageIO.read(new File("res/help.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//update game objects
	public void update() {
		mousePos();
		if(state == menuState){
			menuState();
		}
		else if(state == helpState){
			helpState();
		}
		else if(state == playState){
			playState();
		}
	}
	
	//checks if mouse is clicked and return coordinates
	private void mousePos() {
		if(mouse.isPress()){
			x = mouse.getX();
			y = mouse.getY();
			mouse.setPress(false);
		}
	}
	
	//creates buttons, and changes state when clicked
	private void menuState() {
		if(x >= 350 && x <= 450 && y >= 420 && y <= 470){
			state = playState;
			x = -1;
			y = -1;
		}
		if(x >= 350 && x <= 450 && y >= 475 && y <= 525){
			state = helpState;
			x = -1;
			y = -1;
		}
	}

	//creates back button, and goes back to menu when clicked
	private void helpState() {
		if(x >= 50 && x <= 150 && y >= 50 && y <= 100){
			state = menuState;
			x = -1;
			y = -1;
		}
	}
	
	//has counter that loops through all the playStates
	private void playState() {
		switch (playCounter) {
			 case 1:	
				 reset(2);
				 break;
			 case 2:	
				 dealCards(3);
				 break;
			 case 3:
				 AIBet(4, 0, player2);
				 AIBet(4, 0, player3);
				 AIBet(4, 0, player4);
				 break;
			 case 4:	
				 playerBet(5);
				 break;
			 case 5:
				 dealFlop(6);
				 break;
			 case 6:
				 AIBet(7, 1, player2);
				 AIBet(7, 1, player3);
				 AIBet(7, 1, player4);
				 break;
			 case 7:	
				 playerBet(8);
				 break;
			 case 8:
				 dealTurn(9);
				 break;
			 case 9:
				 AIBet(10, 2, player2);
				 AIBet(10, 2, player3);
				 AIBet(10, 2, player4);
				 break;
			 case 10:	
				 playerBet(11);
				 break;
			 case 11:
				 dealRiver(12);
				 break;
			 case 12:
				 AIBet(13, 3, player2);
				 AIBet(13, 3, player3);
				 AIBet(13, 3, player4);
				 break;
			 case 13:	
				 playerBet(14);
				 break;
			 case 14:
				 evaluateTable(15);
				 break;
			 case 15:
				 findWinner(16);
				 break;
			 case 16:
				 splitMoney(17);
				 break;
			 case 17:
				 checkDead(18);
				 break;
			 case 18:
				 checkEndCondition(19);
				 break;
			 case 19:
				 waitTillClick(1);
				 break;
		}
	}
	
	//resets all play state variables
	private void reset(int next) {
		player1.clearPool();
		player2.clearPool();
		player3.clearPool();
		player4.clearPool();
		pot = 0;
		deck.shuffle();
		perPerson = 0;
		player1.setBet(0);
		player2.setBet(0);
		player3.setBet(0);
		player4.setBet(0);
		player1.setNotFold(true);
		player2.setNotFold(true);
		player3.setNotFold(true);
		player4.setNotFold(true);
		player1.setHandStrength("0");
		player2.setHandStrength("0");
		player3.setHandStrength("0");
		player4.setHandStrength("0");
		playCounter = next;
	}
	
	//deals cards to remaining players
	private void dealCards(int next) {
		player1.setValueA(deck.dealCard());
		player1.setValueB(deck.dealCard());
		if(player2.isAlive()){
			player2.setValueA(deck.dealCard());
			player2.setValueB(deck.dealCard());
		}
		if(player3.isAlive()){
			player3.setValueA(deck.dealCard());
			player3.setValueB(deck.dealCard());
		}
		if(player4.isAlive()){
			player4.setValueA(deck.dealCard());
			player4.setValueB(deck.dealCard());
		}
		playCounter = next;
	}
	
	//uses algorithm to place AI bets
	private void AIBet(int next, int cardNum, AI current) {
		if(current.isNotFold()){
			if(cardNum == 0)
				current.preFlopBet();
			if(cardNum == 1)
				current.postFlopBet(deck.getCommunity(), 0);
			if(cardNum == 2)
				current.postFlopBet(deck.getCommunity(), 1);
			if(cardNum == 3)
				current.postFlopBet(deck.getCommunity(), 2);
			if(current.betState == 0){
				current.setBet(2 + perPerson);
				perPerson += 2;
			}
			else if(current.betState == 1){
				current.setBet(perPerson);
			}
			else if(current.betState == 2){
				current.setNotFold(false);
			}
		}
		getPot();
		playCounter = next;	
	}
	
	//adds all player bets into the pot
	private void getPot() {
		pot = player1.getBet() + player2.getBet() + player3.getBet() + player4.getBet();
	}
	
	//checks, raises or folds based on button clicked
	private void playerBet(int next) {
		if(x >= 500 && x <= 600 && y >= 475 && y <= 525){
			player1.setBet(2+perPerson);
			perPerson += 2;
			x = -1;
			y = -1;
			if(player2.isAlive())
				AIsettle(next, player2);
			if(player3.isAlive())
				AIsettle(next, player3);
			if(player4.isAlive())
				AIsettle(next, player4);
		}
		else if(x >= 605 && x <= 705 && y >= 530 && y <= 580){
			player1.setNotFold(false);
			playCounter = 19;
			x = -1;
			y = -1;
		}
		else if(x >= 500 && x <= 600 && y >= 530 && y <= 580){
			player1.setBet(perPerson);
			playCounter = next;
			x = -1;
			y = -1;
		}
		getPot();
	}

	//if human raises, AI gets another round to bet, without the chance to raise
	private void AIsettle(int next, AI current) {
		if(current.isNotFold()){
			current.postFlopBet(deck.getCommunity(), 2);
			if(current.betState == 1 || current.betState == 0){
				current.setBet(0 + perPerson);
				perPerson += 0;
			}
			else if(current.betState == 2){
				current.setNotFold(false);
			}
		}
		getPot();
		playCounter = next;
	}
	
	//deals the three flop cards
	private void dealFlop(int next) {
		deck.setCommunity(0,deck.dealCard());
		deck.setCommunity(1,deck.dealCard());
		deck.setCommunity(2,deck.dealCard());
		playCounter = next;
	}
	
	//deals the turn (fourth) card
	private void dealTurn(int next) {
		deck.dealCard();
		deck.setCommunity(3,deck.dealCard());
		playCounter = next;
	}
	
	//deals the river (fifth) card
	private void dealRiver(int next) {
		deck.dealCard();
		deck.setCommunity(4,deck.dealCard());
		playCounter = next;
	}
	
	//enters bet into pot and checks player hand strength
	private void evaluateTable(int next) {
		player1.enterBet();
		player1.evaluateHand(deck.getCommunity(), 2);
		if(player2.isAlive()){
			player2.enterBet();
			player2.evaluateHand(deck.getCommunity(), 2);
		}
		if(player3.isAlive()){
			player3.enterBet();
			player3.evaluateHand(deck.getCommunity(), 2);
		}
		if(player4.isAlive()){
			player4.enterBet();
			player4.evaluateHand(deck.getCommunity(), 2);
		}
		playCounter = next;
	}

	//finds winner of the hand
	private void findWinner(int next) {
		winner = compareHands();
		playCounter = next;
	}
	
	//compares player hands and determines winner, or tie
	private int compareHands() {
		int win = -1, win2 = -1, win3;
		if(player2.isAlive())
			win = compareTwo(player1, player2);
		else return 1;
		if(player3.isAlive() && player4.isAlive())
			win2 = compareTwo(player3, player4);
		else if(player3.isAlive())
			win2 = 1;
		else if(player4.isAlive())
			win2 = 2;
		else
			return 1;
		 
		if(win == -1){
			if(win2 == -1)
				return 0;
			else
				return win2+2;
		}
		
		else if(win2 == -1)
			return win;
		
		else if(win == 1 || win == 0){
			if(win2 == 1 || win2 == 0){
				win3 = compareTwo(player1, player3);
				if(win3 == 0)
					return 5;
				else if(win3 == 2)
					return 3;
				return 1;
			}
			if(win2 == 2){
				win3 = compareTwo(player1, player4);
				if(win3 == 0)
					return 6;
				else if(win3 == 2)
					return 4;
				return 1;
			}
			else return 1;
		}
		else if(win == 2){
			if(win2 == 1 || win2 == 0){
				win3 = compareTwo(player2, player3);
				if(win3 == 0)
					return 7;
				else if(win3 == 2)
					return 3;
				return 2;
			}
			if(win2 == 2){
				win3 = compareTwo(player2, player4);
				if(win3 == 0)
					return 8;
				else if(win3 == 2)
					return 4;
				return 2;
			}
		}
		return 0;
	}
	
	//compares two players and returns winner or tie
	private int compareTwo(Player current, Player current2) {
		if(!current.isNotFold() && !current2.isNotFold()){
			return -1;
		}
		if(current.isNotFold() && current2.isNotFold()){
			for(int i = 0; i < 7; i++){
				if(current.getHandStrength().charAt(i)>current2.getHandStrength().charAt(i))
					return 1;
				else if(current.getHandStrength().charAt(i)<current2.getHandStrength().charAt(i))
					return 2;
			}
		}
		if(!current.isNotFold()){
			return 2;
		}
		if(!current2.isNotFold()){
			return 1;
		}
		return 0;
	}
	
	//splits money based on winner
	private void splitMoney(int next) {
		if(winner == 1)
			player1.setMoney(player1.getMoney()+pot);
		else if(winner == 2)
			player2.setMoney(player2.getMoney()+pot);
		else if(winner == 3)
			player2.setMoney(player2.getMoney()+pot);
		else if(winner == 4)
			player2.setMoney(player2.getMoney()+pot);
		else if(winner == 5){
			player1.setMoney(player1.getMoney()+(pot/2));
			player3.setMoney(player3.getMoney()+(pot/2));
		}
		else if(winner == 6){
			player1.setMoney(player1.getMoney()+(pot/2));
			player4.setMoney(player4.getMoney()+(pot/2));
		}
		else if(winner == 7){
			player2.setMoney(player2.getMoney()+(pot/2));
			player3.setMoney(player3.getMoney()+(pot/2));
		}
		else if(winner == 8){
			player2.setMoney(player2.getMoney()+(pot/2));
			player4.setMoney(player4.getMoney()+(pot/2));
		}
		else{
			player1.setMoney(player1.getMoney()+(pot/numAlive));
			if(player2.isAlive())
				player2.setMoney(player2.getMoney()+(pot/numAlive));
			if(player3.isAlive())
				player3.setMoney(player3.getMoney()+(pot/numAlive));
			if(player4.isAlive())
				player4.setMoney(player4.getMoney()+(pot/numAlive));
		}
		playCounter = next;
	}
	
	//checks if any players are out of money
	private void checkDead(int next) {
		if(player1.getMoney() == 0)
			endCondition = 1;
		if(player2.getMoney() == 0){
			player2.setAlive(false);
			numAlive--;
		}
		if(player3.getMoney() == 0){
			player3.setAlive(false);
			numAlive--;
		}
		if(player4.getMoney() == 0){
			player4.setAlive(false);
			numAlive--;
		}
		if(numAlive == 1){
			endCondition = 2;
		}
		playCounter = next;
		
	}

	//checks to see if any end conditions are met
	private void checkEndCondition(int next) {
		if(endCondition == 0)
			playCounter = next;
		else
			state = endState;
	}

	//pauses game until click, used to let human observe everyones hands
	private void waitTillClick(int next) {
		if(x >= 0 && x <= 800 && y >= 0 && y <= 600){
			playCounter = next;
			x=-1;
			y=-1;
		}
	}

	

	//draw things to the screen
	//draws everything
	public void draw() {
		if(state == menuState)
			drawMenu();
		else if(state == playState)
			drawPlay();
		else if(state == helpState)
			drawHelp();
		else if(state == endState)
			drawEnd();
	}
	
	
	//draws menu items
	private void drawMenu() {
		drawBackground();
		drawMenuButtons();
		
	}
	
	//draws common background
	private void drawBackground() {
		graphics.drawImage(mainScreen,0,0,WIDTH,HEIGHT,null);
	}

	//draws menu buttons
	private void drawMenuButtons() {
		graphics.setColor(Color.blue);
		graphics.fillRect(350, 420, 100, 50);
		graphics.fillRect(350, 475, 100, 50);
		graphics.setColor(Color.white);
		graphics.drawString("Play", 390, 450);
		graphics.drawString("Help", 390, 505);
	}
	
	//draws play state items
	private void drawPlay() {
		drawBackground();
		drawButtons();
		drawCards();
		drawInfo();
		drawCommunity();
	}

	//draws play state buttona
	private void drawButtons() {
		graphics.setColor(Color.blue);
		graphics.fillRect(500, 475, 100, 50);
		graphics.fillRect(500, 530, 100, 50);
		graphics.fillRect(605, 530, 100, 50);
		graphics.setColor(Color.yellow);
		graphics.drawString("Raise", 535, 505);
		graphics.drawString("Check", 535, 560);
		graphics.drawString("Fold", 645, 560);
		
	}
	
	//draws cards: community cards, and all players, flips player cards once hand is over
	private void drawCards() {
		graphics.drawImage(card[player1.getValueA()], 335, 475, 70, 100, null);
		graphics.drawImage(card[player1.getValueB()], 405, 475, 70, 100, null);
		if(playCounter > 13){
			graphics.drawImage(card[player2.getValueA()], 615, 250, 70, 100, null);
			graphics.drawImage(card[player2.getValueB()], 685, 250, 70, 100, null);
			graphics.drawImage(card[player3.getValueA()], 335, 75, 70, 100, null);
			graphics.drawImage(card[player3.getValueB()], 405, 75, 70, 100, null);
			graphics.drawImage(card[player4.getValueA()], 45, 250, 70, 100, null);
			graphics.drawImage(card[player4.getValueB()], 115, 250, 70, 100, null);
		}
		else{
			graphics.drawImage(card[52], 615, 250, 70, 100, null);
			graphics.drawImage(card[52], 685, 250, 70, 100, null);
			graphics.drawImage(card[52], 335, 75, 70, 100, null);
			graphics.drawImage(card[52], 405, 75, 70, 100, null);
			graphics.drawImage(card[52], 45, 250, 70, 100, null);
			graphics.drawImage(card[52], 115, 250, 70, 100, null);
		}	
	}

	//draws pot, bets, money, and AI state
	private void drawInfo() {
		graphics.drawString("Pot:  $" + Integer.toString(pot), 380, 380);
		graphics.drawString("Bet:  $" + Integer.toString(player1.getBet()), 380, 440);
		graphics.drawString("Bet:  $" + Integer.toString(player2.getBet()), 660, 200);
		graphics.drawString("Bet:  $" + Integer.toString(player3.getBet()), 380, 20);
		graphics.drawString("Bet:  $" + Integer.toString(player4.getBet()), 90, 200);
		graphics.drawString("Money:  $" + Integer.toString(player1.getMoney()), 365, 455);
		graphics.drawString("Money:  $" + Integer.toString(player2.getMoney()), 645, 215);
		graphics.drawString("Money:  $" + Integer.toString(player3.getMoney()), 365, 35);
		graphics.drawString("Money:  $" + Integer.toString(player4.getMoney()), 75, 215);
		graphics.drawString("State:  " + (player2.betStateString[player2.betState]), 650, 230);
		graphics.drawString("State:  " + (player3.betStateString[player3.betState]), 370, 50);
		graphics.drawString("State:  " + (player3.betStateString[player4.betState]), 80, 230);
	}
	
	//draws community cards
	private void drawCommunity() {
		if(playCounter>4){
			graphics.drawImage(card[deck.getCommunity(0)], 225, 250, 70, 100, null);
			graphics.drawImage(card[deck.getCommunity(1)], 295, 250, 70, 100, null);
			graphics.drawImage(card[deck.getCommunity(2)], 365, 250, 70, 100, null);
		}
		if(playCounter>7){
			graphics.drawImage(card[deck.getCommunity(3)], 435, 250, 70, 100, null);
		}
		if(playCounter>10){
			graphics.drawImage(card[deck.getCommunity(4)], 505, 250, 70, 100, null);
		}
	}
	
	//draws help state
	private void drawHelp() {
		drawHelpBackground();
		drawBackButton();
	}
	
	//draws help background
	private void drawHelpBackground() {
		graphics.drawImage(helpScreen,0,0,WIDTH,HEIGHT,null);
	}
	
	//draws back button
	private void drawBackButton() {
		graphics.setColor(Color.blue);
		graphics.fillRect(20, 20, 100, 50);
		graphics.setColor(Color.white);
		graphics.drawString("Back", 60, 45);
	}

	//draws end state and shows winner
	private void drawEnd() {
		drawBackground();
		graphics.setColor(Color.yellow);
		if(endCondition == 2)
			graphics.drawString("YOU WIN!", 395, 300);
		else
			graphics.drawString("YOU LOSE!", 395, 300);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); //game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
	
	
	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		frame = new JFrame();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	//starts a new thread for the game
	
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}
	
	//main game loop
	
	public void run() {
		init();
		long startTime = System.nanoTime();
		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		long secondTimer = System.nanoTime();
		while(running) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while(delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if(System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		System.exit(0);
	}
	
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); //method from Canvas class
		
		if(bs == null) {
			createBufferStrategy(3); //creates it only for the first time the loop runs (trip buff)
			return;
		}
		
		graphics = (Graphics2D)bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}
	
	//stops the game thread and quits
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}