package tree;

import java.util.ArrayList;

import entities.PointCreator;
import entities.PointManager;

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
		 
		 recursiveSplit(points, 0);
	}
	
	
	private void recursiveSplit(ArrayList<PointCreator> points, int depth) {
		
		// find a way to then split the left and right arrays on y, and flip that every time until
		// there is just one point in each
		// use recursion
		
		ArrayList<PointCreator> left = new ArrayList<>();
		ArrayList<PointCreator> right = new ArrayList<>();
		
		int axis = depth % 2;
		
		if(axis == 0) { // y
			PointCreator medianPoint = quickSelect.findMedianY(points, 0, points.size() - 1, points.size()/2);
		} else if(axis == 1) { // x
			PointCreator medianPoint = quickSelect.findMedianX(points, 0, points.size() - 1, points.size()/2);
		}
		
		
		// idea: Store all median points in an array list?
	}

	
	

	
}


//private Node insertRecursive(Node root, Node node, int depth, ArrayList<PointCreator> points) {
//	
//	if(root == null) {
//		root = node;
//		return root;
//	}
//	
//	int axis = depth % 2;
//
//	// find median point
//	
//	PointCreator medianPoint = null;
//	
//	if(axis == 0) { // x
//		medianPoint = quickSelect.findMedianX(points, 0, points.size() - 1, points.size()/2);
//	} else if(axis == 1) { // y
//		medianPoint = quickSelect.findMedianY(points, 0, points.size() - 1, points.size()/2);
//
//	}
//	
//	// split the points into to lists
//	
//	ArrayList<PointCreator> leftPoints = new ArrayList<>(); // points less than median
//	ArrayList<PointCreator> rightPoints = new ArrayList<>(); // points greater than median
//	
//	if(axis == 0) {
//		leftPoints = quickSelect.pointsLessThanMedianX(leftPoints, medianPoint);
//		rightPoints = quickSelect.pointsGreaterThanMedianX(rightPoints, medianPoint);
//	} else {
//		leftPoints = quickSelect.pointsLessThanMedianY(leftPoints, medianPoint);
//		rightPoints = quickSelect.pointsGreaterThanMedianY(rightPoints, medianPoint);
//	}
//	
//	// insert  the points
//	
//	if(axis == 0) { // x axis
//		if(node.point.getX() < root.point.getX()) {
//			root.left = insertRecursive(root.left, node, depth + 1, leftPoints);
//		} else {
//			root.right = insertRecursive(root.right, node, depth + 1, rightPoints);
//		}
//	} else if(axis == 1) { // y axis
//		if(node.point.getY() < root.point.getY()) {
//			root.left = insertRecursive(root.left, node, depth + 1, leftPoints);
//		} else {
//			root.right = insertRecursive(root.right, node, depth + 1, rightPoints);
//		}
//	}
//
//	return root;
//}