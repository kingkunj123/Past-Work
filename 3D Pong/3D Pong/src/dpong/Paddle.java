package dpong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle extends Rectangle{

    private final int size;
    private final Color color;
    private int dX;
    private int dY;
    private int dSize;

    /**
     * Constructs a paddle object with the passed parameters
     * @param x the x position of the paddle
     * @param y the y position on the paddle
     * @param size the size of the paddle
     * @param color the color of the paddle
     */
    public Paddle(int x, int y, int size, Color color){
        super(x, y, size, size);
        this.size = size;
        this.color = color;
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
        this.x = x - 50;
        this.y = y - 50;
        if (this.x < 0){
            this.x = 0;
        }
        if (this.x > 700){
            this.x = 700;
        }
        if (this.y < 0){
            this.y = 0;
        }
        if (this.y > 500){
            this.y = 500;
        }

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
        this.x = x - 50;
        this.y = y - 50;
        if (this.x < 0){
            this.x = 0;
        }
        if (this.x > 700){
            this.x = 700;
        }
        if (this.y < 0){
            this.y = 0;
        }
        if (this.y > 500){
            this.y = 500;
        }
    }

    /**
     * Method that calculates the depth specific coordinates based on the flat coordinates
     */
    private void depthPosition(){
        dX = (int) (x * 0.25 + ((PongPanel.MAXWIDTH - (PongPanel.MAXWIDTH * 0.25)) / 2));
        dY = (int) (y * 0.25 + ((PongPanel.MAXHEIGHT - (PongPanel.MAXHEIGHT * 0.25)) / 2));
        dSize = (int) (size * 0.25);
    }
    
    /**
     * Method that paints the paddle
     * @param g the graphics object that will paint the paddle
     */
    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(this.dX, this.dY, this.dSize, this.dSize);
    }

}
