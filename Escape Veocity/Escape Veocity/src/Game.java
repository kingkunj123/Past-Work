//Kunj Patel
//Escape Velocity, a side scrolling survival game

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.*;
import java.text.AttributedString;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
 
public class Game extends Canvas implements Runnable,KeyListener{
    //screen dimensions and variables
    static final int WIDTH = 1024;
    static final int HEIGHT = WIDTH / 4 * 3; //4:3 aspect ratio
    private JFrame frame;
    //game updates per second
    static final int UPS = 60;
    //images for sprites
    BufferedImage mainScreen = null;
    BufferedImage helpScreen = null;
    BufferedImage bombSprite = null;
    BufferedImage wallSprite = null;
    BufferedImage shipSprite = null;
    BufferedImage bulletSprite = null;
    BufferedImage heartSprite = null;
    //variables for the thread
    private Thread thread;
    private boolean running;
    //game state variables
    private int gameState=0;
    private final int gameMenu=0;
	private final int gamePlay=1;
	private final int gameHelp=2;
	private final int gameScore=3;
	private final int gameQuit=4;
	private final int gameLose=5;
	private final int gamePause=6;
	//menu state variables
	private int menuState=0;
	private final int menuPlay=0;
	private final int menuHelp=1;
	private final int menuScore=2;
	private final int menuQuit=3;
	private int menuTiming=0;
	private boolean enterPressed=false;
    //used for drawing items to the screen
    private Graphics2D graphics;
   //creation of objects
	private Jet jet; 
	private Bullet bullet;
	private Vector <Bullet> bulletVec=null;
	private Missile missile;
	private Vector <Missile> missileVec=null;
	private Bomb bomb;
	private Vector <Bomb> bombVec=null;
	private Wall wall;
	private Vector <Wall> wallVec=null;
	private Heart heart;
	private Vector <Heart> heartVec=null;
	//velocity variables
	private double downSpeed =1;
	private double upSpeed =1;
	private double downHeart =1;
	private boolean heartUp = false;
	private double upHeart =1;
	//loading variables
	private LevelLoader levelLoad;
	private ScoreLoader scoreLoad;
	private RatingLoader ratingLoad;
	private long random;
	//object counters
	private int bulletTiming=0;
	private int bombScoreCounter=0;
	private int optionTiming=0;
	private int updateCounter= 0;
	private int missileTimer= 0;
	private int heartTimer= 0;
	private int lives = 3;
	private int score = 0;
	//other
	boolean newScore;
	boolean checked = false;
	boolean checkRating=false;
	int played=0;
	//keyboard variables
	private boolean[] keysPressed = new boolean[2];
	private boolean[] menuKeys = new boolean[3];
	private final int KEY_UP = 0, KEY_SPACE = 1, KEY_DOWN = 1, KEY_ENTER = 2;
	private long startTime, currTime, ms;
    //initialize game objects, load media(pics, music, etc)
    public void init() {
    bulletVec = new Vector<Bullet>();
    missileVec = new Vector<Missile>();
    bombVec = new Vector<Bomb>();
    wallVec = new Vector<Wall>();
    heartVec = new Vector<Heart>();
     makeMenuBackgrounds();
     makeHelpBackgrounds();
     initializeJet();
     initializeBullet();
     initializeMissile();
     initializeBomb();
     initializeWall();
     initializeHeart();
     initializeLevel();
     initializeScoreLoader();
     //initializeRatingLoader();
    }
    
    //cuts out sprites from spritesheets
    public void makeMenuBackgrounds() {
		SpriteSheet menuBackground = new SpriteSheet();
		menuBackground.loadSpriteSheet("mainBackground.png");
		mainScreen = menuBackground.getSprite(0, 0, 1280, 720);
		
	}
    
    public void makeHelpBackgrounds(){
    	SpriteSheet helpBackground = new SpriteSheet();
		helpBackground.loadSpriteSheet("help.png");
		helpScreen = helpBackground.getSprite(0, 0, 1024, 768);
		
    }
    //creates objects
	public void initializeJet() {
    	SpriteSheet planeSprite = new SpriteSheet();
    	planeSprite.loadSpriteSheet("jet.png");
    	shipSprite = planeSprite.getSprite(446, 139, 65, 41);
    	jet = new Jet(50, 718,50, 100);		
	}
    
