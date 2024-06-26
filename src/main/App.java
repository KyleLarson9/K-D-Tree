package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import entities.PointCreator;
import entities.PointManager;
import inputs.MouseInputs;
import tree.KdTree;
import tree.Node;

public class App {
	
	public AppPanel panel;
	private AppFrame frame;
	private PointManager pointManager;
	private KdTree kdTree;
	private MouseInputs mouseInputs;
	private Random rand = new Random(); 
	
	private PointCreator closestPoint; 
	private PointCreator currentClosestPoint;
	
	double closestDistance;
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 30;
	private final static int TILES_IN_HEIGHT = 30;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int APP_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int APP_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public static int myPointX, myPointY;
	 	
	public App() {	
		
		myPoint();
		initializeClasses();
		
		
		panel.setFocusable(true);
		panel.requestFocus();
	}
	
	// public methods
	
	public void updateClosestPoint() {
		closestDistance = 1000000;
		currentClosestPoint = closestPoint(myPointX, myPointY, kdTree.root, closestPoint, 0);

	}
	
	public void render(Graphics2D g2d) {
		pointManager.renderPoints(g2d);

		g2d.setColor(Color.black);
		g2d.fill(new Ellipse2D.Double(myPointX - 5, myPointY - 5, 10, 10));
		
		if(currentClosestPoint != null) {
			g2d.setColor(Color.red);
			g2d.draw(new Ellipse2D.Double(currentClosestPoint.getX() - 10, currentClosestPoint.getY() - 10, 20, 20));		
		}
		
	}
	
	// private methods
	
	private void initializeClasses() {
		mouseInputs = new MouseInputs(this);
		pointManager = new PointManager(this);
		panel = new AppPanel(this);
		frame = new AppFrame(panel);
		kdTree = new KdTree();
		panel.addMouseMotionListener(mouseInputs);
		panel.addMouseListener(mouseInputs);
	}
	
	private void myPoint() {
		
		myPointX = rand.nextInt(APP_WIDTH);
		myPointY = rand.nextInt(APP_HEIGHT);
		
		PointCreator myPoint = new PointCreator(myPointX, myPointY);
	
	}

	private PointCreator closestPoint(int targetX, int targetY, Node currentNode, PointCreator closestPoint, int depth) {
		if(currentNode == null) {
			return closestPoint;
	    }

	    int axis = depth % 2;

	    // Calculate distance from the target point to the current node
	    double distance = Math.sqrt(Math.pow(targetX - currentNode.point.getX(), 2) + Math.pow(targetY - currentNode.point.getY(), 2));
	    
	    // Update closestDistance and closestPoint if a closer point is found
	    if(distance < closestDistance) {
	    	closestDistance = distance;
	    	closestPoint = currentNode.point; 
	    }

	    Node nextNode;
	    Node otherNode;

	        // Choose the next node to explore
	    if(axis == 0) { // x
	    	if(targetX > currentNode.point.getX()) {
	    		nextNode = currentNode.right;
	    		otherNode = currentNode.left;
	    	} else {
	    		nextNode = currentNode.left;
	    		otherNode = currentNode.right;
	    	}
	    } else { // y
	    	if(targetY > currentNode.point.getY()) {
	    		nextNode = currentNode.right;
	            otherNode = currentNode.left;
	    	} else {
	            nextNode = currentNode.left;
	            otherNode = currentNode.right;
	        }
	   }

	    // Recursively search the next node
	    closestPoint = closestPoint(targetX, targetY, nextNode, closestPoint, depth + 1);

	    // Check if need to explore the other side of the split
	    double axisDistance;
	    if(axis == 0) {
	    	axisDistance = Math.abs(targetX - currentNode.point.getX());
	    } else {
	        axisDistance = Math.abs(targetY - currentNode.point.getY());
	    }

	    if(axisDistance < closestDistance && otherNode != null) {
	        		     	
		    closestPoint = closestPoint(targetX, targetY, otherNode, closestPoint, depth + 1);	        		
		    
	    }

	    return closestPoint;
	}
}
