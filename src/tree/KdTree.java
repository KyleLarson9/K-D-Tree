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
		 
		 for(PointCreator point : points) {
			 System.out.println(point);
		 }
		 
		 getMedian();		 
		 splitRecursive(points, 0, 1);
	}
	
	private void getMedian() {
		
		PointCreator medianPointX = quickSelect.findMedianX(points, 0, points.size() - 1, points.size()/2);
		PointCreator medianPointY = quickSelect.findMedianY(points, 0, points.size() - 1, points.size()/2);
		System.out.println("First median X: " + medianPointX);
		System.out.println("First median Y: " + medianPointY);
	}

	// odd number fuck it up
	
	private void splitRecursive(ArrayList<PointCreator> points, int depth, int count) {
		
		if(points.size() <= 1) {
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
		if(axis == 0) {
			left = quickSelect.filter(points, medianPoint, axis, true);
			right = quickSelect.filter(points, medianPoint, axis, false);
		} else if(axis == 1) {
			left = quickSelect.filter(points, medianPoint, axis, true);
			right = quickSelect.filter(points, medianPoint, axis, false);
		}
		
		System.out.println("Left Half " + count + ": " + left);
		System.out.println("Right Half " + count + ": " + right);

		splitRecursive(left, depth + 1, count + 1);
		splitRecursive(right, depth + 1, count + 1);
	}
	
	// Find the first median of the array, add that as the root
	// Break the array into two sub arrays, find those medians and make those the children
	
	

	
}
