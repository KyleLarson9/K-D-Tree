package inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.App;

public class MouseInputs extends MouseAdapter implements MouseMotionListener, MouseListener{

	private int x, y;
	private App app;
	private boolean dragging = false;
	
	public MouseInputs(App app) {
		this.app = app;
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		if(Math.abs(App.myPointX - x) <= 5 && Math.abs(App.myPointY - y) <= 5) {
			dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(dragging) {
			x = e.getX();
			y = e.getY();
			App.myPointX = x;
			App.myPointY = y;
			app.panel.repaint();
			app.updateClosestPoint();
		}
	}
	
}