    public void initializeBullet() {
    	SpriteSheet billSprite = new SpriteSheet();
    	billSprite.loadSpriteSheet("bullet.png");
    	bulletSprite = billSprite.getSprite(37, 43, 723, 463);
    	bullet = new Bullet(50,jet.getY()+20, 30, 10);
	}
    
    public void initializeMissile(){
    	missile = new Missile(1015,jet.getY()+10, 30, 10, 0);
    }
    
    public void initializeBomb(){
		SpriteSheet ssbbSprite = new SpriteSheet();
		ssbbSprite.loadSpriteSheet("ssbb.png");
		bombSprite = ssbbSprite.getSprite(260, 527, 63, 63);
    	bomb = new Bomb(1023,68, 70, 70);
    }
    
    public void initializeWall(){
    	SpriteSheet boltSprite = new SpriteSheet();
		boltSprite.loadSpriteSheet("bolt.png");
		wallSprite = boltSprite.getSprite(161, 8, 17, 65);
    	wall = new Wall(1023,68,30, 70);
    }
    
    public void initializeHeart(){
    	SpriteSheet ssbbSprite = new SpriteSheet();
		ssbbSprite.loadSpriteSheet("ssbb.png");
		heartSprite = ssbbSprite.getSprite(133, 77, 58, 53);
    	heart = new Heart(1023, 0,50, 50);	
    }
    
    //opens randomly one file as obstacles
    public void initializeLevel(){
    	random = (System.currentTimeMillis())%4;
    	levelLoad = new LevelLoader();
    	if(random == 0)
			levelLoad.openLevelFile("option1.txt");
		else if(random == 1)
			levelLoad.openLevelFile("option2.txt");
		else if(random == 2)
			levelLoad.openLevelFile("option3.txt");
		else if(random == 3)
			levelLoad.openLevelFile("option4.txt");
    }
    
    public void initializeScoreLoader(){
    	scoreLoad = new ScoreLoader();
    	scoreLoad.openFile("scoreFile");
    }
    
//    public void initializeRatingLoader(){
//    	ratingLoad = new RatingLoader();
//    	ratingLoad.openFile("rating");
//    }
    
	//update game objects
    public void update() {
    	updateTime();
    	if(gameState==gamePlay){
    		updateJet();
    		updateBullet();
    		updateMissile();
    		updateBomb();
    		updateWall();
    		updateHeart();
    		updateOption();
    		updateScore();
    		checkForLose();
    	}
    	else if(gameState==gameMenu)
    		reset();
    	updateKey();
    	if(gameState==gameLose){
    		if(checked==false){
    		checkScore();
    		checked=true;
    		}
    	}
    }
    
	//counts seconds played
    public void updateTime() {
		currTime = System.currentTimeMillis();	
		ms=((currTime-startTime)/1000);
	}

    //updates jet position based on user input
	public void updateJet() {
		if(keysPressed[0] == true && jet.getY() > 68){//up movement
			jet.setVelocityY((int) (0-upSpeed));
			if(upSpeed<9)
				upSpeed+=0.2;
			downSpeed = 1;
		}
		else if(jet.getY()+ jet.getHeight() < HEIGHT){
			jet.setVelocityY((int) downSpeed);// down movement
			if(downSpeed<9)
				downSpeed+=0.2;
			upSpeed = 1;
		}
		else
			jet.setVelocityY(0);
		//updates jet
		jet.setY(jet.getY() + jet.getVelocityY());
	}
	
	public void updateBullet() {
    	createBullet();
    	for(int i=0;i<bulletVec.size();i++){
    		Bullet B = bulletVec.elementAt(i);
    		B.update();
    		if(B.getX()>1024)
    			bulletVec.removeElementAt(i);
    	}
	}
    
	//creates bullets when user input is given, while spacing them out
	public void createBullet(){
    	if(keysPressed[KEY_SPACE] == true){
    		if(bulletTiming==0){
    			initializeBullet();
    			bulletVec.add(bullet);
    		}
    		bulletTiming++;
    		if(bulletTiming==25)
    			bulletTiming=0;
    	}
    	else if(keysPressed[KEY_SPACE] == false)
    		bulletTiming=0;
    }
    
