package tree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entities.PointCreator;
import entities.PointManager;

public class KdTree {
	
	private QuickSelect quickSelect;
	private PointManager pointManager;
	private ArrayList<PointCreator> points;
	
	public Node root;

	public KdTree() {
		this.root = null;
		quickSelect = new QuickSelect();
		pointManager = new PointManager();
		points = pointManager.getPoints();
		
				
		split(points, 0);
		
	}
	
	private void split(ArrayList<PointCreator> points, int depth) {
		if (points.isEmpty()) {
			return;
		}
		
		// Determine the current axis (0 for x, 1 for y)
		int axis = depth % 2;
		
		// Find the median point based on the current axis
		PointCreator medianPoint = (axis == 0) 
			? quickSelect.findMedianX(points, 0, points.size() - 1, points.size() / 2)
			: quickSelect.findMedianY(points, 0, points.size() - 1, points.size() / 2);
		
		// Create a new node for the median point
		Node currentMedian = new Node(medianPoint, axis);
		insert(currentMedian);
		
		// Partition points into left and right subtrees
		ArrayList<PointCreator> left = quickSelect.filterPoints(points, medianPoint, axis, true);
		ArrayList<PointCreator> right = quickSelect.filterPoints(points, medianPoint, axis, false);
		
		// Recursively split the left and right subtrees
		split(left, depth + 1);
		split(right, depth + 1);
	} 
	
	private void insert(Node node) {
		if (root == null) {
			root = node;
			return;
		}
		insertRecursive(root, node);
	}

	private void insertRecursive(Node current, Node newNode) {
		int axis = current.axis;
		if ((axis == 0 && newNode.point.getX() < current.point.getX()) || (axis == 1 && newNode.point.getY() < current.point.getY())) {
			if (current.left == null) {
				current.left = newNode;
			} else {
				insertRecursive(current.left, newNode);
			}
		} else {
			if (current.right == null) {
				current.right = newNode;
			} else {
				insertRecursive(current.right, newNode);
			}
		}
	}
	
	public int height(Node root) {
		if (root == null) {
			return 0;
		} else {
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);
			
			if (leftHeight > rightHeight) {
				return (leftHeight + 1);
			} else {
				return (rightHeight + 1);
			}
		}
	}
	
	// ********Methods For Visual Purposes*******
	
	public void render(Graphics2D g2d) {
	    if (root != null) {
	        int width = 1000;  
	        int height = 1000; 
	        axisLines(g2d, root, 0, width, 0, height, 0);
	    }
	    
	}
	
	private void printLevelOrder() {
		int h = height(root);
		for (int i = 1; i <= h; i++) {
			printCurrentLevel(root, i, i);
		}
	}
	
	private void printCurrentLevel(Node root, int level, int currentLevel) {
		if (root == null) {
			return;
		} 
		
		if (level == 1) {
			System.out.println("Level: " + currentLevel + ", " + "Point: " + root.point + " on axis: " + root.axis);
		} else if (level > 1) {
			printCurrentLevel(root.left, level - 1, currentLevel);
			printCurrentLevel(root.right, level - 1, currentLevel);
		}
	}
	
	public void display() {
		displayHelper(root);
	}
	
	private void displayHelper(Node root) {
		if (root != null) { // in order traversal
			System.out.println("Point: " + root.point + ", axis: " + root.axis);
			displayHelper(root.left);
			displayHelper(root.right);
		}
	}
	
	private void axisLines(Graphics2D g2d, Node node, int minX, int maxX, int minY, int maxY, int axisCount) {
	    if (node == null) {
	        return;
	    } 
	    
	    int x = (int) node.point.getX();
	    int y = (int) node.point.getY();
	    int axis = node.axis;

	    // Determine the color for the axis line
	    Color axisColor;
	    if (axisCount == 0) {
	        axisColor = Color.green; // Color for the first axis line
	    } else {
	        axisColor = (axis == 0) ? Color.red : Color.blue; // Color based on axis (x or y)
	    }
	    
	    g2d.setColor(axisColor);

	    // Draw the axis line based on the axis
	    if (axis == 0) { // x-axis
	        g2d.drawLine(x, minY, x, maxY);
	    } else { // y-axis
	        g2d.drawLine(minX, y, maxX, y);
	    }

	    // Recursively draw axis lines for left and right subtrees
	    axisLines(g2d, node.left, minX, (axis == 0) ? x : maxX, minY, (axis == 1) ? y : maxY, axisCount + 1); // Adjust boundaries for left subtree
	    axisLines(g2d, node.right, (axis == 0) ? x : minX, maxX, (axis == 1) ? y : minY, maxY, axisCount + 1); // Adjust boundaries for right subtree
	}

}




