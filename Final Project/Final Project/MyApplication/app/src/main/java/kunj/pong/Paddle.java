package kunj.pong;

/**
 * Created by kunj on 16/05/2015.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Paddle {

    private final int size;
    private int x;
    private int y;
    private int dX;
    private int dY;
    private int dSize;
    private int paddleSpeed;

    /**
     * Constructs a paddle object with the passed parameters
     * @param x the x position of the paddle
     * @param y the y position on the paddle
     * @param size the size of the paddle
     */
    public Paddle(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
        paddleSpeed = 15;
    }

    /**
     * Methods that updates the user paddle position based on the mouse
     * @param x the x value of the mouse
     * @param y the y value of the mouse
     */
    public void update(int x, int y){
        movePaddle(x, y);
        convert();
    }

    /**
     * Method tat updates the computer paddle position based on the ball position
     * @param ballX the x coordinate of the ball
     * @param ballY the y coordinate of the ball
     */
    public void update(double ballX, double ballY){
        trackBall((int) ballX, (int) ballY);
        depthPosition();
    }

    /**
     * Method that calculates the x and y coordinates of the paddle, keeping it on screen
     * @param x the x value of the mouse
     * @param y the y value of the mouse
     */
    private void movePaddle(int x, int y){
        if(y>MainView.HEIGHT - size/2)
            y = (int)MainView.HEIGHT - size/2;
        else if(y<size/2)
            y = size/2;
        if(x>MainView.WIDTH - size/2)
            x = (int)MainView.WIDTH - size/2;
        else if(x<size/2)
            x = size/2;
        this.x = x - size/2;
        this.y = y - size/2;


    }

    /**
     * Method that passes the flat size and coordinates to the depth specific variables
     */
    private void convert(){
        dSize = size;
        dX = x;
        dY = y;
    }

    /**
     * Method that tracks the ball and sets the computer paddle positions
     * @param x the x coordinate of the ball
     * @param y the y coordinate of the ball
     */
    private void trackBall(int x, int y){
        if(Math.abs(this.x-(x - size/2))<paddleSpeed) {
            this.x = x - size / 2;
        }
        else if(x-size/2 > this.x)
            this.x+=paddleSpeed;
        else
            this.x-=paddleSpeed;
        if(Math.abs(this.y-(y - size/2))<paddleSpeed) {
            this.y = y - size / 2;
        }
        else if(y-size/2 > this.y)
            this.y+=paddleSpeed;
        else
            this.y-=paddleSpeed;
    }

    /**
     * Method that calculates the depth specific coordinates based on the flat coordinates
     */
    private void depthPosition(){
        dX = (int) (x * 0.25 + ((MainView.WIDTH - (MainView.WIDTH * 0.25)) / 2));
        dY = (int) (y * 0.25 + ((MainView.HEIGHT - (MainView.HEIGHT * 0.25)) / 2));
        dSize = (int) (size * 0.25);
    }

    /**
     * Method that paints the paddle
     *
     */
    public void draw(Canvas canvas, Paint paint){
        canvas.drawRect(dX, dY,  dSize+dX, dSize+dY, paint);
    }

    /**
     * Method that returns the bounding rectangle of the paddle
     * @return the bounding rectangle
     */
    public Rect getRect(){
        return new Rect(dX, dY, dX+dSize, dY+dSize);
    }

    /**
     * Method that increases the AI paddle speed by 1
     */
    public void speedUp(){paddleSpeed += 1;}

}