package tree;

import entities.PointCreator;

class Node {
	
    public PointCreator point;
    public Node left;
    public Node right;

    public Node(PointCreator point) {
        this.point = point;
        this.left = null;
        this.right = null;
    }
}