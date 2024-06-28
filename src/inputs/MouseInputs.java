package inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.App;

public class MouseInputs extends MouseAdapter implements MouseMotionListener, MouseListener{

	private int x, y;
	private App app;
	
	public MouseInputs(App app) {
		this.app = app;
		
		System.out.println("My Point: " + App.x + ", " + App.y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		x = e.getX();
		y = e.getY();
		
		if(App.x == x && App.y == y) {
			System.out.println("test");
		}

	}
	
}
