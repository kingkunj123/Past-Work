package com.patel.kunj.a3dpong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;



public class Ball{

    private double z;
    private double x, y;
    private double dX;
    private double dY;
    private final double size;
    private double dSize;
    private boolean forward;
    private boolean right;
    private boolean down;
    private boolean active;
    private int ballSpeed;

    /**
     * Constructs a ball with the given parameters
     * @param x the x coordinate of the ball
     * @param y the y coordinate of the ball
     * @param size the size of the ball
     */
    public Ball(double x, double y, double size){
        this.x = x;
        this.y = y;
        this.z = 0.0;
        this.dX = 0.0;
        this.dY = 0.0;
        this.size = size;
        this.dSize = 0.0;
        this.forward = true;
        this.right = true;
        this.down = true;
        this.active = false;
        this.ballSpeed = 15;
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
            z += ballSpeed;
        } else{
            z -= ballSpeed;
        }
        if (right){
            x += ballSpeed;
        } else{
            x -= ballSpeed;
        }
        if (down){
            y += ballSpeed;
        } else{
            y -= ballSpeed;
        }
    }

    /**
     * Method that checks if the ball has collided with the walls and changes its direction accordingly
     */
    private void checkWallCollision(){
        if (x >= MainView.WIDTH - size / 2){
            right = false;
        } else if (x <= size / 2){
            right = true;
        }
        if (y >= MainView.HEIGHT - size / 2){
            down = false;
        } else if (y <= size / 2){
            down = true;
        }
    }

    /**
     * Method that calculates the depth x and y coordinates based of the flat coordinates
     */
    private void depthCalc(){
        dX = x * ((MainView.DEPTH - z) / MainView.DEPTH) + ((MainView.WIDTH - (MainView.WIDTH * ((MainView.DEPTH - z) / MainView.DEPTH))) / 2) - dSize / 2;
        dY = y * ((MainView.DEPTH - z) / MainView.DEPTH) + ((MainView.HEIGHT - (MainView.HEIGHT * ((MainView.DEPTH - z) / MainView.DEPTH))) / 2) - dSize / 2;
    }

    /**
     * Method that calculates the size of the ball based on its depth
     */
    private void sizeCalc(){
        dSize = size * ((MainView.DEPTH - z) / MainView.DEPTH);
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
     *
     */
    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle((int)(dX + dSize/2), (int)(dY + dSize/2), (int) dSize/2, paint);
    }


    /**
     * Method that creates a bounding rectangle around the ball
     * @return the bounding rectangle
     */
    public Rect getRect(){

        Rect r = new Rect((int)(dX), (int)(dY), (int)(dX+dSize), (int)(dY+dSize));
        return r;
    }


    /**
     * Method that increases the ball speed by 2
     */
    public void speedUp(){
        ballSpeed += 2;
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

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

}
