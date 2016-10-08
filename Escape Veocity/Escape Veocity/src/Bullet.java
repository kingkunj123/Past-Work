import java.awt.Color;
import java.awt.geom.Rectangle2D;
public class Bullet {
	private int x,y;
	private int velocityX = 0;
	private int height ,width ;
	private Color colour = Color.red;
	public Bullet(int x,int y, int width, int height){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public Rectangle2D getBoundaryRectangle(){
		Rectangle2D bulletBoundary = new Rectangle2D.Double(x,y,width,height);
		return bulletBoundary;
	}
	
		public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getVelocityX() {
		return velocityX;
	}
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}

	public void update() {
		x+=10;
	}

}
