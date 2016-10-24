package dpong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Rectangle{

    private double z;
    private double dX;
    private double dY;
    private final double size;
    private double dSize;
    private final Color color;
    private boolean forward;
    private boolean right;
    private boolean down;
    private boolean active;

    /**
     * Constructs a ball with the given parameters
     * @param x the x coordinate of the ball
     * @param y the y coordinate of the ball
     * @param size the size of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, double size, Color color){
        super((int) x, (int) y, (int) size, (int) size);
        this.z = 0.0;
        this.dX = 0.0;
        this.dY = 0.0;
        this.size = size;
        this.dSize = 0.0;
        this.color = color;
        this.forward = true;
        this.right = true;
        this.down = true;
        this.active = false;
    }

    /**
     * updates the ball position and size
     */
    public void update(){
        if (active){
            move();
        }
        checkWallCollision();
        depthCalc();
        sizeCalc();
    }

    /**
     * Method that updates ball position based on the direction it is moving
     */
    private void move(){
        if (forward){
            z += 20.0;
        } else{
            z -= 20.0;
        }
        if (right){
            x += 10.0;
        } else{
            x -= 10.0;
        }
        if (down){
            y += 10.0;
        } else{
            y -= 10.0;
        }
    }

    /**
     * Method that checks if the ball has collided with the walls and changes its direction accordingly
     */
    private void checkWallCollision(){
        if (x >= PongPanel.MAXWIDTH - size / 2){
            right = false;
        } else if (x <= size / 2){
            right = true;
        }
        if (y >= PongPanel.MAXHEIGHT - size / 2){
            down = false;
        } else if (y <= size / 2){
            down = true;
        }
    }

    /**
     * Method that calculates the depth x and y coordinates based of the flat coordinates
     */
    private void depthCalc(){
        dX = x * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH) + ((PongPanel.MAXWIDTH - (PongPanel.MAXWIDTH * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH))) / 2) - dSize / 2;
        dY = y * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH) + ((PongPanel.MAXHEIGHT - (PongPanel.MAXHEIGHT * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH))) / 2) - dSize / 2;
    }

    /**
     * Method that calculates the size of the ball based on its depth
     */
    private void sizeCalc(){
        dSize = size * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH);
    }

    /**
     * Method that sets the ball z axis direction
     * @param forward the direction of the ball, true if it is going down the field
     */
    public void changeZ(boolean forward){
        this.forward = forward;
    }

    /**
     * Method that paints the ball
     * @param g the graphics object that will draw the ball
     */
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int) dX, (int) dY, (int) dSize, (int) dSize);
    }

    /**
     * Returns the z value of the ball
     * @return the z value of the ball
     */
    public double getZ(){
        return z;
    }

    /**
     * Returns the status of the active boolean
     * @return the state of the active boolean
     */
    public boolean isActive(){
        return active;
    }

    /**
     * Sets the status of the active boolean
     * @param active the status of the active boolean
     */
    public void setActive(boolean active){
        this.active = active;
    }
}
