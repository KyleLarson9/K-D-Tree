package tree;

import java.util.ArrayList;

import entities.PointCreator;
import entities.PointManager;

public class QuickSelect {
		
	public QuickSelect() {
		 
	} 
	
	public ArrayList<PointCreator> filterPoints(ArrayList<PointCreator> points, PointCreator medianPoint, int coordinate, boolean lessThan) {
		
		ArrayList<PointCreator> filteredPoints = new ArrayList<>();
		
		if(points == null || medianPoint == null) {
			throw new IllegalArgumentException("Cannot be null");
		}
		
		for (PointCreator currentPoint : points) {
		      if (coordinate == 0) { // x-axis
		          if ((lessThan && medianPoint.getX() > currentPoint.getX()) || (!lessThan && medianPoint.getX() <= currentPoint.getX() && !currentPoint.equals(medianPoint))) {
		              filteredPoints.add(currentPoint);
		          }
		      } else { // y-axis
		          if ((lessThan && medianPoint.getY() > currentPoint.getY()) || (!lessThan && medianPoint.getY() <= currentPoint.getY() && !currentPoint.equals(medianPoint))) {
		              filteredPoints.add(currentPoint);
		          }
		      }
		  }
		
		return filteredPoints;
		
	}
	
	private int partitionY(ArrayList<PointCreator> points, int low, int high) {
		PointCreator pivot = points.get(high);
	    int pivotLocation = low;
	    for(int i = low; i < high; i++) {
	    	if(points.get(i).getY() < pivot.getY()) {
	    		PointCreator temp = points.get(i);
	    		points.set(i, points.get(pivotLocation));
	    		points.set(pivotLocation, temp);
	    		pivotLocation++;
	    	}
	    }
	    
	    points.set(high, points.get(pivotLocation));
	    points.set(pivotLocation, pivot);
	    
	    return pivotLocation;
	}
    

	
	public PointCreator findMedianY(ArrayList<PointCreator> points, int low, int high, int k) {
		if(low == high) {
			return points.get(low);
		}
		int partitionIndex = partitionY(points, low, high);
		if(partitionIndex == k) {
			return points.get(partitionIndex);
	 	} else if(partitionIndex < k) {
	      return findMedianY(points, partitionIndex + 1, high, k);
	 	} else {
	      return findMedianY(points, low, partitionIndex - 1, k);
	 	}
	}
	
	public int partitionX(ArrayList<PointCreator> points, int low, int high) {
		
		PointCreator pivot = points.get(high);
        int pivotLocation = low;
        
        for (int i = low; i < high; i++) {
            if (points.get(i).getX() < pivot.getX()) {
                PointCreator temp = points.get(i);
                points.set(i, points.get(pivotLocation));
                points.set(pivotLocation, temp);
                pivotLocation++;
            }
        }
        
        points.set(high, points.get(pivotLocation));
        points.set(pivotLocation, pivot);
        
        return pivotLocation; // Return the index of the pivot
		
	}
	
	  public PointCreator findMedianX(ArrayList<PointCreator> points, int low, int high, int k) {
		  if (low == high) {
		      return points.get(low);
		  }
		  int partitionIndex = partitionX(points, low, high);
		  if (partitionIndex == k) {
		      return points.get(partitionIndex);
		  } else if (partitionIndex < k) {
		      return findMedianX(points, partitionIndex + 1, high, k);
		  } else {
		      return findMedianX(points, low, partitionIndex - 1, k);
		  }
	}
	
	
	
}
