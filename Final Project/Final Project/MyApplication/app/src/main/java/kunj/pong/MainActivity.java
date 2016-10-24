package kunj.pong;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;


public class MainActivity extends Activity {

    MainView mainView;
    public static float WIDTH, HEIGHT;
    public static float DEPTH;
    public static boolean running = true;
    static Thread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        mainView = new MainView(this);

        WIDTH = mainView.getWidth();
        HEIGHT = mainView.getHeight();
        DEPTH = HEIGHT*2;




        layout.addView(mainView);
        setContentView(layout);
        immersive();//sets the app into full screen Immersive FullScreen Mode.
    }
    @SuppressLint("InlinedApi")
    public void immersive(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void run(final MainView sView){
        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())

                    try{
                        Thread.sleep(50);
                        runOnUiThread(new Runnable(){ // start actions in UI thread

                            @Override
                            public void run(){
                                if(running){
                                    Canvas canvas = sView.getHolder().lockCanvas();
                                    mainView.update(canvas);
                                }
                            }
                        });
                    }
                    catch (InterruptedException e){}
            }
        }); // the while thread will start in BG thread
        thread.start();
    }

}

class MainView extends SurfaceView implements SurfaceHolder.Callback{

    public static float WIDTH;
    public static float DEPTH;

    //HEIGHT is the height of the game field, realHEIGHT is the height of the screen
    public static float HEIGHT;
    public static float realHEIGHT;

    public static Paint paint = new Paint();
    public Canvas canvas = new Canvas();
    public static MainActivity context;

    private Paddle paddle;
    private Ball ball;
    private Paddle paddle2;
    private int x1, x2, y1, y2;
    private int myScore;

    private int x, y;
    private boolean click;

    //game states to navigate through screens
    private int gameState;
    private final int gameMenu = 0;
    private final int gamePlay = 1;
    private final int gameHelp = 2;
    private final int gameScore = 3;


    private int lives;

    //array that saves the highscores
    int[] scores;


    String FILENAME = "scoreFile";
    File file;
    String test;


    //makeshift buttons
    private Rect playButton;
    private Rect menuButton;
    private Rect helpButton;
    private Rect scoreButton;

    FileOutputStream fos;
    FileInputStream fis;

    public MainView(Context context) {
        super(context);
        this.context = (MainActivity) context;
        this.setBackgroundColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        getHolder().addCallback(this);
    }
    boolean init = false;

    /**
     * Method that initializes all game variables
     */
    public void init(){

        scores = new int[11];

        file = new File(context.getFilesDir(), FILENAME);
        test = "";
        recoverScore();



        WIDTH = getWidth();
        HEIGHT = WIDTH + 200;
        DEPTH = HEIGHT*2;
        realHEIGHT = getHeight();



        click = false;
        x = 0;
        y = 0;

        gameState = gameMenu;

        initMenu();
        Log.d("init", "init happened");
    }

    /**
     * Method that gets the saved scores from the file and places them in the array
     */
    private void recoverScore() {
        int num = 0;
        try {
            if(file.exists())
                fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        try {
            while (fis.available()>0){
                //gets the data as bytes, converts it into chars and then into ints
                test = test + (char) fis.read();
                if (test.charAt(test.length() - 1) == '.') {
                    test = test.substring(0, test.length()-1);
                    scores[num] = Integer.parseInt(test);
                    num++;
                    test = "";
                }
            }

        } catch (IOException e) {
        }
        try {
            fis.close();
        } catch (IOException e) {
        }
    }

    /**
     * Method that initializes all variables related to the menu screen
     */
    public void initMenu(){
        playButton = new Rect(0,0, (int) WIDTH, (int)realHEIGHT/3);
        helpButton = new Rect(0, (int) realHEIGHT/3, (int) WIDTH, (int)realHEIGHT/3*2);
        scoreButton = new Rect(0, (int) realHEIGHT/3*2, (int) WIDTH, (int)realHEIGHT);
    }

    /**
     * Method that initializes all variables related to the play screen
     */
    public void initPlay(){
        menuButton = new Rect(0,(int) HEIGHT, (int) WIDTH/3, (int)realHEIGHT);
        helpButton = new Rect((int)WIDTH/3, (int) HEIGHT, (int) WIDTH/3*2, (int)realHEIGHT);
        scoreButton = new Rect((int)WIDTH/3*2, (int) HEIGHT, (int) WIDTH, (int)realHEIGHT);

        ball = new Ball(WIDTH/2, HEIGHT/2, 200);
        paddle = new Paddle(100, 100, 300);
        paddle2 = new Paddle(0, 0, 300);
        myScore = 0;

        lives = 3;
    }

    /**
     * Method that initializes all variables related to the score screen
     */
    public void initScore(){
        saveScore();
    }

    /**
     * Method that saves the scores from the array to the file
     */
    private void saveScore() {
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
        }

        try {
            for(int i = 0; i < scores.length; i++){
                fos.write((scores[i] + ".").getBytes());
            }
        } catch (IOException e) {
        }

        try {
            fos.close();
        } catch (IOException e) {
        }
    }

