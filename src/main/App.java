package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D.Double;
import java.util.Random;

import entities.PointCreator;
import entities.PointManager;
import tree.KdTree;
import tree.Node;

public class App {
	
	protected AppPanel panel;
	private AppFrame frame;
	private PointManager pointManager;
	private KdTree kdTree;
	private PointCreator myPoint;
	private PointCreator closestPoint;
	private Random rand = new Random();
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 30;
	private final static int TILES_IN_HEIGHT = 30;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int APP_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int APP_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private static int x, y;
	
	public App() {	
		initializeClasses();
		 
		panel.setFocusable(true);
		panel.requestFocus();
		
		myPoint();
		
		System.out.println(x + ", " + y);
		

		
	}
	
	public void render(Graphics2D g2d) {
		pointManager.renderPoints(g2d);
		kdTree.render(g2d);

		g2d.setColor(Color.black);
		g2d.fill(new Ellipse2D.Double(x - 5, y - 5, 10, 10));
		
		PointCreator currentClosest = getClosestPoint(x, y, kdTree.root, closestPoint, 0);
		
		System.out.println("Closest Point: " + currentClosest);
		
		g2d.setColor(Color.red);
		g2d.draw(new Ellipse2D.Double(currentClosest.getX() - 10, currentClosest.getY() - 10, 20, 20));
	}
	
	// private methods
	
	private void initializeClasses() {
		pointManager = new PointManager(this);
		panel = new AppPanel(this);
		frame = new AppFrame(panel);
		kdTree = new KdTree();
	}
	
	private void myPoint() {
		
		x = rand.nextInt(APP_WIDTH);
		y = rand.nextInt(APP_HEIGHT);
		
		myPoint = new PointCreator(x, y);
	
	}
	private PointCreator getClosestPoint(int pointX, int pointY, Node currentNode, PointCreator closestPoint, int depth) {
		
		if(currentNode == null) {
			return null;
		}
		
		int height = kdTree.height(currentNode);
		System.out.println("Height: " + height);
		
		Node closestCurrentNode = null;
		
		closestPoint = null;
		int axis = currentNode.axis; 
		
		if(axis == 0) {
			if(pointX > currentNode.point.getX()) {
				closestPoint = currentNode.right.point;
				closestCurrentNode = currentNode.right;
			} else if(pointX < currentNode.point.getX()) {
				closestPoint = currentNode.left.point;
				closestCurrentNode = currentNode.left;
			}
		} else if(axis == 1) {
			if(pointY > currentNode.point.getY()) {
				closestPoint = currentNode.right.point;
				closestCurrentNode = currentNode.right;
			} else if(pointY < currentNode.point.getY()) {
				closestPoint = currentNode.left.point;
				closestCurrentNode = currentNode.left;
			}
		}
		
		if(pointY > closestPoint.getY()) {
			closestPoint = closestCurrentNode.right.point;
		} else {
			closestPoint = closestCurrentNode.left.point;
		}
		
		// then check that current points y, and find if the pointY is greater or less than
		// I might not have to do recursion 
		
		return closestPoint;
		
	}
}