	//updates missile position from track state to advance state
	public void updateMissile(){
    	createMissile();
    	for(int i=0;i<missileVec.size();i++){
    		Missile m= missileVec.elementAt(i);
    		m.setMoveYet(m.getMoveYet() + 1);
    		if(m.getMoveYet()>100)
    			m.setX(m.getX() - 20);
    		if(m.getX()<0)
    			missileVec.removeElementAt(i);
    		if(m.getMoveYet()<100)
    			m.setY(jet.getY());
    		if(jet.getBoundaryRectangle().intersects(m.getBoundaryRectangle()) == true){
    			lives--;
    			missileVec.removeElementAt(i);
    			i--;
    			//gameState=gameLose;
    		}
    	}
    }
    
	//creates missile randomly
    public void createMissile(){
    	if((int)System.currentTimeMillis()%1001==0 && missileTimer==0){
    		initializeMissile();
    		missileVec.add(missile);
    		missileTimer=500;
    	}
    	if(missileTimer!=0)
    		missileTimer--;
    }
    
    //creates objects from text files
	public void updateBomb() {
		for(int i=0;i<bombVec.size();i++){
			Bomb b= bombVec.elementAt(i);
    		b.setX(b.getX() - 10);
    		if(b.getX()<-50)
    			bombVec.removeElementAt(i);
    		if(jet.getBoundaryRectangle().intersects(b.getBoundaryRectangle()) == true){
    			lives--;
    			bombVec.removeElementAt(i);
    			i--;
    		}
    		i = bombCollision(b,i);
		} 
	}
	
	public int bombCollision(Bomb b, int i){
		for(int j=0;j<bulletVec.size();j++){
    		Bullet B = bulletVec.elementAt(j);
    		if(B.getBoundaryRectangle().intersects(b.getBoundaryRectangle()) == true){
    			bombVec.removeElementAt(i);
    			bulletVec.removeElementAt(j);
    			i--;
    			bombScoreCounter++;
    		}
    	}
		return i;
	}
	
	public void updateWall(){
		for(int i=0;i<wallVec.size();i++){
			Wall w= wallVec.elementAt(i);
    		w.setX(w.getX() - 10);
    		if(w.getX()<-50)
    			wallVec.removeElementAt(i);
    		i = wallCollision(w,i);
		}
	}
    
	public int wallCollision(Wall w, int i){
		if(jet.getBoundaryRectangle().intersects(w.getBoundaryRectangle()) == true){
    			lives--;
    			wallVec.removeElementAt(i);
    			i--;
    		}
		return i;
	}
	
	public void updateHeart(){
		createHeart();
		if(heartTimer!=0)
			heartTimer--;
		for(int i=0;i<heartVec.size();i++){
    		Heart h= heartVec.elementAt(i);
			if(h.getY()<100)
				heartUp=false;
			if(h.getY()>700)
				heartUp=true;
			if(heartUp==true){
				h.setVelocityY((int)(0-upHeart));
				if(upHeart<9)
					upHeart+=1;
				downHeart = 1;
			}
			if(heartUp==false){
				h.setVelocityY((int)downHeart);
				if(downHeart<9)
					downHeart+=1;
				upHeart = 1;
			}
			if(h.getX()<0)
    			heartVec.removeElementAt(i);
			h.setY(h.getY() + h.getVelocityY());
			h.setX(h.getX() - 2);
			heartCollision(h, i);	
    	}
	}
	
	public void createHeart(){
		if(((int)System.currentTimeMillis()%500)==0 && heartTimer==0){
    		initializeHeart();
    		heartVec.add(heart);
    		heartTimer=1000;
    	}
	}
	
	public void heartCollision(Heart h, int i){
		if(jet.getBoundaryRectangle().intersects(h.getBoundaryRectangle()) == true) {
				lives++;
				heartVec.removeElementAt(i);
				i--;
			}
	}
	
	//updates position in text file
	public void updateOption() {
		if(updateCounter==6){
			initializeLevel();
			updateCounter=0;
		}
		if(optionTiming==150){
			levelLoad.createLevelGrid(bombVec, wallVec);
			updateCounter++;
		}
		optionTiming++;
		if(optionTiming==151)
    		optionTiming=0;
		
			
	}

	public void updateScore(){
		if(gameState!=gameMenu){
			score=(int) (ms+bombScoreCounter);
		}
	}
		
	public void checkForLose(){
		if(lives<1)
			gameState=gameLose;
	}
	