    /**
     * Method that updates the game
     * @param canvas the canvas that is drawn on
     */
    public void update(Canvas canvas) {

        if(gameState == gameMenu) updateMenu();
        else if(gameState == gamePlay) updatePlay();
        else if(gameState == gameHelp) updateHelp();
        else if(gameState == gameScore) updateScore();

        this.getHolder().unlockCanvasAndPost(canvas);
        postInvalidate();
        //	Log.d("update", "Updating");
    }

    /**
     * Method that updates the menu variables
     */
    private void updateMenu() {
        //switches screens based on buttons clicked
        if(click) {
            if(playButton.contains(x,y)){
                gameState = gamePlay;
                initPlay();
            }
            else if(helpButton.contains(x,y)){
                gameState = gameHelp;
            }
            else if(scoreButton.contains(x,y)){
                gameState = gameScore;
                initScore();
            }
            click = false;
        }

    }

    /**
     * Method that updates the play screen variables
     */
    private void updatePlay() {
        //switches screens based on button clicked
        if(click) {
            if(menuButton.contains(x,y)){
                gameState = gameMenu;
                initMenu();
            }
            else if(helpButton.contains(x,y)){
                gameState = gameHelp;
            }
            else if(scoreButton.contains(x,y)){
                gameState = gameScore;
                initScore();
            }

        }



        ballDepth();

        //creates a new ball in the center of the field when it leaves the field of play
        if (!ball.isActive()) {
            this.ball = new Ball(WIDTH / 2, HEIGHT / 2, 200);

        }

        if (click) {
            ball.setActive(true);
        }

        ball.update();

        if (ball.isActive()) {
            ball.setActive(checkPaddleCollision());
        }

        paddle.update(x, y);
        paddle2.update(ball.getX(), ball.getY());

        if(lives == 0){
            updateRank();
            gameState = gameScore;
            initScore();
        }
        click = false;
    }

    /**
     * Method that checks paddle collision and returns game state
     * @return true if the ball hits a paddle at either end
     */
    private boolean checkPaddleCollision(){
        if (ball.getZ() > (DEPTH / 4) * 3){
            if (paddle2.getRect().intersect(ball.getRect())){
                ball.changeZ(false);
            } else{
                paddle2.speedUp();
                setMyScore();
                return false;
            }
        } else if (ball.getZ() < 0){
            if (paddle.getRect().intersect(ball.getRect())){
                ball.changeZ(true);

                ball.speedUp();
            } else{
                lives--;
                return false;
            }
        }
        return true;
    }

    /**
     *Method that calculates the perimeter rectangle at the ball depth
     */
    private void ballDepth(){
        double z = ball.getZ();
        x1 = (int) ((WIDTH - (WIDTH * ((DEPTH - z) / DEPTH))) / 2);
        y1 = (int) ((HEIGHT - (HEIGHT * ((DEPTH - z) / DEPTH))) / 2);
        x2 = (int) ((WIDTH + (WIDTH * ((DEPTH - z) / DEPTH))) / 2);
        y2 = (int) ((HEIGHT + (HEIGHT * ((DEPTH - z) / DEPTH))) / 2);
    }



    /**
     * Method that increments the player's score by one
     */
    private void setMyScore(){
        myScore++;
    }


    /**
     * Method that records the latest score and sorts it in the array, the lowest score is eliminated
     */
    private void updateRank() {
        scores[0] = myScore;
        Arrays.sort(scores);
    }

    /**
     * Method that updates the help screen variables
     */
    private void updateHelp(){
        if(click){
            gameState = gameMenu;
            initMenu();
            click = false;
        }
    }

    /**
     * Method that updates the score screen variables
     */
    private void updateScore() {

        if(click){
            gameState = gameMenu;
            initMenu();
            click = false;
        }
    }


