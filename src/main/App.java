package main;

import java.awt.Graphics2D;
import entities.PointManager;
import tree.KdTree;
import tree.QuickSelect;

public class App {
	
	protected AppPanel panel;
	private AppFrame frame;
	private PointManager pointManager;
	private KdTree kdTree;
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 30;
	private final static int TILES_IN_HEIGHT = 30;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int APP_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int APP_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
		
	public App() {	
		initializeClasses();
		
		panel.setFocusable(true);
		panel.requestFocus();
		
		
	}
	
	public void render(Graphics2D g2d) {
		pointManager.renderPoints(g2d);
	}
	
	// private methods
	
	private void initializeClasses() {
		pointManager = new PointManager(this);
		panel = new AppPanel(this);
		frame = new AppFrame(panel);
		kdTree = new KdTree();
	}
	
}
