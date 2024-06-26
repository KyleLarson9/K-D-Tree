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
	 
	private Node nextNode;
	
	public App() {	
		initializeClasses();
		 
		panel.setFocusable(true);
		panel.requestFocus();
		
		myPoint();
			
	}
	
	public void render(Graphics2D g2d) {
		pointManager.renderPoints(g2d);
		kdTree.render(g2d);

		g2d.setColor(Color.black);
		g2d.fill(new Ellipse2D.Double(x - 5, y - 5, 10, 10));
		
		PointCreator currentClosest = closestPoint(x, y, kdTree.root, closestPoint, 0);
		
		if(currentClosest != null) {
			System.out.println("Closest Point: " + currentClosest);
			
			g2d.setColor(Color.red);
			g2d.draw(new Ellipse2D.Double(currentClosest.getX() - 10, currentClosest.getY() - 10, 20, 20));		
		}
		
	
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
	
	// Things to fix: sometimes the closest point is on the other side that was eliminated
	// Make the way back up the tree to check that there are no hidden points
	// Visit a different node if there is a chance the point can be there (where it hasn't looked)]\
	
	private PointCreator closestPoint(int targetX, int targetY, Node currentNode, PointCreator closestPoint, int depth) {
		
		if(currentNode == null) {
			return closestPoint;
		}
		
		int axis = depth % 2;
		double currentClosestDistance = 1_000_000;
		Node nextNode = null;
		
		// find distance between target point and current closest point (root node) and set that as the new closest distance
		double distance = Math.sqrt(Math.pow(targetX - currentNode.point.getX(), 2) + Math.pow(targetY - currentNode.point.getY(), 2));
		
		if(distance < currentClosestDistance) {
			currentClosestDistance = distance;
			closestPoint = currentNode.point;
		}
		
		// find the next node to explore based on if the x or y value is greater than or less than, what to do if it equals?
		if(axis == 0) { // x
			
			if(targetX > currentNode.point.getX()) {
				nextNode = currentNode.right;
			} else {
				nextNode = currentNode.left;
			}
			
		} else if(axis == 1) { // y
			if(targetY > currentNode.point.getY()) {
				nextNode = currentNode.right;
			} else {
				nextNode = currentNode.left;
			}
		}
		
		// work back up the tree to make sure there aren't hidden points
		// get the final minimum distance
		// then check if the point above is within that minimum distance based on the axis?
		
		
		return closestPoint(targetX, targetY, nextNode, closestPoint, depth + 1);
		
	}

}








