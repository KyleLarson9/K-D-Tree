package tree;

import java.util.ArrayList;

import entities.PointCreator;
import entities.PointManager;
import tree.Node;
public class KdTree {
	
	private QuickSelect quickSelect;
	private PointManager pointManager;
	private ArrayList<PointCreator> points;
	
	private Node root;
	
	
	public KdTree() {
		 this.root = null;
		 quickSelect = new QuickSelect();
		 pointManager = new PointManager();
		 points = pointManager.getPoints();
		 
		 split(points, 0);
		 
		 printLevelOrder();
	}

	private void split(ArrayList<PointCreator> points, int depth) {

	    // Base case: if there are no points, return
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
	
	private int height(Node root) {
	
		if(root == null) {
			return 0;
		} else {
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);
			
			if(leftHeight > rightHeight) {
				return (leftHeight + 1);
			} else {
				return (rightHeight + 1);
			}
			
		}
	}
	
	private void printLevelOrder() {
		int h = height(root);
		for(int i = 1; i <= h; i++) {
			printCurrentLevel(root, i, i);
		}
	}
	
	private void printCurrentLevel(Node root, int level, int currentLevel) {
		if(root == null) {
			return;
		} 
		
		if(level == 1) {
			System.out.println("Level: " + currentLevel + ", " + "Point: " + root.point + " on axis: " + root.axis);
		} else if(level > 1) {
			printCurrentLevel(root.left, level - 1, currentLevel);
			printCurrentLevel(root.right, level - 1, currentLevel);
		}
	}
	
	public void display() {
		
		displayHelper(root);
		
	}
	
	private void displayHelper(Node root) {
		
		if(root != null) { // in order traversal
			System.out.println("Point: " + root.point + ", axis: " + root.axis);
			displayHelper(root.left);
			displayHelper(root.right);
		}
		
	}
	
	
}




