package tree;

import entities.PointCreator;

public class Node {
	
    public PointCreator point; 
    public int axis;
    public Node left;
    public Node right;

    public Node(PointCreator point, int axis) {
        this.point = point;
        this.axis = axis;
        this.left = null;
        this.right = null; 
    }
}