	//resets all counters when game ends
	public void reset(){
		bulletVec = new Vector<Bullet>();
		missileVec = new Vector<Missile>();
		bombVec = new Vector<Bomb>();
		wallVec = new Vector<Wall>();
		heartVec = new Vector<Heart>();
	    initializeLevel();
	    downSpeed =1;
	 	upSpeed =1;
	 	downHeart =1;
	 	heartUp = false;
	 	upHeart =1;
	 	bulletTiming=0;
	 	bombScoreCounter=0;
	 	optionTiming=0;
	 	updateCounter= 0;
	 	missileTimer= 0;
	 	heartTimer= 0;
	 	lives = 3;
	 	score = 0;
	 	keysPressed[KEY_UP] = false;
	 	startTime = System.currentTimeMillis();
	 	checked=false;
	 	checkRating=false;
	}
	
	//changes menu button state, and stops buttons from going too fast
	public void updateKey(){
		if(menuKeys[KEY_ENTER] == false)
			enterPressed=false;
		if(gameState==gameMenu){
			if(menuKeys[KEY_UP] == true){
					if(menuTiming==0){
						menuState--;
					if(menuState==-1)
						menuState=3;
				}
				menuTiming++;
		    	if(menuTiming==25)
		    		menuTiming=0;
			}
			if(menuKeys[KEY_DOWN] == true){
				if(menuTiming==0){
					menuState++;
					if(menuState==4)
						menuState=0;
				}
				menuTiming++;
				if(menuTiming==25)
	    			menuTiming=0;
			}
			if(menuKeys[KEY_UP] == false && menuKeys[KEY_DOWN] == false){
				menuTiming=0;
			}
		}
	}
	
	public void checkScore(){
		newScore = scoreLoad.arrangeScore(score);
	}
	
	//draw things to the screen
    public void draw() {
		gameState();
    }

    public void gameState() {
    	if(gameState==gameMenu)
			menu();
		else if(gameState==gamePlay)
			play();
		else if(gameState==gameHelp)
			help();
		else if(gameState==gameScore)
			score();
		else if(gameState==gameQuit)
			quit();
		else if(gameState==gameLose)
			lose();
		else if(gameState==gamePause)
			pause();
	}

    public void menu() {
		menuBackground();
		drawPrompt();
		drawTitle();
		quitButton();
		scoreButton();
		helpButton();
		playButton();
		startTime = System.currentTimeMillis();
		
	}
	
    public void menuBackground() {	
		graphics.drawImage(mainScreen,0,0,WIDTH,HEIGHT,null);
	}

    public void drawPrompt(){
    	graphics.setColor(Color.black);
		graphics.drawRect(90, 180, 300, 30);
		graphics.fillRect (90, 180, 300, 30);
		graphics.setColor(Color.yellow);
		graphics.drawString("use up and down to scroll, press enter to select", 100, 200);	
    }

    public void drawTitle(){
    	graphics.setColor(Color.cyan);
		Font courier = new Font("TimesNewRoman", Font.BOLD, 100);
		AttributedString as = new AttributedString("Escape Velocity");
		as.addAttribute(TextAttribute.FONT, courier);
		graphics.drawString(as.getIterator(), 10, 85);
    }
    
    public void quitButton() {
		Rectangle2D quitButton = new Rectangle2D.Double(275, 560, 150, 75);
		if(menuState==menuQuit){
			graphics.setColor(Color.blue);
			graphics.fill(quitButton);
			graphics.setColor(Color.red);
			graphics.drawString("QUIT", 330, 600);
			if(menuKeys[KEY_ENTER] == true && enterPressed==false){
				enterPressed=true;
				gameState=gameQuit;
			}
		}
		else{
			graphics.setColor(Color.magenta);
			graphics.fill(quitButton);
			graphics.setColor(Color.cyan);
			graphics.drawString("QUIT", 330, 600);
		}	
		
	}

	public void quit() {
		running = false;
	}

	public void scoreButton(){
		Rectangle2D scoreButton = new Rectangle2D.Double(275, 460, 150, 75);
		if(menuState==menuScore){
			graphics.setColor(Color.blue);
			graphics.fill(scoreButton);
			graphics.setColor(Color.red);
			graphics.drawString("SCORE", 330, 500);
			if(menuKeys[KEY_ENTER] == true && enterPressed==false){
				enterPressed=true;
				gameState=gameScore;
			}
		}
		else{
			graphics.setColor(Color.magenta);
			graphics.fill(scoreButton);
			graphics.setColor(Color.cyan);
			graphics.drawString("SCORE", 330, 500);
		}	
	}
	
