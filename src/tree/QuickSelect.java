package tree;

import java.util.ArrayList;

import entities.PointCreator;
import entities.PointManager;

public class QuickSelect {
	
	private PointManager pointManager;
	
	public QuickSelect() {
		
	}
	
	public QuickSelect(PointManager pointManager) {
		this.pointManager = pointManager;
	}
	
	public ArrayList<PointCreator> filterPoints(ArrayList<PointCreator> points, PointCreator medianPoint, String coordinate, boolean lessThan) {
		
		ArrayList<PointCreator> filteredPoints = new ArrayList<>();
		
		if(points == null || medianPoint == null || coordinate == null) {
			throw new IllegalArgumentException("Cannot be null");
		}
		
		for(int i = 0; i < points.size(); i++ ) {
			
			PointCreator currentPoint = points.get(i);
			
			if(coordinate.equals("x")) {
				if((lessThan && currentPoint.getX() < medianPoint.getX()) || (!lessThan && currentPoint.getX() > medianPoint.getX())) {
					filteredPoints.add(currentPoint);
				}
			} else if(coordinate.equalsIgnoreCase("y")) {
				if((lessThan && currentPoint.getY() < medianPoint.getY()) || (!lessThan && currentPoint.getY() > medianPoint.getY())) {
					filteredPoints.add(currentPoint);
				}
			}
			
		}
		
		return filteredPoints;
		
	}
	
	public int partitionY(ArrayList<PointCreator> points, int low, int high) {
        PointCreator pivot = points.get(high);
        int pivotLocation = low;
        
        for (int i = low; i < high; i++) {
            if (points.get(i).getY() < pivot.getY()) {
                PointCreator temp = points.get(i);
                points.set(i, points.get(pivotLocation));
                points.set(pivotLocation, temp);
                pivotLocation++;
            }
        }
        
        PointCreator temp = points.get(high);
        points.set(high, points.get(pivotLocation));
        points.set(pivotLocation, temp);
        
        return pivotLocation; // Return the index of the pivot
    }
    
	public PointCreator findMedianY(ArrayList<PointCreator> points, int low, int high, int k) {
				
		int partionIndex = partitionY(points, low, high);
		
		if(partionIndex == k - 1)
			return points.get(partionIndex);
		else if(partionIndex < k - 1)
			return findMedianY(points, partionIndex + 1, high, k);
		else 
			return findMedianY(points, low, partionIndex - 1, k);
		
	}
	
	public int partitionX(ArrayList<PointCreator> points, int low, int high) {
		
		int pivot = points.get(high).getX();
		int pivotLocation = low;
		
		for(int i = low; i < high; i++) {
			if(points.get(i).getX() < pivot) {
				
				//Swap object
				PointCreator temp = points.get(i);
				
				points.set(i, points.get(pivotLocation));
				points.set(pivotLocation, temp);
				pivotLocation++;

			}
		}
		
		// Swap pivot point
		PointCreator temp = points.get(high);
		points.set(high, points.get(pivotLocation));
		points.set(pivotLocation, temp);
		
		return pivotLocation;
		
	}
	
	public PointCreator findMedianX(ArrayList<PointCreator> points, int low, int high, int k) {
		
		int partitionIndex = partitionX(points, low, high);
		
		if(partitionIndex == k - 1)
			return points.get(partitionIndex);
		else if(partitionIndex < k - 1)
			return findMedianX(points, partitionIndex + 1, high, k);
		else 
			return findMedianX(points, low, partitionIndex - 1, k);
		
	}
	
	
	
}
