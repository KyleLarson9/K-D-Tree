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
		 
		 splitRecursive(points, 0);
		 
		 display();
	}

	private void splitRecursive(ArrayList<PointCreator> points, int depth) {
		
		if(points.size() == 0) {
			return;
		}
		
		int axis = depth % 2;
		
		PointCreator medianPoint = new PointCreator(-1, -1);
		if(axis == 0) { //  x
			medianPoint = quickSelect.findMedianX(points, 0, points.size() - 1, points.size()/2);
		} else if(axis == 1) {
			medianPoint = quickSelect.findMedianY(points, 0, points.size() - 1, points.size()/2);
		}
		
		ArrayList<PointCreator> left = new ArrayList<>();
		ArrayList<PointCreator> right = new ArrayList<>();
		
		left = quickSelect.filterPoints(points, medianPoint, axis, true);
		right = quickSelect.filterPoints(points, medianPoint, axis, false);
				
		System.out.println("Median split on: " + medianPoint + " on axis: " + axis);
		
		points.remove(medianPoint);
		
		Node currentMedian = new Node(medianPoint, axis);
		insert(currentMedian);
		
		splitRecursive(left, depth + 1);
		splitRecursive(right, depth + 1);
	}

	public void insert(Node node) {
		
		root = insertHelper(root, node);
		
	}
	
	private Node insertHelper(Node root, Node node) {
		
		PointCreator median = node.point;
		int axis = node.axis; // 0 == x, 1 == y
		
		if(root == null) {
			root = node;
			return root;
		} else if((axis == 0 && median.getX() > root.point.getX()) || (axis == 1 && median.getY() > root.point.getY())) {
			root.left = insertHelper(root.left, node);
		} else if((axis == 0 && median.getX() < root.point.getX()) || (axis == 1 && median.getY() < root.point.getY())){
			root.right = insertHelper(root.right, node);
		}
		
		return root;
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