	public void score(){
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		drawScoreList();
		//drawRating();
		goToMenu();
    	graphics.setColor(Color.orange);
		graphics.drawString("Press Enter to go to menu", 100, 100);	
	}
	
	public void drawScoreList(){
		int y = 150;
		for(int i=0;i<scoreLoad.lines.size();i++){
			graphics.setColor(Color.red);
			Font courier = new Font("TimesNewRoman", Font.BOLD, 25);
			AttributedString as = new AttributedString(scoreLoad.lines.elementAt(i));
			as.addAttribute(TextAttribute.FONT, courier);
			graphics.drawString(as.getIterator(), 100, y);
			y+=50;
		}
	}
	
	public void drawRating(){
		graphics.setColor(Color.green);
		Font courier = new Font("TimesNewRoman", Font.BOLD, 25);
		AttributedString as = new AttributedString("Times this game has been played: " + ratingLoad.lines.elementAt(0));
		as.addAttribute(TextAttribute.FONT, courier);
		graphics.drawString(as.getIterator(), 100, 500);
		AttributedString as1 = new AttributedString("This game is rated: " + ratingLoad.lines.elementAt(1));
		as1.addAttribute(TextAttribute.FONT, courier);
		graphics.drawString(as1.getIterator(), 100, 550);
	}
	
	public void helpButton(){
		Rectangle2D helpButton = new Rectangle2D.Double(275, 360, 150, 75);
		if(menuState==menuHelp){
			graphics.setColor(Color.blue);
			graphics.fill(helpButton);
			graphics.setColor(Color.red);
			graphics.drawString("HELP", 330, 400);
			if(menuKeys[KEY_ENTER] == true && enterPressed==false){
				enterPressed=true;
				gameState=gameHelp;
			}
		}
		else{
			graphics.setColor(Color.magenta);
			graphics.fill(helpButton);
			graphics.setColor(Color.cyan);
			graphics.drawString("HELP", 330, 400);
		}	
		
	}
	
    public void help(){
		graphics.drawImage(helpScreen,0,0,WIDTH,HEIGHT,null);
		goToMenu();
	}
	
    public void playButton() {
		Rectangle2D playButton = new Rectangle2D.Double(275, 260, 150, 75);
		if(menuState==menuPlay){
			graphics.setColor(Color.blue);
			graphics.fill(playButton);
			graphics.setColor(Color.red);
			graphics.drawString("PLAY", 330, 300);
			if(menuKeys[KEY_ENTER] == true && enterPressed==false){
				enterPressed=true;
				gameState=gamePlay;
			}
		}
		else{
			graphics.setColor(Color.magenta);
			graphics.fill(playButton);
			graphics.setColor(Color.cyan);
			graphics.drawString("PLAY", 330, 300);
		}	
		
	}

    public void play() {
		PlayBackground();
		drawBullet();
		drawJet();
		drawMissile();
		drawBomb();
		drawWall();
		drawHeart();
		drawScore();
		drawLives();
		goToPause();
	}

    public void drawJet() {
		int x = jet.getX();
		int y = jet.getY();
		int width = jet.getWidth();
		int height = jet.getHeight();
		graphics.drawImage(shipSprite,x,y,width,height,null);
	}

    public void drawBullet() {
		for(int i=0;i<bulletVec.size();i++){
		Bullet b = null;
		b = bulletVec.elementAt(i);
		int x = b.getX();
		int y = b.getY();
		int width = b.getWidth();
		int height = b.getHeight();
		graphics.drawImage(bulletSprite,x,y,width,height,null);
		}
	}
	
    public void drawMissile() {
		for(int i=0;i<missileVec.size();i++){
		Missile m = null;
		m = missileVec.elementAt(i);
		int x = m.getX();
		int y = m.getY();
		int width = m.getWidth();
		int height = m.getHeight();
		RoundRectangle2D CurrentMissile = new RoundRectangle2D.Double(x,y,width,height, 0, 0);
		graphics.setColor(Color.orange);
		graphics.fill(CurrentMissile);
		}
	}
	
    public void drawBomb() {
		for(int i=0;i<bombVec.size();i++){
			Bomb b = null;
			b = bombVec.elementAt(i);
			int x = b.getX();
			int y = b.getY();
			int width = b.getWidth();
			int height = b.getHeight();
			graphics.drawImage(bombSprite,x,y,width,height,null);
			}
	}
	
