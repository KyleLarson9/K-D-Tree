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
	
	// it seems to be adding points somewhat right but the splitting is still messed up
	
	public KdTree() {
		 this.root = null;
		 quickSelect = new QuickSelect();
		 pointManager = new PointManager();
		 points = pointManager.getPoints();
		 
		 for(PointCreator point : points) {
			 System.out.println(point);
		 }
		 
		 split(points, 0);
		 
		 printLevelOrder();
	}

	// It doesn't necessarly swap after every turn, each depth should be the same. I don't think this is doing that
	
	// add all the children of the left node before moving onto the right one
	
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
	    splitRecursive(left, depth + 1);
	    splitRecursive(right, depth + 1);
	}


	private void splitRecursive(ArrayList<PointCreator> points, int depth) {

		// find a way to stop the left recursion if its done and keep doing the right one

		if(points.isEmpty()) {
			return;
		}

		int axis = depth % 2;

		PointCreator medianPoint = new PointCreator(-1, -1);
		if(axis == 0) { //  x
			medianPoint = quickSelect.findMedianX(points, 0, points.size() - 1, points.size()/2);
		} else if(axis == 1) {
			medianPoint = quickSelect.findMedianY(points, 0, points.size() - 1, points.size()/2);
		}

		Node currentMedian = new Node(medianPoint, axis);
		insert(currentMedian);

		ArrayList<PointCreator> left = new ArrayList<>();
		ArrayList<PointCreator> right = new ArrayList<>();

		left = quickSelect.filterPoints(points, medianPoint, axis, true);
		right = quickSelect.filterPoints(points, medianPoint, axis, false);
				
		//System.out.println("Median split on: " + medianPoint + " on axis: " + axis);	

		if(!left.isEmpty()) {
			//System.out.println("Left list: " + left);
			splitRecursive(left, depth + 1);
		}
			
		if(!right.isEmpty()) {
			//System.out.println("Right list: " + right);
			splitRecursive(right, depth + 1);
		}

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