    /**
     * Method that records touch interactions
     * @param event the motionlistener that records touch events
     * @return true if the screen has been interacted with
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                click = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                click = false;
                break;
            case MotionEvent.ACTION_UP:
                click = false;
                break;
            default:
                return false;
        }
        return true;
    }


    /**
     * Method that draws all objects on the screen
     * @param canvas the canvas being drawn on
     */
    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);

        this.canvas = canvas;
        if(!init){
            init();
            init = true;
        }
        if(gameState == gameMenu) drawMenu();
        else if(gameState == gamePlay) drawPlay();
        else if(gameState == gameHelp) drawHelp();
        else if(gameState == gameScore) drawScore();
    }


    /**
     * Method that draws the menu objects
     */
    private void drawMenu() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(playButton, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(helpButton, paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(scoreButton, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Play", 100, 100, paint);
        canvas.drawText("Help",100,realHEIGHT/3 + 100,paint);
        canvas.drawText("Score",100,realHEIGHT/3*2 + 100,paint);

    }

    /**
     * Method that draws the play objects
     */
    private void drawPlay() {
        drawField();
        drawObjects();
        drawScores();
        drawButtons();
    }

    /**
     * Method that draws the field
     */
    public void drawField() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(WIDTH / 2 - WIDTH / 8, HEIGHT / 2 - HEIGHT / 8, WIDTH / 2 + WIDTH / 8, HEIGHT / 2 + HEIGHT / 8, paint);
        canvas.drawLine(0, 0, WIDTH / 2 - WIDTH / 8, HEIGHT / 2 - HEIGHT / 8, paint);
        canvas.drawLine(WIDTH / 2 + WIDTH / 8, HEIGHT / 2 + HEIGHT / 8, WIDTH, HEIGHT, paint);
        canvas.drawLine(WIDTH / 2 + WIDTH / 8, HEIGHT / 2 - HEIGHT / 8, WIDTH, 0, paint);
        canvas.drawLine(WIDTH / 2 - WIDTH / 8, HEIGHT / 2 + HEIGHT / 8, 0, HEIGHT, paint);
        canvas.drawRect(0, 0, WIDTH, HEIGHT, paint);
        //rectangle that is created to show the depth of the ball
        canvas.drawRect(x1, y1, x2, y2, paint);

    }

    /**
     * Method that draws the paddles and the ball
     */
    public void drawObjects(){

        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        paint.setColor(Color.RED);
        paddle2.draw(canvas, paint);

        paint.setColor(Color.argb(255, 100, 100, 255));
        ball.draw(canvas, paint);

        paint.setColor(Color.BLUE);
        paddle.draw(canvas, paint);
    }

    /**
     * Method that draws the score and lives
     */
    private void drawScores(){
        paint.setColor(Color.RED);
        paint.setTextSize(70);
        canvas.drawText("Lives: " + lives, 700, 70, paint);
        canvas.drawText("Score: " + myScore, 100, 70, paint);
    }

    /**
     * Method that draws the buttons, and their labels
     */
    private void drawButtons() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(menuButton, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(helpButton, paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(scoreButton, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Menu", 50, realHEIGHT-50, paint);
        canvas.drawText("Help", 50 + WIDTH/3, realHEIGHT-50, paint);
        canvas.drawText("Score", 50 + WIDTH/3*2, realHEIGHT-50, paint);
    }

    /**
     * Method that draws the help objects
     */
    private void drawHelp(){
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Move the paddle to", 0, 100, paint);
        canvas.drawText("hit the ball.", 0, 200, paint);
        canvas.drawText("Every time the", 0, 300, paint);
        canvas.drawText("opponent misses", 0, 400, paint);
        canvas.drawText("you get a point.", 0, 500, paint);
        canvas.drawText("When you miss,", 0, 600, paint);
        canvas.drawText("its game over.", 0, 700, paint);
        canvas.drawText("Get the highest score", 0, 800, paint);
        canvas.drawText("possible.", 0, 900, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawText("Tap to go back", 0, 1200, paint);
    }

    /**
     * Method that draws the score objects
     */
    private void drawScore() {
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        for(int i = scores.length-1; i>0; i--){
            canvas.drawText(Integer.toString(scores[i]),200,(11-i)*110 + 110, paint);
        }
        canvas.drawText("Click for Menu", 0, realHEIGHT, paint);
    }

    //DO NOT TOUCH!!!----------------------------------------------------------------------------------------------------------
    @Override
    protected void onDraw(Canvas canvas){
        draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.setWillNotDraw(false);
        context.run(this);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

        context.run(this);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            MainActivity.running = false;
            MainActivity.thread.join();
        } catch (InterruptedException e) {
        }
    }
}