    public void drawWall() {
		for(int i=0;i<wallVec.size();i++){
			Wall w = null;
			w = wallVec.elementAt(i);
			int x = w.getX();
			int y = w.getY();
			int width = w.getWidth();
			int height = w.getHeight();
			graphics.drawImage(wallSprite,x,y,width,height,null);
			}
	}
	
    public void drawHeart() {
		
		for(int i=0;i<heartVec.size();i++){
			Heart h = null;
			h = heartVec.elementAt(i);
			int x = h.getX();
			int y = h.getY();
			int width = h.getWidth();
			int height = h.getHeight();
			graphics.drawImage(heartSprite,x,y,width,height,null);
		}
	}
	
    public void drawScore() {
		graphics.setColor(Color.red);
		Font courier = new Font("TimesNewRoman", Font.BOLD, 25);
		AttributedString as = new AttributedString("Score:" + score);
		as.addAttribute(TextAttribute.FONT, courier);
		graphics.drawString(as.getIterator(), 600, 55);
	}
	
    public void drawLives(){
    	graphics.setColor(Color.red);
		Font courier = new Font("TimesNewRoman", Font.BOLD, 25);
		AttributedString as = new AttributedString("Lives:" + lives);
		as.addAttribute(TextAttribute.FONT, courier);
		graphics.drawString(as.getIterator(), 800, 55);
    }
    
    public void goToPause(){
    	if(menuKeys[KEY_ENTER]==true && enterPressed==false){
    		enterPressed=true;
    		gameState=gamePause;
    	}
    }
    
    public void PlayBackground() {
		graphics.setColor(Color.black);
		graphics.drawRect(0, 0, getWidth(), getHeight());
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.white);
		graphics.drawRect(0, 0, getWidth(),68);
		graphics.fillRect(0, 0, getWidth(),68);
	}
	
    public void lose(){
    	loseBackground();
    	goToMenu();
		drawScore();
		graphics.setColor(Color.red);
		graphics.drawString("You lose! Press Enter to go to menu", 100, 100);
		if(newScore)
			drawScorePrompt();
    }
    
    public void drawScorePrompt(){
		String name = JOptionPane.showInputDialog(null, "Enter name 5 letters long: ");
		newScore=false;
		scoreLoad.getName(name, score);
    }
    
    public void drawRatingPrompt(){
    	String rating = JOptionPane.showInputDialog(null, "rate the game from 0-9: ");
    	checkRating=true;
    	ratingLoad.addRating(rating);
    }
    
	public void goToMenu(){
    	if(menuKeys[KEY_ENTER]==true && enterPressed==false){
    		enterPressed=true;
    		gameState=gameMenu;
    	}
    }
	
    public void loseBackground(){
    	graphics.setColor(Color.black);
		graphics.drawRect(0, 0, getWidth(), getHeight());
		graphics.fillRect(0, 0, getWidth(), getHeight());
    }
	
    public void pause(){
    	goToMenu();
    	resume();
    	graphics.setColor(Color.red);
		graphics.drawString("Press Down to resume, Press Enter to go to menu", 100, 100);	
    }
    
    public void resume(){
    	if(menuKeys[KEY_DOWN]==true){
    		gameState=gamePlay;
    	}
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
         
        //KEYBOARD and MOUSE handling code goes here
		addKeyListener(this);
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

	public void keyPressed(KeyEvent e) {
		if(gameState==gamePlay){
			if(e.getKeyCode() == KeyEvent.VK_UP)
				keysPressed[KEY_UP] = true;
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			keysPressed[KEY_SPACE] = true;
		}
		else{
			if(e.getKeyCode() == KeyEvent.VK_UP)
				menuKeys[KEY_UP] = true;
		}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
				menuKeys[KEY_DOWN] = true;
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				menuKeys[KEY_ENTER] = true;
	}	

	public void keyReleased(KeyEvent e) {
		if(gameState==gamePlay){
			if(e.getKeyCode() == KeyEvent.VK_UP)
				keysPressed[KEY_UP] = false;
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			keysPressed[KEY_SPACE] = false;
		}
		else{
			if(e.getKeyCode() == KeyEvent.VK_UP)
				menuKeys[KEY_UP] = false;
		}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
				menuKeys[KEY_DOWN] = false;
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				menuKeys[KEY_ENTER] = false;	
	}
	
	public void keyTyped(KeyEvent e) {
		}
}