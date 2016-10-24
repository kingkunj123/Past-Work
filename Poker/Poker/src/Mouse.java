import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{
	private boolean press = false;
	private int x = 0;
	private int y = 0;
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	//makes press true when mouse clicked
	@Override
	public void mousePressed(MouseEvent e) {
		press = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	//sets x and y coordinates of cursor
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
	}

	public boolean isPress() {
		return press;
	}

	public void setPress(boolean press) {
		this.press = press;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
