package dpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Random;

/**
 *
 * @author kunj
 */
public class Game{

    private final Paddle paddle;
    private Ball ball;
    private final Paddle paddle2;
    private int x1, x2, y1, y2;
    private int myScore, otherScore;
    private boolean active;
    private final Random r;
    private boolean hitMyPaddle;
    private boolean aIOpposite;
    AttributedString as;

    /**
     *Constructs a game object
     */
    public Game(){
        this.ball = new Ball(400, 300, 50, new Color(255, 0, 200));
        this.paddle = new Paddle(0, 0, 100, new Color(0, 200, 255, 200));
        this.paddle2 = new Paddle(0, 0, 100, new Color(0, 0, 0));
        myScore = 0;
        otherScore = 0;
        r = new Random();
        hitMyPaddle = false;
        aIOpposite = false;
    }

    /**
     * Method that paints the game, including the perimeter and the ball and paddle objects
     * @param g the graphics object that will paint the field
     */
    public void paint(Graphics g){
        if (active){
            //draws the outide rectangle, the inner one, as well as the diagonal lines coonecting them both
            g.drawRect(300, 225, 200, 150);
            g.drawLine(0, 0, 300, 225);
            g.drawLine(500, 375, 800, 600);
            g.drawLine(500, 225, 800, 0);
            g.drawLine(300, 375, 0, 600);
            g.drawRect(x1, y1, x2, y2);
            this.paddle2.paint(g);
            this.ball.paint(g);
            this.paddle.paint(g);
            drawScore(g);
        } else{
            paintWinner(g);
        }

    }

    /**
     * Method that updates the game parameters, such as score and win conditions, as well as calls the ball and paddle update methods
     * @param mouseX the X coordinate of the mouse
     * @param mouseY the Y coordinate of the mouse
     * @param click the state of the left mouse button
     */
    public void update(int mouseX, int mouseY, boolean click){
        active = true;
        
        if (click){
            ball.setActive(true);
        }

        ballDepth();
        
        //creates a new ball in the center of the field when it leaves the field of play
        if (!ball.isActive()){
            this.ball = new Ball(400, 300, 50, new Color(255, 0, 200));
            hitMyPaddle = true;
        }
        
        ball.update();
        
        if (ball.isActive()){
            ball.setActive(checkPaddleCollision());
        }

        paddle.update(mouseX, mouseY);
        updateAI();
        checkWin();
    }

    /**
     * Method that checks paddle collision and returns game state
     * @return true if the ball hits a paddle at either end
     */
    private boolean checkPaddleCollision(){
        if (ball.getZ() > (PongPanel.MAXDEPTH / 4) * 3){
            if (paddle2.intersects(ball)){
                ball.changeZ(false);
            } else{
                setMyScore();
                return false;
            }
        } else if (ball.getZ() < 0){
            if (paddle.intersects(ball)){
                ball.changeZ(true);
                hitMyPaddle = true;
            } else{
                setOtherScore();
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
        x1 = (int) ((PongPanel.MAXWIDTH - (PongPanel.MAXWIDTH * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH))) / 2);
        y1 = (int) ((PongPanel.MAXHEIGHT - (PongPanel.MAXHEIGHT * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH))) / 2);
        x2 = (int) (PongPanel.MAXWIDTH * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH));
        y2 = (int) (PongPanel.MAXHEIGHT * ((PongPanel.MAXDEPTH - z) / PongPanel.MAXDEPTH));
    }

    /**
     * Method that draws the score
     * @param g the graphics object that is drawing the score
     */
    private void drawScore(Graphics g){
        g.setColor(Color.blue);
        Font courier = new Font("TimesNewRoman", Font.BOLD, 25);
        as = new AttributedString("Score: " + myScore + ":" + otherScore);
        as.addAttribute(TextAttribute.FONT, courier);
        g.drawString(as.getIterator(), 50, 25);
    }

    /**
     * Method that increments the player's score by one
     */
    private void setMyScore(){
        this.myScore++;
    }

    /**
     *Method that increments the opponent's score by one
     */
    private void setOtherScore(){
        this.otherScore++;
    }

    /**
     * Method that updates the AIs ball tracking, there is a 1 in 8 chance the paddle will move in the wrong direction
     */
    private void updateAI(){
        if (hitMyPaddle){
            //fancy if statement, didnt know it was possible until netbeans told me
            aIOpposite = r.nextInt(8) < 1;
            hitMyPaddle = false;
        }
        
        // moves the AI paddle in the opposite direction when the random int is 0
        if (aIOpposite){
            paddle2.update(PongPanel.MAXWIDTH - ball.getX(), PongPanel.MAXHEIGHT - ball.getY());
        } else{
            paddle2.update(ball.getX(), ball.getY());
        }
    }

    /**
     *Method that checks if either player has gotten 9 points, and sets active to false if they have
     */
    private void checkWin(){
        if (myScore > 8 || otherScore > 8){
            active = false;
        }
    }

    /**
     * Method that draws the game tend screen, displaying the winner
     * @param g the graphics object that will draw the game end screen
     */
    private void paintWinner(Graphics g){
        g.setColor(Color.black);
        Font courier = new Font("TimesNewRoman", Font.BOLD, 100);

        if (myScore > 8){
            as = new AttributedString("You Win!!!");
        } else{
            as = new AttributedString("You Lose");
        }
        as.addAttribute(TextAttribute.FONT, courier);
        g.drawString(as.getIterator(), 300, 400);
    }
}
