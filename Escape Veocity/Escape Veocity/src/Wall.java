import java.awt.Color;
import java.awt.geom.Rectangle2D;
public class Wall {
	private int x,y;
	private int velocityY = 0;
	private int height ,width ;
	private Color colour = Color.green;
	
	public Wall(int x, int y, int height, int width){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	// instance methods
	public Rectangle2D getBoundaryRectangle(){
		Rectangle2D wallBoundary = new Rectangle2D.Double(x,y,width,height);
		return wallBoundary;
	}

	// getters and setters
	public int getX(){
		return this.x;
	}
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return this.y;
	}
	public void setY(int y){
		this.y = y;
	}

	public int getHeight(){
		return this.height;
	}
	public void setHeight(int height){
		this.height = height;
	}

	public int getWidth(){
		return this.width;
	}
	public void setWidth(int width){
		this.width = width;
	}

	public int getVelocityY(){
		return this.velocityY;
	}
	public void setVelocityY(int velY){
		this.velocityY = velY;
	}

	public Color getColour(){
		return this.colour;
	}
	public void setColour(Color colour){
		this.colour = colour;
	}
}